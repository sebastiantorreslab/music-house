package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.Registry.CategoryRegistry;
import ApiRestFul.InstrumentRental.Service.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    public static final int DEFAULT_PAGE_SIZE = 2;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/registry")
    public ResponseEntity<?> categoryRegistry(@RequestBody @Valid CategoryRegistry categoryRegistry){
        return categoryService.categoryRegistry(categoryRegistry);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> updateCategoryByTitle(@RequestBody @Valid CategoryRegistry categoryRegistry){
        return categoryService.updateCategory(categoryRegistry);
    }
    @DeleteMapping(value = "/{categoryTitle}")
    public ResponseEntity<?> deleteCategoryByTitle(@PathVariable("categoryTitle") String categotyTitle){
        return categoryService.deleteCategory(categotyTitle);
    }
}
