package ApiRestFul.InstrumentRental.Service;
import ApiRestFul.InstrumentRental.DTO.CategoryDTO;
import ApiRestFul.InstrumentRental.Entity.Category;
import ApiRestFul.InstrumentRental.Registry.CategoryRegistry;
import ApiRestFul.InstrumentRental.Repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> listCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.size()>0){
            return ResponseEntity.ok(categoryList.stream().map(CategoryDTO::new));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body("No hay Categorias Registradas en la BD!");
        }
    }
    public ResponseEntity<?> categoryRegistry(CategoryRegistry categoryRegistry){
        if(!categoryRepository.existsCategoryByName(categoryRegistry.categoryTitle())){
            return new ResponseEntity<>(new CategoryDTO(categoryRepository.save(new Category(categoryRegistry))),
                    HttpStatus.CREATED);
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Categoria ya registrada!");
        }
    }
    public ResponseEntity<?> findCategoryByTitle(String categoryTitle){
        if(categoryRepository.existsCategoryByName(categoryTitle)){
            Category category = categoryRepository.findCategoryByName(categoryTitle);
            return new ResponseEntity<>(new CategoryDTO(category),HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria no encontrada en la BD!");
        }
    }
    public ResponseEntity<?> updateCategory(CategoryRegistry categoryRegistry){
        String categoryTitle = categoryRegistry.categoryTitle();
        if(categoryRepository.existsCategoryByName(categoryTitle)){
            if(categoryRepository.updateCategoryByName(categoryRegistry.categoryDescription(),categoryRegistry.categoryImage(),
                    categoryTitle) > 0){
                return ResponseEntity.ok(new CategoryDTO(categoryRepository.findCategoryByName(categoryTitle)));
            } else {
                return new ResponseEntity<>("La categoria no fue actualizada en la BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria no encontrada en la BD!");
        }
    }
    public ResponseEntity<?> deleteCategory(String categoryTitle){
        if(categoryRepository.existsCategoryByName(categoryTitle)){
            if(categoryRepository.deleteCategoryByName(categoryTitle)>0){
                return new ResponseEntity<>("Categoria Borrada de BD exitosamente!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("La categoria no fue borrada de la BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria no encontrada en la BD!");
        }
    }
}
