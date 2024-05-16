public class generic {

    public static void main(String[] args) {
        Student student = new Student();
        saltClass<Student> c = new saltClass<Student>();
    }
    static class saltClass <E extends Comparable<? super E>>{ }
    static class Person { }
    static class Student extends Person implements Comparable<Person>{
        @Override
        public int compareTo(Person e) {
            return 0;
        }
    }
    interface Comparable<Person>{
        public int compareTo(Person e);
    }
}
