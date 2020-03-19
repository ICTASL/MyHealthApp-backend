package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.config.GAPIConfigurations;
import lk.gov.govtech.covid19.config.PortalUserConfiguration;
import lk.gov.govtech.covid19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static lk.gov.govtech.covid19.util.Constants.*;

/**
 * Controller class for all health promotion bureau related APIs. i.e: from web portal
 */
@Controller
@RequestMapping(value = Constants.PORTAL_API_CONTEXT)
public class WebPortalController {

    @Autowired
    GAPIConfigurations gAPIConfig;

    @GetMapping
    public String login(Model model) {
        return "login";
    }

    @GetMapping(NEWS_PATH)
    public String news(Model model) {
        return "newsPortal";
    }

    @GetMapping(CASES_PATH)
    public String cases(Model model) {
        model.addAttribute("mapKey", gAPIConfig.getMapKey());
        return "casePortal";
    }
}
