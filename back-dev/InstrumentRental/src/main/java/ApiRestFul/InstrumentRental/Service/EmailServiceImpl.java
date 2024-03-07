package ApiRestFul.InstrumentRental.Service;

import ApiRestFul.InstrumentRental.Exceptions.MailSenderException;
import ApiRestFul.InstrumentRental.Utils.Mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import jakarta.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    public EmailServiceImpl(JavaMailSender emailSender, MailProperties mailProperties) {
        this.emailSender = emailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    @Async
    public void sendMail(String to, String subject, String body) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);
            helper.setFrom(mailProperties.username());
            emailSender.send(message);
        } catch (Exception e) {
            throw new MailSenderException(e);
        }
    }
}
