package contoller;

import base.BaseController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Actions;
import entities.TransactionModel;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import view.TransactionsChartWindow;

import java.util.ArrayList;
import java.util.List;

public class ChartsController extends BaseController {

    public void showCharts(int actionNumber, int currentTable) {
        new TransactionsChartWindow("Визуализация", getDistanceFromCenterBarChartData(actionNumber, currentTable)).setVisible(true);
    }

    private PieDataset getDistanceFromCenterBarChartData(int actionNumber, int currentTable) {
        final DefaultPieDataset dataset = new DefaultPieDataset();

        sendDataToServer(Actions.GET_VISUALIZATION_DATA);
        sendDataToServer(actionNumber + " " + currentTable);
        String transactionsStr = getDataFromServer();

        List<TransactionModel> transactions = new Gson().fromJson(transactionsStr, new TypeToken<ArrayList<TransactionModel>>() {
        }.getType());

        transactions.forEach(it -> dataset.setValue(it.getName(), it.getSum()));
        return dataset;
    }

}
