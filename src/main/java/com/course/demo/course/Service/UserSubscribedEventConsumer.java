package com.course.demo.course.Service;

import com.course.demo.endpoint.event.model.UserSubscribedEvent;
import com.course.demo.mail.Email;
import com.course.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSubscribedEventConsumer implements Consumer<UserSubscribedEvent> {

  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(UserSubscribedEvent event) {
    var recipientAddress = new InternetAddress(event.getUserEmail());

    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Confirmation d'inscription",
            "Félicitations ! Vous êtes inscrit au cours : " + event.getCourseName(),
            List.of() // Pièces jointes
            ));
  }
}
