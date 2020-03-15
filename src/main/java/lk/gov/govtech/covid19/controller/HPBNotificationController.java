package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.util.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for all health promotion bureau related APIs. i.e: from web portal
 */
@RestController
@RequestMapping(value = Constants.HPB_API_CONTEXT)
public class HPBNotificationController {
}
