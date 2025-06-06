package service;

import audit.AuditService;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService extends GenericService<Customer> {

    public CustomerService() throws SQLException {
        super();
    }

    @Override
    public void create(Customer c) throws SQLException {
        String sql = "INSERT INTO customers (name, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = prepare(sql, c.getName(), c.getPassword(), c.getEmail())) {
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                c.setId(keys.getInt(1));
            }
        }
        AuditService.log("createCustomer");
    }

    @Override
    public Customer read(int id) throws SQLException {
        String sql = "SELECT id, name, password, email FROM customers WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        }
        return null;
    }

    @Override
    public List<Customer> readAll() throws SQLException {
        String sql = "SELECT id, name, password, email FROM customers";
        List<Customer> list = new ArrayList<>();
        try (PreparedStatement stmt = prepare(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Customer c) throws SQLException {
        String sql = "UPDATE customers SET name = ?, password = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, c.getName(), c.getPassword(), c.getEmail(), c.getId())) {
            stmt.executeUpdate();
        }
        AuditService.log("updateCustomer");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = prepare(sql, id)) {
            stmt.executeUpdate();
        }
        AuditService.log("deleteCustomer");
    }
}
