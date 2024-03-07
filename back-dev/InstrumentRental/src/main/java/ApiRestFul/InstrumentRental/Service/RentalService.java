package ApiRestFul.InstrumentRental.Service;

import ApiRestFul.InstrumentRental.DTO.RentalDTO;
import ApiRestFul.InstrumentRental.Entity.Customer;
import ApiRestFul.InstrumentRental.Entity.Instrument;
import ApiRestFul.InstrumentRental.Entity.Rental;
import ApiRestFul.InstrumentRental.Registry.RentalQuery;
import ApiRestFul.InstrumentRental.Registry.RentalRating;
import ApiRestFul.InstrumentRental.Registry.RentalRegistry;
import ApiRestFul.InstrumentRental.Registry.RentalUpdate;
import ApiRestFul.InstrumentRental.Repository.CustomerRepository;
import ApiRestFul.InstrumentRental.Repository.InstrumentRepository;
import ApiRestFul.InstrumentRental.Repository.RentalRepository;
import org.javatuples.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final InstrumentRepository instrumentRepository;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    public RentalService(RentalRepository rentalRepository,
                         InstrumentRepository instrumentRepository, EmailService emailService, CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.instrumentRepository = instrumentRepository;
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }
    public ResponseEntity<?> unavailability(String instTagNumber){
        List<Pair<Date, Date>> response = new ArrayList<>();
        if(rentalRepository.existsByInstTagNumber(instTagNumber)){
            Date today = java.sql.Date.valueOf(java.time.LocalDate.now());
            List<Rental> rentalList = rentalRepository.unavailableDatesByInstTagNumber(instTagNumber,today,today);
            if(!rentalList.isEmpty()){
                List<Date> dates = new ArrayList<>(2);
                rentalList.forEach(rental ->{
                    dates.clear();
                    dates.add(0,rental.getStartDate());
                    dates.add(1,rental.getEndDate());
                    response.add(Pair.fromCollection(dates));
                });
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public ResponseEntity<?> listAllRental(Pageable pageable){
        Page<Rental> rentals = rentalRepository.findAll(pageable);
        if(rentals.hasContent()){
            return ResponseEntity.ok(rentals.map(RentalDTO::new));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body("No se encuentran registros de renta en la BD!");
        }
    }
    public ResponseEntity<?> listRentalByCustDni(String custDni, Pageable pageable){
        Page<Rental> rentalsByCust = rentalRepository.rentalByCustDni(custDni, pageable);
        if(rentalsByCust.hasContent()){
            return ResponseEntity.ok(rentalsByCust.map(RentalDTO::new));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body("No se encuentran registros de renta en la BD!");
        }
    }
    public ResponseEntity<?> listRentalByInstTagNumber(String instTagNumber, Pageable pageable){
        Page<Rental> rentalsByCust = rentalRepository.rentalByInstTagNumber(instTagNumber, pageable);
        if(rentalsByCust.hasContent()){
            return ResponseEntity.ok(rentalsByCust.map(RentalDTO::new));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body("No se encuentran registros de renta en la BD!");
        }
    }
    public ResponseEntity<?> rentalRegistry(RentalRegistry rentalRegistry){
        if(customerRepository.existsByDni(rentalRegistry.custDni())){
            if(instrumentRepository.existsByInstTagNumber(rentalRegistry.instTagNumber())){
                Date endDate = rentalRegistry.endDate();
                Date startDate = rentalRegistry.startDate();
                if(endDate.after(startDate)){
                    int datesOverlap = rentalRepository.rentalList(rentalRegistry.instTagNumber(),
                            endDate, startDate).size();
                    if(datesOverlap>0){
                        return new ResponseEntity<>("Fechas de renta no disponibles!",
                                HttpStatus.BAD_REQUEST);
                    } else {
                        int rentalDays = Math.round(Math.abs(TimeUnit.DAYS.convert(endDate.getTime()-startDate.getTime(),
                                TimeUnit.MILLISECONDS)));
                        Instrument instrument = instrumentRepository.findByInstTagNumber(rentalRegistry.instTagNumber());
                        Rental rental = rentalRepository.save(new Rental(rentalRegistry,
                                rentalDays*instrument.getInstPrice()));
                        Customer customer = customerRepository.findByCustDni(rentalRegistry.custDni());
                        sendConfirmationEmail(customer,rental, instrument);
                        return new ResponseEntity<>(new RentalDTO(rental),
                                HttpStatus.CREATED);
                    }
                } else {
                    return new ResponseEntity<>("La fecha de fin de la renta debe ser posterior al inicio",
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Instrumento no creado en la BD!",
                        HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Cliente no creado en la BD! Registrese primero.",
                    HttpStatus.OK);
        }
    }
    private void sendConfirmationEmail(Customer customer, Rental rental, Instrument instrument){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy", Locale.forLanguageTag("es-CO"));
        emailService.sendMail(customer.getCustEmail(), "Renta de instrumento exitosa!",
                "Hola "+ customer.getCustFirsName() + ", gracias por rentar con Music House." + "\n"
                        + "El instrumento rentado es " + instrument.getInstName() + "\n"
                        +" El id de la reserva es "+ rental.getId() + "\n"
                        +"La fecha de incio es el "+ sdf.format(rental.getStartDate()) + " y la fecha de finalización es el "
                        + sdf.format(rental.getEndDate()) + " el importe del servicio es $" + rental.getRentalCharge());
    }
    public ResponseEntity<?> rentalById(RentalQuery rentalQuery){
        Long id = rentalQuery.id();
        String custDni = rentalQuery.custDni();
        if(rentalRepository.existsRentalId(id)){
            Rental rental = rentalRepository.findById(id).get();
            if(rental.getCustDni().equalsIgnoreCase(custDni)){
                return ResponseEntity.ok(rentalRepository.findById(id));
            } else {
                return new ResponseEntity<>("No autorizado. Solo está autorizado a consultar sus servicios. Verifique el ID ingresado!",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("El ID de renta no existe en la BD, verifique e intente de nuevo!!",
                    HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> rentalUpdate(RentalUpdate rentalUpdate){
        if(rentalRepository.existsRentalId(rentalUpdate.id())){
            Rental currentRental = rentalRepository.findById(rentalUpdate.id()).get();
            if(currentRental.getCustDni().equalsIgnoreCase(rentalUpdate.custDni())){
                Date newStartDate = rentalUpdate.startDate();
                Date newEndDate = rentalUpdate.endDate();
                if(newStartDate==null){
                    newStartDate = currentRental.getStartDate();
                }
                if(newEndDate==null){
                    newEndDate = currentRental.getEndDate();
                }
                int datesOverlap = rentalRepository.rentalListDistinct(currentRental.getInstTagNumber(), rentalUpdate.id(),
                        newEndDate, newStartDate).size();
                if(datesOverlap>0){
                    return new ResponseEntity<>("Fechas de renta no disponibles",
                            HttpStatus.BAD_REQUEST);
                } else {
                    Double instrumentPrice = instrumentRepository.findByInstTagNumber(currentRental.getInstTagNumber()).getInstPrice();
                    if(instrumentPrice != null){
                        int rentalDays = Math.round(Math.abs(TimeUnit.DAYS.convert(newEndDate.getTime()-newStartDate.getTime(),
                                TimeUnit.MILLISECONDS)));
                        if(rentalRepository.rentalUpdate(newStartDate, newEndDate,
                                instrumentPrice*rentalDays, rentalUpdate.id())==1){
                            return ResponseEntity.ok("Renta actualizada en BD!");
                        } else {
                            return new ResponseEntity<>("Falló la actualización de la renta del instrumento!",
                                    HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity<>("Precio de instrument null!",
                                HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                return new ResponseEntity<>("El ID de renta no existe en la BD, verifique e intente de nuevo!!",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("No autorizado. Solo está autorizado a consultar sus servicios. Verifique el ID! ingresado",
                    HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> rentalRating(RentalRating rentalRating){
        if(rentalRepository.existsRentalId(rentalRating.id())){
            Rental rental = rentalRepository.findById(rentalRating.id()).get();
            if(rental.getCustDni().equalsIgnoreCase(rentalRating.custDni())){
                if(rentalRepository.rentalRating(rentalRating.rating(), rentalRating.review(),
                        rentalRating.id())==1){
                    String instTagNumber = rental.getInstTagNumber();
                    double rankingProm = rentalRepository.updateInstAvg(instTagNumber);
                    int rankingCount = rentalRepository.updateInstCount(instTagNumber);
                    instrumentRepository.instrumentRating(rankingProm, rankingCount,instTagNumber);
                    return ResponseEntity.ok("Valoración de servicio exitosa!");
                } else {
                    return new ResponseEntity<>("No se efectuó la valoración del servicio!",
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("No autorizado. Solo está autorizado a consultar sus servicios. Verifique el ID! ingresado",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("El ID de renta no existe en la BD, verifique e intente de nuevo!!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteRental(RentalQuery rentalQuery){
        Long id = rentalQuery.id();
        if(rentalRepository.existsRentalId(id)){
            String custDni = rentalQuery.custDni();
            if(rentalRepository.findById(id).get().getCustDni().equalsIgnoreCase(custDni)){
                if(rentalRepository.deleteRental(id)==1){
                    return ResponseEntity.ok("Renta borrada de la BD!");
                } else {
                    return new ResponseEntity<>("Falló la operación de borrar la renta de la BD!",
                            HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("No autorizado. Solo está autorizado a consultar sus servicios. Verifique el ID! ingresado",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("El ID de renta no existe en la BD, verifique e intente de nuevo!!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
