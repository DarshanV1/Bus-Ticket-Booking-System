public class BookingWorker extends Thread {
    private BookingManager manager;
    private String busId;
    private int seatNo;
    private Passenger passenger;
    private boolean isDemo;


    public BookingWorker(BookingManager manager,
                     String busId,
                     int seatNo,
                     Passenger passenger,
                     boolean isDemo) {
    this.manager = manager;
    this.busId = busId;
    this.seatNo = seatNo;
    this.passenger = passenger;
    this.isDemo = isDemo;
}


    @Override
    public void run() {
        PaymentProcessor payment = new CashPayment();
        try {
            manager.bookTicket(busId, seatNo, passenger, payment, isDemo);

        } catch (SeatUnavailableException | PaymentFailedException |
                 InvalidInputException | java.io.IOException e) {
            System.err.println("Booking failed for " + passenger.getName() + ": " + e.getMessage());
        }
    }
}
