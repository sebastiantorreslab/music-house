package ApiRestFul.InstrumentRental.DTO;

import ApiRestFul.InstrumentRental.Entity.Country;
import ApiRestFul.InstrumentRental.Enum.CountryCode;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ApiRestFul.InstrumentRental.Entity.Country} entity
 */
@Data
public class CountryDTO implements Serializable {
    private final CountryCode countryCode;

    public CountryDTO(Country country) {
        this.countryCode = country.getCountryCode();
    }
}