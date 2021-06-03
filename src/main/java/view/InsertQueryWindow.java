package view;

import base.BaseView;
import contoller.InsertQueryController;

import javax.swing.*;

public class InsertQueryWindow extends JFrame implements BaseView {

    private InsertQueryController controller = new InsertQueryController();

    private int id;

    public InsertQueryWindow(int id) {
        this.id = id;
        controller.attachView(this);
        initWindow();
    }

    @Override
    public void initWindow() {
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));
        setSize(300, 300);

        JTextField buttonName = new JTextField("Имя кнопки");
        JTextField input1 = new JTextField("Query1");
        JTextField input2 = new JTextField("Query1");
        JTextField input3 = new JTextField("Query1");
        JTextField input4 = new JTextField("Query1");
        JTextField input5 = new JTextField("Query1");
        JTextField input6 = new JTextField("Query6");
        JButton save = new JButton("Сохранить");

        add(buttonName);
        add(input1);
        add(input2);
        add(input3);
        add(input4);
        add(input5);
        add(input6);
        add(save);

        save.addActionListener(e -> {
            if (!buttonName.getText().isEmpty() && !input1.getText().isEmpty() && !input2.getText().isEmpty() && !input3.getText().isEmpty() && !input4.getText().isEmpty() && !input5.getText().isEmpty() && !input6.getText().isEmpty()) {
                controller.saveQueries(buttonName.getText(), input1.getText(), input2.getText(), input3.getText(), input4.getText(), input5.getText(), input6.getText());
            }
        });

    }

    public void showMenu() {
        new MenuWindow(1, id).setVisible(true);
        setVisible(false);
    }
}
