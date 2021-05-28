package entities;

public class MenuButtonModel {

    private int id;
    private String name;

    public MenuButtonModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
