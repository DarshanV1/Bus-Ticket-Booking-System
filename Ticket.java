import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static int nextId = 1;

    private int ticketId;
    private Passenger passenger;
    private Bus bus;
    private int seatNo;
    private double price;
    private LocalDateTime timestamp;

    public Ticket(Passenger passenger, Bus bus, int seatNo, double price) {
        this.ticketId = nextId++;
        this.passenger = passenger;
        this.bus = bus;
        this.seatNo = seatNo;
        this.price = price;
        this.timestamp = LocalDateTime.now();
    }

    public int getTicketId() {
        return ticketId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Bus getBus() {
        return bus;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(fmt);
    }

    @Override
    public String toString() {
        return "Ticket #" + ticketId +
                " | " + passenger.getName() +
                " | Bus: " + bus.getBusId() +
                " | Seat: " + seatNo +
                " | Rs" + price +
                " | " + getFormattedTime();
    }

    public String toFileString() {
        return ticketId + "," +
                passenger.getName() + "," +
                passenger.getPhone() + "," +
                passenger.getEmail() + "," +
                bus.getBusId() + "," +
                bus.getRoute() + "," +
                bus.getType() + "," +
                seatNo + "," +
                price + "," +
                getFormattedTime();
    }
}
