package contoller;

import base.BaseController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Actions;
import entities.MenuButtonModel;
import view.MenuWindow;

import java.util.ArrayList;
import java.util.List;

public class MenuController extends BaseController<MenuWindow> {

    public void getButtons() {
        sendDataToServer(Actions.GET_MENU_BUTTONS);
        // посылаю 2 раза, потому что на пустой строке почему-то валится
        sendDataToServer(Actions.GET_MENU_BUTTONS);
        String buttonsJson = getDataFromServer();
        List<MenuButtonModel> buttons = new Gson().fromJson(buttonsJson, new TypeToken<ArrayList<MenuButtonModel>>() {
        }.getType());
        view.showButtons(buttons);
    }

}
