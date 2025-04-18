import model.*;
import service.TicketingService;

public class Main {
    public static void main(String[] args) {
        TicketingService service = new TicketingService();
        // simulare utilizator
        Customer alice = new Customer(1, "alice", "123", "alice@example.com");
        // listare evenimente
        System.out.println("Evenimente:");
        for (Event e : service.listEvents()) {
            System.out.println(" - " + e.getTitle());
        }
        Ticket ticket = service.purchaseTicket(alice,1, 101);
        System.out.println("Bilet achizitionat: " + ticket);

        Payment pay = new CreditCardPayment(1, 50.0, "1234-5678-9012-3456");
        service.processPayment(ticket.getId(), pay);

        System.out.println("Bilete Alice:");
        service.listUserTickets(alice.getId()).forEach(System.out::println);
    }
}
