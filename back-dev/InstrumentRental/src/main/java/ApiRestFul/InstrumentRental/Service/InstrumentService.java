package ApiRestFul.InstrumentRental.Service;
import ApiRestFul.InstrumentRental.DTO.InstrumentDTO;
import ApiRestFul.InstrumentRental.DTO.RankingDTO;
import ApiRestFul.InstrumentRental.Entity.Category;
import ApiRestFul.InstrumentRental.Entity.Instrument;
import ApiRestFul.InstrumentRental.Entity.Ranking;
import ApiRestFul.InstrumentRental.Registry.InstrumentRegistry;
import ApiRestFul.InstrumentRental.Repository.CategoryRepository;
import ApiRestFul.InstrumentRental.Repository.InstrumentRepository;
import ApiRestFul.InstrumentRental.Repository.RankingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;
    private final CategoryRepository categoryRepository;
    private final RankingRepository rankingRepository;
    private final ObjectMapper mapper = new ObjectMapper();



    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository,
                             CategoryRepository categoryRepository, RankingRepository rankingRepository) {
        this.instrumentRepository = instrumentRepository;
        this.categoryRepository = categoryRepository;
        this.rankingRepository = rankingRepository;
    }
    public ResponseEntity<?> findByInstTagNumber(String instTagNumber) {
        if(instrumentRepository.existsByInstTagNumber(instTagNumber)){
            return new ResponseEntity<>(new InstrumentDTO(instrumentRepository.findByInstTagNumber(instTagNumber)),
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El Tag Number del instrumento no encontrado en la BD!",HttpStatus.OK);
        }
    }

    public ResponseEntity<?> instrumentRegistry(InstrumentRegistry instrumentRegistry){
        if(!instrumentRepository.existsByInstTagNumber(instrumentRegistry.instTagNumber())){
            if(categoryRepository.existsCategoryByName(instrumentRegistry.category())){
                Category instCategory = categoryRepository.findCategoryByName(instrumentRegistry.category());
                Instrument instrument = new Instrument(instrumentRegistry,instCategory);
                InstrumentDTO instrumentDTO = new InstrumentDTO(instrumentRepository.save(instrument));
                return ResponseEntity.ok(instrumentDTO);
            } else {
                return ResponseEntity.badRequest().body("Categoria no creada en BD!");
            }
        } else{
            return ResponseEntity.badRequest().body("Tag Number de instrumento ya registrado en BD!");
        }
    }
    public ResponseEntity<?> listAllInstruments(Pageable pageagle){
        Page<Instrument> response = instrumentRepository.findAll(pageagle);
        if(response.hasContent()){
            return ResponseEntity.ok(response
                    .map(InstrumentDTO::new));
        }else{
            return new ResponseEntity<>("No hay instrumentos registrados en la BD!",HttpStatus.OK);
        }
    }
    public ResponseEntity<?> listInstrActive(Pageable pageable){
        boolean isActive = true;
        Page<InstrumentDTO> response = instrumentRepository.listAllInstrumentsActive(isActive,pageable).map(InstrumentDTO::new);
        if(response.hasContent()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Customer Results", "Only Active Instruments");
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("No hay instrumentos registrados en la BD!",HttpStatus.OK);
        }
    }
    public ResponseEntity<?> listInstsByBrand(String instBrand, Pageable pageable){
        if(instrumentRepository.existsBrand(instBrand)){
            Page<InstrumentDTO> pageInstList = instrumentRepository.instrumentsByBrand(instBrand,true,pageable)
                    .map(InstrumentDTO::new);
            return ResponseEntity.ok(pageInstList);
        } else{
            return ResponseEntity.status(HttpStatus.OK).body("La marca no existe en BD!");
        }
    }
    public ResponseEntity<?> listInstsByCategory(String categoryTitle, Pageable pageable){
        if(categoryRepository.existsCategoryByName(categoryTitle)){
            Long categoryId = categoryRepository.findCategoryByName(categoryTitle).getId();
            Page<InstrumentDTO> pageInstList = instrumentRepository.instrumentsByCategory(categoryId,true, pageable)
                    .map(InstrumentDTO::new);
            return ResponseEntity.ok(pageInstList);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("La categoria no existe en BD!");
        }
    }

    public ResponseEntity<?> updateInstrument(InstrumentRegistry instrumentRegistry){
        if(instrumentRepository.existsByInstTagNumber(instrumentRegistry.instTagNumber())){
            Category category = null;
            if(instrumentRegistry.category() != null){
                if(categoryRepository.existsCategoryByName(instrumentRegistry.category())){
                    category = categoryRepository.findCategoryByName(instrumentRegistry.category());
                } else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoria no existe en BD!");
                }
            }
            int updatedInstruments = instrumentRepository.updateInstrument(instrumentRegistry.instName(), instrumentRegistry.instBrand(),
                    instrumentRegistry.instAcqDate(), category, instrumentRegistry.instDescription(),
                    instrumentRegistry.instPhoto(), instrumentRegistry.instPrice(),
                    instrumentRegistry.instTagNumber());
            if(updatedInstruments>0){
                return new ResponseEntity<>("Instrumento Actualizado en la BD Exitosamente!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("El Instrumento no fue actualizado en la BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            return new ResponseEntity<>("Tag Number de Instrumento no encontrado en BD!",HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<String> deleteInstByTagNumber(String instTagNumber){
        if(instrumentRepository.existsByInstTagNumber(instTagNumber)){
            if(instrumentRepository.deleteByInstTagNumber(instTagNumber)>0){
                return new ResponseEntity<>("Instrumento Borrado de BD exitosamente!", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("El Instrumento no fue borrado de la BD!",HttpStatus.NOT_ACCEPTABLE);
            }
        } else{
            return new ResponseEntity<>("Tag Number de Instrumento no encontrado en BD!",HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<String> deleteInstLogic(String instTagNumber){
        if(instrumentRepository.deleteInstLogic(false,instTagNumber)>0){
            return new ResponseEntity<>("Instrumento Inactivado de la BD! IsActive --- false", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("El Instrumento no fue desactivado de la BD!",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public void setRankingsForInstrument(RankingDTO rankingDTO, String instTagNumber) {
        Instrument instrument = instrumentRepository.findByInstTagNumber(instTagNumber);
        if(rankingDTO != null && instrument != null ){
            Ranking ranking = mapper.convertValue(rankingDTO, Ranking.class);
            ranking.setInstrument(instrument);
            rankingRepository.save(ranking);
            instrument.setRankingProm(setRankingProm(instrument));
            instrument.setRankingCount(instrument.getRankingSet().size());
            instrumentRepository.save(instrument); //TODO: VALIDAR QUE ESTE PUNTO NO INTERFIERA CON LA OPERACIÓN NORMAL DE INSTRUMENT NI CREE UNO NUEVO.

        }else {
            throw new RuntimeException("Ranking is not set");
        }

    }

    public Double setRankingProm(Instrument instrument) {
        Set<Ranking> rankingSet = instrument.getRankingSet();
        double sum = 0;
        for(Ranking ranking:rankingSet){
            sum += ranking.getScore().doubleValue(); // todo: porque esto devuelve null
        }
        Double  prom = (sum / rankingSet.size());
        return prom;
    }








}
