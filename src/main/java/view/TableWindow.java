package view;

import base.BaseView;
import com.google.gson.Gson;
import contoller.ChartsController;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class TableWindow extends JFrame implements BaseView {

    private JTable table;
    private JButton btnSave;
    private JButton btnShowCharts;
    private List<String> headers;
    private List<List<String>> data;

    @SuppressWarnings("unchecked")
    public TableWindow(List<String> headers, List<List<String>> data) {
        super("Table");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Vector<Vector<String>> vectorData = new Vector();
        data.forEach(it -> vectorData.add(new Vector<>(it)));
        this.headers = headers;
        this.data = data;
        table = new JTable(vectorData, new Vector<>(headers));
        btnSave = new JButton("Сохранить");
        btnShowCharts = new JButton("Визуализация");
        initWindow();
    }

    private void saveTable() {
        String name = "output" + System.currentTimeMillis() + ".txt";
        try {
            FileWriter writer = new FileWriter(name);
            writer.write("Column names:" + new Gson().toJson(headers) + "\n");
            writer.write("Data: " + new Gson().toJson(data));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initWindow() {
        setSize(600, 300);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(btnSave);
        springLayout.putConstraint(SpringLayout.WEST, btnSave, 0, SpringLayout.EAST, table);
        springLayout.putConstraint(SpringLayout.NORTH, btnSave, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(btnShowCharts);
        springLayout.putConstraint(SpringLayout.WEST, btnShowCharts, 0, SpringLayout.EAST, table);
        springLayout.putConstraint(SpringLayout.NORTH, btnShowCharts, 20, SpringLayout.SOUTH, btnSave);

        btnShowCharts.addActionListener(e -> new ChartsController().showCharts());

    }

}
