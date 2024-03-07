package ApiRestFul.InstrumentRental.Entity;
import ApiRestFul.InstrumentRental.Enum.CityCode;
import ApiRestFul.InstrumentRental.Registry.CityRegistry;
import ApiRestFul.InstrumentRental.Registry.CountryRegistry;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "city_code", nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private CityCode cityCode;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "country_id", nullable = false, unique = true)
    private Country country;

    @ToString.Exclude
    @OneToMany(mappedBy = "city", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        City city = (City) o;
        return getId() != null && Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public City(CityRegistry cityRegistry) {
        CountryRegistry countryRegistry = new CountryRegistry(cityRegistry.country().getCountryCode());
        this.cityCode = cityRegistry.cityCode();
        this.country = new Country(countryRegistry);
    }
}