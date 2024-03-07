package ApiRestFul.InstrumentRental.Entity;

import ApiRestFul.InstrumentRental.Registry.RentalRegistry;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Size(message = "Max 45", max = 45)
    @NotBlank(message = "Blank not allowed")
    @Column(name = "inst_tag_number", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instTagNumber;

    @Size(message = "Max 45", max = 45)
    @NotBlank(message = "Blank not allowed")
    @Column(name = "cust_dni", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custDni;

    @FutureOrPresent(message = "Only present and on ")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date startDate;

    @FutureOrPresent(message = "Only present and on")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date endDate;

    @Range(message = "0 to 5 only", min = 0, max = 5)
    @PositiveOrZero(message = "Only positive")
    @Digits(message = "Only digits 0 to 5", integer = 1, fraction = 0)
    @Column(name = "instrument_rating", nullable = false)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private Integer instrumentRating;

    @Size(message = "Max 255", max = 255)
    @Column(name = "review", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String review;

    @PositiveOrZero(message = "Only positive values allowed")
    @Column(name = "rental_charge")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    private Double rentalCharge;

    public Rental(RentalRegistry rentalRegistry, double rentalCharge) {
        this.instTagNumber = rentalRegistry.instTagNumber();
        this.custDni = rentalRegistry.custDni();
        this.startDate = rentalRegistry.startDate();
        this.endDate = rentalRegistry.endDate();
        this.rentalCharge = rentalCharge;
    }
}
