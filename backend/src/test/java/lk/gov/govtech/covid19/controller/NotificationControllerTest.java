package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.service.NotificationService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static lk.gov.govtech.covid19.util.Constants.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NotificationService notificationService;

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void post_notificationAlert_success() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject title = new JSONObject();
        JSONObject message = new JSONObject();

        title.put("english", "english title");
        message.put("english", "english message");

        json.put("source", "the source");
        json.put("title", title);
        json.put("message", message);

        doNothing().when(notificationService).addAlertNotificaiton(new AlertNotificationRequest());

        this.mockMvc.perform(post(NOTIFICATION_API_CONTEXT + "/alert/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toString()))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    public void post_notificationAlert_unauthorized() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject title = new JSONObject();
        JSONObject message = new JSONObject();

        title.put("english", "english title");
        message.put("english", "english message");

        json.put("source", "the source");
        json.put("title", title);
        json.put("message", message);

        doNothing().when(notificationService).addAlertNotificaiton(new AlertNotificationRequest());

        this.mockMvc.perform(post(NOTIFICATION_API_CONTEXT + "/alert/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void post_notificationAlert_noContent() throws Exception {
        doNothing().when(notificationService).addAlertNotificaiton(new AlertNotificationRequest());

        this.mockMvc.perform(post(NOTIFICATION_API_CONTEXT + "/alert/add")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = { AUTHORITY_NOTIFICATION })
    public void post_notificationAlert_missingField() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject title = new JSONObject();
        JSONObject message = new JSONObject();

        doNothing().when(notificationService).addAlertNotificaiton(new AlertNotificationRequest());

        message.put("english", "english message");
        json.put("source", "the source");
        //missing field - title
        json.put("message", message);
        this.mockMvc.perform(post(NOTIFICATION_API_CONTEXT + "/alert/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toString()))
                .andExpect(status().isBadRequest());

        json.put("title", title); //title is an empty json
        this.mockMvc.perform(post(NOTIFICATION_API_CONTEXT + "/alert/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json.toString()))
                .andExpect(status().isBadRequest());
    }
}
