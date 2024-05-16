# 오버 로딩과 오버 라이딩

오버 로딩(Overloading) : 메서드의 이름은 같고 매개변수의 유형과 개수가 다르도록 하는 것

오버 라이딩(Overriding) : 상위 클래스가 가지고 있는 메서드를 하위 클래스가 재정의 해서 사용하는 것

오버 라이딩

```java
class Animal {
    void makeSound() {
        System.out.println("Some generic sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Woof");
    }
}

public class Main1 {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.makeSound(); // Output: Some generic sound

        Dog dog = new Dog();
        dog.makeSound(); // Output: Woof
    }
}

```


오버로딩 특징

매개변수의 유형에 따라 오버 로딩이 되었다 라고 하는데, 그에 따라 반환값은 어떻게 달라지는가

```java
public class Example {
    public int method(int a) {
        return a;
    }

    public double method(double a) {
        return a;
    }
}

class Superclass {
    final void display(int num) {
        System.out.println("Display in Superclass with int: " + num);
    }
}

class Subclass extends Superclass {
    // This method is overloading, not overriding
    void display(double num) {
        System.out.println("Display in Subclass with double: " + num);
    }
}

public class Main1 {
    public static void main(String[] args) {
        Subclass obj = new Subclass();
        obj.display(10);  // Calls display(int) method of Superclass
        obj.display(3.14);  // Calls display(double) method of Subclass
    }
}
```
두 메서드 모두 반환 형식이 다르더라도 매개 변수 형식이 다르기 때문에 오버로드된 것으로 간주, 반환 유형의 차이가 아니라 매개변수 목록의 차이가 메서드 오버로드를 가능하게 한다

호출될 메서드를 컴파일 단계에서 결정한다


### + 정적 메서드 숨기기

하위 클래스가 슈퍼클래스의 정적 메서드와 동일한 시그니처를 사용하여 정적 메서드를 정의하는 경우
```java
class Superclass {
    static void display() {
        System.out.println("Display in Superclass.");
    }
}

class Subclass extends Superclass {
    // This is not overriding but hiding
    static void display() {
        System.out.println("Display in Subclass.");
    }
}

public class TestHiding {
    public static void main(String[] args) {
        Superclass.display();  // Calls Superclass's display
        Subclass.display();    // Calls Subclass's display
    }
}
```

위 클래스가 슈퍼클래스의 정적 메서드와 동일한 시그니처를 사용하여 정적 메서드를 정의하는 경우, 참조 유형에 따라 컴파일 타임에 해결