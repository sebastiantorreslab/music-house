package ApiRestFul.InstrumentRental.Service;


public interface EmailService {
    void sendMail(String to, String subject, String body);
}
