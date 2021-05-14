package contoller;

import base.BaseController;
import com.google.gson.Gson;
import constants.Actions;
import entities.AnalysisModel;
import view.AnalyzeDataWindow;
import view.TableWindow;

import java.io.FileWriter;
import java.io.IOException;

public class AnalyzeDataController extends BaseController<AnalyzeDataWindow> {
    public void executeQueries(String query) {
        sendDataToServer(Actions.SEND_QUERY);
        sendDataToServer(String.valueOf(query));

        String responseStr = getDataFromServer();
        AnalysisModel model = new Gson().fromJson(responseStr, AnalysisModel.class);
        new TableWindow(model.getHeaders(), model.getData()).setVisible(true);
    }
}
