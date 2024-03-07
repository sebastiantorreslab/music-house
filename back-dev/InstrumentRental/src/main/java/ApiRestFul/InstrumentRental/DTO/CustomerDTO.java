package ApiRestFul.InstrumentRental.DTO;

import ApiRestFul.InstrumentRental.Entity.Customer;
import ApiRestFul.InstrumentRental.Enum.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Customer} entity
 */
@Data
public class CustomerDTO implements Serializable {
    private final String custDni;
    private final String custFirsName;
    private final String custLastName;
    private final String custEmail;
    private final String custPhone;
    private final Role role;
    private final AddressDTO address;

    public CustomerDTO(Customer customer) {
        this.custDni = customer.getCustDni();
        this.custFirsName = customer.getCustFirsName();
        this.custLastName = customer.getCustLastName();
        this.custEmail = customer.getCustEmail();
        this.custPhone = customer.getCustPhone();
        this.role = customer.getRole();
        this.address = new AddressDTO(customer.getAddress());
    }

}