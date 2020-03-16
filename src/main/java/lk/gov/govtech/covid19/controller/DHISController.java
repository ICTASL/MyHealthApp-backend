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
import org.springframework.util.StringUtils;
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
    
    @PostMapping(path = "/passenger-information", produces = "application/json")
    public ResponseEntity pushPassengerInformation(@RequestBody FlightPassengerInformation fpInfo) {
        DHISResponse response = dhis2Service.pushFlightPassengerInformation(fpInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getResponse());
    }

}
