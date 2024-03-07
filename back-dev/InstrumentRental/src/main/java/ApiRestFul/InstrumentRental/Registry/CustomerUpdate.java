package ApiRestFul.InstrumentRental.Registry;
import ApiRestFul.InstrumentRental.Entity.Address;
import ApiRestFul.InstrumentRental.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerUpdate(@NotBlank(message = "Blank not allowed")
                             @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                             String custDni,
                             @Pattern(regexp = "[a-zA-Z\\u00F1\\u00D1]+", message = "Only letters and no white spaces")
                             String custFirsName,
                             @Pattern(regexp = "[a-zA-Z\\u00F1\\u00D1]+", message = "Only letters and no white spaces")
                             String custLastName,
                             @Pattern(regexp = "^(.+)@(.+)$", message = "email field example@domain.com")
                             String custEmail,
                             String custPassword,
                             @Pattern(regexp = "\\d{6,25}", message = "Only numbers. Min 6 - Max 25")
                             String custPhone,
                             @NotNull
                             Address address) {

}
