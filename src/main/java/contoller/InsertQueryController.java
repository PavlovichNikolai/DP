package contoller;

import base.BaseController;
import constants.Actions;
import view.InsertQueryWindow;

public class InsertQueryController extends BaseController<InsertQueryWindow> {

    public void saveQueries(String buttonName, String query1, String query2, String query3, String query4, String query5, String query6) {
        sendDataToServer(Actions.INSERT_BUTTON);
        sendDataToServer(buttonName + "--" + query1 + "--" + query2 + "--" + query3 + "--" + query4 + "--" + query5 + "--" + query6);
        getDataFromServer();
        view.showMenu();
    }
}
