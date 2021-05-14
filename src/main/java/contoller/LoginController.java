package contoller;

import base.BaseController;
import constants.Actions;
import view.LoginWindow;
import view.MenuWindow;

import javax.swing.*;

public class LoginController extends BaseController<LoginWindow> {

    public void logInUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            sendDataToServer(Actions.LOGIN);
            sendDataToServer(login + " " + password);
            int role = Integer.parseInt(getDataFromServer());
            int id = Integer.parseInt(getDataFromServer());
            if (role == -1) {
                view.showRegisterDialog();
            } else if (role == 0) {
                view.showMessageDialog("Вы вошли как пользователь", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(0, id).setVisible(true);
                view.setVisible(false);
            } else if (role == 1) {
                view.showMessageDialog("Вы вошли как админ", JOptionPane.INFORMATION_MESSAGE);
                new MenuWindow(1, id).setVisible(true);
                view.setVisible(false);
            }
        } else {
            view.showMessageDialog("Заполните все поля!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registerUser(String login, String password) {
        sendDataToServer(Actions.REGISTER);
        sendDataToServer(login + " " + password);
        String result = getDataFromServer();
        if (result.equalsIgnoreCase("Регистрация")) {
            view.showMessageDialog("Успешно", JOptionPane.INFORMATION_MESSAGE);
            logInUser(login, password);
        }
    }

}
