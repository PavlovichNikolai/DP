package entities;

public class TransactionModel {
    private String name;
    private int sum;

    public TransactionModel(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public TransactionModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
