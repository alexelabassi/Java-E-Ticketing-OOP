package model;

public class PaypalPayment extends Payment {
    private String paypalAccount;

    public PaypalPayment(int id, double amount, String paypalAccount) {
        super(id, amount);
        this.paypalAccount = paypalAccount;
    }
    @Override
    public boolean process() {
        System.out.println("Processing PayPal payment of " + amount + " with account " + paypalAccount);
        return true;
    }
    @Override
    public boolean refund() {
        System.out.println("Refunding PayPal payment of " + amount + " with account " + paypalAccount);
        return true;
    }
}
