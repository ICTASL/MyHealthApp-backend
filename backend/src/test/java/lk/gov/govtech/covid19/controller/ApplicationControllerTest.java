package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.UpdateStatusRequest;
import lk.gov.govtech.covid19.service.ApplicationService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static lk.gov.govtech.covid19.util.Constants.APPLICATION_API_CONTEXT;
import static lk.gov.govtech.covid19.util.Constants.AUTHORITY_NOTIFICATION;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void put_dashboardStatus_success() throws Exception {
        JSONObject json = new JSONObject();
        json.put("lk_total_case", 85);
        json.put("lk_recovered_case", 85);
        json.put("lk_total_deaths", 85);
        json.put("lk_total_suspect", 85);

        UpdateStatusRequest dummy = new UpdateStatusRequest();
        doNothing().when(applicationService).updateStatus(dummy);

        this.mockMvc.perform(put(APPLICATION_API_CONTEXT + "/dashboard/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toString()))
                    .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    public void put_dashboardStatus_unauthorized() throws Exception {
        JSONObject json = new JSONObject();
        json.put("lk_total_case", 85);
        json.put("lk_recovered_case", 85);
        json.put("lk_total_deaths", 85);
        json.put("lk_total_suspect", 85);

        UpdateStatusRequest dummy = new UpdateStatusRequest();
        doNothing().when(applicationService).updateStatus(dummy);

        this.mockMvc.perform(put(APPLICATION_API_CONTEXT + "/dashboard/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void put_dashboardStatus_noContent() throws Exception {
        UpdateStatusRequest dummy = new UpdateStatusRequest();
        doNothing().when(applicationService).updateStatus(dummy);

        this.mockMvc.perform(put(APPLICATION_API_CONTEXT + "/dashboard/status")
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(put(APPLICATION_API_CONTEXT + "/dashboard/status")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void put_dashboardStatus_missingField() throws Exception {
        JSONObject json = new JSONObject();
        //missing field lk_total_case
        json.put("lk_recovered_case", 85);
        json.put("lk_total_deaths", 85);
        json.put("lk_total_suspect", 85);

        UpdateStatusRequest dummy = new UpdateStatusRequest();
        doNothing().when(applicationService).updateStatus(dummy);

        this.mockMvc.perform(put(APPLICATION_API_CONTEXT + "/dashboard/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isBadRequest());
    }
}
