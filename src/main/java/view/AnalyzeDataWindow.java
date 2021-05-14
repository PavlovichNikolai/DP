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
    private JButton threeBtn = new JButton("Три");
    private JButton saveBtn = new JButton("Сохранить");

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

        if (currentTable == 0) {
            executeBtn.setText("Запрос1_1");
        } else if (currentTable == 1) {
            executeBtn.setText("Запрос2_1");
        }

        getContentPane().add(executeBtn);
        executeBtn.addActionListener(e -> {
            if (currentTable == 0) {
                controller.executeQueries("SELECT u_login as T1 FROM USERS");
            } else if (currentTable == 1) {
                controller.executeQueries("SELECT * from USERS");
            }
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, executeBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, executeBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(saveBtn);
        saveBtn.addActionListener(e -> {
            controller.executeQueries("SELECT u_login as T1 FROM USERS");
        });

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveBtn, 0, SpringLayout.HORIZONTAL_CENTER, executeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 20, SpringLayout.SOUTH, executeBtn);

        getContentPane().add(threeBtn);
        threeBtn.addActionListener(e -> {
            controller.executeQueries("SELECT u_login as T1 FROM USERS");
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
