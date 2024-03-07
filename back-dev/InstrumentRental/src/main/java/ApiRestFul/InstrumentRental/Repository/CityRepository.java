package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.City;
import ApiRestFul.InstrumentRental.Enum.CityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Transactional
    @Modifying
    @Query("update City c set c.cityCode = ?1 where c.id = ?2")
    int updateCity(CityCode cityCode, Long id);

    @Transactional
    @Modifying
    @Query("delete from City c where c.id = ?1")
    int deleteCity(Long id);




}