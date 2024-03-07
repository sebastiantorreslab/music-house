package ApiRestFul.InstrumentRental.Enum;
import java.util.Arrays;
import java.util.List;

public enum CountryCode {
    COL,
    ARG;
    public static final List<CountryCode> countryList = Arrays.stream(CountryCode.values()).toList();
}

