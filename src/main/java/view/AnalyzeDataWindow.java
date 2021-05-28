package view;

import base.BaseView;
import contoller.AnalyzeDataController;

import javax.swing.*;

public class AnalyzeDataWindow extends JFrame implements BaseView {
    private AnalyzeDataController controller = new AnalyzeDataController();

    private JButton backBtn = new JButton("Назад");
    private JButton executeBtn = new JButton("Выполнить");
    private JButton threeBtn = new JButton("Статистика активности участника");
    private JButton saveBtn = new JButton("Ранжирование по сумме транзакций");

    private int currentTable;

    private MenuWindow window;

    AnalyzeDataWindow(MenuWindow window, int currentTable) {
        super("Конструктор отчетов");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
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
        executeBtn.addActionListener(e -> controller.executeQueries(1, currentTable));

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, executeBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, executeBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(saveBtn);
        saveBtn.addActionListener(e -> controller.executeQueries(2, currentTable));

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveBtn, 0, SpringLayout.HORIZONTAL_CENTER, executeBtn);
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 20, SpringLayout.SOUTH, executeBtn);

        getContentPane().add(threeBtn);
        threeBtn.addActionListener(e -> controller.executeQueries(3, currentTable));
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


}
