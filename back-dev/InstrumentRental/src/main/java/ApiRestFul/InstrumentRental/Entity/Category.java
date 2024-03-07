package ApiRestFul.InstrumentRental.Entity;
import ApiRestFul.InstrumentRental.Registry.CategoryRegistry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Pattern(message = "Only Letters", regexp = "[a-zA-Z À-ú \\\\u00F1\\\\u00D1]+")
    @Size(message = "Min 3 Max 45", min = 3, max = 45)
    @NotBlank
    @ToString.Exclude
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "category_title", nullable = false, unique = true, length = 45)
    private String categoryTitle;

    @Column(name = "category_description", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String categoryDescription;

    @URL
    @Column(name = "category_image", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String categoryImage;

    @ToString.Exclude
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Instrument> instruments = new ArrayList<>();

    public Category(CategoryRegistry categoryRegistry) {
        this.categoryTitle = categoryRegistry.categoryTitle();
        this.categoryDescription = categoryRegistry.categoryDescription();
        this.categoryImage = categoryRegistry.categoryImage();
    }
}