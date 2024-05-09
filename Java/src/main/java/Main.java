class Superclass {
    // Final method in the superclass
    final void display() {
        System.out.println("Display in Superclass.");
    }
}

class Subclass extends Superclass {
    // This method cannot override the final method in the superclass
    // Attempting to override will result in a compilation error
    // Uncommenting the @Override annotation will cause a compilation error
    // @Override

}

public class Main {
    public static void main(String[] args) {
        // Creating an instance of the subclass
        Subclass obj = new Subclass();
        // Calling the display method of the subclass
        obj.display(); // This will call the display method of the superclass
    }
}