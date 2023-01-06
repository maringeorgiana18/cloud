package cc.procesator;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("licentatudorpurcarelu@gmail.com");
        message.setTo(to);
        message.setSubject("Active account for Quizster to go");
        message.setText(text);
        emailSender.send(message);
    }
}