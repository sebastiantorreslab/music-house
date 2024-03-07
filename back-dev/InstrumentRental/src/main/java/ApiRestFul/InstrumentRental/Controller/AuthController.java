package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.Entity.Customer;
import ApiRestFul.InstrumentRental.Registry.AuthDataRegistry;
import ApiRestFul.InstrumentRental.Registry.DataJWTToken;
import ApiRestFul.InstrumentRental.Repository.CustomerRepository;
import ApiRestFul.InstrumentRental.Utils.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager,
                          CustomerRepository customerRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<?> authUser(@RequestBody @Valid AuthDataRegistry authDataRegistry){
        if(customerRepository.emailAlreadyExists(authDataRegistry.custEmail())){
            Authentication authToken = new UsernamePasswordAuthenticationToken(authDataRegistry.custEmail(),
                    authDataRegistry.custPassword());
            var authenticatedUser = authenticationManager.authenticate(authToken);
            var jwtToken = tokenService.generateToken((Customer) authenticatedUser.getPrincipal());
            Customer customer = customerRepository.getCustByEmail(authDataRegistry.custEmail());
            String custDni = customer.getCustDni();
            String custName = customer.getCustFirsName();
            String custLastName = customer.getCustLastName();
            String custRole = customer.getRole().name();
            return ResponseEntity.ok(new DataJWTToken(custDni,custName, custLastName, custRole, jwtToken));
        } else {
            return new ResponseEntity<>("Email errado!",HttpStatus.BAD_REQUEST);
        }

    }
}
