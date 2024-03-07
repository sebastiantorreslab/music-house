package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.Registry.*;
import ApiRestFul.InstrumentRental.Service.RentalService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/rental")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/unavailable-dates")
    public ResponseEntity<?> rentalUnavailableDates(@RequestBody @Valid UnavailableDates unavailableDates){
        return rentalService.unavailability(unavailableDates.instTagNumber());
    }
    @PostMapping("/registry")
    public ResponseEntity<?> rentalRegistry(@RequestBody @Valid RentalRegistry rentalRegistry){
        return rentalService.rentalRegistry(rentalRegistry);
    }
    @GetMapping
    public ResponseEntity<?> rentalById(@RequestBody @Valid RentalQuery rentalQuery){
        return rentalService.rentalById(rentalQuery);
    }
    @PostMapping("/list")
    public ResponseEntity<?> listAllRentalByCustomer(@RequestBody @Valid RentalListByCustomer rentalListByCustomer,
                                           Pageable pageable){
        return rentalService.listRentalByCustDni(rentalListByCustomer.custDni(), pageable);
    }
    @PostMapping("/instrument")
    public ResponseEntity<?> listAllRentalByInstrument(@RequestBody @Valid RentalListByInstrument rentalListByInstrument,
                                                     Pageable pageable){
        return rentalService.listRentalByInstTagNumber(rentalListByInstrument.instTagNumber(), pageable);
    }
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> rentalUpdateById(@RequestBody @Valid RentalUpdate rentalUpdate){
        return rentalService.rentalUpdate(rentalUpdate);
    }

    @PutMapping("/review")
    @Transactional
    public ResponseEntity<?> rentalRating(@RequestBody @Valid RentalRating rentalRating){
        return rentalService.rentalRating(rentalRating);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteRentalBYId(@RequestBody @Valid RentalQuery rentalQuery){
        return rentalService.deleteRental(rentalQuery);
    }
}

