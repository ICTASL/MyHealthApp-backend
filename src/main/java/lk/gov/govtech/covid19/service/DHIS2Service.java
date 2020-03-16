package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightInformation;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;
import lk.gov.govtech.covid19.dto.IdDisplayAttribute;
import lk.gov.govtech.covid19.dto.PassengerInformation;
import lk.gov.govtech.covid19.dto.ProgramsResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Service
public class DHIS2Service {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHIS2Service.class);

    @Autowired
    private DHISConfiguration dhisConfiguration;
    
    private Gson gson = new Gson();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private FieldIdHolder fieldIds = new FieldIdHolder();
    
    @PostConstruct
    private void init() throws Exception {
        this.initPrograms();
    }
    
    private void initPrograms() throws Exception {
        ProgramsResponse resp = this.getPrograms();
        Map<String, String> idMap = this.generateReverseMap(resp.getPrograms());
        this.fieldIds.portOfEntrySurveillance = idMap.get(DHIS2Constants.DISP_PORT_OF_ENTRY_SURVEILLANCE);
        if (this.fieldIds.portOfEntrySurveillance == null) {
            throw new Exception("'Port of Entry Surveillance' program does not exist");
        }
    }
    
    private Map<String, String> generateReverseMap(List<IdDisplayAttribute> attrs) {
        Map<String, String> result = new HashMap<String, String>();
        for (IdDisplayAttribute attr : attrs) {
            result.put(attr.getDisplayName(), attr.getId());
        }
        return result;
    }
    
    private ProgramsResponse getPrograms() throws Exception {
        GetMethod getRequest = new GetMethod(this.dhisConfiguration.getUrl() + "/programs?paging=false");
        try {
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            String content = getRequest.getResponseBodyAsString();
            if (response != DHIS2Constants.OK_CODE) {
                throw new Exception("Error in retrieving programs: " + content);
            }
            return this.gson.fromJson(content, ProgramsResponse.class);
        } catch (IOException e) {
            throw new Exception("Error in retrieving programs", e);
        } finally {
            getRequest.releaseConnection();
        }
    }

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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
            dhisResponse.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoke getInfoBorderPassengerList");
        }
        GetMethod getRequest = new GetMethod(this.dhisConfiguration.getInfoBorderUrl() + "/" + flightNo + "/" + date);
        try {
            HttpClient httpClient = getHttpClient();
            int response = httpClient.executeMethod(getRequest);
            String content = getRequest.getResponseBodyAsString();
            if (response != DHIS2Constants.OK_CODE) {
                throw new Exception("Error in looking up passenger list from InfoBorder service: " + content);
            }
            return Arrays.asList(this.gson.fromJson(content, FlightPassengerInformation[].class));
        } finally {
            getRequest.releaseConnection();
        }
    }
    
    private void saveFlightPassengerInformation(List<FlightPassengerInformation> fpInfos) {
        //TODO - save to DHIS2
    }
    
    private String extractFlightNumber(FlightInformation flightInfo) throws Exception {
        String flightNumber = flightInfo.getFlightNumber();
        if (flightNumber == null) {
            throw new Exception("Flight number not available");
        }
        return flightNumber;
    }
    
    private String extractFlightDate(FlightInformation flightInfo) throws Exception {
        String flightDateTime = flightInfo.getFlightDateTime();
        if (flightDateTime == null) {
            throw new Exception("Flight data/time not available");
        }
        try {
            return this.extractDate(flightDateTime);
        } catch (ParseException e) {
            throw new Exception("Invalid flight date/time", e);
        }
    }
    
    private FlightInformation extractFlightInformation(FlightPassengerInformation fpInfo) throws Exception {
        FlightInformation flightInfo = fpInfo.getFlightInformation();
        if (flightInfo == null) {
            throw new Exception("Flight information not available");
        }
        return flightInfo;
    }
    
    private synchronized String extractDate(String dateTime) throws ParseException {
        Date date = this.dateTimeFormat.parse(dateTime);
        return this.dateFormat.format(date);
    }
    
    private void clearoutImages(FlightPassengerInformation fpInfo) {
        PassengerInformation pInfo = fpInfo.getPassengerInformation();
        if (pInfo != null) {
            pInfo.setArrivalCardImage("***");
            pInfo.setFaceImage("***");
            pInfo.setPassportDataPage("***");
        }
    }
    
    public DHISResponse pushFlightPassengerInformation(FlightPassengerInformation fpInfo) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoke pushFlightPassengerInformation");
        }
        DHISResponse result = new DHISResponse();
        try {
            FlightInformation flightInfo = this.extractFlightInformation(fpInfo);
            String flightNo = this.extractFlightNumber(flightInfo);
            String date = this.extractFlightDate(flightInfo);
            this.saveFlightPassengerInformation(Arrays.asList(fpInfo));
            List<FlightPassengerInformation> passengersInFlight = this.getInfoBorderPassengerList(flightNo, date);
            this.saveFlightPassengerInformation(passengersInFlight);
            result.setStatus(DHIS2Constants.OK_CODE);
        } catch (Exception e) {
            this.clearoutImages(fpInfo);
            String message = "Error in pushing flight passenger info: " + fpInfo;
            LOGGER.error(message, e);
            result.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
            result.setResponse(message + " - " + e.getMessage());
        }
        return result;
    }

}
