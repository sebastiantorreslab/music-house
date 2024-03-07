package ApiRestFul.InstrumentRental.DTO;
import ApiRestFul.InstrumentRental.Entity.Instrument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ApiRestFul.InstrumentRental.Entity.Instrument} entity
 */
@Data
public class InstrumentDTO implements Serializable {
    @NotBlank(message = "Blank not allowed")
    @Size(message = "Check length", min = 3, max = 40)
    private final String instName;
    private final String instBrand;
    private final boolean instIsActive;
    private final Long category;
    private final String instDescription;
    private final String instPhoto;
    private final Double instPrice;
    private final String instTagNumber;

    private final double ranking_prom; //TODO: IMPLEMENTAR LA DEVOLUCIÓN DEL RANKING PROM Y RAKING COUNT DESDE ESTE DTO?
    private final int ranking_count;


    public InstrumentDTO(Instrument instrument) {
        this.instName = instrument.getInstName();
        this.instBrand = instrument.getInstBrand();
        this.instIsActive = instrument.isInstIsActive();
        this.category = instrument.getCategory().getId();
        this.instDescription = instrument.getInstDescription();
        this.instPhoto = instrument.getInstPhoto();
        this.instPrice = instrument.getInstPrice();
        this.instTagNumber = instrument.getInstTagNumber();
        this.ranking_prom = instrument.getRankingProm();
        this.ranking_count = instrument.getRankingCount();
    }







}