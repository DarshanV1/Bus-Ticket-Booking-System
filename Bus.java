import java.util.HashSet;
import java.util.Set;

public class Bus {
    private String busId;
    private String route;
    private int capacity;
    private BusType type;
    private double pricePerSeat;
    private Set<Integer> bookedSeats;

    public Bus(String busId, String route, BusType type, int capacity, double pricePerSeat) {
        this.busId = busId;
        this.route = route;
        this.capacity = capacity;
        this.type = type;
        this.pricePerSeat = pricePerSeat;
        this.bookedSeats = new HashSet<>();
    }

    public String getBusId() {
        return busId;
    }

    public String getRoute() {
        return route;
    }

    public int getCapacity() {
        return capacity;
    }

    public BusType getType() {
        return type;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public synchronized boolean isSeatAvailable(int seatNo) {
        return !bookedSeats.contains(seatNo) && seatNo >= 1 && seatNo <= capacity;
    }

    public synchronized void bookSeat(int seatNo) throws SeatUnavailableException {
        if (!isSeatAvailable(seatNo)) {
            throw new SeatUnavailableException("Seat " + seatNo + " is not available on bus " + busId);
        }
        bookedSeats.add(seatNo);
    }

    @Override
    public String toString() {
        String typeStr;
        switch (type) {
            case NON_AC: typeStr = "Non-AC"; break;
            case AC: typeStr = "AC"; break;
            case SLEEPER: typeStr = "Sleeper Couch"; break;
            default: typeStr = type.toString();
        }
        return "Bus " + busId +
                " | Route: " + route +
                " | Type: " + typeStr +
                " | Capacity: " + capacity +
                " | Price/Seat: Rs" + pricePerSeat;
    }
}
