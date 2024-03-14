package com.doughtodoor.notification.service;

import com.doughtodoor.notification.exception.EmailSendingException;
import com.doughtodoor.notification.model.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(NotificationRequest request, String subject, boolean isHtmlContent) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(request.getRecipient());
            helper.setSubject(subject);
            helper.setText(request.getMessage(), isHtmlContent);

            javaMailSender.send(mimeMessage);
            logger.info("Email sent to {}", request.getRecipient());
        } catch (MessagingException e) {
            logger.error("Failed to send email to {}", request.getRecipient(), e);
            throw new EmailSendingException("Failed to send email to " + request.getRecipient(), e);
        }
    }
}