# 인터페이스(Interface)

동일한 목적 하에 동일한 기능을 수행하게끔 강제하는 것(자바의 다형성을 극대화해 개발 코드의 수정을 줄이고, 유지보수성을 높이기 위해 인터페이스 사용)

```java
// 상수
public interface 인터페이스명 {
    // 타입 상수명 = 값;
    // 인터페이스에서 값 정해주니 바꾸지 말고 제공해주는 값만 사용할 것(절대적)
}

// 추상 메소드
// 가이드만 줄테니 추상메소드를 오버라이딩해서 재구현(강제적 implements 안하면 사용안해도 됨)
타입 메소드명(매개변수, ... );

// 디폴트 메소드
default 타입 메소드명(매개변수, ... ){
    // 구현부
    // 인터페이스에서 기본적으로 제공해주지만, 마음에 안들시 각자 구현해서 사용(선택적)
}

//정적 메소드
static 타입 메소드명(매개변수) {
    // 구현부
    // 인터페이스에서 제공하는 것으로 무조건 사용(절대적)
}
```

<br>

**다 클래스를 통해서 할수 있는일 아닌가?**

클래스를 이용해서 다중 상속할 경우 메서드 출처의 모호성이 생길 수 있음(자바에서는 클래스를 통한 다중 상속은 지원하지 않음)

-> 이를 인터페이스를 통해서 다중 상속을 지원함

모든 필드는 public static final 이어야 하며, 모든 메소드는 public abstract 이어야 함. 이 부분은 모든 인터페이스에 공통으로 적용되는 부분이므로 이 제어자는 생략할 수 있습니다


<br>

### 인터페이스 관련 패턴

#### 팩토리 메서드 패턴

부모 클래스에서 객체들을 생성할 수 있는 인터페이스를 제공하지만, 자식 클래스들이 생성될 객체들의 유형을 변경할 수 있도록 하는 생성 패턴

<img src="https://refactoring.guru/images/patterns/diagrams/factory-method/example.png?id=67db9a5cb817913444efcb1c067c9835">

<br>

#### 추상 팩토리 패턴

구체적 클래스 지정하지 않고 관련, 또는 종속 객체의 패밀리를 생성하기 위한 인터페이스를 제공. 정확한 클래스 유형을 지정하지 않고도 관련 객체를 생성가능

<img src="https://refactoring.guru/images/patterns/diagrams/abstract-factory/example.png">


```java
// 추상 제품 인터페이스
interface Button {
    void click(); // 클릭 동작 정의
}

interface TextField {
    void type(); // 입력 동작 정의
}

// 추상 팩토리 인터페이스
interface GUIFactory {
    Button createButton(); // 버튼 생성 메서드
    TextField createTextField(); // 텍스트 필드 생성 메서드
}

// Windows용 구체 제품 구현체
class WindowsButton implements Button {
    @Override
    public void click() {
        System.out.println("Windows 버튼 클릭됨.");
    }
}

class WindowsTextField implements TextField {
    @Override
    public void type() {
        System.out.println("Windows 텍스트 필드 입력 중.");
    }
}

// Windows용 구체 팩토리 구현체
class WindowsUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
               
    @Override                  
    public TextField createTextField() {
        return new WindowsTextField();
    }
}

// 클라이언트 코드
public class AbstractFactoryClient {
    public static void main(String[] args) {
        // Windows UI 생성
        GUIFactory windowsFactory = new WindowsUIFactory();
        Button windowsButton = windowsFactory.createButton();
        TextField windowsTextField = windowsFactory.createTextField();

        windowsButton.click();       // 출력: Windows 버튼 클릭됨.
        windowsTextField.type();     // 출력: Windows 텍스트
```


#### 관찰자 패턴

한 개체의 상태가 변경되면 해당 개체의 모든 종속 개체에 알림이 전달되고 자동으로 업데이트되도록 개체 간의 일대다 종속성을 정의

#### 어댑터 패턴

클래스의 인터페이스를 클라이언트가 기대하는 다른 인터페이스로 변환, 호환되지 않는 인터페이스가 함께 작동할 수 있도록 해줌

#### 데코레이터 패턴

객체에 추가 책임을 동적으로 첨부, 기능 확장을 위해 서브클래싱에 대한 유연한 대안을 제공

<br>

간단하면 interface 에서는 오히려 쓸 필요 없음.

구현체 분리, 결제관련(카드 결제, 상품권 결제, 체크카드 -> 결제 취소, 각각을 개발자가 개발자 취소라는 형태로 만들어서 interface로 상속)

정의만 놔둔 상태로 각자가 개발하니 효율성이 높아짐. 인터페이스 없이(추상화) 디자인 패턴이 구현되는 경우가 많지 않다. new로 객체를 생성하는

각각의 장단점을 어느정도 알고 설계를 하는 것이, (참고하는데 있어서도, 코드 한줄한줄의 이유가 있어야 하고, 다른 디자인 패턴에 비해서 이 패턴을 사용하는 이유), 고민의 근거?(선택의 이유)

설계를 연습한다면 (어느정도 정해놓고 하기, 나중에 틀리는 경우가 많지만서도 시도해보면서 장점, -> 바꿨을때 왜 바꿧는지가 남는다) 

초기 -> 정해놓고 

