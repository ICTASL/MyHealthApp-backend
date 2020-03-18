package lk.gov.govtech.covid19.dto;

import lombok.Data;

/**
 * Represents the last departure information.
 */
@Data
public class LastDepartureInformation {
    String departureFlightNo;
    String departureDate;
    String flightDestination;
    String departureCardNo;
}
