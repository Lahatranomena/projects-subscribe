package com.course.demo.course.service;

import com.course.demo.course.entity.SubscriptionEmailRequested;
import com.course.demo.mail.Email;
import com.course.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionEmailRequestedService implements Consumer<SubscriptionEmailRequested> {

    private final Mailer mailer;

    @SneakyThrows
    @Override
    public void accept(SubscriptionEmailRequested event) {
        InternetAddress recipientAddress = new InternetAddress(event.getTo());
        String subject = "Confirmation d'inscription";
        String htmlBody = "Bonjour " + event.getUserFirstName()
                + ", votre inscription au cours \"" + event.getCourseTitle() + "\" est bien enregistrée.";

        mailer.accept(new Email(
                recipientAddress,
                List.<InternetAddress>of(),
                List.<InternetAddress>of(),
                subject,
                htmlBody,
                List.<File>of()));
    }
}