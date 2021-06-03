package server;

import com.google.gson.Gson;
import constants.Actions;
import entities.AnalysisModel;
import entities.MenuButtonModel;
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
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_VISUALIZATION_DATA)) {
                                getVisualizationForChart(outputStream, queryContent);
                            } else if (clientAction.equalsIgnoreCase(Actions.GET_MENU_BUTTONS)) {
                                getMenuButtons(outputStream);
                            } else if (clientAction.equalsIgnoreCase(Actions.INSERT_BUTTON)) {
                                insertButton(outputStream, queryContent);
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

    private void insertButton(OutputStream outputStream, String queryContent) {
        String[] arr = queryContent.split("--");
        String name = arr[0];
        String query1 = arr[1];
        String query2 = arr[2];
        String query3 = arr[3];
        String query4 = arr[4];
        String query5 = arr[5];
        String query6 = arr[6];

        try {
            statement.executeUpdate("insert into screen_analyze(name) values('" + name + "');");
            ResultSet resultSet = statement.executeQuery("select max(id) from screen_analyze;");
            resultSet.first();
            int index = resultSet.getInt("max(id)");
            statement.executeUpdate("insert into query(id_but, id_act, analyze_text, visual_text) values(" + index + ", 1, '" + query1 + "', '" + query2 + "');");
            statement.executeUpdate("insert into query(id_but, id_act, analyze_text, visual_text) values(" + index + ", 2, '" + query3 + "', '" + query4 + "');");
            statement.executeUpdate("insert into query(id_but, id_act, analyze_text, visual_text) values(" + index + ", 3, '" + query5 + "', '" + query6 + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sendDataToClient(outputStream, "added");
    }

    private void getMenuButtons(OutputStream outputStream) {
        try {
            ResultSet resultSet = statement.executeQuery("select * from screen_analyze;");
            List<MenuButtonModel> menuButtonModelList = new ArrayList<>();
            while (resultSet.next()) {
                menuButtonModelList.add(new MenuButtonModel(resultSet.getInt("id"), resultSet.getString("name")));
            }
            sendDataToClient(outputStream, new Gson().toJson(menuButtonModelList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getVisualizationForChart(OutputStream outputStream, String queryContent) {
        try {
            int actionId = Integer.parseInt(queryContent.split(" ")[0]);
            int buttonId = Integer.parseInt(queryContent.split(" ")[1]);
            System.out.println(queryContent);

            ResultSet resultSet = statement.executeQuery("select visual_text from query where id_but=" + buttonId + " AND id_act=" + actionId);
            resultSet.first();
            resultSet = statement.executeQuery(resultSet.getString("visual_text"));
            ResultSetMetaData rsmd = resultSet.getMetaData();
            String keyColumn = rsmd.getColumnLabel(1);
            String valueColumn = rsmd.getColumnLabel(2);

            List<TransactionModel> transactionModels = new ArrayList<>();
            while (resultSet.next()) {
                transactionModels.add(new TransactionModel(resultSet.getString(keyColumn), resultSet.getInt(valueColumn)));
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
            int actionId = Integer.parseInt(queryContent.split(" ")[0]);
            int buttonId = Integer.parseInt(queryContent.split(" ")[1]);
            System.out.println(queryContent);

            ResultSet resultSet = statement.executeQuery("select analyze_text from query where id_but=" + buttonId + " AND id_act=" + actionId);
            resultSet.first();
            resultSet = statement.executeQuery(resultSet.getString("analyze_text"));
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