import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPerformanceComparison {
    public static void main(String[] args) {
        int numIterations = 100;
        long startTime, endTime;

        // 가상 스레드에서 작업 실행하는 시간 측정
        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numIterations; i++) {
            virtualExecutor.submit(() -> {
                // 가상 스레드에서 실행될 작업
                doWork();
            });
        }
        virtualExecutor.shutdown();
        while (!virtualExecutor.isTerminated()) {}
        endTime = System.currentTimeMillis();
        long virtualTime = endTime - startTime;
        System.out.println("Virtual Thread Time: " + virtualTime + " ms");

        // 플랫폼 스레드에서 작업 실행하는 시간 측정
        ExecutorService platformExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numIterations; i++) {
            platformExecutor.submit(() -> {
                // 플랫폼 스레드에서 실행될 작업
                doWork();
            });
        }
        platformExecutor.shutdown();
        while (!platformExecutor.isTerminated()) {}
        endTime = System.currentTimeMillis();
        long platformTime = endTime - startTime;
        System.out.println("Platform Thread Time: " + platformTime + " ms");

        // 성능 비교
        if (virtualTime > platformTime) {
            System.out.println("Platform Thread is faster.");
        } else if (virtualTime < platformTime) {
            System.out.println("Virtual Thread is faster.");
        } else {
            System.out.println("Both have similar performance.");
        }
    }

    // 간단한 작업을 수행하는 메서드
    private static void doWork() {
        // 여기에 실제로 수행할 작업을 넣어주세요.
        // 이 예시에서는 단순히 루프를 돌며 시간을 소비하는 작업을 수행합니다.
        for (int i = 0; i < 100000; i++) {
            Math.random();
        }
    }
}

