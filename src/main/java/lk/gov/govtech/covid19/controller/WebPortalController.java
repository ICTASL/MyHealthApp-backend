package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.config.GAPIConfigurations;
import lk.gov.govtech.covid19.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static lk.gov.govtech.covid19.util.Constants.*;

/**
 * Controller class for all health promotion bureau related APIs. i.e: from web portal
 */
@Slf4j(topic="PortalAccess")
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
    public String news(Model model, Principal principal) {
        log.info("GET {} username:{}", "/portal/news", principal.getName());
        return "newsPortal";
    }

    @GetMapping(CASES_PATH)
    public String cases(Model model, Principal principal) {
        log.info("GET {} username:{}", "/portal/cases", principal.getName());
        model.addAttribute("mapKey", gAPIConfig.getMapKey());
        return "casePortal";
    }

    @GetMapping(DASHBOARD_PATH)
    public String dashboard(Model model, Principal principal) {
        log.info("GET {} username:{}", "/portal/cases", principal.getName());
        return "dashboard";
    }
}
