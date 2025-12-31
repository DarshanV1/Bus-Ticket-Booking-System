public class InputValidator {

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        if (!name.matches("[A-Za-z ]{3,50}")) {
            throw new InvalidInputException("Name should contain only letters and spaces (min 3 chars).");
        }
    }

    public static void validatePhone(String phone) throws InvalidInputException {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new InvalidInputException("Phone must be exactly 10 digits.");
        }
    }

    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email cannot be empty.");
        }
        // Very simple email validation
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            throw new InvalidInputException("Email format is invalid.");
        }
    }
}
