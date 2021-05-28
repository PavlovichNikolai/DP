package view;

import base.BaseView;
import contoller.MenuController;
import entities.MenuButtonModel;

import javax.swing.*;
import java.util.List;

public class MenuWindow extends JFrame implements BaseView {

    private int role;
    private int id;

    public MenuWindow(int role, int id) {
        super("Области анализа");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.id = id;
        this.role = role;
        initWindow();
        MenuController controller = new MenuController();
        controller.attachView(this);
        controller.getButtons();
    }

    @Override
    public void initWindow() {
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));
        setSize(300, 300);
    }

    public void showButtons(List<MenuButtonModel> buttons) {
        buttons.forEach(it -> {
            JButton button = new JButton(it.getName());
            button.addActionListener(actionEvent -> new AnalyzeDataWindow(id, this, it.getId()).setVisible(true));
            add(button);
        });
    }
}
