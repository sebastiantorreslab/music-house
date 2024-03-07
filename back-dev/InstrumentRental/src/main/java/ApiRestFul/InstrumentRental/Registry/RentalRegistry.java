package ApiRestFul.InstrumentRental.Registry;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record RentalRegistry(@NotBlank
                             @Pattern(regexp = "\\d{4,6}")
                             String instTagNumber,
                             @NotBlank(message = "Blank not allowed in DNI field")
                             @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                             String custDni,
                             @NotNull
                             @FutureOrPresent
                             Date startDate,
                             @NotNull
                             @FutureOrPresent
                             Date endDate) {
}
