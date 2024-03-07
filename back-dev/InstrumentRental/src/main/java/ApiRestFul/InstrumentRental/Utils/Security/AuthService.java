package ApiRestFul.InstrumentRental.Utils.Security;
import ApiRestFul.InstrumentRental.Repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private CustomerRepository customerRepository;

    public AuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String custEmail) throws UsernameNotFoundException {
        return customerRepository.findCustByEmail(custEmail);
    }
}
