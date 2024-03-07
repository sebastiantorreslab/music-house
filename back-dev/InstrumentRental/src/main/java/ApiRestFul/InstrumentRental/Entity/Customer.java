package ApiRestFul.InstrumentRental.Entity;
import ApiRestFul.InstrumentRental.Enum.Role;
import ApiRestFul.InstrumentRental.Registry.AddressRegistry;
import ApiRestFul.InstrumentRental.Registry.AdminRegistry;
import ApiRestFul.InstrumentRental.Registry.CustomerRegistry;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Digits(message = "Only Digits Max 10", integer = 10, fraction = 0)
    @NotBlank(message = "Blank Not Allowed")
    @Column(name = "cust_dni", nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custDni;

    @Size(message = "Max 45 Min 2", min = 2, max = 45)
    @NotBlank(message = "Blank not Allowed")
    @Column(name = "cust_firs_name", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custFirsName;

    @Size(message = "Max 45 Min 2", min = 2, max = 45)
    @NotBlank(message = "Blanck not Allowed")
    @Column(name = "cust_last_name", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custLastName;

    @Email(message = "example@domain.com", regexp = "^(.+)@(.+)$")
    @Column(name = "cust_email", nullable = false, unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custEmail;

    @Size(message = "Max 255 Min 1", min = 1, max = 255)
    @NotBlank(message = "Blank not Allowed")
    @Column(name = "cust_password", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custPassword;

    @Digits(message = "Only Numbers", integer = 25, fraction = 0)
    @Column(name = "cust_phone", length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String custPhone;

    @NotNull(message = "Null not Allowed")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Role role;

    @NotNull(message = "Null not Allowed")
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rental",
            joinColumns = @JoinColumn(name = "cust_dni"),
            inverseJoinColumns = @JoinColumn(name = "inst_tag_number"))
    private Set<Instrument> instruments = new LinkedHashSet<>();

    public Customer(CustomerRegistry customerRegistry) {
        AddressRegistry addressRegistry = new AddressRegistry(customerRegistry.address().getCustAddress(),
                customerRegistry.address().getCity());
        this.custDni = customerRegistry.custDni();
        this.custFirsName = customerRegistry.custFirsName();
        this.custLastName = customerRegistry.custLastName();
        this.custEmail = customerRegistry.custEmail();
        this.custPassword = customerRegistry.custPassword();
        this.custPhone = customerRegistry.custPhone();
        this.role = customerRegistry.role();
        this.address = new Address(addressRegistry);
    }
    public Customer(AdminRegistry customerRegistry) {
        AddressRegistry addressRegistry = new AddressRegistry(customerRegistry.address().getCustAddress(),
                customerRegistry.address().getCity());
        this.custDni = customerRegistry.custDni();
        this.custFirsName = customerRegistry.custFirsName();
        this.custLastName = customerRegistry.custLastName();
        this.custEmail = customerRegistry.custEmail();
        this.custPassword = customerRegistry.custPassword();
        this.custPhone = customerRegistry.custPhone();
        this.role = customerRegistry.role();
        this.address = new Address(addressRegistry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(custFirsName, customer.custFirsName) && Objects.equals(custLastName, customer.custLastName) && Objects.equals(custEmail, customer.custEmail) && Objects.equals(custPassword, customer.custPassword) && Objects.equals(custPhone, customer.custPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(custFirsName, custLastName, custEmail, custPassword, custPhone);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return custPassword;
    }

    @Override
    public String getUsername() {
        return custEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
