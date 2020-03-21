package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.service.NotificationService;
import lk.gov.govtech.covid19.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j(topic="PortalAccess")
@RestController
@RequestMapping(value = Constants.NOTIFICATION_API_CONTEXT)
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping(path = "/alert/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewAlert(@RequestBody AlertNotificationRequest request, Principal principal){
        log.info("POST {} username:{}", "/alert/add", principal.getName());
        notificationService.addAlertNotificaiton(request);

        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "/case/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity addNewCase(@RequestBody CaseNotificationRequest request, Principal principal){
        log.info("POST {} username:{}", "/case/add", principal.getName());
        notificationService.addCaseNotification(request);

        return ResponseEntity.accepted().build();
    }


}
