package view;

import base.BaseView;
import contoller.AnalyzeDataController;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class AnalyzeDataWindow extends JFrame implements BaseView {
    private AnalyzeDataController controller = new AnalyzeDataController();

    private Vector<String> columnNames = new Vector<>();
    private JButton backBtn = new JButton("Назад");
    private JButton executeBtn = new JButton("Выполнить");
    private JButton threeBtn = new JButton("Статистика активности участника");
    private JButton saveBtn = new JButton("Ранжирование по сумме транзакций");

    private int currentTable = 0;


    private int id;
    private MenuWindow window;

    AnalyzeDataWindow(int id, MenuWindow window, int currentTable) {
        super("Конструктор отчетов");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.id = id;
        this.currentTable = currentTable;
        this.window = window;
        controller.attachView(this);
        initWindow();
    }


    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        executeBtn.setText("Статистика активных пользователей");

        getContentPane().add(executeBtn);
        executeBtn.addActionListener(e -> {
            controller.executeQueries(1, currentTable);
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, executeBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, executeBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(saveBtn);
        saveBtn.addActionListener(e -> {
//            controller.executeQueries("SELECT u_login as T1 FROM USERS");
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveBtn, 0, SpringLayout.HORIZONTAL_CENTER, executeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 20, SpringLayout.SOUTH, executeBtn);

        getContentPane().add(threeBtn);
        threeBtn.addActionListener(e -> {
//            controller.executeQueries("SELECT u_login as T1 FROM USERS");
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, threeBtn, 0, SpringLayout.HORIZONTAL_CENTER, saveBtn);
        springLayout.putConstraint(SpringLayout.NORTH, threeBtn, 20, SpringLayout.SOUTH, saveBtn);

        getContentPane().add(backBtn);
        backBtn.addActionListener(e -> {
            this.setVisible(false);
            window.setVisible(true);
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, threeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, threeBtn);

        setSize(450, 400);
    }

    public void setColumnNames(List<String> columns) {
        columnNames.clear();
        columnNames.addAll(columns);
    }


}
