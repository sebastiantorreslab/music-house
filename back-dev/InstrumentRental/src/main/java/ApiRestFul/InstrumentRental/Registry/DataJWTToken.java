package ApiRestFul.InstrumentRental.Registry;

public record DataJWTToken(String custDni,
                           String custName,
                           String custLastName,
                           String custRole,
                           String jwtToken) {
}
