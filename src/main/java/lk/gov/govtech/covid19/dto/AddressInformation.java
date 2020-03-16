package lk.gov.govtech.covid19.dto;

import lombok.Data;

/**
 * Represents the address information.
 */
@Data
public class AddressInformation {
    String fullAddress;
    String addressLine1;
    String addressLine2;
    String city;
    String postalCode;
    String stateProvince;
    String country;
}
