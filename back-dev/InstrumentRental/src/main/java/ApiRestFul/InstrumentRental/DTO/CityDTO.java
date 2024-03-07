package ApiRestFul.InstrumentRental.DTO;

import ApiRestFul.InstrumentRental.Entity.City;
import ApiRestFul.InstrumentRental.Enum.CityCode;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ApiRestFul.InstrumentRental.Entity.City} entity
 */
@Data
public class CityDTO implements Serializable {
    private final CityCode cityCode;
    private final CountryDTO country;

    public CityDTO(City city) {
        this.cityCode = city.getCityCode();
        this.country = new CountryDTO(city.getCountry());
    }
}