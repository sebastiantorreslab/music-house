package ApiRestFul.InstrumentRental.Utils.Security;
import ApiRestFul.InstrumentRental.Entity.Customer;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String generateToken(Customer customer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("3454*/"); //revisar el secret
            return JWT.create()
                    .withIssuer("InstrumentRental")
                    .withSubject(customer.getCustEmail())
                    .withClaim("DNI",customer.getCustDni())
                    .withExpiresAt(generateExpDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(exception);
        }
    }
    private Instant generateExpDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token){
        if (token == null) {
            throw new RuntimeException("Token null!");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("3454*/"); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer("InstrumentRental")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.getMessage());
        }
        try {
            if (verifier.getSubject() == null) {
                throw new RuntimeException("Verifier invalido");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return verifier.getSubject();
    }
}
