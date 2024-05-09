public class Throws2 {
    public static void main(String[] args) {
        try {
            method1();
            method2();
            method3();
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }

    public static void method1() throws Exception {
        System.out.println("Method 1 is executing.");
        throw new Exception("Exception in Method 1");
    }

    public static void method2() throws Exception {
        System.out.println("Method 2 is executing.");
        throw new Exception("Exception in Method 2");
    }

    public static void method3() throws Exception {
        System.out.println("Method 3 is executing.");
        throw new Exception("Exception in Method 3");
    }
}
