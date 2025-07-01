package com.r2l.notificationService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class NotificationServiceApplicationTests {

  @Test
  void contextLoads() {
    // This test checks if the Spring application context loads successfully.
    // No additional assertions are needed here, as a failure to load the context
    // will automatically fail this test.
  }
}
