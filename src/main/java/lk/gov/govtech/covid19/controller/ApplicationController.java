package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.NotificationResponse;
import lk.gov.govtech.covid19.service.ApplicationService;
import lk.gov.govtech.covid19.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all task force application related apis
 */
@Slf4j
@RestController
@RequestMapping(value = Constants.TF_API_CONTEXT)
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping(path = "/message/{messageId}/{lang}", produces = "application/json")
    public ResponseEntity getMessage(@PathVariable("messageId") String messageId, @PathVariable("lang") String lang) {

        NotificationResponse response = applicationService.getNotification(messageId, lang);

        if (response == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.accepted().body(response);
        }

    }
}
