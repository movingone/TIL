# 예외처리(Exception)

에러에는 컴파일 에러, 런타임 에러, 논리적 에러가 있다

자바 프로그래밍에서는 실행 시 발생할 수 있는 오류에 에러(error)와 예외(exception) 이 있다

에러는 메모리 부족(OutOfMemoryError)나 스택오버플로우(StackOverflowError)와 같은 일어나면 복구할 수 없는 심각한 오류인 반면 예외는 어느정도 대응 코드를 작성해서 개발자가 대처할 수 있는 수준이 가능하다

_JVM 은 프로그램을 실행하는 도중에 예외가 발생하면 해당 예외 클래스로 객체를 생성하고 예외 처리 코드에서 예외 객체를 이용할 수 있게 해준다_


<br>

자바의 오류 클래스 계층 구조

<img src="https://www3.ntu.edu.sg/home/ehchua/programming/java/images/Exception_Classes.png">

[출처 : https://www3.ntu.edu.sg/home/ehchua/programming/java/J5a_ExceptionAssert.html]

여기서 Throws2 클래스는 외부적 요인으로 대처할 수 없기에 Exception 클래스를 본다

> Throwable 클래스는 자바의 최상위 클래스 Object 를 상속받으며 이는 오류나 예외에 대한 메시지를 담는다. getMessage(), printStackTrace()

Exception 에서는 한번더 분기하는데 런타임에러와 컴파일에러로 나뉜다. 보통 컴파일은 사용자의 실수와 같은 외적 요인에 의해 발생하며 런타임은 프로그래머의 실수로 발생한다

    compile : FileNotFound, ClassNotFound, DataFormat
    runtime : IndexOutOf, NullPointer, ClassCast, Arithmetic

> 개발자라면 각 예외 메시지에 따라서 빠르게 오류를 해결할 수 있어야 할 것이다

<br>

### Checked Exception, Unchecked Exception

그런데 여기서 한번더 안나뉘는듯 나뉜다. 왜 이렇게 말하냐면 런타임과 컴파일 구분은 같지만 `코드적 관점에서` 예외 처리 동작을 필수 지정하느냐에 따라서 나뉘기에 이렇게 표현했다

    Checked Exception(컴파일 단계) : 반드시 예외 처리해야 함
    Unchecked Exception(런타임 단계) : 명시적인 처리 하지 않아도 됨

Checked Exception 은 반드시 처리를 해줘야지 프로그램이 실행하기에 처리해줘야 하고, Unchecked Exception 은 프로그램이 실행되며 터지거나 해서 따로 처리하지 않아도 된다

만약 이러한 Checked Exception 를 매번 해주기 귀찮다면 Unchecked Exception 로 바꿔주면 된다

`throw new RuntimeException(new IOException("컴파일 에러지만 런타임중에 발생합니다"))`

이렇게 런타임 예외안에 컴파일 예외를 감싸면 런타임 예외가 된다

<br>

### Try - Catch, Finally

```java
try {
        int result = divide(10, 0);
        System.out.println("Result: " + result); // This line will not be reached if an exception occurs
    } catch (ArithmeticException e)
        System.out.println("An arithmetic exception occurred: " + e.getMessage());
```

이렇게 에러 상황에서 회피하는 식으로 예외 처리를 할 수 있는데, 모든 경우 이런식으로 하기에는 복잡해진다. 그래서 `ArithmeticException e` 과 같이 특정 예외 클래스에 대한 것을 못아서 처리할 수 있다

여기 까지는 에러 처리 였지만 예외가 발생해도 실행이 되는 **finally** 문을 더해 반드시 실행해야 하는 문을 추가할 수 있다

이 예외 클래스들 안에는 메서드의 정보와 예외 메시지를 전달할수 있는 메서드가 있는데

printStackTrace() : 예외발생 당시의 호출스택(Call Stack)에 있었던 메서드의 정보와 예외 메시지를 화면에 출력할수 있다

getMessage() : 발생한 예외클래스의 인스턴스에 저장된 메시지를 얻을 수 있다

<br>

### Throw, Throws

자바에서는 throw 키워드를 통해 강제 예외를 발생시킬 수 있다

new throw (예외 클래스("메시지")) 와 같은 형식으로 사용하게 된다

throws 는 예외 떠넘기기로 예외를 다른 곳에서 처리할 수 있도록 넘길 수 있다. 메서드 선언부 끝에 작성되어 예외를 호출한 곳으로 넘긴다

> 예외 발생 키워드는 throw, 예외 메서드 선언 키워드는 throws


---

**출처**

- https://www3.ntu.edu.sg/home/ehchua/programming/java/J5a_ExceptionAssert.html 자바 오류 계층구조
- https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%90%EB%9F%ACError-%EC%99%80-%EC%98%88%EC%99%B8-%ED%81%B4%EB%9E%98%EC%8A%A4Exception-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC 에러와 예외
- https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%ACException-%EB%AC%B8%EB%B2%95-%EC%9D%91%EC%9A%A9-%EC%A0%95%EB%A6%AC 예외 문법
- https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%98%88%EC%99%B8-%EB%8D%98%EC%A7%80%EA%B8%B0throw-%EC%98%88%EC%99%B8-%EC%97%B0%EA%B2%B0Chained-Exception 예외 던지기