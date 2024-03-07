package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.Category;
import ApiRestFul.InstrumentRental.Entity.Instrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    boolean existsByInstTagNumber(String instTagNumber);
    Instrument findByInstTagNumber(String instTagNumber);
    @Query("select i from Instrument i where i.instIsActive = ?1")
    Page<Instrument> listAllInstrumentsActive(boolean instIsActive, Pageable pageable);
    @Query("select (count(i) > 0) from Instrument i where upper(i.instBrand) = upper(?1)")
    boolean existsBrand(String instBrand);
    @Query("select i from Instrument i where upper(i.instBrand) = upper(?1) and i.instIsActive = ?2")
    Page<Instrument> instrumentsByBrand(String instBrand, boolean instIsActive, Pageable pageable);
    @Query("select i from Instrument i where i.category.id = ?1 and i.instIsActive = ?2")
    Page<Instrument> instrumentsByCategory(Long id, boolean instIsActive, Pageable pageable);
    @Transactional
    @Modifying
    @Query("""
            update Instrument i set i.instName = ?1, i.instBrand = ?2, i.instAcqDate = ?3, i.category = ?4, i.instDescription = ?5, i.instPhoto = ?6, i.instPrice = ?7
            where i.instTagNumber = ?8""")
    int updateInstrument(@Nullable String instName, @Nullable String instBrand, @Nullable Date instAcqDate,
                         @Nullable Category category, @Nullable String instDescription, @Nullable String instPhoto,
                         @Nullable Double instPrice, String instTagNumber);
    @Transactional
    @Modifying
    @Query("delete from Instrument i where upper(i.instTagNumber) = upper(?1)")
    int deleteByInstTagNumber(@NonNull String instTagNumber);
    @Transactional
    @Modifying
    @Query("update Instrument i set i.instIsActive = ?1 where i.instTagNumber = ?2")
    int deleteInstLogic(boolean instIsActive, String instTagNumber);

    @Transactional
    @Modifying
    @Query("update Instrument i set i.rankingProm = ?1, i.rankingCount = ?2 where i.instTagNumber = ?3")
    int updateInstrumentRating(double rankingProm, int rankingCount, String instTagNumber);

    @Transactional
    @Modifying
    @Query("update Instrument i set i.rankingProm = ?1, i.rankingCount = ?2 where i.instTagNumber = ?3")
    void instrumentRating(@Nullable Double rankingProm, @Nullable Integer rankingCount, String instTagNumber);



}
