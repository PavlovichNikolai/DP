package view;

import base.BaseView;
import contoller.AnalyzeDataController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class AnalyzeDataWindow extends JFrame implements BaseView {
    private AnalyzeDataController controller = new AnalyzeDataController();

    private JLabel queryLB = new JLabel("Запрос: ");
    private JTextArea queryTA = new JTextArea();
    private JScrollPane sp = new JScrollPane(queryTA);
    private Vector<String> columnNames = new Vector<>();
    private JButton backBtn = new JButton("Назад");
    private JButton executeBtn = new JButton("Выполнить");
    private JButton saveBtn = new JButton("Сохранить");


    private int id;
    private MenuWindow window;

    AnalyzeDataWindow(int id, MenuWindow window) {
        super("Конструктор отчетов");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.id = id;
        this.window = window;
        controller.attachView(this);
        initWindow();
    }


    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(queryLB);
        springLayout.putConstraint(SpringLayout.WEST, queryLB, 20, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, queryLB, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(queryTA);
        queryTA.setPreferredSize(new Dimension(200, 200));
        springLayout.putConstraint(SpringLayout.WEST, queryTA, 20, SpringLayout.EAST, queryLB);
        springLayout.putConstraint(SpringLayout.NORTH, queryTA, 20, SpringLayout.NORTH, getContentPane());


        getContentPane().add(executeBtn);
        executeBtn.addActionListener(e -> {
            String query = queryTA.getText().replace('\n', ' ');
            controller.executeQueries(query);
            //System.out.println(query);
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, executeBtn, 0, SpringLayout.HORIZONTAL_CENTER, queryTA);
        springLayout.putConstraint(SpringLayout.NORTH, executeBtn, 20, SpringLayout.SOUTH, queryTA);

        getContentPane().add(saveBtn);
        saveBtn.addActionListener(e -> {
            String query = queryTA.getText().replace('\n', ' ');
            controller.executeQueries(query);
            //System.out.println(query);
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveBtn, 0, SpringLayout.HORIZONTAL_CENTER, executeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 20, SpringLayout.SOUTH, executeBtn);

        getContentPane().add(backBtn);
        backBtn.addActionListener(e -> {
            this.setVisible(false);
            window.setVisible(true);
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, saveBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, saveBtn);




        setSize(450, 400);
    }

    public void setColumnNames(List<String> columns) {
        columnNames.clear();
        columnNames.addAll(columns);
    }


}
