package ApiRestFul.InstrumentRental.Entity;
import ApiRestFul.InstrumentRental.Enum.CountryCode;
import ApiRestFul.InstrumentRental.Registry.CountryRegistry;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "country_code", nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private CountryCode countryCode;

    @ToString.Exclude
    @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();

    public Country(CountryRegistry countryRegistry) {
        this.countryCode = countryRegistry.countryCode();
    }
}