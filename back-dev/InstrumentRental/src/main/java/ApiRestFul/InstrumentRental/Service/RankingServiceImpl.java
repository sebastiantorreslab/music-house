package ApiRestFul.InstrumentRental.Service;

import ApiRestFul.InstrumentRental.Repository.InstrumentRepository;
import ApiRestFul.InstrumentRental.Repository.RankingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RankingServiceImpl implements IRankingService {

    private final InstrumentRepository instrumentRepository;
    private final RankingRepository rankingRepository;

    public RankingServiceImpl(InstrumentRepository instrumentRepository, RankingRepository rankingRepository) {
        this.instrumentRepository = instrumentRepository;
        this.rankingRepository = rankingRepository;
    }



}
