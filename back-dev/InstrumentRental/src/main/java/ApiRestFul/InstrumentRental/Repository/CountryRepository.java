package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.Country;
import ApiRestFul.InstrumentRental.Enum.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Transactional
    @Modifying
    @Query("update Country c set c.countryCode = ?1 where c.id = ?2")
    int updateCountry(CountryCode countryCode, Long id);

    @Transactional
    @Modifying
    @Query("delete from Country c where c.id = ?1")
    int deleteCountry(Long id);



}