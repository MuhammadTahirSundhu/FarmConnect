package com.example.springone.NotificationPackage.HelperClasses;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo("mrsundhu313@gmail.com");
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true); // true for HTML emails
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            logger.error("MailException occurred while sending email", e);
        } catch (MessagingException e) {
            logger.error("MessagingException occurred while sending email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
