package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.hisp.dhis.Dhis2;
import org.hisp.dhis.Dhis2Config;
import org.hisp.dhis.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class DHIS2Service {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHIS2Service.class);
    private final static int INTERNAL_ERROR_CODE = 500;

    @Autowired
    private DHISConfiguration dhisConfiguration;
    
    private Gson gson = new Gson();

    public DHISResponse getEntityTypes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityTypes");
        DHISResponse dhisResponse = new DHISResponse();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting entity types");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(new String(getRequest.getResponseBody()));
        } catch (IOException e) {
            LOGGER.error("Error while getting entity types information", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            getRequest.releaseConnection();
        }
        return dhisResponse;
    }

    public DHISResponse getEntityAttributes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityAttributes");
        DHISResponse dhisResponse = new DHISResponse();

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting entity attributes");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(getRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while getting entity attributes information", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            getRequest.releaseConnection();
        }
        return dhisResponse;
    }

    public DHISResponse getOrganizationUnits() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/organisationUnits");
        DHISResponse dhisResponse = new DHISResponse();

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting organization units");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(getRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while getting organization units information", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            getRequest.releaseConnection();
        }
        return dhisResponse;
    }

    public DHISResponse createEntityInstance(EntityInstance entityInstance) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/trackedEntityInstances");
        DHISResponse dhisResponse = new DHISResponse();
        try {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity instance");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(entityInstance)));
            int response = httpClient.executeMethod(postRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(postRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while creating entity instance", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            postRequest.releaseConnection();
        }
        return dhisResponse;
    }

    public DHISResponse createEnrollment(Enrollment enrollment) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/enrollments");
        DHISResponse dhisResponse = new DHISResponse();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity enrollment");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(enrollment)));
            int response = httpClient.executeMethod(postRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(postRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while enrolling", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            postRequest.releaseConnection();
        }
        return dhisResponse;
    }

    public DHISResponse createEvents(Events events) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/events.json");
        DHISResponse dhisResponse = new DHISResponse();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity events");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(events)));
            int response = httpClient.executeMethod(postRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(postRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while creating event", e);
            dhisResponse.setStatus(INTERNAL_ERROR_CODE);
            dhisResponse.setResponse(e.getLocalizedMessage());
        } finally {
            postRequest.releaseConnection();
        }
        return dhisResponse;
    }

    private void setAuthorizationHeader(HttpMethodBase request) {

        String basicauth = dhisConfiguration.getUsername() + ":" + dhisConfiguration.getPassword();
        String encodedString = Base64.getEncoder().encodeToString(basicauth.getBytes());
        request.addRequestHeader("Authorization", "Basic " + encodedString);
    }

    private StringRequestEntity getRequestEntity(String json) throws UnsupportedEncodingException {

        return new StringRequestEntity(json,
                "application/json",
                "UTF-8");
    }

    private HttpClient getHttpClient() {

        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getParams().setParameter("http.connection.stalecheck", new Boolean(true));
        return httpClient;
    }
    
    private List<FlightPassengerInformation> getInfoBorderPassengerList(String flightNo, 
                                                                        String date) throws Exception {
        GetMethod getRequest = new GetMethod(this.dhisConfiguration.getInfoBorderUrl() + "/" + flightNo + "/" + date);
        try {
            HttpClient httpClient = getHttpClient();
            int response = httpClient.executeMethod(getRequest);
            String content = getRequest.getResponseBodyAsString();
            if (response != 200) {
                throw new Exception("Error in looking up passenger list from InfoBorder service: " + content);
            }
            return Arrays.asList(this.gson.fromJson(content, FlightPassengerInformation[].class));
        } finally {
            getRequest.releaseConnection();
        }
    }
    
    private void saveFlightPassengerInformation(List<FlightPassengerInformation> fpInfos) {
        //TODO
    }
    
    public DHISResponse pushFlightPassengerInformation(FlightPassengerInformation fpInfo) {
        String flightNo = fpInfo.getFlightInformation().getFlightNumber();
        String date = fpInfo.getFlightInformation().getFlightDateTime();
        DHISResponse result = new DHISResponse();
        try {
            this.saveFlightPassengerInformation(Arrays.asList(fpInfo));
            List<FlightPassengerInformation> passengersInFlight = this.getInfoBorderPassengerList(flightNo, date);
            this.saveFlightPassengerInformation(passengersInFlight);
            result.setStatus(200);
        } catch (Exception e) {
            String message = "Error in pushing flight passenger info: " + fpInfo;
            LOGGER.error(message, e);
            result.setStatus(INTERNAL_ERROR_CODE);
            result.setResponse(message + " - " + e.getMessage());
        }
        return result;
    }

}
