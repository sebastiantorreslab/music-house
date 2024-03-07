package ApiRestFul.InstrumentRental.Enum;
import java.util.Arrays;
import java.util.List;

public enum CityCode {
    BOGOTA,
    BUENOS_AIRES,
    CALI,
    CORDOBA,
    MEDELLIN,
    MENDOZA;
    public static final List<CityCode> cityList = Arrays.stream(CityCode.values()).toList();
}
