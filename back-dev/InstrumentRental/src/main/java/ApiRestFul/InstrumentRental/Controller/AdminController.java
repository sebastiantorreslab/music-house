package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.Registry.AdminRegistry;
import ApiRestFul.InstrumentRental.Registry.CustomerUpdateRole;
import ApiRestFul.InstrumentRental.Registry.InstrumentRegistry;
import ApiRestFul.InstrumentRental.Service.CustomerService;
import ApiRestFul.InstrumentRental.Service.InstrumentService;
import ApiRestFul.InstrumentRental.Service.RentalService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    public static final int DEFAULT_PAGE_SIZE = 2;
    private final CustomerService customerService;
    private final InstrumentService instrumentService;
    private final RentalService rentalService;

    public AdminController(CustomerService customerService, InstrumentService instrumentService, RentalService rentalService) {
        this.customerService = customerService;
        this.instrumentService = instrumentService;
        this.rentalService = rentalService;
    }
    @GetMapping("/instrument/list")
    public ResponseEntity<?> listAllInstruments(@PageableDefault(size = DEFAULT_PAGE_SIZE)
                                             Pageable pageable){
        return instrumentService.listAllInstruments(pageable);
    }
    @PostMapping("/instrument/registry")
    public ResponseEntity<?> instrumentRegistry(@RequestBody @Valid InstrumentRegistry instrumentRegistry){
        return instrumentService.instrumentRegistry(instrumentRegistry);
    }    
    @PutMapping("/instrument/update")
    public ResponseEntity<?> updateInstrument(@RequestBody InstrumentRegistry instrumentRegistry){
        return instrumentService.updateInstrument(instrumentRegistry);
    }
    @DeleteMapping(value = "/instrument/{tag}")
    public ResponseEntity<String> deleteInstByTagNumber(@PathVariable("tag") String instTagNumber){
        return instrumentService.deleteInstByTagNumber(instTagNumber);
    }
    @PostMapping("/registry")
    public ResponseEntity<?> adminRegistry(@RequestBody @Valid AdminRegistry customerRegistry){
        return customerService.adminRegistry(customerRegistry);
    }
    @GetMapping("/customer/list")
    public ResponseEntity<?> listCustomers(@PageableDefault(size = DEFAULT_PAGE_SIZE)
                                           Pageable pageable){
        return customerService.listCustomers(pageable);
    }
    @PutMapping("/customer/update-role")
    @Transactional
    public ResponseEntity<?> updateRole(@RequestBody CustomerUpdateRole updateRole){
        return customerService.updateRole(updateRole);
    }
    @DeleteMapping(value = "/customer/delete/{dni}")
    public ResponseEntity<String> deleteCustByDni(@PathVariable("dni") String custDni){
        return customerService.deleteCustByDni(custDni);
    }
    @GetMapping("/rental/list")
    public ResponseEntity<?> listAllRental(@PageableDefault(size = DEFAULT_PAGE_SIZE)
                                                Pageable pageable){
        return rentalService.listAllRental(pageable);
    }
}
