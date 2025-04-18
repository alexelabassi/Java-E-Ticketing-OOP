package model;

public class Seat implements Comparable<Seat> {
    private int id;
    private String row;
    private int number;


    public Seat(int id, String row, int number) {
        this.id = id;
        this.row = row;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }



    @Override
    public int compareTo(Seat other) {
        if (this.row.equals(other.row)) {
            return Integer.compare(this.number, other.number);
        }
        return this.row.compareTo(other.row);
    }
}
