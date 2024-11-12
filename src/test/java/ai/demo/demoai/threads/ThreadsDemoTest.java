package ai.demo.demoai.threads;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThreadsDemoTest {

    private static final Logger log = LoggerFactory.getLogger(ThreadsDemoTest.class);

    @Test
    @Order(1)
    void runTestForPlatformThreadsWithAHundredThreadsInThePool() {
        ThreadsDemo.platformThreadsDemoWithExecutorService(1_00, 1_00);
    }

    @Test
    @Order(2)
    void runTestForPlatformThreadsWithAThousandThreadsInThePool() {
        ThreadsDemo.platformThreadsDemoWithExecutorService(1_000, 1_000);
    }

    @Test
    @Order(3)
    void runTestForPlatformThreadsWithFourThousandThreadsInThePool() {
        ThreadsDemo.platformThreadsDemoWithExecutorService(4_000, 4_000);
    }


    @Test
    @Order(4)
    void runTestForPlatformThreadsWithFiveThousandThreadsInThePoolAndExpectError() {
        var thrown = assertThrows(OutOfMemoryError.class, () -> {
            ThreadsDemo.platformThreadsDemoWithExecutorService(5_000, 5_000);
        });

        log.info("Expected error: {}", thrown.getMessage());
    }

    @Test
    @Order(5)
    void runTestForPlatformThreadsWithFourThousandThreadsInThePoolAndOneHundredThousandThreads() {
        ThreadsDemo.platformThreadsDemoWithExecutorService(100_000, 4_000);
    }

    @Test
    @Order(6)
    void runTestForVirtualThreadsWithOneHundredThousandThreads() {
        ThreadsDemo.virtualThreadsDemoWithExecutorService(100_000);
    }

    @Test
    @Order(7)
    void runTestForVirtualThreadsWithFiveHundredThousandThreads() {
        ThreadsDemo.virtualThreadsDemoWithExecutorService(500_000);
    }

    @Test
    @Order(8)
    void runTestForVirtualThreadsWithOneMillionThreads() {
        ThreadsDemo.virtualThreadsDemoWithExecutorService(1_000_000);
    }
}