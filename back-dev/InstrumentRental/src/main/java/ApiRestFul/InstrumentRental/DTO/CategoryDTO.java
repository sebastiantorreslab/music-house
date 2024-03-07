package ApiRestFul.InstrumentRental.DTO;
import ApiRestFul.InstrumentRental.Entity.Category;
import lombok.Data;

@Data
public class CategoryDTO {
    private final Long Id;
    private final String categoryTitle;
    private final String categoryDescription;
    private final String cegoryImageURL;

    public CategoryDTO(Category category) {
        this.Id = category.getId();
        this.categoryTitle = category.getCategoryTitle();
        this.categoryDescription = category.getCategoryDescription();
        this.cegoryImageURL = category.getCategoryImage();
    }
}
