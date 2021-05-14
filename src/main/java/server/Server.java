package server;

import com.google.gson.Gson;
import constants.Actions;
import entities.AnalysisModel;
import entities.TransactionModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private Connection db;
    private Statement statement;
    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
        System.out.println("Server initialized");
        server.listenConnections();
    }

    private void initializeServer() {
        try {
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/course_work" +
                            "?verifyServerCertificate=false" +
                            "&allowPublicKeyRetrieval=true" +
                            "&useSSL=false" +
                            "&requireSSL=false" +
                            "&useLegacyDatetimeCode=false" +
                            "&amp" +
                            "&serverTimezone=UTC",
                    "Sergei",
                    "12345");
            statement = db.createStatement();
            socket = new ServerSocket(1024);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void listenConnections() {
        System.out.println("Listening connections ... ");
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket client = socket.accept();
                new Thread(() -> {
                    System.out.println("Client accepted");
                    try {
                        OutputStream outputStream = client.getOutputStream();
                        InputStream inputStream = client.getInputStream();

                        String clientAction;
                        String queryContent;

                        boolean flag = true;

                        while (flag) {
                            byte[] msg = new byte[1024];
                            int k = inputStream.read(msg);
                            clientAction = new String(msg, 0, k);
                            clientAction = clientAction.trim();
                            msg = new byte[1024];
                            k = inputStream.read(msg);
                            queryContent = new String(msg, 0, k);
                            queryContent = queryContent.trim();

                            if (clientAction.equalsIgnoreCase(Actions.END)) {
                                flag = false;
                            } else if (clientAction.equalsIgnoreCase(Actions.LOGIN)) {
                                logInUser(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.REGISTER)) {
                                String[] arr = queryContent.split(" ");
                                String login = arr[0];
                                String password = arr[1];
                                registerUser(login, password);
                                sendDataToClient(outputStream, "REGISTERED");
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_ALL_USERS)) {
                                getAllUsers(outputStream);
                            } else if (clientAction.equalsIgnoreCase(Actions.SEND_QUERY)) {
                                executeQueries(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_TRANSACTIONS_FOR_CHART)) {
                                getTransactionsForChart(outputStream);
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getTransactionsForChart(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("select cl.cl_surname, sum(tr_amount) from transactions tr inner join clients cl on tr.cl_id = cl.cl_id group by cl.cl_surname;");
            List<TransactionModel> transactionModels = new ArrayList<>();
            while (resultSet.next()) {
                transactionModels.add(new TransactionModel(resultSet.getString("cl_surname"), resultSet.getInt("sum(tr_amount)")));
            }
            sendDataToClient(outputStream, new Gson().toJson(transactionModels));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logInUser(OutputStream outputStream, String queryContent) {
        String[] arr = queryContent.split(" ");
        String login = arr[0];
        String password = arr[1];
        int role;
        int id;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE u_login='" + login + "' AND u_password='" + password + "'");
            resultSet.last();
            if (resultSet.getRow() != 0) {
                role = resultSet.getInt("u_role");
                id = resultSet.getInt("u_id");
            } else {
                role = -1;
                id = -1;
            }
            sendDataToClient(outputStream, String.valueOf(role));
            sendDataToClient(outputStream, String.valueOf(id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllUsers(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            resultSet.last();
            int rows = resultSet.getRow();
            sendDataToClient(outputStream, String.valueOf(rows));
            resultSet.first();

            //todo prettify this part of code
            String res = resultSet.getString("u_login") + " " + resultSet.getString("u_password") + " " + resultSet.getInt("u_role");
            sendDataToClient(outputStream, res);
            while (resultSet.next()) {
                res = resultSet.getString("u_login") + " " + resultSet.getString("u_password") + " " + resultSet.getInt("u_role");
                sendDataToClient(outputStream, res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendDataToClient(OutputStream outputStream, String data) {
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerUser(String login, String password) {
        try {
            statement.executeUpdate("INSERT INTO user (login, password, role) VALUE ('" + login + "', '" + password + "', 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQueries(OutputStream outputStream, String queryContent) {
        try {
            System.out.println(queryContent);
            ResultSet resultSet = statement.executeQuery(queryContent);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            List<String> columnNames = new ArrayList<>();

            if (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    columnNames.add(rsmd.getColumnLabel(i));
                }
            }

            resultSet.first();

            List<List<String>> data = new ArrayList<>();
            List<String> subData = new ArrayList<>();
            for (String value : columnNames) {
                subData.add(resultSet.getString(value));
            }
            data.add(subData);

            while (resultSet.next()) {
                subData = new ArrayList<>();
                for (String s : columnNames) {
                    subData.add(resultSet.getString(s));
                }
                data.add(subData);
            }

            AnalysisModel analysisModel = new AnalysisModel(data, columnNames);

            sendDataToClient(outputStream, new Gson().toJson(analysisModel));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}