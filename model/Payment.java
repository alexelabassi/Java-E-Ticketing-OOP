package model;

public abstract class Payment {
    protected int id;
    protected double amount;

    public Payment(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }
    public abstract boolean process();
    public abstract boolean refund();
}
