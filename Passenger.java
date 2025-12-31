public class Passenger extends Person {
    private String email;

    public Passenger(String name, String phone, String email) {
        super(name, phone);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " [" + email + ", " + phone + "]";
    }
}
