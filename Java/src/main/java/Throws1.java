public class Throws1 {

        public static void main(String[] args) {
            try {
                method1();
                method2();
                method3();
            } catch (Exception e) {
                System.out.println("An exception occurred: " + e.getMessage());
            }
        }

        public static void method1() {
            try {
                System.out.println("Method 1 is executing.");
                throw new Exception("Exception in Method 1");
            } catch (Exception e) {
                System.out.println("Caught an exception in Method 1: " + e.getMessage());
            }
        }

        public static void method2() {
            try {
                System.out.println("Method 2 is executing.");
                throw new Exception("Exception in Method 2");
            } catch (Exception e) {
                System.out.println("Caught an exception in Method 2: " + e.getMessage());
            }
        }

        public static void method3() {
            try {
                System.out.println("Method 3 is executing.");
                throw new Exception("Exception in Method 3");
            } catch (Exception e) {
                System.out.println("Caught an exception in Method 3: " + e.getMessage());
            }
        }
}
