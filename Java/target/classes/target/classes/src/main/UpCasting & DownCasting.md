# 업캐스팅과 다운캐스팅

하나의 데이터 타입을 다른 타입으로 바꾸는 것을 타입 변환 혹은 형변환(캐스팅) 이라 한다

자바의 데이터형에는 크게 두가지로 나뉜다

- 기본형(primitive type) - Boolean Type(boolean) - Numeric Type(short, int, long, float, double, char)

- 참조형(reference type) - Class Type - Interface Type - Array Type - Enum Type - 그 외 다른 것들

서로 간에는 형변환이 가능한데, 기본적으로 자바는 대입 연산자로(=) 서로 양쪽의 타입이 일치하지 않으면 할당이 불가능하다

```java
long d = 10.234; // ERROR
long d = (long) 10.234;
 ```

그래서 이와 같이 (타입) 캐스팅 연산자를 사용해 강제적으로 타입을 지정해서 변수에 대입한다

또한 상속 관계에서도 형변환이 가능한데. 클래스는 reference 타입으로 _참조형 캐스팅(업캐스팅 / 다운캐스팅)_ 이라 부른다

<img src="https://media.geeksforgeeks.org/wp-content/uploads/20200505231745/Upcasting-Vs-Downcasting.png">

참조 : https://www.geeksforgeeks.org/upcasting-vs-downcasting-in-java/

<br>

**업캐스팅** : 하위 객체를 상위 객체로 타입 캐스팅 하는데(자식에서 부모로), 이는 암시적으로 수행될 수 있다(따로 표시하지 않아도 알아서 됨). 상위 클래스에 액세스 할 수 있는 유연성이 제공되지만, 모든 하위 클래스 멤버에 액세스 할 수는 없다. 일부 지정된 멤버에만 액세스 할 수 있다

**다운캐스팅** : 상위 객체를 하위 객체로 변환하는데(부모에서 자식으로), 이는 임시적으로 이루어질 수 없다

```java
// 부모 클래스
class Parent {
	String name;

	// 부모 클래스의 시그니처를 출력하는 메서드
	void method() {
        System.out.println("Method from Parent");
    }
}

// 자식 클래스
class Child extends Parent {
	int id;

	// 부모 메서드를 오버라이딩하여
	// 자식 클래스의 시그니처를 출력합니다.
	@Override void method() {
		System.out.println("Method from Child");
	}
}

// 업캐스팅 및 다운캐스팅의 차이를 확인하기 위한 데모 클래스
public class GFG {

	public static void main(String[] args)
	{
		// 업캐스팅
		Parent p = new Child();
		p.name = "GeeksforGeeks";

		// 부모 클래스의 이름을 출력합니다.
		System.out.println(p.name);
		// 부모 클래스의 메서드는 오버라이드된 메서드이기 때문에 이것이 실행됩니다.
		p.method();

		// 암묵적으로 다운캐스팅 시도
		// Child c = new Parent(); - > 컴파일 시 오류

		// 명시적으로 다운캐스팅
		Child c = (Child)p;

		c.id = 1;
		System.out.println(c.name);
		System.out.println(c.id);
		c.method();
	}
}
--------출력--------
GeeksforGeeks
Method from Child
GeeksforGeeks
1
Method from Child
```

<br>

대표적인 참조형 캐스팅으로 ArrayList 자료형을 보면

```java
List<Integer> list = new ArrayList<>();
```

이와 같이 List 로 변수 타입을 했지만 ArrayList 로 하는 이유는 ArrayList 가 List 를 부모 클래스로서 상속 받기 때문이다. 이것 또한 업캐스팅으로 볼 수 있다

but. 같은 부모 클래스를 상속받아도 형제 클래스끼리는 타입이 다르기에 참조 형변환이 불가능 하다

<br>

### 업캐스팅과 다운캐스팅을 하는 이유는??

업캐스팅 : 공통적으로 할 수 있는 부분을 만들어 간단하게 다루기 위해서(하나의 인스턴스로 묶기)

다운캐스팅 : 단순히 업케스팅의 반대 개념이 아닌, 업 캐스팅된 자식 클래스를 복구하여 본연의 필드와 기능을 회복하기 위해 사용한다. 또한 다운 캐스팅을 통해서 사용할 수 있는 객체가 증가하는데 어떠한 것이 추가되는지 알 수 없기에 ( ) 로 기재해 줌으로 알려줘야 한다


### 다운캐스팅 에러 해결 방안

`instanceof 연산자` 어느 객체 변수가 어느 클래스 타입인지를 판별해서 true/false 를 반환해준다. 주의점은
 클래스(참조형) 타입에만 사용할 수가 있다

```java
Zealot zealot = new Zealot();

if (zealot instanceof Unit) {
    System.out.println("업캐스팅 가능"); // 실행
    Unit u = zealot; // 업캐스팅
} else 
    System.out.println("업캐스팅 불가능");

// * 다운스캐팅 유무
Unit unit = new Unit();
Unit unit2 = new Zealot();

if (unit instanceof Zealot) {
    System.out.println("다운캐스팅 가능");
} else 
    System.out.println("다운캐스팅 불가능"); // 실행

if (unit2 instanceof Zealot) {
    System.out.println("다운캐스팅 가능"); // 실행
    Zealot z = (Zealot) unit2; // 다운캐스팅
} else 
    System.out.println("다운캐스팅 불가능");

```

### + 더하기

int -> Integer, Integer -> int 는 업다운 캐스팅은 아니지만, 원시 값(int)를 래퍼 클래스(Integer)로 변환하는데

```java
-128 =< Integer.valueOf(int) <= 127
```
이 사이의 값은 캐시되어서 재사용해서 사용한다. 이는 정수 캐싱, 정수 풀링이라는 최적화 기술로 매번 새 인스턴스를 생성하는 것이 아니라 한번 생성하면 그 값을 계속해서 사용한다(한번 Integer.valueOf(10) 을 하게되면 다음번에 다시 10을 부를때 그 인스턴스를 사용!)

---

### 참조

- https://www.geeksforgeeks.org/upcasting-vs-downcasting-in-java/ 업다운 캐스팅
- https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%85%EC%BA%90%EC%8A%A4%ED%8C%85-%EB%8B%A4%EC%9A%B4%EC%BA%90%EC%8A%A4%ED%8C%85-%ED%95%9C%EB%B0%A9-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0 instanceof & UpDownCasting
- https://developer.android.com/reference/java/lang/Integer#valueOf(int) Integer 캐싱값