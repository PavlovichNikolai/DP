package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.PieDataset;

public class TransactionsChartWindow extends ApplicationFrame {

    public TransactionsChartWindow(String title, PieDataset dataset) {
        super(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        JFreeChart barChart = ChartFactory.createPieChart(
                "Клиенты",
                dataset,
                true,
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        setContentPane(chartPanel);
        this.pack();
    }

}
