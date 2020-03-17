package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.AddressInformation;
import lk.gov.govtech.covid19.dto.Attribute;
import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightInformation;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;
import lk.gov.govtech.covid19.dto.IdDisplayAttribute;
import lk.gov.govtech.covid19.dto.OrgUnitAttributesResponse;
import lk.gov.govtech.covid19.dto.PassengerInformation;
import lk.gov.govtech.covid19.dto.ProgramsResponse;
import lk.gov.govtech.covid19.dto.TrackedEntityAttributesResponse;
import lk.gov.govtech.covid19.dto.TrackedEntityTypesResponse;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
    private FieldIdHolder fieldIds = new FieldIdHolder();
    
    @PostConstruct
    private void init() throws Exception {
        this.initPrograms();
        this.initTEAttributesForFlightUserReg();
        this.initOrganizationUnits();
        this.initTrackedEntityTypes();
        LOGGER.info("DHIS2 service initialized successfully");
    }
    
    private void initPrograms() throws Exception {
        ProgramsResponse resp = this.getPrograms();
        Map<String, String> idMap = this.generateReverseMap(resp.getPrograms());
        this.fieldIds.programPortOfEntrySurveillance = this.idLookup(idMap, DHIS2Constants.DISP_PORT_OF_ENTRY_SURVEILLANCE);
    }
    
    private void initTEAttributesForFlightUserReg() throws Exception {
        TrackedEntityAttributesResponse resp = this.getTEAttrsResponse();
        Map<String, String> idMap = this.generateReverseMap(resp.getTrackedEntityAttributes());
        this.fieldIds.tePassportNumber = this.idLookup(idMap, DHIS2Constants.DISP_PASSPORT_NUMBER);
        this.fieldIds.teNationality = this.idLookup(idMap, DHIS2Constants.DISP_NATIONALITY);
        this.fieldIds.teInitials = this.idLookup(idMap, DHIS2Constants.DISP_INITIALS);
        this.fieldIds.teSurname = this.idLookup(idMap, DHIS2Constants.DISP_SURNAME);
        this.fieldIds.teMiddleName = this.idLookup(idMap, DHIS2Constants.DISP_MIDDLENAME);
        this.fieldIds.teGivenName = this.idLookup(idMap, DHIS2Constants.DISP_GIVENNAME);
        this.fieldIds.teIdCardNumber = this.idLookup(idMap, DHIS2Constants.DISP_IDCARDNUMBER);
        this.fieldIds.teDateOfBirth = this.idLookup(idMap, DHIS2Constants.DISP_DATEOFBIRTH);
        this.fieldIds.teGender = this.idLookup(idMap, DHIS2Constants.DISP_GENDER);
        this.fieldIds.teEmailAddress = this.idLookup(idMap, DHIS2Constants.DISP_EMAILADDRESS);
        this.fieldIds.teFaceImage = this.idLookup(idMap, DHIS2Constants.DISP_FACEIMAGE);
        this.fieldIds.tePassportDataPage = this.idLookup(idMap, DHIS2Constants.DISP_PASSPORTDATAPAGE);
        this.fieldIds.teFullAddress = this.idLookup(idMap, DHIS2Constants.DISP_FULLADDRESS);
        this.fieldIds.teAddressLine1 = this.idLookup(idMap, DHIS2Constants.DISP_ADDRESSLINE1);
        this.fieldIds.teAddressLine2 = this.idLookup(idMap, DHIS2Constants.DISP_ADDRESSLINE2);
        this.fieldIds.teCity = this.idLookup(idMap, DHIS2Constants.DISP_CITY);
        this.fieldIds.tePostalCode = this.idLookup(idMap, DHIS2Constants.DISP_POSTALCODE);
        this.fieldIds.teStateProvince = this.idLookup(idMap, DHIS2Constants.DISP_STATEPROVINCE);
        this.fieldIds.teCountry = this.idLookup(idMap, DHIS2Constants.DISP_COUNTRY);
    }
    
    private void initOrganizationUnits() throws Exception {
        OrgUnitAttributesResponse resp = this.getOrgAttrsResponse();
        Map<String, String> idMap = this.generateReverseMap(resp.getOrganisationUnits());
        this.fieldIds.organizationSriLanka = this.idLookup(idMap, DHIS2Constants.DISP_SRI_LANKA);
    }
    
    private void initTrackedEntityTypes() throws Exception {
        TrackedEntityTypesResponse resp = this.getTrackedEntityTypesResponse();
        Map<String, String> idMap = this.generateReverseMap(resp.getTrackedEntityTypes());
        this.fieldIds.personTrackedEntityType = this.idLookup(idMap, DHIS2Constants.DISP_PERSON);
    }
    
    private String idLookup(Map<String, String> idMap, String dispName) throws Exception {
        String result = idMap.get(dispName);
        if (result == null) {
            throw new Exception("'" + dispName + "' attribute ID does not exist");
        }
        return result;
    }
        
    private Map<String, String> generateReverseMap(List<IdDisplayAttribute> attrs) {
        Map<String, String> result = new HashMap<String, String>();
        for (IdDisplayAttribute attr : attrs) {
            result.put(attr.getDisplayName(), attr.getId());
        }
        return result;
    }
    
    private OrgUnitAttributesResponse getOrgAttrsResponse() throws Exception {
        DHISResponse resp = this.getOrganizationUnits();
        String content = resp.getResponse();
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in retrieving organization unit attributes: " + content);
        }
        return this.gson.fromJson(content, OrgUnitAttributesResponse.class);
    }
    
    private TrackedEntityTypesResponse getTrackedEntityTypesResponse() throws Exception {
        DHISResponse resp = this.getEntityTypes();
        String content = resp.getResponse();
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in retrieving tracked entity type attributes: " + content);
        }
        return this.gson.fromJson(content, TrackedEntityTypesResponse.class);
    }
    
    private TrackedEntityAttributesResponse getTEAttrsResponse() throws Exception {
        DHISResponse resp = this.getEntityAttributes();
        String content = resp.getResponse();
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error in retrieving TE attributes: " + content);
        }
        return this.gson.fromJson(content, TrackedEntityAttributesResponse.class);
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
    
    private void saveFlightPassengerInformation(List<FlightPassengerInformation> fpInfos) throws Exception {
        for (FlightPassengerInformation fpInfo : fpInfos) {
            this.saveFlightPassengerInformation(fpInfo);
        }
    }
    
    private Attribute attr(String id, String value) {
        Attribute attr = new Attribute();
        attr.setAttribute(id);
        attr.setValue(value);
        return attr;
    }
    
    private List<Attribute> generateFPInfoAttrs(FlightPassengerInformation fpInfo) {
        List<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(attr(this.fieldIds.tePassportNumber, fpInfo.getPassengerInformation().getPassportNumber()));
        attrs.add(attr(this.fieldIds.teNationality, fpInfo.getPassengerInformation().getNationality()));
        attrs.add(attr(this.fieldIds.teInitials, fpInfo.getPassengerInformation().getInitials()));
        attrs.add(attr(this.fieldIds.teSurname, fpInfo.getPassengerInformation().getSurname()));
        attrs.add(attr(this.fieldIds.teMiddleName, fpInfo.getPassengerInformation().getMiddleName()));
        attrs.add(attr(this.fieldIds.teGivenName, fpInfo.getPassengerInformation().getGivenName()));
        attrs.add(attr(this.fieldIds.teIdCardNumber, fpInfo.getPassengerInformation().getIdCardNumber()));
        attrs.add(attr(this.fieldIds.teDateOfBirth, fpInfo.getPassengerInformation().getDateOfBirth()));
        attrs.add(attr(this.fieldIds.teGender, fpInfo.getPassengerInformation().getGender()));
        attrs.add(attr(this.fieldIds.teEmailAddress, fpInfo.getPassengerInformation().getEmailAddress()));
        attrs.add(attr(this.fieldIds.teFaceImage, fpInfo.getPassengerInformation().getFaceImage()));
        attrs.add(attr(this.fieldIds.tePassportDataPage, fpInfo.getPassengerInformation().getPassportDataPage()));
        if (fpInfo.getAddressInformation().size() > 0) {
            AddressInformation addrInfo = fpInfo.getAddressInformation().get(0);
            attrs.add(attr(this.fieldIds.teFullAddress, addrInfo.getFullAddress()));
            attrs.add(attr(this.fieldIds.teAddressLine1, addrInfo.getAddressLine1()));
            attrs.add(attr(this.fieldIds.teAddressLine2, addrInfo.getAddressLine2()));
            attrs.add(attr(this.fieldIds.teCity, addrInfo.getCity()));
            attrs.add(attr(this.fieldIds.tePostalCode, addrInfo.getPostalCode()));
            attrs.add(attr(this.fieldIds.teCountry, addrInfo.getCountry()));
        }
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
    
    private String createFPEntityInstance(FlightPassengerInformation fpInfo) throws Exception {
        EntityInstance entityInstance = new EntityInstance();
        entityInstance.setOrgUnit(this.fieldIds.organizationSriLanka);
        entityInstance.setTrackedEntityType(this.fieldIds.personTrackedEntityType);
        entityInstance.setAttributes(this.generateFPInfoAttrs(fpInfo));
        DHISResponse resp = this.createEntityInstance(entityInstance);
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error increating FP tracked entity instance: " + resp.getResponse());
        }
        return this.extractTEInstanceId(resp.getResponse());
    }
    
    private void createFPEnrollment(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Enrollment enrollment = new Enrollment(); 
        enrollment.setOrgUnit(this.fieldIds.organizationSriLanka);
        enrollment.setProgram(this.fieldIds.programPortOfEntrySurveillance);
        String currentDate = this.getCurrentDate();
        enrollment.setEnrollmentDate(currentDate);
        enrollment.setIncidentDate(currentDate);
        enrollment.setStatus(DHIS2Constants.ENROLLMENT_STATUS_ACTIVE);
        enrollment.setTrackedEntityInstance(teInstanceId);
        DHISResponse resp = this.createEnrollment(enrollment);
        if (resp.getStatus() != DHIS2Constants.OK_CODE) {
            throw new Exception("Error increating FP enrollment: " + resp.getResponse());
        }
    }
    
    private void saveFlightPassengerInformation(FlightPassengerInformation fpInfo) throws Exception {
        if (fpInfo.getPassengerInformation() == null) {
            throw new Exception("Passenger information is not available");
        }
        String teInstanceId = this.createFPEntityInstance(fpInfo);
        this.createFPEnrollment(fpInfo, teInstanceId);
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
