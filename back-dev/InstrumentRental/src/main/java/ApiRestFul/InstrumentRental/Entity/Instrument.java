package ApiRestFul.InstrumentRental.Entity;

import ApiRestFul.InstrumentRental.Registry.InstrumentRegistry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

import java.util.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "instrument", uniqueConstraints = {
        @UniqueConstraint(name = "uc_instrument_category_id", columnNames = {"category_id"})
})
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(message = "Check long", min = 3, max = 40)
    @Column(name = "inst_name", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instName;

    @Column(name = "inst_brand", nullable = false, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instBrand;

    @Column(name = "inst_tag_number", nullable = false, unique = true, length = 45)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instTagNumber;

    @Column(name = "inst_acq_date", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date instAcqDate;

    @Column(name = "inst_is_active", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private boolean instIsActive;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "inst_description", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instDescription;

    @URL
    @Column(name = "inst_photo", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String instPhoto;

    @Column(name = "inst_price", nullable = false)
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private Double instPrice;

    @Column(name = "ranking_prom")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    private Double rankingProm;

    @Column(name = "ranking_count")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer rankingCount;

    @OneToMany(mappedBy = "instrument", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Ranking> rankingSet = new HashSet<>();

    @ManyToMany(mappedBy = "instruments", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Customer> customers = new LinkedHashSet<>();



    //TODO: SE AGREGA RELACIÓN UNO A MUCHOS - UN INST PUEDE TENER MUCHAS PUNTUACIONES.
    //TODO: EL RANKING DEBE PASAR POR EL REGISTRY PARA FUNCIONAR?



    public Instrument(InstrumentRegistry instrumentRegistry, Category category) {
        this.instName = instrumentRegistry.instName();
        this.instBrand = instrumentRegistry.instBrand();
        this.instTagNumber = instrumentRegistry.instTagNumber();
        this.instAcqDate = instrumentRegistry.instAcqDate();
        this.instIsActive = instrumentRegistry.instIsActive();
        this.category = category;
        this.instDescription = instrumentRegistry.instDescription();
        this.instPhoto = instrumentRegistry.instPhoto();
        this.instPrice = instrumentRegistry.instPrice();
        this.rankingProm = instrumentRegistry.ranking_prom();
        this.rankingCount = instrumentRegistry.ranking_count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Instrument that = (Instrument) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}