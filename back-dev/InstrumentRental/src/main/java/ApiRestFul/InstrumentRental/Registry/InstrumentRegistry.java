package ApiRestFul.InstrumentRental.Registry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

public record InstrumentRegistry(@NotBlank(message = "Blank not allowed")
                                 @Pattern(regexp = "[a-zA-Z \\u00F1\\u00D1]+")
                                 String instName,
                                 @NotBlank
                                 @Pattern(regexp = "[a-zA-Z \\u00F1\\u00D1]+")
                                 String instBrand,
                                 @NotBlank
                                 @Pattern(regexp = "\\d{4,6}")
                                 String instTagNumber,
                                 @NotNull
                                 Date instAcqDate,
                                 @NotNull
                                 boolean instIsActive,
                                 @NotNull
                                 String category,
                                 String instDescription,
                                 String instPhoto,
                                 @NotNull
                                 Double instPrice,
                                 Double ranking_prom, //TODO: VALIDAR ESTAS OPCIONES DE PASO POR REGISTRY
                                 Integer ranking_count
                                 ) {
}
