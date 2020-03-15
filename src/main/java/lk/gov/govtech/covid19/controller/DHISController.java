package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.NotificationResponse;
import lk.gov.govtech.covid19.dto.Patient;
import lk.gov.govtech.covid19.util.Constants;
import lk.gov.govtech.covid19.util.DHISConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all DHIS2 related api calls.
 * User registration calls should be implemented here
 */
@RestController
@RequestMapping(value = Constants.DHIS_API_CONTEXT)
public class DHISController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHISController.class);

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity register(@RequestBody Patient patient) {

        LOGGER.info("COVID registration invoked....: " + patient.getFirstName() + " " + patient.getLastName());
        DHISConnector dhisConnector = new DHISConnector();
        dhisConnector.register("");
        return ResponseEntity.accepted().build();
    }

    @GetMapping(path = "/entitytypes", produces = "application/json")
    public ResponseEntity getEntityType() {

        DHISConnector dhisConnector = new DHISConnector();
        String entityTypes = dhisConnector.getEntityTypes();
        if (StringUtils.isEmpty(entityTypes)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(entityTypes);
        }
    }

    @GetMapping(path = "/organizationunits", produces = "application/json")
    public ResponseEntity getOrganizationUnits() {

        DHISConnector dhisConnector = new DHISConnector();
        String organizationUnits = dhisConnector.getOrganizationUnits();
        if (StringUtils.isEmpty(organizationUnits)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(organizationUnits);
        }
    }

}
