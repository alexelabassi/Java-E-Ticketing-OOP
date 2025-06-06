package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Ticket implements Comparable<Ticket> {
    private int id;
    private Event event;
    private Seat seat;
    private Customer owner;
    private LocalDateTime purchaseDate;

    public Ticket(int id, Event event, Customer owner, Seat seat, LocalDateTime purchaseDate) {
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
    public LocalDateTime getPurchaseDate() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Seat getSeat() {
        return seat;
    }
}
