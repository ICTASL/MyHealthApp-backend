package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.AddressInformation;
import lk.gov.govtech.covid19.dto.Attribute;
import lk.gov.govtech.covid19.dto.ContactNumber;
import lk.gov.govtech.covid19.dto.DHISResponse;
import lk.gov.govtech.covid19.dto.DataElement;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Event;
import lk.gov.govtech.covid19.dto.Events;
import lk.gov.govtech.covid19.dto.FlightInformation;
import lk.gov.govtech.covid19.dto.FlightPassengerInformation;
import lk.gov.govtech.covid19.dto.LastDepartureInformation;
import lk.gov.govtech.covid19.dto.PassengerInformation;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DHIS2 service implementation.
 */
@Service
public class DHIS2Service {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHIS2Service.class);

    @Autowired
    private DHISConfiguration dhisConfiguration;
    
    private Gson gson = new Gson();
    private final static SimpleDateFormat FPI_DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat FPI_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat DHIS2_DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private final static SimpleDateFormat DHIS2_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

    public DHISResponse getProgrammes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/programs?paging=false");
        DHISResponse dhisResponse = new DHISResponse();

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting programmes");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            dhisResponse.setStatus(response);
            dhisResponse.setResponse(getRequest.getResponseBodyAsString());
        } catch (IOException e) {
            LOGGER.error("Error while getting programmes information", e);
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
    
    private String addFileResource(String fileName, byte[] data) throws Exception {
        if (data == null) {
            return null;
        }
        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/fileResources");
        try {
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            Part[] parts = { new FilePart("file", new ByteArrayPartSource(fileName, data)) };
            postRequest.setRequestEntity(new MultipartRequestEntity(parts, new HttpMethodParams()));
            int response = httpClient.executeMethod(postRequest);
            String content = postRequest.getResponseBodyAsString();
            if (response != DHIS2Constants.OK_CODE) {
                throw new Exception("Error in adding file resource: " + content);
            }
            try {
                return JsonParser.parseString(content).getAsJsonObject().get("response").getAsJsonObject()
                        .get("fileResource").getAsJsonObject().get("id").getAsString();
            } catch (Exception e) {
                throw new Exception("Invalid file resource add result JSON: " + content);
            }
        } finally {
            postRequest.releaseConnection();
        }
    }
    
    private String addFileResource(String name, String base64Data) throws Exception {
        if (base64Data == null) {
            return null;
        }
        byte[] data;
        try {
            data = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid base64 encoded data to be put as a file resource");
        }
        return this.addFileResource(name, data);
    }
    
    private List<String> saveFlightPassengerInformation(Set<FlightPassengerInformation> fpInfos) throws Exception {
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
    
    private String generateFullName(PassengerInformation pinfo) {
        String fullName = pinfo.getGivenName() == null ? "" : pinfo.getGivenName();
        fullName += " " + (pinfo.getMiddleName() == null ? "" : pinfo.getMiddleName());
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            fullName = pinfo.getInitials() == null ? "" : pinfo.getInitials();
        }
        fullName += " " + pinfo.getSurname();
        fullName = fullName.trim();
        if (fullName.isEmpty()) {
            fullName = null;
        }
        return fullName;
    }
    
    private List<Attribute> generateFPInfoAttrs(FlightPassengerInformation fpInfo) {
        List<Attribute> attrs = new ArrayList<Attribute>();
        PassengerInformation pinfo = fpInfo.getPassengerInformation();
        attrs.add(attr(DHIS2Constants.UID_ATTR_PASSPORT_NUMBER, pinfo.getPassportNumber()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_NATIONALITY, pinfo.getNationality()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_NIC, pinfo.getIdCardNumber()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_DOB, pinfo.getDateOfBirth()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_GENDER, pinfo.getGender()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_EMAIL, pinfo.getEmailAddress()));
        attrs.add(attr(DHIS2Constants.UID_ATTR_FULLNAME, this.generateFullName(pinfo)));
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
    
    private DataElement del(String id, Object value) {
        DataElement result = new DataElement();
        result.setDataElement(id);
        result.setValue(value);
        return result;
    }
    
    private String fpiToDHIS2DataTime(String datetime) throws Exception {
        if (datetime == null) {
            return null;
        }
        return DHIS2_DATETIMEFORMAT.format(FPI_DATETIMEFORMAT.parse(datetime));
    }
    
    private List<DataElement> generateFlightInfoDataElements(FlightPassengerInformation fpInfo) throws Exception {
        FlightInformation finfo = fpInfo.getFlightInformation();
        if (finfo == null) {
            throw new Exception("The flight information is not available");
        }
        List<DataElement> result = new ArrayList<DataElement>();
        result.add(del(DHIS2Constants.UID_DEL_FLIGHTNUMBER, finfo.getFlightNumber()));
        result.add(del(DHIS2Constants.UID_DEL_FLIGHTDATETIME, this.fpiToDHIS2DataTime(finfo.getFlightDateTime())));
        result.add(del(DHIS2Constants.UID_DEL_ARRIVEFROMPORT, finfo.getArriveFromPort()));
        result.add(del(DHIS2Constants.UID_DEL_LANDEDPORT, finfo.getLandedPort()));
        result.add(del(DHIS2Constants.UID_DEL_CARRIERCODE, finfo.getCarrierCode()));
        result.add(del(DHIS2Constants.UID_DEL_ARRIVALPASSENGERCOUNT, finfo.getArrivalPassengerCount()));
        result.add(del(DHIS2Constants.UID_DEL_TRANSITPASSENGERCOUNT, finfo.getTransitPassengerCount()));
        return result;
    }
    
    private List<DataElement> generateFPInfoDataElements(FlightPassengerInformation fpInfo) throws Exception {
        List<DataElement> result = new ArrayList<DataElement>();
        PassengerInformation pinfo = fpInfo.getPassengerInformation();
        //result.add(del(DHIS2Constants.UID_DEL_INITIALS, pinfo.getInitials()));
        //result.add(del(DHIS2Constants.UID_DEL_SURNAME, pinfo.getSurname()));
        //result.add(del(DHIS2Constants.UID_DEL_MIDDLENAME, pinfo.getMiddleName()));
        //result.add(del(DHIS2Constants.UID_DEL_GIVENNAME, pinfo.getGivenName()));
        result.add(del(DHIS2Constants.UID_DEL_COUNTRYOFRESIDENCE, pinfo.getCountryOfResidence()));
        result.add(del(DHIS2Constants.UID_DEL_ARRIVEFROM, pinfo.getArriveFrom()));
        result.add(del(DHIS2Constants.UID_DEL_ARRIVALCARDNUMBER, pinfo.getArrivalCardNumber()));
        result.add(del(DHIS2Constants.UID_DEL_PURPOSEOFVISIT, pinfo.getPurposeOfVisit()));
        result.add(del(DHIS2Constants.UID_DEL_REQUESTEDVISADAYS, pinfo.getRequestedVisaDays()));
        result.add(del(DHIS2Constants.UID_DEL_DESTINATIONCITY, pinfo.getDestinationCity()));
        result.add(del(DHIS2Constants.UID_DEL_ARRIVALCARDIMAGE, 
                this.addFileResource(DHIS2Constants.ARRIVAL_CARD_IMG_NAME, pinfo.getArrivalCardImage())));
        result.add(del(DHIS2Constants.UID_DEL_FACEIMAGE, 
                this.addFileResource(DHIS2Constants.FACE_IMG_NAME, pinfo.getFaceImage())));
        result.add(del(DHIS2Constants.UID_DEL_PASSPORTDATAPAGE, 
                this.addFileResource(DHIS2Constants.PASSPORTDATAPAGE_IMG_NAME, pinfo.getPassportDataPage())));
        return result;
    }
    
    private void populateCommonEventValues(Event event, String teInstanceId) {
        event.setDueDate(this.getDHIS2CurrentDate());
        event.setProgram(DHIS2Constants.UID_PROGRAMPORTOFENTRYSURVEILLANCE);
        event.setProgramStage(DHIS2Constants.UID_PROGRAMSTAGEPORTOFENTRY);
        event.setOrgUnit(DHIS2Constants.UID_ORGANIZATIONSRILANKA);
        event.setStatus(DHIS2Constants.STATUS_COMPLETED);
        event.setTrackedEntityInstance(teInstanceId);
    }
    
    private Event generateFlightInfoEvent(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        event.setDataValues(this.generateFlightInfoDataElements(fpInfo));
        return event;
    }
    
    private List<DataElement> generateLastDepartureDataElements(FlightPassengerInformation fpInfo) throws Exception {
        LastDepartureInformation ldInfo = fpInfo.getLastDepartureInformation();
        if (ldInfo == null) {
            throw new Exception("The last departure information is not available");
        }
        List<DataElement> result = new ArrayList<DataElement>();
        result.add(del(DHIS2Constants.UID_DEL_LASTDEPARTUREFLIGHTNO, ldInfo.getDepartureFlightNo()));
        result.add(del(DHIS2Constants.UID_DEL_LASTDEPARTUREDATE, ldInfo.getDepartureDate()));
        result.add(del(DHIS2Constants.UID_DEL_LASTDEPARTUREFLIGHTDESTINATION, ldInfo.getFlightDestination()));
        result.add(del(DHIS2Constants.UID_DEL_LASTDEPARTURECARDNO, ldInfo.getDepartureCardNo()));
        return result;
    }
    
    private Event generateLastDepartureEvent(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        event.setDataValues(this.generateLastDepartureDataElements(fpInfo));
        return event;
    }
    
    private Event generateFPInfoEvent(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        event.setDataValues(this.generateFPInfoDataElements(fpInfo));
        return event;
    }
    
    private List<Event> generateFPAddressEvents(FlightPassengerInformation fpInfo, 
            String teInstanceId) throws Exception {
        List<Event> result = new ArrayList<Event>();
        List<AddressInformation> addressInfos = fpInfo.getAddressInformation();
        if (addressInfos == null) {
            return new ArrayList<Event>(0);
        }
        for (AddressInformation addressInfo : addressInfos) {
            result.add(this.generateFPAddressEvent(addressInfo, teInstanceId));
        }
        return result;
    }
    
    private Event generateFPContactNumberEvent(ContactNumber contactNumber, String teInstanceId) throws Exception {
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        List<DataElement> dataEls = new ArrayList<DataElement>();
        dataEls.add(del(DHIS2Constants.UID_DEL_CONTACTNUMBER, contactNumber.getContactNumber()));
        event.setDataValues(dataEls);
        return event;
    }
    
    private List<Event> generateFPContactNumberEvents(FlightPassengerInformation fpInfo, 
            String teInstanceId) throws Exception {
        List<Event> result = new ArrayList<Event>();
        List<ContactNumber> contactNumbers = fpInfo.getContactNumbers();
        if (contactNumbers == null) {
            return new ArrayList<Event>(0);
        }
        for (ContactNumber contactNumber : contactNumbers) {
            result.add(this.generateFPContactNumberEvent(contactNumber, teInstanceId));
        }
        return result;
    }
    
    private List<DataElement> generateFPAddressDataElements(AddressInformation addressInfo) throws Exception {
        List<DataElement> result = new ArrayList<DataElement>();
        result.add(del(DHIS2Constants.UID_DEL_FULLADDRESS, addressInfo.getFullAddress()));
        result.add(del(DHIS2Constants.UID_DEL_ADDRESSLINE1, addressInfo.getAddressLine1()));
        result.add(del(DHIS2Constants.UID_DEL_ADDRESSLINE2, addressInfo.getAddressLine2()));
        result.add(del(DHIS2Constants.UID_DEL_CITY, addressInfo.getCity()));
        result.add(del(DHIS2Constants.UID_DEL_POSTALCODE, addressInfo.getPostalCode()));
        result.add(del(DHIS2Constants.UID_DEL_STATEPROVINCE, addressInfo.getStateProvince()));
        result.add(del(DHIS2Constants.UID_DEL_COUNTRY, addressInfo.getCountry()));
        return result;
    }
    
    private Event generateFPAddressEvent(AddressInformation addressInfo, String teInstanceId) throws Exception {
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        event.setDataValues(this.generateFPAddressDataElements(addressInfo));
        return event;
    }
        
    private List<Event> generateEvents(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        List<Event> result = new ArrayList<Event>();
        result.add(this.generateFlightInfoEvent(fpInfo, teInstanceId));
        result.add(this.generateFPInfoEvent(fpInfo, teInstanceId));
        //result.addAll(this.generateFPAddressEvents(fpInfo, teInstanceId));
        //result.addAll(this.generateFPContactNumberEvents(fpInfo, teInstanceId));
        result.add(this.generateLastDepartureEvent(fpInfo, teInstanceId));
        return result;
    }
    
    private void createFPEvents(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        Events events = new Events();
        events.setEvents(this.generateEvents(fpInfo, teInstanceId));
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
        String currentDate = this.getDHIS2CurrentDate();
        enrollment.setEnrollmentDate(currentDate);
        enrollment.setIncidentDate(currentDate);
        enrollment.setStatus(DHIS2Constants.STATUS_ACTIVE);
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
            return this.extractFPIDate(flightDateTime);
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
    
    private synchronized String extractFPIDate(String dateTime) throws ParseException {
        Date date = FPI_DATETIMEFORMAT.parse(dateTime);
        return FPI_DATEFORMAT.format(date);
    }
    
    private synchronized String getDHIS2CurrentDate() {
        return DHIS2_DATEFORMAT.format(new Date());
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
            /* the set is to eliminate similar records on insertion */
            Set<FlightPassengerInformation> passengersInFlight = new HashSet<>(this.getInfoBorderPassengerList(flightNo, date));
            passengersInFlight.add(fpInfo);
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
