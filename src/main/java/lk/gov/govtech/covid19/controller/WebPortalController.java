package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all health promotion bureau related APIs. i.e: from web portal
 */
@Controller
@RequestMapping(value = Constants.PORTAL_API_CONTEXT)
public class WebPortalController {

    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(path = Constants.NEWS_API_CONTEXT, method = RequestMethod.GET)
    public String news(Model model) {
        return "newsPortal";
    }

    @RequestMapping(path = Constants.CASE_API_CONTEXT, method = RequestMethod.GET)
    public String cases(Model model) {
        return "casePortal";
    }
}
