package service;

import audit.AuditService;
import model.Customer;
import model.Event;
import model.Seat;
import model.Ticket;
import model.Venue;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketService extends GenericService<Ticket> {

    public TicketService() throws SQLException {
        super();
    }

    @Override
    public void create(Ticket t) throws SQLException {
        String sql = "INSERT INTO tickets (event_id, seat_id, owner_id, purchase_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = prepare(
                sql,
                t.getEvent().getId(),
                t.getSeat().getId(),
                t.getOwner().getId(),
                Timestamp.valueOf(t.getPurchaseDate())
        )) {
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                t.setId(keys.getInt(1));
            }
        }
        AuditService.log("createTicket");
    }

    @Override
    public Ticket read(int id) throws SQLException {
        String sql = "SELECT t.id AS tid, t.purchase_date, "
                + "e.id AS eid, e.title, e.event_date, "
                + "v.id AS vid, v.name AS v_name, v.location AS v_loc, v.description AS v_desc, "
                + "s.id AS sid, s.row_number, s.seat_number, "
                + "c.id AS cid, c.name AS c_name, c.password AS c_pass, c.email AS c_email "
                + "FROM tickets t "
                + "JOIN events e ON t.event_id = e.id "
                + "JOIN venues v ON e.venue_id = v.id "
                + "JOIN seats s ON t.seat_id = s.id "
                + "JOIN customers c ON t.owner_id = c.id "
                + "WHERE t.id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("vid"),
                        rs.getString("v_name"),
                        rs.getString("v_loc"),
                        rs.getString("v_desc")
                );

                Event event = new Event(
                        rs.getInt("eid"),
                        rs.getString("title"),
                        rs.getTimestamp("event_date").toLocalDateTime(),
                        venue
                );

                Seat seat = new Seat(
                        rs.getInt("sid"),
                        rs.getString("row_number"),
                        rs.getString("seat_number"),
                        venue.getId()
                );

                Customer owner = new Customer(
                        rs.getInt("cid"),
                        rs.getString("c_name"),
                        rs.getString("c_pass"),
                        rs.getString("c_email")
                );

                LocalDateTime purchaseDate = rs.getTimestamp("purchase_date").toLocalDateTime();

                return new Ticket(rs.getInt("tid"), event, owner, seat, purchaseDate);
            }
        }
        return null;
    }


    @Override
    public List<Ticket> readAll() throws SQLException {
        String sql = "SELECT t.id AS tid, t.purchase_date, "
                + "e.id AS eid, e.title, e.event_date, v.id AS vid, v.name AS v_name, v.location AS v_loc, v.description AS v_desc, "
                + "s.id AS sid, s.row_number, s.seat_number, "
                + "c.id AS cid, c.name AS c_name, c.password AS c_pass, c.email AS c_email "
                + "FROM tickets t "
                + "JOIN events e ON t.event_id = e.id "
                + "JOIN venues v ON e.venue_id = v.id "
                + "JOIN seats s ON t.seat_id = s.id "
                + "JOIN customers c ON t.owner_id = c.id";
        List<Ticket> list = new ArrayList<>();
        try (PreparedStatement stmt = prepare(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("vid"),
                        rs.getString("v_name"),
                        rs.getString("v_loc"),
                        rs.getString("v_desc")
                );
                Event event = new Event(
                        rs.getInt("eid"),
                        rs.getString("title"),
                        rs.getTimestamp("event_date").toLocalDateTime(),
                        venue
                );
                Seat seat = new Seat(
                        rs.getInt("sid"),
                        rs.getString("row_number"),
                        rs.getString("seat_number"),
                        venue.getId()
                );
                Customer owner = new Customer(
                        rs.getInt("cid"),
                        rs.getString("c_name"),
                        rs.getString("c_pass"),
                        rs.getString("c_email")
                );
                LocalDateTime purchaseDate = rs.getTimestamp("purchase_date").toLocalDateTime();
                list.add(new Ticket(rs.getInt("tid"), event, owner, seat, purchaseDate));
            }
        }
        return list;
    }

    @Override
    public void update(Ticket t) throws SQLException {
        String sql = "UPDATE tickets SET event_id = ?, seat_id = ?, owner_id = ?, purchase_date = ? WHERE id = ?";
        try (PreparedStatement stmt = prepare(
                sql,
                t.getEvent().getId(),
                t.getSeat().getId(),
                t.getOwner().getId(),
                Timestamp.valueOf(t.getPurchaseDate()),
                t.getId()
        )) {
            stmt.executeUpdate();
        }
        AuditService.log("updateTicket");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            stmt.executeUpdate();
        }
        AuditService.log("deleteTicket");
    }
}
