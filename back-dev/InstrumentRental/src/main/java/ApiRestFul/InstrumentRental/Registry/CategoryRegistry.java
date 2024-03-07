package ApiRestFul.InstrumentRental.Registry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRegistry(@Pattern(message = "Only Letters", regexp = "[a-zA-Z \u00C0-\u00FA \\\\u00F1\\\\u00D1]+")
                               @Size(message = "Min 3 Max 45", min = 3, max = 45)
                               @NotBlank
                               String categoryTitle,
                               String categoryDescription,
                               String categoryImage) {
}
