package ApiRestFul.InstrumentRental.Registry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RentalQuery(@NotNull Long id,
                          @NotBlank(message = "Blank not allowed in DNI field")
                          @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                          String custDni) {
}
