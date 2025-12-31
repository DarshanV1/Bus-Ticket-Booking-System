import java.util.Scanner;

// Custom exception for invalid dimensions
class InvalidDimensionException extends Exception {
    public InvalidDimensionException(String message) {
        super(message);
    }
}

// Abstract class Shape
abstract class Shape {
    abstract double area();
    abstract double perimeter();
}

// Circle subclass
class Circle extends Shape {
    double radius;

    Circle(double radius) throws InvalidDimensionException {
        if (radius <= 0)
            throw new InvalidDimensionException("Radius must be positive.");
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}

// Rectangle subclass
class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) throws InvalidDimensionException {
        if (length <= 0 || width <= 0)
            throw new InvalidDimensionException("Length and width must be positive.");
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }

    @Override
    double perimeter() {
        return 2 * (length + width);
    }
}

// Triangle subclass
class Triangle extends Shape {
    double a, b, c;

    Triangle(double a, double b, double c) throws InvalidDimensionException {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new InvalidDimensionException("Triangle sides must be positive.");
        if (a + b <= c || b + c <= a || a + c <= b)
            throw new InvalidDimensionException("Invalid triangle sides.");
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double area() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c)); // Heron's formula
    }

    @Override
    double perimeter() {
        return a + b + c;
    }
}

public class ShapeDesigner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Shape:");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        Shape shape = null;

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter radius: ");
                    shape = new Circle(sc.nextDouble());
                    break;

                case 2:
                    System.out.print("Enter length: ");
                    double l = sc.nextDouble();
                    System.out.print("Enter width: ");
                    double w = sc.nextDouble();
                    shape = new Rectangle(l, w);
                    break;

                case 3:
                    System.out.print("Enter side a: ");
                    double a = sc.nextDouble();
                    System.out.print("Enter side b: ");
                    double b = sc.nextDouble();
                    System.out.print("Enter side c: ");
                    double c = sc.nextDouble();
                    shape = new Triangle(a, b, c);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // Dynamic method dispatch
            System.out.println("\n--- Results ---");
            System.out.printf("Area: %.2f\n", shape.area());
            System.out.printf("Perimeter: %.2f\n", shape.perimeter());

        } catch (InvalidDimensionException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }

        sc.close();
    }
}
