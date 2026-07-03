package com.course.demo.course.repository;

import com.course.demo.course.entity.Subscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByUser_IdAndCourse_Id(UUID userId, UUID courseId);
}
