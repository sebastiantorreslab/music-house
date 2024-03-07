package ApiRestFul.InstrumentRental.Repository;
import ApiRestFul.InstrumentRental.Entity.Customer;
import ApiRestFul.InstrumentRental.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select (count(c) > 0) from Customer c where c.custDni = ?1")
    boolean existsByDni(String custDni);

    @Query("select c from Customer c where c.custDni = ?1")
    Customer findByCustDni(String custDni);

    @Transactional
    @Modifying
    @Query("""
            update Customer c set c.custFirsName = ?1, c.custLastName = ?2, c.custEmail = ?3, c.custPassword = ?4, c.custPhone = ?5
            where c.custDni = ?6""")
    int updateCustomerByDni(String custFirsName, String custLastName, String custEmail, String custPassword, String custPhone, String custDni);

    @Transactional
    @Modifying
    @Query("update Customer c set c.role = ?1 where c.custDni = ?2")
    int updateRole(Role role, String custDni);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.custDni = ?1")
    int deleteCustByDni(String custDni);

    @Query("select (count(c) > 0) from Customer c where upper(c.custEmail) = upper(?1)")
    boolean emailAlreadyExists(String custEmail);

    @Query("select c from Customer c where c.custEmail = ?1")
    UserDetails findCustByEmail(String custEmail);

    @Query("select c from Customer c where c.custEmail = ?1")
    Customer getCustByEmail(String custEmail);
}