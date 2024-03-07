package ApiRestFul.InstrumentRental.Registry;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record RentalUpdate(@NotNull Long id,
                           @NotBlank(message = "Blank not allowed in DNI field")
                           @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                           String custDni,
                           @Nullable
                           @FutureOrPresent
                           Date startDate,
                           @Nullable
                           @FutureOrPresent
                           Date endDate) {
}
