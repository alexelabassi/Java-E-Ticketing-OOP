package model;

public class Customer extends User {
    private String email;

    public Customer(int id, String name, String password, String email) {
        super(id, name, password);
        this.email = email;
    }
}
