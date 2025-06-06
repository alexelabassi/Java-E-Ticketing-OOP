package model;

public class Seat {
    private int id;
    private String row;
    private String number;
    private int venueId;


    public Seat(int id, String row, String number, int venueId) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.venueId = venueId;
    }

    public int getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public String getNumber() {
        return number;
    }


}
