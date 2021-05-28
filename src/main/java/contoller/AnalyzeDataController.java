package contoller;

import base.BaseController;
import com.google.gson.Gson;
import constants.Actions;
import entities.AnalysisModel;
import view.AnalyzeDataWindow;
import view.TableWindow;

public class AnalyzeDataController extends BaseController<AnalyzeDataWindow> {

    public void executeQueries(int actionNumber, int currentTable) {
        sendDataToServer(Actions.SEND_QUERY);
        sendDataToServer(actionNumber + " " + currentTable);
        String responseStr = getDataFromServer();
        AnalysisModel model = new Gson().fromJson(responseStr, AnalysisModel.class);
        new TableWindow(model.getHeaders(), model.getData()).setVisible(true);
    }
}
