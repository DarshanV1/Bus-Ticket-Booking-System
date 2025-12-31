import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingManager {
    private Map<String, Bus> buses = new HashMap<>();
    private List<Ticket> tickets = new ArrayList<>();
    private double totalAmountCollected = 0.0;

    public void addBus(Bus bus) {
        buses.put(bus.getBusId(), bus);
    }

    public Bus getBus(String busId) {
        return buses.get(busId);
    }

    public Collection<Bus> getAllBuses() {
        return buses.values();
    }

    public List<Ticket> getAllTickets() {
        return tickets;
    }

    public double getTotalAmountCollected() {
        return totalAmountCollected;
    }

    // Booking method now uses fixed price from Bus
    public synchronized Ticket bookTicket(String busId,
                                      int seatNo,
                                      Passenger passenger,
                                      PaymentProcessor paymentProcessor,
                                      boolean isDemoMode)

            throws SeatUnavailableException, PaymentFailedException,
                   InvalidInputException, IOException {

        if (busId == null || passenger == null) {
            throw new InvalidInputException("Bus or passenger is null.");
        }

        Bus bus = buses.get(busId);
        if (bus == null) {
            throw new InvalidInputException("Bus with ID " + busId + " does not exist.");
        }

        if (seatNo < 1 || seatNo > bus.getCapacity()) {
            throw new InvalidInputException("Seat number is out of range for this bus.");
        }

        double price = bus.getPricePerSeat();

        // Process payment
       // First: try to book seat
        bus.bookSeat(seatNo);

// Then: process payment ONLY if seat booking succeeds
        paymentProcessor.processPayment(price);


        Ticket ticket = new Ticket(passenger, bus, seatNo, price);
        tickets.add(ticket);
        totalAmountCollected += price;

        // Save ticket to file
        if (!isDemoMode) {
            TicketPrinter.printTicketToFile(ticket);
}


        System.out.println("Ticket booked successfully: " + ticket);
        return ticket;
    }
}
