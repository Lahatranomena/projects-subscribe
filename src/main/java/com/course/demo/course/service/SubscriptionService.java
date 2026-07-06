package com.course.demo.course.service;

import com.course.demo.course.entity.Course;
import com.course.demo.course.entity.Subscription;
import com.course.demo.course.entity.SubscriptionEmailRequested;
import com.course.demo.course.entity.User;
import com.course.demo.course.repository.CourseRepository;
import com.course.demo.course.repository.SubscriptionRepository;
import com.course.demo.course.repository.UserRepository;
import com.course.demo.endpoint.event.EventProducer;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final EventProducer<SubscriptionEmailRequested> eventProducer;

  @Transactional
  public Subscription subscribe(UUID userId, UUID courseId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User introuvable : " + userId));
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course introuvable : " + courseId));

    Subscription subscription = Subscription.builder().user(user).course(course).build();
    subscription = subscriptionRepository.save(subscription);

    var event =
        SubscriptionEmailRequested.builder()
            .to(user.getEmail())
            .userFirstName(user.getFirstName())
            .courseTitle(course.getTitle())
            .build();
    eventProducer.accept(List.of(event));

    return subscription;
  }
}
