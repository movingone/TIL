import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContextSwitchingComparison {
    public static void main(String[] args) {
        int numIterations = 1000;
        long startTime, endTime;

        // Reactive 방식의 컨텍스트 스위칭 성능 측정
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numIterations; i++) {
            ReactivePoller.poll();
        }
        endTime = System.currentTimeMillis();
        long reactiveTime = endTime - startTime;
        System.out.println("Reactive Polling Time: " + reactiveTime + " ms");

        // Virtual Thread를 사용한 방식의 컨텍스트 스위칭 성능 측정
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numIterations; i++) {
            VirtualThreadPoller.poll(executor);
        }
        endTime = System.currentTimeMillis();
        long virtualThreadTime = endTime - startTime;
        System.out.println("Virtual Thread Polling Time: " + virtualThreadTime + " ms");

        // 성능 비교
        double improvement = ((double) reactiveTime - virtualThreadTime) / reactiveTime * 100;
        System.out.println("Performance Improvement: " + improvement + "%");

        executor.shutdown();
    }
}

class ReactivePoller {
    public static void poll() {
        // 기존 Reactive 방식의 Net.poll() 코드
        // 이곳에는 컨텍스트 스위칭을 시뮬레이션하는 코드를 넣어주세요.
        // 예를 들어, sleep() 메서드를 호출하여 일정 시간동안 대기하는 방식으로 시뮬레이션할 수 있습니다.
        try {
            Thread.sleep(1); // 시뮬레이션을 위해 1ms 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class VirtualThreadPoller {
    public static void poll(ExecutorService executor) {
        // Virtual Thread를 사용한 Poller.poll() 코드
        // 이곳에는 가상 스레드를 사용하여 컨텍스트 스위칭을 시뮬레이션하는 코드를 넣어주세요.
        // 예를 들어, executor의 submit() 메서드를 사용하여 작업을 가상 스레드로 실행할 수 있습니다.
        executor.submit(() -> {
            try {
                Thread.sleep(1); // 시뮬레이션을 위해 1ms 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
