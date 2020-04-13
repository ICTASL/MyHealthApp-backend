package lk.gov.govtech.covid19.dto;

import lombok.Data;

/**
 * Represents the flight information.
 */
@Data
public class FlightInformation {
    String flightNumber;
    String flightDateTime;
    String arriveFromPort;
    String landedPort;
    String carrierCode;
    int arrivalPassengerCount;
    int transitPassengerCount;
}
