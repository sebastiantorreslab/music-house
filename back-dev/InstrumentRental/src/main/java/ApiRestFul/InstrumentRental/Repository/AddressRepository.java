package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Transactional
    @Modifying
    @Query("delete from Address a where a.id = ?1")
    int deleteAddress(Long id);

    @Transactional
    @Modifying
    @Query("update Address a set a.custAddress = ?1 where a.id = ?2")
    int updateAddress(String custAddress, Long id);


}