package model;

public class CreditCardPayment extends Payment {
    private String cardNumber;

    public CreditCardPayment(int id, double amount, String cardNumber) {
        super(id, amount);
        this.cardNumber = cardNumber;
    }
    @Override
    public boolean process() {
        System.out.println("Processing credit card payment of " + amount + " with card number " + cardNumber);
        return true;
    }
    @Override
    public boolean refund() {
        System.out.println("Refunding credit card payment of " + amount + " with card number " + cardNumber);
        return true;
    }
}
