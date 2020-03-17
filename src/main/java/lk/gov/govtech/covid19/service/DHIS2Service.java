package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.Attribute;
import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.DataElement;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Event;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightInformation;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;
import lk.gov.govtech.covid19.dto.PassengerInformation;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * DHIS2 service implementation.
 */
@Service
public class DHIS2Service {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHIS2Service.class);

    @Autowired
    private DHISConfiguration dhisConfiguration;
    
    private Gson gson = new Gson();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DHISResponse getEntityTypes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityTypes?paging=false");
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

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityAttributes?paging=false");
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

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/organisationUnits?paging=false");
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
    
    private List<String> saveFlightPassengerInformation(List<FlightPassengerInformation> fpInfos) throws Exception {
        List<String> teIds = new ArrayList<String>();
        for (FlightPassengerInformation fpInfo : fpInfos) {
            teIds.add(this.saveFlightPassengerInformation(fpInfo));
        }
        return teIds;
    }
    
    private Attribute attr(String id, String value) {
        Attribute attr = new Attribute();
        attr.setAttribute(id);
        attr.setValue(value);
        return attr;
    }
    
    private List<Attribute> generateFPInfoAttrs(FlightPassengerInformation fpInfo) {
        List<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(attr(DHIS2Constants.UID_ATTR_PASSPORT_NUMBER, fpInfo.getPassengerInformation().getPassportNumber()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_NATIONALITY, fpInfo.getPassengerInformation().getNationality()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_NIC, fpInfo.getPassengerInformation().getIdCardNumber()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_DOB, fpInfo.getPassengerInformation().getDateOfBirth()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_GENDER, fpInfo.getPassengerInformation().getGender()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_EMAIL, fpInfo.getPassengerInformation().getEmailAddress()));
        return attrs;
    }
    
    private String extractTEInstanceId(String content) throws Exception {
        JsonElement el = JsonParser.parseString(content);
        try {
            return el.getAsJsonObject().get("response").getAsJsonObject().get("importSummaries").getAsJsonArray().get(0)
                    .getAsJsonObject().get("reference").getAsString();
        } catch (Exception e) {
            throw new Exception("Invalid JSON content for extracting TE instance id: " + content);
        }
    }
    
    private List<DataElement> generateDataElements(FlightPassengerInformation fpInfo) throws Exception {
        List<DataElement> result = new ArrayList<DataElement>();
        //TODO
        return result;
    }
        
    private List<Event> generateEvent(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        List<Event> result = new ArrayList<Event>();
        Event event = new Event();
        event.setDueDate(this.getCurrentDate());
        event.setProgram(DHIS2Constants.UID_PROGRAMPORTOFENTRYSURVEILLANCE);
        event.setProgramStage(DHIS2Constants.UID_PROGRAMSTAGEPORTOFENTRY);
        event.setOrgUnit(DHIS2Constants.UID_ORGANIZATIONSRILANKA);
        event.setTrackedEntityInstance(teInstanceId);
        event.setDataValues(this.generateDataElements(fpInfo));
        return result;
    }
    
    private void createFPEvents(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Events events = new Events();
        events.setEvents(this.generateEvent(fpInfo, teInstanceId));
        DHISResponse resp = this.createEvents(events);
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in creating FP events: " + resp.getResponse());
        }
    }
    
    private String createFPEntityInstance(FlightPassengerInformation fpInfo) throws Exception {
        EntityInstance entityInstance = new EntityInstance();
        entityInstance.setOrgUnit(DHIS2Constants.UID_ORGANIZATIONSRILANKA);
        entityInstance.setTrackedEntityType(DHIS2Constants.UID_PERSONTRACKEDENTITYTYPE);
        entityInstance.setAttributes(this.generateFPInfoAttrs(fpInfo));
        DHISResponse resp = this.createEntityInstance(entityInstance);
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in creating FP tracked entity instance: " + resp.getResponse());
        }
        return this.extractTEInstanceId(resp.getResponse());
    }
    
    private void createFPEnrollment(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Enrollment enrollment = new Enrollment(); 
        enrollment.setOrgUnit(DHIS2Constants.UID_ORGANIZATIONSRILANKA);
        enrollment.setProgram(DHIS2Constants.UID_PROGRAMPORTOFENTRYSURVEILLANCE);
        String currentDate = this.getCurrentDate();
        enrollment.setEnrollmentDate(currentDate);
        enrollment.setIncidentDate(currentDate);
        enrollment.setStatus(DHIS2Constants.ENROLLMENT_STATUS_ACTIVE);
        enrollment.setTrackedEntityInstance(teInstanceId);
        DHISResponse resp = this.createEnrollment(enrollment);
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in creating FP enrollment: " + resp.getResponse());
        }
    }
        
    private String saveFlightPassengerInformation(FlightPassengerInformation fpInfo) throws Exception {
        if (fpInfo.getPassengerInformation() == null) {
            throw new Exception("Passenger information is not available");
        }
        String teInstanceId = this.createFPEntityInstance(fpInfo);
        this.createFPEnrollment(fpInfo, teInstanceId);
        this.createFPEvents(fpInfo, teInstanceId);
        return teInstanceId;
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
    
    private synchronized String getCurrentDate() {
        return this.dateFormat.format(new Date());
    }
    
    private void clearoutImages(FlightPassengerInformation fpInfo) {
        PassengerInformation pInfo = fpInfo.getPassengerInformation();
        if (pInfo != null) {
            pInfo.setArrivalCardImage(DHIS2Constants.BIN_CLEAR_VAL);
            pInfo.setFaceImage(DHIS2Constants.BIN_CLEAR_VAL);
            pInfo.setPassportDataPage(DHIS2Constants.BIN_CLEAR_VAL);
        }
    }
    
    public DHISResponse pushFlightPassengerInformation(FlightPassengerInformation fpInfo) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoke pushFlightPassengerInformation");
        }
        DHISResponse result = new DHISResponse();
        try {
            List<String> teIds = new ArrayList<String>();
            FlightInformation flightInfo = this.extractFlightInformation(fpInfo);
            String flightNo = this.extractFlightNumber(flightInfo);
            String date = this.extractFlightDate(flightInfo);
            teIds.addAll(this.saveFlightPassengerInformation(Arrays.asList(fpInfo)));
            List<FlightPassengerInformation> passengersInFlight = this.getInfoBorderPassengerList(flightNo, date);
            teIds.addAll(this.saveFlightPassengerInformation(passengersInFlight));
            result.setStatus(DHIS2Constants.OK_CODE);
            result.setResponse(this.gson.toJson(teIds));
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
