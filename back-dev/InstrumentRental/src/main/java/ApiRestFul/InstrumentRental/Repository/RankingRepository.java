package ApiRestFul.InstrumentRental.Repository;

import ApiRestFul.InstrumentRental.Entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
}
