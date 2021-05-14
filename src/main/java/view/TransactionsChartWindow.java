package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;

public class TransactionsChartWindow extends ApplicationFrame {

    public TransactionsChartWindow(String title, CategoryDataset dataset) {
        super(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Клиенты",
                "Сумма транзакций",
                dataset,
                PlotOrientation.VERTICAL,
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
