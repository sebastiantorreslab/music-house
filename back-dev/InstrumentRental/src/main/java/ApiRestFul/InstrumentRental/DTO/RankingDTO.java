package ApiRestFul.InstrumentRental.DTO;

import ApiRestFul.InstrumentRental.Entity.Instrument;
import ApiRestFul.InstrumentRental.Entity.Ranking;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankingDTO implements Serializable {

    private Integer score;
}


