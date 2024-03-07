package ApiRestFul.InstrumentRental.Registry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RentalListByInstrument(@NotBlank
                                     @Pattern(regexp = "\\d{4,6}")
                                     String instTagNumber) {
}
