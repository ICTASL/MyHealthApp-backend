package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import lk.gov.govtech.covid19.dto.Geometry;
import lk.gov.govtech.covid19.dto.LastDepartureInformation;
import lk.gov.govtech.covid19.dto.PassengerInformation;

import lk.gov.govtech.covid19.dto.Patients;
import org.apache.catalina.util.URLEncoder;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
    private final static SimpleDateFormat FPI_DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private final static SimpleDateFormat FPI_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat DHIS2_DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private final static SimpleDateFormat DHIS2_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private HttpClient httpClient;

    public DHIS2Service() {

        this.httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        this.httpClient.getParams().setParameter("http.connection.stalecheck", new Boolean(true));
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

    public DHISResponse registerPatient(Patients patients) {

        EntityInstance entityInstance = new EntityInstance();
        entityInstance.setTrackedEntityType(DHIS2Constants.UID_PERSONTRACKEDENTITYTYPE);
        String orgUnit = DHIS2Constants.UID_ORGANIZATION_BIA;
        //getOrganization(patients.getAddress()); //TODO Need to improve to automatically get the organization
        entityInstance.setOrgUnit(orgUnit);

        Geometry geometry = new Geometry();
        geometry.setType(DHIS2Constants.GEOMETRY_TYPE);
        geometry.setCoordinates(new Double[]{Double.parseDouble(patients.getLattitude()), Double.parseDouble(patients.getLongitude())});
        entityInstance.setGeometry(geometry);
        entityInstance.setAttributes(getPatientAtributes(patients));

        DHISResponse response = createEntityInstance(entityInstance);
        if (response.getStatus() == DHIS2Constants.OK_CODE) {
            String reference = getReferenceNumber(response.getResponse());
            String today = DHIS2_DATEFORMAT.format(new Date());

            Enrollment enrollment = new Enrollment();
            enrollment.setTrackedEntityInstance(reference);
            enrollment.setProgram(DHIS2Constants.UID_PROGRAM_SUSPECTED_CASE);
            enrollment.setStatus(DHIS2Constants.STATUS_ACTIVE);
            enrollment.setOrgUnit(orgUnit);
            enrollment.setEnrollmentDate(today);
            enrollment.setIncidentDate(today);
            DHISResponse enrollmentResponse = createEnrollment(enrollment);
            if (enrollmentResponse.getStatus() == DHIS2Constants.OK_CODE) {
                enrollmentResponse.setResponse(getPatientResponse(reference));
            }
            return enrollmentResponse;
        } else {
            return response;
        }
    }

    private String getReferenceNumber(String response) {

        JsonElement jelement = new JsonParser().parse(response);
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("response");
        JsonArray jarray = jobject.getAsJsonArray("importSummaries");
        jobject = jarray.get(0).getAsJsonObject();
        String reference = jobject.get("reference").toString();
        reference = reference.replace("\"", "");
        return reference;
    }

    private List<Attribute> getPatientAtributes(Patients patients) {

        List<Attribute> attributes = new ArrayList<>();
        Attribute name = new Attribute();
        name.setAttribute(DHIS2Constants.UID_ATTR_FULLNAME);
        name.setValue(patients.getName());
        attributes.add(name);

        Attribute email = new Attribute();
        email.setAttribute(DHIS2Constants.UID_ATTR_EMAIL);
        email.setValue(patients.getEmail());
        attributes.add(email);

        Attribute address = new Attribute();
        address.setAttribute(DHIS2Constants.UID_DEL_FULLADDRESS);
        address.setValue(patients.getAddress());
        attributes.add(address);

        Attribute mobileImei = new Attribute();
        mobileImei.setAttribute(DHIS2Constants.UID_ATTR_MOBILEIMIE);
        mobileImei.setValue(patients.getMobileImei());
        attributes.add(mobileImei);

        Attribute caseIDs = new Attribute();
        caseIDs.setAttribute(DHIS2Constants.UID_ATTR_CASEIDS);
        caseIDs.setValue(patients.getCaseList());
        attributes.add(caseIDs);

        Attribute nic = new Attribute();
        nic.setAttribute(DHIS2Constants.UID_ATTR_NIC);
        nic.setValue(patients.getNic());
        attributes.add(nic);

        Attribute passport = new Attribute();
        passport.setAttribute(DHIS2Constants.UID_ATTR_PASSPORT_NUMBER);
        passport.setValue(patients.getPassport());
        attributes.add(passport);

        Attribute country = new Attribute();
        country.setAttribute(DHIS2Constants.UID_DEL_COUNTRY);
        country.setValue(patients.getCountry());
        attributes.add(country);

        Attribute age = new Attribute();
        age.setAttribute(DHIS2Constants.UID_ATTR_AGE);
        age.setValue(patients.getAge());
        attributes.add(age);

        Attribute gender = new Attribute();
        gender.setAttribute(DHIS2Constants.UID_ATTR_GENDER);
        gender.setValue(patients.getGender());
        attributes.add(gender);

        return attributes;
    }

    private String getPatientResponse(String reference) {

        return "{\n" + "\"caseid\":" + reference + "\n" + "}";
    }

    private String getOrganization(String addresss) {

        //getCordinates(addresss);
        return "dKl0ZJcEWbf";
    }

    private Double[] getCordinates(String address) {

        URLEncoder encoder = new URLEncoder();
        String encodeaddress = encoder.encode(address, Charset.defaultCharset());
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodeaddress + "&key=xxxxxx";
        GetMethod getRequest = new GetMethod(url);

        Double[] cordinates = null;

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting programmes");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == DHIS2Constants.OK_CODE) {

                JsonElement jelement = new JsonParser().parse(getRequest.getResponseBodyAsString());
                JsonArray results = jelement.getAsJsonObject().getAsJsonArray("results");
                if (results != null && results.size() > 0) {
                    JsonElement result = results.get(0);
                    JsonObject geometry = result.getAsJsonObject().getAsJsonObject("geometry");
                    double lat = Double.parseDouble(geometry.get("bounds").getAsJsonObject().get("northeast").getAsJsonObject().get("lat").toString());
                    double lng = Double.parseDouble(geometry.get("bounds").getAsJsonObject().get("northeast").getAsJsonObject().get("lng").toString());
                    cordinates = new Double[]{lat, lng};
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error while getting coordinates of the address.", e);
        } finally {
            getRequest.releaseConnection();
        }
        return cordinates;
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

        return this.httpClient;
    }
    
    /* private List<FlightPassengerInformation> getInfoBorderPassengerList(String flightNo, 
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
    } */

    private String addFileResource(String fileName, byte[] data) throws Exception {

        if (data == null) {
            return null;
        }
        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/fileResources");
        try {
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            Part[] parts = {new FilePart("file", new ByteArrayPartSource(fileName, data))};
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

    private String generateFullName(PassengerInformation pinfo) {

        List<String> comps = new ArrayList<>();
        if (StringUtils.isNotEmpty(pinfo.getInitials())) {
            comps.add(pinfo.getInitials());
        }
        if (StringUtils.isNotEmpty(pinfo.getGivenName())) {
            comps.add(pinfo.getGivenName());
        }
        if (StringUtils.isNotEmpty(pinfo.getMiddleName())) {
            comps.add(pinfo.getMiddleName());
        }
        if (StringUtils.isNotEmpty(pinfo.getSurname())) {
            comps.add(pinfo.getSurname());
        }
        if (!comps.isEmpty()) {
            return String.join(" ", comps);
        } else {
            return null;
        }
    }

    private String generateAddressAttributeValue(FlightPassengerInformation fpInfo) {

        List<AddressInformation> addrInfos = fpInfo.getAddressInformation();
        if (addrInfos == null) {
            return null;
        }
        List<String> addrInfoStrings = new ArrayList<>();
        for (AddressInformation addrInfo : addrInfos) {
            String address = addrInfo.toString();
            if (StringUtils.isNotEmpty(address)) {
                addrInfoStrings.add(address);
            }
        }
        if (!addrInfoStrings.isEmpty()) {
            return String.join("\n\n", addrInfoStrings);
        } else {
            return null;
        }
    }

    private boolean isNumberSriLankan(String number) {

        if (StringUtils.isEmpty(number)) {
            return false;
        }
        number = number.trim();
        if (number.startsWith("00")) {
            return number.startsWith("0094");
        }
        if (number.startsWith("+")) {
            return number.startsWith("+94");
        }
        // at the end, if it's just some ten digit number, our first guess it's Sri Lankan
        return number.length() == 10;
    }

    private String[] generateLocalForeignTelNumberValues(FlightPassengerInformation fpInfo) {

        String[] telNos = new String[2]; // [0] - local, [1] - foreign
        // additional multiple numbers will be stored comma separated in the foreign phone number value
        List<ContactNumber> contactNumbers = fpInfo.getContactNumbers();
        if (contactNumbers == null) {
            return telNos;
        }
        List<String> additionalNumbers = new ArrayList<String>();
        for (ContactNumber contactNumber : contactNumbers) {
            String number = contactNumber.getContactNumber();
            if (StringUtils.isEmpty(number)) {
                continue;
            }
            if (this.isNumberSriLankan(number) && StringUtils.isEmpty(telNos[0])) {
                telNos[0] = number;
            } else {
                additionalNumbers.add(number);
            }
        }
        if (!additionalNumbers.isEmpty()) {
            telNos[1] = String.join(",", additionalNumbers);
        }
        return telNos;
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
        attrs.add(attr(DHIS2Constants.UID_ATTR_FULLADDRESS_SRILANKA, this.generateAddressAttributeValue(fpInfo)));
        String[] localForeignTelNos = this.generateLocalForeignTelNumberValues(fpInfo);
        attrs.add(attr(DHIS2Constants.UID_ATTR_TEL_SRILANKA, localForeignTelNos[0]));
        attrs.add(attr(DHIS2Constants.UID_ATTR_TEL_FOREIGN, localForeignTelNos[1]));
        return attrs;
    }

    private String extractTEInstanceId(String content) throws Exception {

        JsonElement el = JsonParser.parseString(content);
        try {
            return el.getAsJsonObject().get("response").getAsJsonObject().get("importSummaries").
                    getAsJsonArray().get(0).getAsJsonObject().get("reference").getAsString();
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

    private List<Event> generateEvents(FlightPassengerInformation fpInfo, String teInstanceId) throws Exception {
        // at this moment, we will put all the data elements to a single event
        Event event = new Event();
        this.populateCommonEventValues(event, teInstanceId);
        List<DataElement> dataElements = new ArrayList<>();
        dataElements.addAll(this.generateFlightInfoDataElements(fpInfo));
        dataElements.addAll(this.generateFPInfoDataElements(fpInfo));
        dataElements.addAll(this.generateLastDepartureDataElements(fpInfo));
        event.setDataValues(dataElements);
        return Arrays.asList(event);
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
    
    /* private String extractFlightNumber(FlightInformation flightInfo) throws Exception {
        String flightNumber = flightInfo.getFlightNumber();
        if (flightNumber == null) {
            throw new Exception("Flight number not available");
        }
        return flightNumber;
    } */
    
    /* private String extractFlightDate(FlightInformation flightInfo) throws Exception {
        String flightDateTime = flightInfo.getFlightDateTime();
        if (flightDateTime == null) {
            throw new Exception("Flight data/time not available");
        }
        try {
            return this.extractFPIDate(flightDateTime);
        } catch (ParseException e) {
            throw new Exception("Invalid flight date/time", e);
        }
    } */
    
    /* private FlightInformation extractFlightInformation(FlightPassengerInformation fpInfo) throws Exception {
        FlightInformation flightInfo = fpInfo.getFlightInformation();
        if (flightInfo == null) {
            throw new Exception("Flight information not available");
        }
        return flightInfo;
    } */
    
    /* private synchronized String extractFPIDate(String dateTime) throws ParseException {
        Date date = FPI_DATETIMEFORMAT.parse(dateTime);
        return FPI_DATEFORMAT.format(date);
    } */

    private synchronized String getDHIS2CurrentDate() {

        return DHIS2_DATEFORMAT.format(new Date());
    }

    private void clearoutImages(List<FlightPassengerInformation> fpInfos) {

        for (FlightPassengerInformation fpInfo : fpInfos) {
            this.clearoutImages(fpInfo);
        }
    }

    private void clearoutImages(FlightPassengerInformation fpInfo) {

        PassengerInformation pInfo = fpInfo.getPassengerInformation();
        if (pInfo != null) {
            pInfo.setArrivalCardImage(DHIS2Constants.BIN_CLEAR_VAL);
            pInfo.setFaceImage(DHIS2Constants.BIN_CLEAR_VAL);
            pInfo.setPassportDataPage(DHIS2Constants.BIN_CLEAR_VAL);
        }
    }

    public DHISResponse pushFlightPassengerInformation(List<FlightPassengerInformation> fpInfos) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoke pushFlightPassengerInformation");
        }
        DHISResponse result = new DHISResponse();
        try {
            List<String> teIds = this.saveFlightPassengerInformation(fpInfos);
            result.setStatus(DHIS2Constants.OK_CODE);
            result.setResponse(this.gson.toJson(teIds));
        } catch (Exception e) {
            this.clearoutImages(fpInfos);
            String message = "Error in pushing flight passenger info: " + fpInfos;
            LOGGER.error(message, e);
            result.setStatus(DHIS2Constants.INTERNAL_ERROR_CODE);
            result.setResponse(message + " - " + e.getMessage());
        }
        return result;
    }

}
