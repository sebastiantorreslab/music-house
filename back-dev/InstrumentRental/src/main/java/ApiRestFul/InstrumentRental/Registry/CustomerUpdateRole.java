package ApiRestFul.InstrumentRental.Registry;

import ApiRestFul.InstrumentRental.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerUpdateRole(@NotBlank(message = "Blank not allowed")
                                 @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                                 String custDni,
                                 @NotNull
                                 Role role) {
}
