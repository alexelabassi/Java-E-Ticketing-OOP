package service;

import audit.AuditService;
import model.Event;
import model.Venue;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventService extends GenericService<Event> {

    public EventService() throws SQLException {
        super();
    }

    @Override
    public void create(Event e) throws SQLException {
        String sql = "INSERT INTO events (title, event_date, venue_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = prepare(
                sql,
                e.getTitle(),
                Timestamp.valueOf(e.getDate()),
                e.getVenue().getId()
        )) {
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                e.setId(keys.getInt(1));
            }
        }
        AuditService.log("createEvent");
    }

    @Override
    public Event read(int id) throws SQLException {
        String sql =
                "SELECT e.id, e.title, e.event_date, " +
                        "       v.id AS vid, v.name, v.location, v.description " +
                        "FROM events e " +
                        "JOIN venues v ON e.venue_id = v.id " +
                        "WHERE e.id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("vid"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description")
                );
                LocalDateTime eventDate = rs.getTimestamp("event_date").toLocalDateTime();
                return new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        eventDate,
                        venue
                );
            }
        }
        return null;
    }

    @Override
    public List<Event> readAll() throws SQLException {
        String sql =
                "SELECT e.id, e.title, e.event_date, " +
                        "       v.id AS vid, v.name, v.location, v.description " +
                        "FROM events e " +
                        "JOIN venues v ON e.venue_id = v.id";
        List<Event> list = new ArrayList<>();
        try (PreparedStatement stmt = prepare(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("vid"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description")
                );
                LocalDateTime eventDate = rs.getTimestamp("event_date").toLocalDateTime();
                list.add(new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        eventDate,
                        venue
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Event e) throws SQLException {
        String sql = "UPDATE events SET title = ?, event_date = ?, venue_id = ? WHERE id = ?";
        try (PreparedStatement stmt = prepare(
                sql,
                e.getTitle(),
                Timestamp.valueOf(e.getDate()),
                e.getVenue().getId(),
                e.getId()
        )) {
            stmt.executeUpdate();
        }
        AuditService.log("updateEvent");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM events WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            stmt.executeUpdate();
        }
        AuditService.log("deleteEvent");
    }
}
