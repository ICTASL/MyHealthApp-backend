package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.Patient;
import lk.gov.govtech.covid19.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    Logger logger = LoggerFactory.getLogger(DHISController.class);

    @PostMapping(path= "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity register(@RequestBody Patient patient) {
        System.out.println("COVID registration invoked....!");
        return ResponseEntity.accepted().build();
    }
}
