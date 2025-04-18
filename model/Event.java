package model;

import java.util.Date;

public class Event {
    private int id;
    private String title;
    private Date date;
    private Venue venue;

    public Event(int id, String title, Date date, Venue venue) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.venue = venue;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Date getDate() {
        return date;
    }
    public Venue getVenue() {
        return venue;
    }
}
