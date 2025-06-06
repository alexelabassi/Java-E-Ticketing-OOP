package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS tickets, events, seats, customers, venues, admins, payments, "
                    + "paypal_payments, creditcard_payments CASCADE");

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS venues (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    location VARCHAR(255) NOT NULL,
                    description TEXT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customers (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS seats (
                    id SERIAL PRIMARY KEY,
                    row_number VARCHAR(10) NOT NULL,
                    seat_number VARCHAR(10) NOT NULL,
                    venue_id INTEGER NOT NULL REFERENCES venues(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS events (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    event_date TIMESTAMP NOT NULL,
                    venue_id INTEGER NOT NULL REFERENCES venues(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS tickets (
                    id SERIAL PRIMARY KEY,
                    event_id INTEGER NOT NULL REFERENCES events(id),
                    seat_id INTEGER NOT NULL REFERENCES seats(id),
                    owner_id INTEGER NOT NULL REFERENCES customers(id),
                    purchase_date TIMESTAMP NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS admins (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS payments (
                    id SERIAL PRIMARY KEY,
                    amount DECIMAL NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS paypal_payments (
                    id INTEGER PRIMARY KEY REFERENCES payments(id),
                    paypal_account VARCHAR(255) NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS creditcard_payments (
                    id INTEGER PRIMARY KEY REFERENCES payments(id),
                    card_number VARCHAR(20) NOT NULL
                );
            """);


            stmt.execute("""
                INSERT INTO venues (name, location, description)
                VALUES ('Main Hall', 'City Center', 'A large event venue');
            """);

            stmt.execute("""
                INSERT INTO seats (row_number, seat_number, venue_id)
                VALUES ('A', '10', currval('venues_id_seq'));
            """);

            stmt.execute("""
                INSERT INTO customers (name, password, email)
                VALUES ('John Doe', 'pass123', 'john@example.com');
            """);

            stmt.execute("""
                INSERT INTO events (title, event_date, venue_id)
                VALUES ('Concert Night', NOW(), currval('venues_id_seq'));
            """);

            stmt.execute("""
                INSERT INTO tickets (event_id, seat_id, owner_id, purchase_date)
                VALUES (
                    currval('events_id_seq'),
                    currval('seats_id_seq'),
                    currval('customers_id_seq'),
                    NOW()
                );
            """);

            System.out.println("Schema and sample data initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize schema: " + e.getMessage());
        }
    }
}
