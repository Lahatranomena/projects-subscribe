package com.course.demo.course.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course.demo.course.entity.Course;
import com.course.demo.course.entity.Subscription;
import com.course.demo.course.entity.SubscriptionEmailRequested;
import com.course.demo.course.entity.User;
import com.course.demo.course.repository.CourseRepository;
import com.course.demo.course.repository.SubscriptionRepository;
import com.course.demo.course.repository.UserRepository;
import com.course.demo.endpoint.event.EventProducer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

  @Mock private SubscriptionRepository subscriptionRepository;
  @Mock private UserRepository userRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private EventProducer<SubscriptionEmailRequested> eventProducer;

  private SubscriptionService subscriptionService;

  private UUID userId;
  private UUID courseId;
  private User user;
  private Course course;

  @BeforeEach
  void setUp() {
    subscriptionService =
        new SubscriptionService(
            subscriptionRepository, userRepository, courseRepository, eventProducer);

    userId = UUID.randomUUID();
    courseId = UUID.randomUUID();

    user = User.builder().id(userId).firstName("Jean").email("jean@example.com").build();

    course = Course.builder().id(courseId).title("Java Avancé").build();
  }

  @Test
  void subscribe_shouldSaveSubscriptionAndPublishEvent() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
    when(subscriptionRepository.save(any(Subscription.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Subscription result = subscriptionService.subscribe(userId, courseId);

    assertThat(result.getUser()).isEqualTo(user);
    assertThat(result.getCourse()).isEqualTo(course);

    ArgumentCaptor<List<SubscriptionEmailRequested>> eventCaptor =
        ArgumentCaptor.forClass(List.class);
    verify(eventProducer).accept(eventCaptor.capture());

    SubscriptionEmailRequested event = eventCaptor.getValue().get(0);
    assertThat(event.getTo()).isEqualTo("jean@example.com");
    assertThat(event.getUserFirstName()).isEqualTo("Jean");
    assertThat(event.getCourseTitle()).isEqualTo("Java Avancé");
  }

  @Test
  void subscribe_shouldThrow_whenUserNotFound() {
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> subscriptionService.subscribe(userId, courseId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("User introuvable");
  }

  @Test
  void subscribe_shouldThrow_whenCourseNotFound() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> subscriptionService.subscribe(userId, courseId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Course introuvable");
  }
}
