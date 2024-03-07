package ApiRestFul.InstrumentRental.Registry;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record RankingRegistry(
        @Range(message = "0 to 5 only", min = 0, max = 5) //TODO: EVALUAR ESTA EXPRESIÓN REGULAR.
         Integer score
        ) {


}
