package service;

import audit.AuditService;
import config.DatabaseConfig;
import model.Venue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueService extends GenericService<Venue> {

    public VenueService() throws SQLException {
        super();
    }

    @Override
    public void create(Venue v) throws SQLException {
        String sql = "INSERT INTO venues (name, location, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = prepare(sql, v.getName(), v.getLocation(), v.getDescription())) {
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                v.setId(keys.getInt(1));
            }
        }
        AuditService.log("createVenue");
    }

    @Override
    public Venue read(int id) throws SQLException {
        String sql = "SELECT id, name, location, description FROM venues WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Venue(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description")
                );
            }
        }
        return null;
    }

    @Override
    public List<Venue> readAll() throws SQLException {
        String sql = "SELECT id, name, location, description FROM venues";
        List<Venue> list = new ArrayList<>();
        try (PreparedStatement stmt = prepare(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Venue(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("description")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Venue v) throws SQLException {
        String sql = "UPDATE venues SET name = ?, location = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, v.getName(), v.getLocation(), v.getDescription(), v.getId())) {
            stmt.executeUpdate();
        }
        AuditService.log("updateVenue");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM venues WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            stmt.executeUpdate();
        }
        AuditService.log("deleteVenue");
    }
}
