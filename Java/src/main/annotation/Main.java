package src.main.annotation;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);

        // 클래스의 메서드중 getAnnotations() 를 통해 조회 하려 하지만 주석과 같은 취급을 받아 조회 되지 않음
    }

//    @MyAnnotation(123)
    static
    class Book {
        int value;
        String name;
    }
}