import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TicketPrinter {
    private static final String FILE_NAME = "tickets.txt";

    public static void printTicketToFile(Ticket ticket) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(ticket.toFileString());
        }
    }
}
