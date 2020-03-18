package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;
import lk.gov.govtech.covid19.util.Constants;
import lk.gov.govtech.covid19.service.DHIS2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all DHIS2 related api calls.
 * User registration calls should be implemented here
 */
@RestController
@RequestMapping(value = Constants.DHIS_API_CONTEXT)
public class DHISController {

    @Autowired
    private DHIS2Service dhis2Service;

    @PostMapping(path = "/entity-instances", consumes = "application/json", produces = "application/json")
    public ResponseEntity createEntityInstance(@RequestBody EntityInstance entityInstance) {

        DHISResponse response = dhis2Service.createEntityInstance(entityInstance);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @PostMapping(path = "/enrollements", consumes = "application/json", produces = "application/json")
    public ResponseEntity createEnrollment(@RequestBody Enrollment enrollment) {

        DHISResponse response = dhis2Service.createEnrollment(enrollment);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @PostMapping(path = "/events", consumes = "application/json", produces = "application/json")
    public ResponseEntity createEvents(@RequestBody Events events) {

        DHISResponse response = dhis2Service.createEvents(events);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @GetMapping(path = "/entity-types", produces = "application/json")
    public ResponseEntity getEntityType() {

        DHISResponse response = dhis2Service.getEntityTypes();
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @GetMapping(path = "/organization-units", produces = "application/json")
    public ResponseEntity getOrganizationUnits() {

        DHISResponse response = dhis2Service.getOrganizationUnits();
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @GetMapping(path = "/entity-attributes", produces = "application/json")
    public ResponseEntity getEntityAttributes() {

        DHISResponse response = dhis2Service.getEntityAttributes();
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

    @GetMapping(path = "/programmes", produces = "application/json")
    public ResponseEntity getProgrammes() {

        DHISResponse response = dhis2Service.getProgrammes();
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }
    
    @PostMapping(path = "/passenger-information", produces = "application/json")
    public ResponseEntity pushPassengerInformation(@RequestBody FlightPassengerInformation fpInfo) {
        DHISResponse response = dhis2Service.pushFlightPassengerInformation(fpInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }
    
    @GetMapping(path = "/infobordertest/{a}/{b}", produces = "application/json")
    public ResponseEntity getInfoBorderPassengerList(String a, String b) {
        String content = "[{\n" + 
                "    \"recordSequence\": 0,\n" + 
                "    \"submitedDate\": null,\n" + 
                "    \"flightInformation\": {\n" + 
                "        \"flightNumber\": null,\n" + 
                "        \"flightDateTime\": \"2020-05-01 05:01:02\",\n" + 
                "        \"arriveFromPort\": null,\n" + 
                "        \"landedPort\": null,\n" + 
                "        \"carrierCode\": null,\n" + 
                "        \"arrivalPassengerCount\": 100,\n" + 
                "        \"transitPassengerCount\": 100\n" + 
                "    },\n" + 
                "    \"passengerInformation\": {\n" + 
                "        \"passportNumber\": null,\n" + 
                "        \"nationality\": null,\n" + 
                "        \"initials\": null,\n" + 
                "        \"surname\": null,\n" + 
                "        \"middleName\": null,\n" + 
                "        \"givenName\": null,\n" + 
                "        \"idCardNumber\": null,\n" + 
                "        \"dateOfBirth\": null,\n" + 
                "        \"gender\": null,\n" + 
                "        \"emailAddress\": null,\n" + 
                "        \"countryOfResidence\": null,\n" + 
                "        \"arriveFrom\": null,\n" + 
                "        \"arrivalCardNumber\": null,\n" + 
                "        \"purposeOfVisit\": null,\n" + 
                "        \"requestedVisaDays\": 0,\n" + 
                "        \"destinationCity\": null,\n" + 
                "        \"arrivalCardImage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\",\n" + 
                "        \"faceImage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\",\n" + 
                "        \"passportDataPage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\"\n" + 
                "    },\n" + 
                "    \"addressInformation\": [{\n" + 
                "        \"fullAddress\": null,\n" + 
                "        \"addressLine1\": null,\n" + 
                "        \"addressLine2\": null,\n" + 
                "        \"city\": null,\n" + 
                "        \"postalCode\": null,\n" + 
                "        \"stateProvince\": null,\n" + 
                "        \"country\": null\n" + 
                "    }, {\n" + 
                "        \"fullAddress\": null,\n" + 
                "        \"addressLine1\": null,\n" + 
                "        \"addressLine2\": null,\n" + 
                "        \"city\": null,\n" + 
                "        \"postalCode\": null,\n" + 
                "        \"stateProvince\": null,\n" + 
                "        \"country\": null\n" + 
                "    }],\n" + 
                "    \"contactNumbers\": [{\n" + 
                "        \"contactNumber\": null\n" + 
                "    }, {\n" + 
                "        \"contactNumber\": null\n" + 
                "    }],\n" + 
                "    \"lastDepartureInformation\": {\n" + 
                "        \"departureFlightNo\": null,\n" + 
                "        \"departureDate\": null,\n" + 
                "        \"flightDestination\": null,\n" + 
                "        \"departureCardNo\": null,\n" + 
                "        \"departureDestination\": null\n" + 
                "    }\n" + 
                "}, {\n" + 
                "    \"recordSequence\": 0,\n" + 
                "    \"submitedDate\": null,\n" + 
                "    \"flightInformation\": {\n" + 
                "        \"flightNumber\": null,\n" + 
                "        \"flightDateTime\": \"2020-05-01 05:01:02\",\n" + 
                "        \"arriveFromPort\": null,\n" + 
                "        \"landedPort\": null,\n" + 
                "        \"carrierCode\": null,\n" + 
                "        \"arrivalPassengerCount\": 100,\n" + 
                "        \"transitPassengerCount\": 100\n" + 
                "    },\n" + 
                "    \"passengerInformation\": {\n" + 
                "        \"passportNumber\": null,\n" + 
                "        \"nationality\": null,\n" + 
                "        \"initials\": null,\n" + 
                "        \"surname\": null,\n" + 
                "        \"middleName\": null,\n" + 
                "        \"givenName\": null,\n" + 
                "        \"idCardNumber\": null,\n" + 
                "        \"dateOfBirth\": null,\n" + 
                "        \"gender\": null,\n" + 
                "        \"emailAddress\": null,\n" + 
                "        \"countryOfResidence\": null,\n" + 
                "        \"arriveFrom\": null,\n" + 
                "        \"arrivalCardNumber\": null,\n" + 
                "        \"purposeOfVisit\": null,\n" + 
                "        \"requestedVisaDays\": 0,\n" + 
                "        \"destinationCity\": null,\n" + 
                "        \"arrivalCardImage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\",\n" + 
                "        \"faceImage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\",\n" + 
                "        \"passportDataPage\": \"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAATABQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9/KKKKACiiigAooooAKKKKAP/2Q==\"\n" + 
                "    },\n" + 
                "    \"addressInformation\": [{\n" + 
                "        \"fullAddress\": null,\n" + 
                "        \"addressLine1\": null,\n" + 
                "        \"addressLine2\": null,\n" + 
                "        \"city\": null,\n" + 
                "        \"postalCode\": null,\n" + 
                "        \"stateProvince\": null,\n" + 
                "        \"country\": null\n" + 
                "    }, {\n" + 
                "        \"fullAddress\": null,\n" + 
                "        \"addressLine1\": null,\n" + 
                "        \"addressLine2\": null,\n" + 
                "        \"city\": null,\n" + 
                "        \"postalCode\": null,\n" + 
                "        \"stateProvince\": null,\n" + 
                "        \"country\": null\n" + 
                "    }],\n" + 
                "    \"contactNumbers\": [{\n" + 
                "        \"contactNumber\": null\n" + 
                "    }, {\n" + 
                "        \"contactNumber\": null\n" + 
                "    }],\n" + 
                "    \"lastDepartureInformation\": {\n" + 
                "        \"departureFlightNo\": null,\n" + 
                "        \"departureDate\": null,\n" + 
                "        \"flightDestination\": null,\n" + 
                "        \"departureCardNo\": null,\n" + 
                "        \"departureDestination\": null\n" + 
                "    }\n" + 
                "}]";
        return ResponseEntity.status(200).body(content);
    }

}
