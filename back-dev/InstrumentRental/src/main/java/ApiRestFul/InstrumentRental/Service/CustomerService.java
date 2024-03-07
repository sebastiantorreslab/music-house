package ApiRestFul.InstrumentRental.Service;
import ApiRestFul.InstrumentRental.DTO.CustomerDTO;
import ApiRestFul.InstrumentRental.Entity.Address;
import ApiRestFul.InstrumentRental.Entity.Customer;
import ApiRestFul.InstrumentRental.Enum.CityCode;
import ApiRestFul.InstrumentRental.Enum.CountryCode;
import ApiRestFul.InstrumentRental.Enum.Role;
import ApiRestFul.InstrumentRental.Registry.*;
import ApiRestFul.InstrumentRental.Repository.AddressRepository;
import ApiRestFul.InstrumentRental.Repository.CityRepository;
import ApiRestFul.InstrumentRental.Repository.CountryRepository;
import ApiRestFul.InstrumentRental.Repository.CustomerRepository;
import ApiRestFul.InstrumentRental.Utils.Security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    ObjectMapper objectMapper = new ObjectMapper();

    public CustomerService(EmailService emailService, PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
                           CityRepository cityRepository,
                           CountryRepository countryRepository,
                           AddressRepository addressRepository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
    public ResponseEntity<?> listCustomers(Pageable pageagle){
        Page<Customer> response = customerRepository.findAll(pageagle);
        if(response.hasContent()){
            return ResponseEntity.ok(response.map(CustomerDTO::new));
        }else{
            return new ResponseEntity<>("No hay registros en la BD!",HttpStatus.OK);
        }
    }
    public ResponseEntity<?> customerRegistry(CustomerRegistry customerRegistry) {
        if(!customerRepository.existsByDni(customerRegistry.custDni())){
            if(!customerRepository.emailAlreadyExists(customerRegistry.custEmail())){
                Customer customer = new Customer(customerRegistry);
                customer.setCustPassword(passwordEncoder.encode(customerRegistry.custPassword()));
                Customer newCustomer = customerRepository.save(customer);
                var jwtToken = automaticLogin(newCustomer.getCustEmail(), customerRegistry.custPassword());
                sendConfirmationEmail(newCustomer.getCustEmail(), newCustomer.getCustFirsName());
                return ResponseEntity.ok(new DataJWTToken(newCustomer.getCustDni(), newCustomer.getCustFirsName(),
                        newCustomer.getCustLastName(), "ROLE_USER", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ya registrado!");
            }
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("DNI ya registrado!");
        }
    }
    public ResponseEntity<?> adminRegistry(AdminRegistry customerRegistry){
        if(!customerRepository.existsByDni(customerRegistry.custDni())){
            if(!customerRepository.emailAlreadyExists(customerRegistry.custEmail())){
                Customer customer = new Customer(customerRegistry);
                customer.setCustPassword(passwordEncoder.encode(customerRegistry.custPassword()));
                Customer newCustomer = customerRepository.save(customer);
                var jwtToken = automaticLogin(customerRegistry.custEmail(), customerRegistry.custPassword());
                sendConfirmationEmail(newCustomer.getCustEmail(), newCustomer.getCustFirsName());
                return ResponseEntity.ok(new DataJWTToken(newCustomer.getCustDni(), newCustomer.getCustFirsName(),
                        newCustomer.getCustLastName(), "ROLE_ADMIN", jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ya registrado!");
            }
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("DNI ya registrado!");
        }
    }
    private void sendConfirmationEmail(String to, String name){
        emailService.sendMail(to,"Registo Exitoso!",
                "Hola "+ name + ", gracias por registrarse en Music House.");
    }

    private String automaticLogin(String email, String password){
        Authentication authToken = new UsernamePasswordAuthenticationToken(email,password);
        var authenticatedUser = authenticationManager.authenticate(authToken);
        return tokenService.generateToken((Customer) authenticatedUser.getPrincipal());
    }
    public ResponseEntity<?> findByDni(String custDni) {
        if(customerRepository.existsByDni(custDni)){
            return new ResponseEntity<>(new CustomerDTO(customerRepository.findByCustDni(custDni)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No encontrado en la BD!",HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateCustomer(CustomerUpdate customerUpdate) {
        if(customerRepository.existsByDni(customerUpdate.custDni())){
            Customer currentCustomer = customerRepository.findByCustDni(customerUpdate.custDni());
            Address currentAddress = currentCustomer.getAddress();
            Address newAddress = customerUpdate.address();
            if(!currentAddress.equals(newAddress)){
                if(newAddress.getCustAddress() != null){
                    addressRepository.updateAddress(newAddress.getCustAddress(),currentAddress.getId());
                }
                CityCode newCityCode =  customerUpdate.address().getCity().getCityCode();
                if(newCityCode != null){
                    Long currenCityId = currentAddress.getCity().getId();
                    if(CityCode.cityList.contains(newCityCode)){
                        cityRepository.updateCity(newCityCode, currenCityId);
                    }else{
                        return new ResponseEntity<>("City Code no corresponde!",HttpStatus.NOT_FOUND);
                    }
                }
                CountryCode newCountryCode = customerUpdate.address().getCity().getCountry().getCountryCode();
                if(newCountryCode != null){
                    Long currentCountryId = currentAddress.getCity().getCountry().getId();
                    if(CountryCode.countryList.contains(newCountryCode)){
                        countryRepository.updateCountry(newCountryCode, currentCountryId);
                    } else{
                        return new ResponseEntity<>("Contry Code no corresponde!",HttpStatus.NOT_FOUND);
                    }
                }
            }
            String custFirstName = currentCustomer.getCustFirsName();
            String custLastName = currentCustomer.getCustLastName();
            String custEmail = currentCustomer.getCustEmail();
            String custPassword = currentCustomer.getCustPassword();
            String custPhone = currentCustomer.getCustPhone();
            Role role = currentCustomer.getRole();

            if(!custFirstName.equalsIgnoreCase(customerUpdate.custFirsName())){
                custFirstName = customerUpdate.custFirsName();
            }
            if(!custLastName.equalsIgnoreCase(customerUpdate.custLastName())){
                custLastName = customerUpdate.custLastName();
            }
            if(!custEmail.equalsIgnoreCase(customerUpdate.custEmail())){
                custEmail = customerUpdate.custEmail();
            }
            if(!custPassword.equalsIgnoreCase(customerUpdate.custPassword())){
                custPassword = passwordEncoder.encode(customerUpdate.custPassword());
            }
            if(!custPhone.equalsIgnoreCase(customerUpdate.custPhone())){
                custPhone = customerUpdate.custPhone();
            }
            if(customerRepository.updateCustomerByDni(custFirstName, custLastName, custEmail, custPassword, custPhone,
                    customerUpdate.custDni())==1){
                Customer updatedCustomer = objectMapper.convertValue(customerUpdate,Customer.class);
                updatedCustomer.setRole(role);
                return ResponseEntity.ok(new CustomerDTO(updatedCustomer));
            } else {
                return new ResponseEntity<>("Actualización no realizada en BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado en la BD!");
        }
    }

    public ResponseEntity<String> deleteCustByDni(String custDni){
        ResponseEntity<String> response;
        if(customerRepository.existsByDni(custDni)){
            Customer customer = customerRepository.findByCustDni(custDni);
            Long addressId = customer.getAddress().getId();
            Long cityId = customer.getAddress().getCity().getId();
            Long countryId = customer.getAddress().getCity().getCountry().getId();
            if(customerRepository.deleteCustByDni(custDni)>0){
                addressRepository.deleteAddress(addressId);
                cityRepository.deleteCity(cityId);
                countryRepository.deleteCountry(countryId);
                response = new ResponseEntity<>("Cliente Borrado de BD exitosamente!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Registro no borrado en la BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado en la BD!");
        }
        return response;
    }
    public ResponseEntity<?> updateRole(CustomerUpdateRole updateRole) {
        if(customerRepository.existsByDni(updateRole.custDni())){
            if (customerRepository.updateRole(updateRole.role(),updateRole.custDni())==1){
                return ResponseEntity.ok("Role actualizado exitosamente! DNI: "+updateRole.custDni());
            }  else {
                return new ResponseEntity<>("Actualización no realizada en BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado en la BD!");
        }
    }
}
