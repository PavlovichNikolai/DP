package view;

import base.BaseView;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class TableWindow extends JFrame implements BaseView {

    private JTable table;

    @SuppressWarnings("unchecked")
    public TableWindow(List<String> headers, List<List<String>> data) {
        super("Table");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Vector<Vector<String>> vectorData = new Vector();
        data.forEach(it -> {
            vectorData.add(new Vector<>(it));
        });
        table = new JTable(vectorData, new Vector<>(headers));
        initWindow();
    }

    @Override
    public void initWindow() {
        setSize(600, 300);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(new JScrollPane(table));
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, table, 20, SpringLayout.NORTH, getContentPane());

    }

}
