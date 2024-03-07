package ApiRestFul.InstrumentRental.Registry;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record RentalRating(@NotNull
                           Long id,
                           @NotNull
                           String custDni,
                           @Range(message = "0 to 5 only", min = 0, max = 5)
                           Integer rating,
                           @Size(message = "Max 255", max = 255)
                           String review) {
}
