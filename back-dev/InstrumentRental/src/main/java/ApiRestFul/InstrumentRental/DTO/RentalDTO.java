package ApiRestFul.InstrumentRental.DTO;
import ApiRestFul.InstrumentRental.Entity.Rental;
import lombok.Data;

import java.util.Date;

@Data
public class RentalDTO {
    private final Long id;
    private final String instTagNumber;
    private final String custDni;
    private final Date startDate;
    private final Date endDate;
    private final Integer instrumentRating;
    private final String review;
    private final Double rentalCharge;

    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.instTagNumber = rental.getInstTagNumber();
        this.custDni = rental.getCustDni();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.instrumentRating = rental.getInstrumentRating();
        this.review = rental.getReview();
        this.rentalCharge = rental.getRentalCharge();
    }
}
