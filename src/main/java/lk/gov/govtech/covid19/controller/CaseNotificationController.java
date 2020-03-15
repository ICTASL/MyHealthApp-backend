package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.util.Constants;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all epid related APIs. i.e: from web portal
 */
@RestController
@RequestMapping(value = Constants.CASE_API_CONTEXT)
public class CaseNotificationController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "casePortal";
    }
}
