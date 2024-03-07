package ApiRestFul.InstrumentRental.DTO;

import ApiRestFul.InstrumentRental.Entity.Address;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ApiRestFul.InstrumentRental.Entity.Address} entity
 */
@Data
public class AddressDTO implements Serializable {
    private final String custAddress;
    private final CityDTO city;

    public AddressDTO(Address address) {
        this.custAddress = address.getCustAddress();
        this.city = new CityDTO(address.getCity());
    }
}