public class CashPayment implements PaymentProcessor {

    @Override
    public boolean processPayment(double amount) throws PaymentFailedException {
        if (amount <= 0) {
            throw new PaymentFailedException("Amount must be greater than zero.");
        }
        System.out.println("Payment of Rs" + amount + " received in cash.");
        return true;
    }
}
