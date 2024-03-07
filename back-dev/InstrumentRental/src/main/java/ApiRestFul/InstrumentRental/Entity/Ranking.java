package ApiRestFul.InstrumentRental.Entity;


import ApiRestFul.InstrumentRental.Registry.RankingRegistry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ranking")
public class Ranking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    @Column(name = "score")
    private Integer score;


}
