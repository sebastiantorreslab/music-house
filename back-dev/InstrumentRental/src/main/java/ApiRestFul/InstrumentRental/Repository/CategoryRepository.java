package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select (count(c) > 0) from Category c where upper(c.categoryTitle) = upper(?1)")
    boolean existsCategoryByName(String categoryTitle);

    @Query("select c from Category c where upper(c.categoryTitle) = upper(?1)")
    Category findCategoryByName(String categoryTitle);

    @Transactional
    @Modifying
    @Query("""
            update Category c set c.categoryDescription = ?1, c.categoryImage = ?2
            where upper(c.categoryTitle) = upper(?3)""")
    int updateCategoryByName(@Nullable String categoryDescription, @Nullable String categoryImage, String categoryTitle);

    @Transactional
    @Modifying
    @Query("delete from Category c where upper(c.categoryTitle) = upper(?1)")
    int deleteCategoryByName(String categoryTitle);





}