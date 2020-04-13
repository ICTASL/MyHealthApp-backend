package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the flight passenger information.
 */
@Data
public class FlightPassengerInformation {
    int recordSequence;
    String submitedDate;
    FlightInformation flightInformation;
    PassengerInformation passengerInformation;
    List<AddressInformation> addressInformation;
    List<ContactNumber> contactNumbers;
    LastDepartureInformation lastDepartureInformation;
}
