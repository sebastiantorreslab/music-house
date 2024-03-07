package ApiRestFul.InstrumentRental.Entity;
import ApiRestFul.InstrumentRental.Registry.AddressRegistry;
import ApiRestFul.InstrumentRental.Registry.CityRegistry;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cust_address", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custAddress;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    public Address(AddressRegistry addressRegistry) {
        CityRegistry cityRegistry = new CityRegistry(addressRegistry.city().getCityCode(),
                addressRegistry.city().getCountry());
        this.custAddress = addressRegistry.custAddress();
        this.city = new City(cityRegistry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(custAddress, address.custAddress) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, custAddress, city);
    }
}