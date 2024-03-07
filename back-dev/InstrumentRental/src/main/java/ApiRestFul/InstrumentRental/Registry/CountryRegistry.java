package ApiRestFul.InstrumentRental.Registry;
import ApiRestFul.InstrumentRental.Enum.CountryCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CountryRegistry(@NotNull(message = "Blank not allowed for Country")
                              @Pattern.List({@Pattern(regexp = "COL"),
                                      @Pattern(regexp = "ARG")})
                              CountryCode countryCode) {
}
