# 람다

람다란? 

메서드로 전달할 수 있는 익명 함수를 단순화한 것으로. 람다 표현식에는 이름은 없지만, 파라미터 리스트, 바디, 반환 형식, 발생할 수 있는 예외 리스트는 가질 수 있다

### 람다 사용예시

```java
Comparator<Apple> byWeight = new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
};

Comparator<Apple> byWeight = (Apple a1, Apple a2)
                                (람다 파라미터)        
        -> a1.getWeight().compareTo(a2.getWeight());
        (화살표)               람다 바디
```
    람다 표현식에는 return 이 함축되어 있다
    (String s) -> s.length()
    (Apple a) -> a.getWeight() > 150

    람다 표현식은 여러 행의 문장을 포함할 수 있다
    (int x, int y) -> {
                    System.out.println("Result:");
                    System.out.println(x + y); }

    파라미터 없이도 쓸 수 있다
    () -> 42

이렇듯 다양하게 사용할 수 있는데 한마디로 `함수형 인터페이스를 람다 표현식으로 표현할 수 있다`

> 함수형 인터페이스 : 하나의 추상 메서드를 지정하는 인터페이스

함수형 인터페이스는 오직 하나의 추상 메서드를 지정하는데, 여기의 추상 메서드는 람다 표현식의 시그니처를 묘사한다(이 시그니처와 다르면 람다 표현식이 될 수 없다)

여기서 함수형 인터페이스의 추상 메서드 시그니처를 가리키는 말로 **함수 디스크립터** 라 한다

<br>

자바 8에 추가된 함수헝 인터페이스

```java
불리언 표현
Predicate<T> : T -> boolean
(List<String> list -> list.isEmpty())
Predicate<List<String>>
        
객체에서 소비
Consumer<T> : T -> void
(Apple a) -> System.out.println(a.getWeight())
Consumer<Apple>

객체에서 선택/추출
Function<T, R> : T -> R
(String s) -> s.length()
Function<String, Integer>

객체 생성
Supplier<T> : T -> R
() -> new Apple(10)
Supplier<Apple>
    
UnaryOperator<T> : T -> T

두 값 조합
BinaryOperator<T> : (T, T) -> T
(int a, int b) -> a * b
IntBinaryOperator

BiPredicate<L, R> : (T, U) -> boolean
BiConsumer<T, U> : (T, U) -> void

두 객체 비교
BiFunction<T, U, R> : (T, U) -> R
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
Comparator<Apple>, BiFunction<Apple, Apple, Integer>
```

### 그런데 컴파일러는 람다인지 아닌지 어떻게 판별 하는가?

**대상 형식**

기본적으로 콘텍스트를 통해서 람다 형식을 추론한다. 이를 **대상 형식** 이라 부른다. 만약 호환되는 추상 메서드를 가진 다른 함수형 인터페이스가 있다면 그것으로도 사용할 수 있다

    filter(inventory, (Apple a) -> a.getWeight() > 150;

1. filter 정의에 맞는가
2. 대상형식 Predicate<T> 에 맞는가
3. 추상 메서드는에 맞는가 (return boolean)
4. 인수와 반환값이 맞는가?

> 람다의 body 에 일반 표현식이 있으면 void 반환하는 함수 디스크립터와 호환된다(물론 파라미터도 호환되어야 한다)

    Predicate<String> p = s -> list.add(s); boolean 반환값 가짐
    Consumer<String> p = s -> list.add(s); add는 boolean을 반환하지만 void를 반환해 유효하다

<br>

**형식 추론**

자바 컴파일러는 람다 표현식이 사용된 콘텍스트(대상 형식)을 이용해 관련 함수형 인터페이스를 추론하니 대상형식을 보고 컴파일러는 람다의 어떤 시그니처를 사용하는지도 추론할 수 있는 것

-> 이를 통해 람다 문법에서 파라미터 형식을 생략할 수 있다

```java
Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
형식 추론하지 않음

Comparator<Apple> c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
형식 추론
```

<br>

### 메서드 참조

특정 메서드만 호출하는 람다의 축양형으로 가독성을 높일 수 있다

ex)

    (Apple apple) -> apple.getWeight()
    Apple::getWeight

    ()-> Thread.currentThread().dumpStack()
    Thread.cureentThread()::dumpStack

    (str, i) -> str.substring(i)
    String::substring

    (String s) -> System.out.println(s) 
    (String s) -> this.isValidNmae(s)
    System.out::println(this::isValidName)

