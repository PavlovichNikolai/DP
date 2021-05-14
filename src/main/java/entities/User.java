package entities;

public class User {
    private String login;
    private String password;
    private int role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public User(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
