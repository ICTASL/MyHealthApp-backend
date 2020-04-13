package lk.gov.govtech.covid19.dto;

import lombok.Data;

/**
 * Represents the passenger information.
 */
@Data
public class PassengerInformation {
    String passportNumber;
    String nationality;
    String initials;
    String surname;
    String middleName;
    String givenName;
    String idCardNumber;
    String dateOfBirth;
    String gender;
    String emailAddress;
    String countryOfResidence;
    String arriveFrom;
    String arrivalCardNumber;
    String purposeOfVisit;
    int requestedVisaDays;
    String destinationCity;
    String arrivalCardImage;
    String faceImage;
    String passportDataPage;
}
