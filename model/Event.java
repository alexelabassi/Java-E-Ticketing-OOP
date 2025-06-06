package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    private int id;
    private String title;
    private LocalDateTime date;
    private Venue venue;

    public Event(int id, String title, LocalDateTime date, Venue venue) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.venue = venue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }
}
