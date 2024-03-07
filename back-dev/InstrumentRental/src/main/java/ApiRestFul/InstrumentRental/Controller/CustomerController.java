package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.Registry.*;
import ApiRestFul.InstrumentRental.Service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/registry")
    public ResponseEntity<?> customerRegistry(@RequestBody @Valid CustomerRegistry customerRegistry){
        return customerService.customerRegistry(customerRegistry);
    }
    @GetMapping(value = "/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable("dni") String custDni) {
        return customerService.findByDni(custDni);
    }
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdate customerUpdate){
        return customerService.updateCustomer(customerUpdate);
    }
}
