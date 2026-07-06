package com.course.demo.course.repository;

import com.course.demo.course.entity.Subscription;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

  Optional<Subscription> findByUserIdAndCourseId(UUID userId, UUID courseId);
}
