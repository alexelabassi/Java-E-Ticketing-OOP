package service;

import model.*;

import java.util.*;


public class TicketingService {
    private List<Event> events = new ArrayList<>();
    private Map<Integer, Ticket> tickets = new HashMap<>();
    private SortedSet<Seat> allSeats = new TreeSet<>();
    private Map<Integer, Set<Seat>> availableSeatsPerEvent = new HashMap<>();

    private int nextTicketId = 1;

    public TicketingService() {
        Venue v1 = new Venue(1, "Sala Mare", "Str. Victoriei 10");
        Venue v2 = new Venue(2, "Teatrul Mic", "Bd. Libertății 5");

        Calendar cal = Calendar.getInstance();

        cal.set(2025, Calendar.MAY, 20, 19, 0);
        events.add(new Event(1, "Concert Rock", cal.getTime(), v1));

        cal.set(2025, Calendar.JUNE, 5, 20, 30);
        events.add(new Event(2, "Teatru Comedie", cal.getTime(), v2));

        cal.set(2025, Calendar.JULY, 1, 18, 0);
        events.add(new Event(3, "Spectacol Dans", cal.getTime(), v1));

        int seatId = 1;
        for (char row = 'A'; row <= 'C'; row++) {
            for (int num = 1; num <= 10; num++) {
                allSeats.add(new Seat(seatId++, String.valueOf(row), num));
            }
        }

        for (Event e : events) {
            // clone the master set so each event tracks availability separately
            Set<Seat> seatsForThisEvent = new TreeSet<>(allSeats);
            availableSeatsPerEvent.put(e.getId(), seatsForThisEvent);
        }
    }

    public List<Event> listEvents() {
        return Collections.unmodifiableList(events);
    }

    public List<Event> searchEvents(String keyword) {
        List<Event> res = new ArrayList<>();
        for (Event e : events) {
            if (e.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                res.add(e);
            }
        }
        return res;
    }

    public Set<Seat> listAvailableSeats(int eventId) {
        return new TreeSet<>(allSeats);
    }

    public Ticket purchaseTicket(Customer user, int eventId, int seatId) {
        Event ev = events.stream().filter(e -> e.getId() == eventId).findFirst().orElse(null);
        Seat seat = allSeats.stream().filter(s -> s.getId() == seatId).findFirst().orElse(null);
        Ticket t = new Ticket(nextTicketId++, ev, user, seat, new Date());
        tickets.put(t.getId(), t);
        return t;
    }

    public boolean cancelTicket(int ticketId) {
        return tickets.remove(ticketId) != null;
    }

    public List<Ticket> listUserTickets(int userId) {
        List<Ticket> res = new ArrayList<>();
        for (Ticket t : tickets.values()) {
            if (t.getOwner().getId() == userId) res.add(t);
        }
        return res;
    }

    public boolean processPayment(int ticketId, Payment payment) {
        if (!tickets.containsKey(ticketId)) return false;
        return payment.process();
    }

    public boolean refundPayment(int ticketId, Payment payment) {
        return payment.refund();
    }

    public String generateSalesReport(Date from, Date to) {
        long count = tickets.values().stream()
                .filter(t -> !t.getPurchaseDate().before(from) && !t.getPurchaseDate().after(to))
                .count();
        return "Bilete vandute intre " + from + " si " + to + ": " + count;
    }
}
