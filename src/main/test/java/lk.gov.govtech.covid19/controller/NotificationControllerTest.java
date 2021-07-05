package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) //avoiding authentication for tests
class NotificationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Test
    void addNewAlert() throws Exception {
        String jsonContent = "{\n" +
                "   \"source\":\"Test src\",\n" +
                "   \"title\":{\n" +
                "      \"english\":\"This is a test notification\",\n" +
                "      \"sinhala\":\"මෙය පරීක්ෂණයකි\",\n" +
                "      \"tamil\":\"இது ஒரு சோதனை\"\n" +
                "   },\n" +
                "   \"message\":{\n" +
                "      \"english\":\"<p>Hi </p>\",\n" +
                "      \"sinhala\":\"<p>සාදරයෙන් පිළිගනිමු</p>\",\n" +
                "      \"tamil\":\"<p>வரவேற்பு</p>\"\n" +
                "   }\n" +
                "}";
        RequestBuilder addNewAlertReq = MockMvcRequestBuilders.post("/notification/alert/add")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON);
        mvc.perform(addNewAlertReq).andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void addNewCase() throws Exception {
        String jsonContent = "{\n" +
                "   \"caseNumber\":\"DHIS/WP/COL/1234\",\n" +
                "   \"locations\":[\n" +
                "      {\n" +
                "         \"date\":\"2020/02/02\",\n" +
                "         \"from\":\"2020/02/02 00:00:00\",\n" +
                "         \"to\":\"2020/02/02 11:59:59\",\n" +
                "         \"address\":\"Colombo Municipal Council\",\n" +
                "         \"longitude\":\"1234\",\n" +
                "         \"latitude\":\"4321\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"date\":\"2020/03/02\",\n" +
                "         \"from\":\"2020/03/02 13:00:00\",\n" +
                "         \"to\":\"2020/03/02 15:45:00\",\n" +
                "         \"address\":\"Galadari Hotel\",\n" +
                "         \"longitude\":\"1234\",\n" +
                "         \"latitude\":\"4321\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"message_en\":\"Person confirmed with COVID-19\",\n" +
                "   \"message_si\":\"COVID-19 සමඟ තහවුරු කළ පුද්ගලයා\",\n" +
                "   \"message_ta\":\"COVID-19 உடன் நபர் உறுதிப்படுத்தப்பட்டார்\"\n" +
                "}";
        RequestBuilder addNewAlertReq = MockMvcRequestBuilders.post("/notification/case/add")
                .content(jsonContent).contentType(MediaType.APPLICATION_JSON);
        mvc.perform(addNewAlertReq).andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}