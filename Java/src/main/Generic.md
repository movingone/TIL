# 제네릭

제네릭이란? 클래스 내부에서 지정하는 것이 아닌 외부에서 사용자에 의해 지정되는 것으로 특정타입을 미리 지정하지 않고 필요에 의해 지정할수 있도록 하는 일반 타입이다

보편적으로 많이쓰는 타입들

    <T> type
    <E> Element
    <K> Key
    <V> Value
    <N> Number

설명과 반드시 일치 할 필요는 없다. 암묵적인 규칙이라 봐도된다

주의할 점은 타입 파라미터로 명시할 수 있는 건 참조 타입만 올 수 있다

```java
class ClassName<E> {
    private E element;	// 제네릭 타입 변수
    void set(E element) {	// 제네릭 파라미터 메소드
        this.element = element;
    }
    
    E get() {	// 제네릭 타입 반환 메소드
        return element;
    }
}
 
class Main1 { 
    public static void main(String[] args) {
        ClassName<String> a = new ClassName<String>();
        ClassName<Integer> b = new ClassName<Integer>();
        
        a.set("10");
        b.set(10);
	
        System.out.println("a data : " + a.get());
        // 반환된 변수의 타입 출력 
        System.out.println("a E Type : " + a.get().getClass().getName());
		
        System.out.println();
        System.out.println("b data : " + b.get());
        // 반환된 변수의 타입 출력 
        System.out.println("b E Type : " + b.get().getClass().getName());
    }
}

---
출력값

a data : 10
a E Type : java.lang.String

b data : 10
b E Type : java.lang.Integer
```

### 제네릭 메소드

메서드 한정 제네릭도 사용할 수 있다

```java
public <T> T genericMethod(T o) {
    
    [접근 제어자] <제네릭타입> [반환타입] [메소드명]([제네릭타입] [파라미터])
}
```

이러한 메서드가 필요한 이유는 정적 메서드로 선언할 때 필요로 한다

정적 메서드의 매개변수가 특정 클래스 타입이라면 이미 메모리에 올라간 정적 메서드는 어디서 타입을 얻어올 수 있는가?

객체가 생성되기전 접근 할 수 있으나 유형을 지정할 방법이 없어 에러가 날 것이다

```java
class ClassName<E> {
    static E genericMethod(E o) {
        return o;
    }
}

Main1 {
    ClassNmae.getnerMethod(3);
    // 에러 날 것
}
```

### 와일드 카드 ?

쉽게 말해 **알 수 없는 타입**으로 <?> 이와 같이 쓰인다

    <K extends T>	// T와 T의 자손 타입만 가능 (K는 들어오는 타입으로 지정 됨)
    <K super T>	// T와 T의 부모(조상) 타입만 가능 (K는 들어오는 타입으로 지정 됨)

    <? extends T>	// T와 T의 자손 타입만 가능
    <? super T>	// T와 T의 부모(조상) 타입만 가능
    <?>		// 모든 타입 가능. <? extends Object>랑 같은 의미

차이점은 경계를 지정함으로서 K는 특정 타입으로 지정되지만, ? 는 타입이 지정되지 않는다

    <K extends Number> Number와 이를 상속하는 Integer, Double 
    등의 타입으로 지정되고 변환될 수 있지만

    <? extends T> 는 타입이 지정될 수 있지만, 객체 혹은 메서드 호출 할 때
    타입 참조는 할 수 없다

<br>

제네릭 타입에 대한 객체 비교
```java
public class ClassName <E extends Comparable<? super E>> { ... }

E 객체는 반드시 Comparable 을 구현해야 한다?

public class SaltClass <E extends Comparable<E>> { ... }	// Error가능성 있음
public class SaltClass <E extends Comparable<? super E>> { ... }	// 안전성이 높음
```

<?> 은 <? extends Object> 와 마찬가지로 최상위 클래스와 마찬가지로 사용된다(어느 타입이든 와도 상관없다)

보통 데이터가 아닌 '기능' 의 사용에 관심이 있을때만 사용한다


---

**참조**
- https://st-lab.tistory.com/153