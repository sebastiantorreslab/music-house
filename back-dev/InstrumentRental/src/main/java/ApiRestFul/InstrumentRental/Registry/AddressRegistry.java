package ApiRestFul.InstrumentRental.Registry;
import ApiRestFul.InstrumentRental.Entity.City;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRegistry(@NotBlank(message = "Blank not allowe in address")
                              String custAddress,
                              @NotNull
                              City city) {
}
