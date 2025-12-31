public interface PaymentProcessor {
    boolean processPayment(double amount) throws PaymentFailedException;
}
