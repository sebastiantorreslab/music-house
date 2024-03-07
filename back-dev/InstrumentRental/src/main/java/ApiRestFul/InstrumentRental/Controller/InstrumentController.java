package ApiRestFul.InstrumentRental.Controller;
import ApiRestFul.InstrumentRental.DTO.RankingDTO;
import ApiRestFul.InstrumentRental.Service.CategoryService;
import ApiRestFul.InstrumentRental.Service.InstrumentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/instruments")
public class InstrumentController {
    public static final int DEFAULT_PAGE_SIZE = 2;
    private final InstrumentService instrumentService;
    private final CategoryService categoryService;

    public InstrumentController(InstrumentService instrumentService, CategoryService categoryService) {
        this.instrumentService = instrumentService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listInstruments(@PageableDefault(size = DEFAULT_PAGE_SIZE)
                                                 Pageable pageable){
        return instrumentService.listInstrActive(pageable);
    }
    @GetMapping("/categories/list")
    public ResponseEntity<?> listCategories(){
        return categoryService.listCategories();
    }
    @GetMapping(value = "/category-detail/{categoryTitle}")
    public ResponseEntity<?> findCategoryByTitle(@PathVariable("categoryTitle") String categotyTitle){
        return categoryService.findCategoryByTitle(categotyTitle);
    }
    @GetMapping(value = "/brand/{brand-name}")
    public ResponseEntity<?> listInstsByBrand(@PathVariable("brand-name") String instBrand,
                                              @PageableDefault(size = DEFAULT_PAGE_SIZE)
                                              Pageable pageable){
        return instrumentService.listInstsByBrand(instBrand, pageable);
    }
    @GetMapping(value = "/category/{category-name}")
    public ResponseEntity<?> listInstsByCategory(@PathVariable("category-name") String category,
                                                 @PageableDefault(size = DEFAULT_PAGE_SIZE)
                                                 Pageable pageable){
        return instrumentService.listInstsByCategory(category, pageable);
    }
    @GetMapping(value = "/{tag}")
    public ResponseEntity<?> findByTagNumber(@PathVariable("tag") String instTagNumber) {
        return instrumentService.findByInstTagNumber(instTagNumber);
    }

    @PostMapping("/ranking/{instTagNumber}") // TODO: ENDPOINT PARA SETEAR RANKING DE PRODUCTO
    public ResponseEntity<?> setRankingsForInstrument(@RequestBody RankingDTO rankingDTO,
                                                      @PathVariable("instTagNumber") String instTagNumber){
        instrumentService.setRankingsForInstrument(rankingDTO,instTagNumber);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
