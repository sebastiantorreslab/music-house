package ApiRestFul.InstrumentRental.Repository;

import ApiRestFul.InstrumentRental.Entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Transactional
    @Modifying
    @Query("update Rental r set r.instrumentRating = ?1, r.review = ?2 where r.id = ?3")
    int rentalRating(@Nullable Integer instrumentRating, @Nullable String review, Long id);

    @Query("select (count(r) > 0) from Rental r where r.id = ?1")
    boolean existsRentalId(Long id);

    @Query("select r from Rental r where r.instTagNumber = ?1 and r.startDate < ?2 and r.endDate > ?3")
    List<Rental> rentalList(String instTagNumber, Date startDate, Date endDate);

    @Query("select r from Rental r where r.instTagNumber = ?1 and r.id <> ?2 and r.startDate < ?3 and r.endDate > ?4")
    List<Rental> rentalListDistinct(String instTagNumber, Long id, Date startDate, Date endDate);

    @Query("select r from Rental r where r.custDni = ?1 order by r.startDate DESC")
    Page<Rental> rentalByCustDni(String custDni, Pageable pageable);

    @Query("select r from Rental r where r.instTagNumber = ?1 order by r.startDate DESC")
    Page<Rental> rentalByInstTagNumber(String instTagNumber, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Rental r set r.startDate = ?1, r.endDate = ?2, r.rentalCharge = ?3 where r.id = ?4")
    int rentalUpdate(Date startDate, Date endDate, Double rentalCharge, Long id);

    @Transactional
    @Modifying
    @Query("delete from Rental r where r.id = ?1")
    int deleteRental(Long id);

    @Query("""
            select r from Rental r
            where r.instTagNumber = ?1 and (r.startDate > ?2 or r.endDate > ?3)
            order by r.startDate""")
    List<Rental> unavailableDatesByInstTagNumber(String instTagNumber, Date startDate, Date endDate);

    @Query("select (count(r) > 0) from Rental r where r.instTagNumber = ?1")
    boolean existsByInstTagNumber(String instTagNumber);

    @Query("select count(r) from Rental r where r.instTagNumber = ?1")
    int updateInstCount(String instTagNumber);

    @Query("select avg (r.instrumentRating) from Rental r where r.instTagNumber = ?1")
    double updateInstAvg(String instTagNumber);

}
