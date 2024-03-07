package ApiRestFul.InstrumentRental.Registry;
import ApiRestFul.InstrumentRental.Entity.Address;
import ApiRestFul.InstrumentRental.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AdminRegistry(@NotBlank(message = "Blank not allowed in DNI field")
                           @Pattern(regexp = "\\d{5,10}", message = "Only digits. Min 5 - Max 10")
                           String custDni,
                            @NotBlank(message = "Blank not allowed in First Name")
                           @Pattern(regexp = "[a-zA-ZÀ-ú\\u00F1\\u00D1]+", message = "Only letters and no white spaces")
                           String custFirsName,
                            @NotBlank(message = "Blank not allowed in Last Name")
                           @Pattern(regexp = "[a-zA-ZÀ-ú\\u00F1\\u00D1]+", message = "Only letters and no white spaces")
                           String custLastName,
                            @NotBlank(message = "Blank not allowed in email")
                           @Pattern(regexp = "^(.+)@(.+)$", message = "email field example@domain.com")
                           String custEmail,
                            @NotBlank(message = "Blank not allowed for password")
                           String custPassword,
                            @Pattern(regexp = "\\d{6,25}", message = "Only numbers. Min 6 - Max 25")
                           String custPhone,
                            @NotNull(message = "Blank not allowed for address")
                           Address address) {
    public Role role() {
        return Role.ROLE_ADMIN;
    }
}
