import config.SchemaInitializer;
import model.*;
import service.CustomerService;
import service.EventService;
import service.TicketService;
import service.VenueService;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        SchemaInitializer.initialize();

        try {
            // --- CUSTOMER CRUD ---
            CustomerService customerService = new CustomerService();
            System.out.println("=== CUSTOMER CRUD ===");

            // CREATE
            Customer customer = new Customer(0, "Alice", "pass123", "alice@example.com");
            customerService.create(customer);
            System.out.println("Created Customer ID: " + customer.getId());

            // READ
            Customer fetchedCustomer = customerService.read(customer.getId());
            System.out.println("Read Customer: " +
                    fetchedCustomer.getId() + ", " +
                    fetchedCustomer.getName() + ", " +
                    fetchedCustomer.getEmail());

            // UPDATE
            fetchedCustomer.setName("Alice Smith");
            fetchedCustomer.setPassword("newpass456");
            fetchedCustomer.setEmail("alice.smith@example.com");
            customerService.update(fetchedCustomer);
            Customer updatedCustomer = customerService.read(fetchedCustomer.getId());
            System.out.println("Updated Customer: " +
                    updatedCustomer.getId() + ", " +
                    updatedCustomer.getName() + ", " +
                    updatedCustomer.getEmail());

            // DELETE
            customerService.delete(updatedCustomer.getId());
            Customer deletedCustomer = customerService.read(updatedCustomer.getId());
            System.out.println("Deleted Customer returns null: " + (deletedCustomer == null));
            System.out.println();

            // --- VENUE CRUD ---
            VenueService venueService = new VenueService();
            System.out.println("=== VENUE CRUD ===");

            // CREATE
            Venue venue = new Venue(0, "Grand Hall", "Downtown", "Spacious multi-purpose venue");
            venueService.create(venue);
            System.out.println("Created Venue ID: " + venue.getId());

            // READ
            Venue fetchedVenue = venueService.read(venue.getId());
            System.out.println("Read Venue: " +
                    fetchedVenue.getId() + ", " +
                    fetchedVenue.getName() + ", " +
                    fetchedVenue.getLocation());

            // UPDATE
            fetchedVenue.setDescription("Updated description: now with new seating");
            venueService.update(fetchedVenue);
            Venue updatedVenue = venueService.read(fetchedVenue.getId());
            System.out.println("Updated Venue: " +
                    updatedVenue.getId() + ", " +
                    updatedVenue.getDescription());

            // DELETE
            venueService.delete(updatedVenue.getId());
            Venue deletedVenue = venueService.read(updatedVenue.getId());
            System.out.println("Deleted Venue returns null: " + (deletedVenue == null));
            System.out.println();

            // --- EVENT CRUD ---
            Venue eventVenue = new Venue(0, "City Theater", "Uptown", "Indoor theater with stage");
            venueService.create(eventVenue);
            System.out.println("Event–Venue ID: " + eventVenue.getId());

            EventService eventService = new EventService();
            System.out.println("=== EVENT CRUD ===");

            // CREATE
            Event event = new Event(0, "Spring Concert", LocalDateTime.now().plusDays(7), eventVenue);
            eventService.create(event);
            System.out.println("Created Event ID: " + event.getId());

            // READ
            Event fetchedEvent = eventService.read(event.getId());
            System.out.println("Read Event: " +
                    fetchedEvent.getId() + ", " +
                    fetchedEvent.getTitle() + ", Venue: " +
                    fetchedEvent.getVenue().getName());

            // UPDATE
            fetchedEvent.setTitle("Spring Concert – UPDATED");
            eventService.update(fetchedEvent);
            Event updatedEvent = eventService.read(fetchedEvent.getId());
            System.out.println("Updated Event: " +
                    updatedEvent.getId() + ", " +
                    updatedEvent.getTitle());

            // DELETE
            eventService.delete(updatedEvent.getId());
            Event deletedEvent = eventService.read(updatedEvent.getId());
            System.out.println("Deleted Event returns null: " + (deletedEvent == null));
            System.out.println();

            // --- TICKET CRUD ---
            // Preparing data needed

            Customer ticketCustomer = new Customer(0, "Bob", "securePass", "bob@example.com");
            customerService.create(ticketCustomer);
            System.out.println("Ticket Customer ID: " + ticketCustomer.getId());

            Venue ticketVenue = new Venue(0, "Open Arena", "Suburb", "Outdoor arena");
            venueService.create(ticketVenue);
            System.out.println("Ticket Venue ID: " + ticketVenue.getId());

            Event ticketEvent = new Event(0, "Summer Festival", LocalDateTime.now().plusMonths(1), ticketVenue);
            eventService.create(ticketEvent);
            System.out.println("Ticket Event ID: " + ticketEvent.getId());

            Seat dummySeat = new Seat(1, null, null, ticketVenue.getId());

            TicketService ticketService = new TicketService();
            System.out.println("=== TICKET CRUD ===");

            // CREATE
            Ticket ticket = new Ticket(0, ticketEvent, ticketCustomer, dummySeat, LocalDateTime.now());
            ticketService.create(ticket);
            System.out.println("Created Ticket ID: " + ticket.getId());

            // READ
            Ticket fetchedTicket = ticketService.read(ticket.getId());
            System.out.println("Read Ticket: ID=" + fetchedTicket.getId()
                    + ", Event=" + fetchedTicket.getEvent().getTitle()
                    + ", SeatID=" + fetchedTicket.getSeat().getId()
                    + ", Owner=" + fetchedTicket.getOwner().getName());

            // UPDATE
            fetchedTicket.setPurchaseDate(LocalDateTime.now().plusDays(1));
            ticketService.update(fetchedTicket);
            Ticket updatedTicket = ticketService.read(fetchedTicket.getId());
            System.out.println("Updated Ticket purchaseDate: " + updatedTicket.getPurchaseDate());

            // DELETE
            ticketService.delete(updatedTicket.getId());
            Ticket deletedTicket = ticketService.read(updatedTicket.getId());
            System.out.println("Deleted Ticket returns null: " + (deletedTicket == null));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

