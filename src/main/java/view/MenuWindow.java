package view;

import base.BaseView;
import contoller.ChartsController;

import javax.swing.*;

public class MenuWindow extends JFrame implements BaseView {
    private JButton analyzeBtn = new JButton("Анализ");
    private JButton showCharts = new JButton("Графики");
    private JButton showAllUsersBtn = new JButton("Показать всех пользователей");
    private JButton fourthButton = new JButton("Четыре");

    private int role;
    private int id;

    public MenuWindow(int role, int id) {
        super("Меню");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.id = id;
        this.role = role;
        initWindow();
    }

    @Override
    public void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(analyzeBtn);
        analyzeBtn.addActionListener(e -> {
            new AnalyzeDataWindow(id, this).setVisible(true);
            this.setVisible(false);
        });
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, analyzeBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, analyzeBtn, 20, SpringLayout.NORTH, getContentPane());

        getContentPane().add(showCharts);
        showCharts.addActionListener(e -> new ChartsController().showCharts());
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showCharts, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showCharts, 20, SpringLayout.SOUTH, analyzeBtn);

        getContentPane().add(showAllUsersBtn);
        showAllUsersBtn.setVisible(role == 1);
        showAllUsersBtn.addActionListener(e -> new ShowUsersWindow(id).setVisible(true));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showAllUsersBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, showAllUsersBtn, 20, SpringLayout.SOUTH, showCharts);

        getContentPane().add(fourthButton);
//        fourthButton.setVisible(role == 1);
//        fourthButton.addActionListener(e -> new ShowUsersWindow(id).setVisible(true));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fourthButton, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, fourthButton, 20, SpringLayout.SOUTH, showAllUsersBtn);

        setSize(300, 300);

    }
}
