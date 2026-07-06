package com.course.demo.course.controller;

import com.course.demo.endpoint.event.EventProducer;
import com.course.demo.endpoint.event.model.UserSubscribedEvent;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SubscriptionController {

  private final EventProducer eventProducer;

  @PostMapping("/subscribe")
  public String subscribe(@RequestParam String userId, @RequestParam String courseId) {

    String userEmail = "nantenainabakari@gmail.com";
    String courseName = "Architecture Spring & Poja";

    UserSubscribedEvent event =
        UserSubscribedEvent.builder().userEmail(userEmail).courseName(courseName).build();

    eventProducer.accept(List.of(event));

    return "Inscription prise en compte. Un mail de confirmation va vous être envoyé.";
  }
}
