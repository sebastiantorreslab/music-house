package ApiRestFul.InstrumentRental.Registry;
import ApiRestFul.InstrumentRental.Entity.Country;
import ApiRestFul.InstrumentRental.Enum.CityCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CityRegistry(@NotBlank(message = "Blank not allowed for City")
                           @Pattern.List(
                            {@Pattern(regexp = "BOGOTA"),
                            @Pattern(regexp = "MEDELLIN"),
                            @Pattern(regexp = "CALI"),
                            @Pattern(regexp = "BUENOS_AIRES"),
                            @Pattern(regexp = "CORDOBA"),
                            @Pattern(regexp = "MENDOZA")})
                           CityCode cityCode,
                           @NotNull(message = "Blank not allowed in Country")
                           Country country) {
}
