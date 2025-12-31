import java.util.List;
import java.util.Scanner;

public class BusTicketApp {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Scanner scanner = new Scanner(System.in);

        // Sample buses with fixed prices and types
        manager.addBus(new Bus("BUS101", "Bengaluru → Mysuru", BusType.NON_AC, 40, 200.0));
        manager.addBus(new Bus("BUS202", "Bengaluru → Mysuru", BusType.AC, 40, 260.0));
        manager.addBus(new Bus("BUS303", "Bengaluru → Mysuru", BusType.SLEEPER, 30, 320.0));

        manager.addBus(new Bus("BUS404", "Bengaluru → Chennai", BusType.NON_AC, 45, 350.0));
        manager.addBus(new Bus("BUS505", "Bengaluru → Chennai", BusType.AC, 45, 420.0));

        int choice = -1;

        while (choice != 5) {
            System.out.println("\n=== BUS TICKET COLLECTION SYSTEM ===");
            System.out.println("1. View All Buses");
            System.out.println("2. Book Ticket");
            System.out.println("3. View All Tickets");
            System.out.println("4. Show Total Amount & Multithreading Demo");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Buses:");
                    for (Bus b : manager.getAllBuses()) {
                        System.out.println(" - " + b);
                    }
                    break;

                case 2:
                    handleSingleBooking(manager, scanner);
                    break;

                case 3:
                    handleViewTickets(manager);
                    break;

                case 4:
                    handleTotalAndConcurrentDemo(manager);
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void handleSingleBooking(BookingManager manager, Scanner scanner) {

    Passenger passenger = null;

    // 🔁 KEEP ASKING for valid passenger details
    while (passenger == null) {
        try {
            System.out.println("\nEnter Passenger Details:");

            System.out.print("Name: ");
            String name = scanner.nextLine();
            InputValidator.validateName(name);

            System.out.print("Phone (10 digits): ");
            String phone = scanner.nextLine();
            InputValidator.validatePhone(phone);

            System.out.print("Email: ");
            String email = scanner.nextLine();
            InputValidator.validateEmail(email);

            passenger = new Passenger(name, phone, email);
        } catch (InvalidInputException e) {
            System.out.println(" Invalid input: " + e.getMessage());
            System.out.println(" Please re-enter passenger details.");
        }
    }

    System.out.println("\nAvailable Buses:");
    for (Bus b : manager.getAllBuses()) {
        System.out.println(" - " + b);
    }

    System.out.print("\nEnter Bus ID: ");
    String busId = scanner.nextLine();

    PaymentProcessor payment = new CashPayment();

    boolean booked = false;

    // 🔁 KEEP ASKING for seat until success
    while (!booked) {
        try {
            System.out.print("Enter Seat Number: ");
            int seatNo = Integer.parseInt(scanner.nextLine());

            Ticket ticket = manager.bookTicket(busId, seatNo, passenger, payment, false);


            System.out.println("Ticket booked successfully!");
            System.out.println(ticket);
            booked = true;

        } catch (SeatUnavailableException e) {
            System.out.println("❌ " + e.getMessage());
            System.out.println(" Please choose another seat.");
        } catch (NumberFormatException e) {
            System.out.println(" Seat number must be numeric.");
        } catch (InvalidInputException | PaymentFailedException | java.io.IOException e) {
            System.out.println("Booking error: " + e.getMessage());
        }
    }
}



    private static void handleViewTickets(BookingManager manager) {
        System.out.println("\nAll Tickets:");
        List<Ticket> tickets = manager.getAllTickets();
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
        } else {
            for (Ticket t : tickets) {
                System.out.println(t);
            }
        }
    }

    private static void handleTotalAndConcurrentDemo(BookingManager manager) {
        System.out.println("\nTotal Amount Collected So Far: Rs" + manager.getTotalAmountCollected());

        System.out.println("\n--- Multithreading Demo: Concurrent Booking on Same Seat ---");

        Passenger p1 = new Passenger("Alice", "9999999991", "alice@example.com");
        Passenger p2 = new Passenger("Bob", "9999999992", "bob@example.com");

        // Two threads trying to book the same seat on BUS101
        BookingWorker worker1 = new BookingWorker(manager, "BUS101", 5, p1, true);


        BookingWorker worker2 = new BookingWorker(manager, "BUS101", 5, p2, true);
        worker1.start();
        worker2.start();

        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("After concurrent booking attempt, total amount: Rs" +
                manager.getTotalAmountCollected());
    }
}
