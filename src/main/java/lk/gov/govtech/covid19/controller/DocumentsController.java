package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for all documents required by the mobile app
 */
@Controller
@Slf4j
@RequestMapping(value = Constants.DOCUMENTS_API_CONTEXT)
public class DocumentsController {

    @GetMapping(path = "/privacy-policy")
    public String privacyPolicy(Model model) {
        log.info("Returning documents/privacy-policy");
        return "documents/PrivacyPolicy";
    }

}
