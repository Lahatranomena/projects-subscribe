package com.course.demo.course.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.course.demo.course.entity.SubscriptionEmailRequested;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class SubscriptionEmailRequestedTest {

  @Test
  void builder_shouldSetAllFields() {
    SubscriptionEmailRequested event =
        SubscriptionEmailRequested.builder()
            .to("jean@example.com")
            .userFirstName("Jean")
            .courseTitle("Java Avancé")
            .build();

    assertThat(event.getTo()).isEqualTo("jean@example.com");
    assertThat(event.getUserFirstName()).isEqualTo("Jean");
    assertThat(event.getCourseTitle()).isEqualTo("Java Avancé");
  }

  @Test
  void maxConsumerDuration_shouldBe30Seconds() {
    SubscriptionEmailRequested event = SubscriptionEmailRequested.builder().build();

    assertThat(event.maxConsumerDuration()).isEqualTo(Duration.ofSeconds(30));
  }

  @Test
  void maxConsumerBackoffBetweenRetries_shouldBe5Seconds() {
    SubscriptionEmailRequested event = SubscriptionEmailRequested.builder().build();

    assertThat(event.maxConsumerBackoffBetweenRetries()).isEqualTo(Duration.ofSeconds(5));
  }
}
