package model;

import java.util.Date;

public class Ticket implements Comparable<Ticket> {
    private int id;
    private Event event;
    private Seat seat;
    private Customer owner;
    private Date purchaseDate;

    public Ticket(int id, Event event, Customer owner, Seat seat, Date purchaseDate) {
        this.id = id;
        this.event = event;
        this.owner = owner;
        this.seat = seat;
        this.purchaseDate = purchaseDate;
    }
    public int getId() {
        return id;
    }
    public Event getEvent() {
        return event;
    }
    public Customer getOwner() {
        return owner;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    @Override
    public int compareTo(Ticket other) {
        return this.purchaseDate.compareTo(other.purchaseDate);
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event.getTitle() +
                ", owner=" + owner.getName() +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
