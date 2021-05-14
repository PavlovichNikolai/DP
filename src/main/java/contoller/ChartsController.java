package contoller;

import base.BaseController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Actions;
import entities.TransactionModel;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import view.TransactionsChartWindow;

import java.util.ArrayList;
import java.util.List;

public class ChartsController extends BaseController {

    public void showCharts() {
        showDistanceFromCenterBarChartWindow();
    }

    private void showDistanceFromCenterBarChartWindow() {
        new TransactionsChartWindow("Transactions", getDistanceFromCenterBarChartData()).setVisible(true);
    }

    private CategoryDataset getDistanceFromCenterBarChartData() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        sendDataToServer(Actions.GET_TRANSACTIONS_FOR_CHART);
        sendDataToServer(" ");
        String transactionsStr = getDataFromServer();

        List<TransactionModel> transactions = new Gson().fromJson(transactionsStr, new TypeToken<ArrayList<TransactionModel>>() {
        }.getType());

        transactions.forEach(it -> dataset.addValue(it.getSum(), "Transactions", it.getName()));
        return dataset;
    }

}
