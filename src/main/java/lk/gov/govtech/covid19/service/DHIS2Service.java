package lk.gov.govtech.covid19.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.Enrollment;
import lk.gov.govtech.covid19.dto.EntityInstance;
import lk.gov.govtech.covid19.dto.Events;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
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
import java.util.Base64;

@Service
public class DHIS2Service {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHIS2Service.class);
    @Autowired
    private DHISConfiguration dhisConfiguration;

    public String getEntityTypes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityTypes");
        String entityTypes = "";
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting entity types");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == HttpStatus.SC_OK) {
                entityTypes = new String(getRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while getting entity types information", e);
            entityTypes = e.getLocalizedMessage();
        } finally {
            getRequest.releaseConnection();
        }
        return entityTypes;
    }

    public String getEntityAttributes() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/trackedEntityAttributes");
        String entityAttributes = "";

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting entity attributes");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == HttpStatus.SC_OK) {
                entityAttributes = new String(getRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while getting entity attributes information", e);
            entityAttributes = e.getLocalizedMessage();
        } finally {
            getRequest.releaseConnection();
        }
        return entityAttributes;
    }

    public String getOrganizationUnits() {

        GetMethod getRequest = new GetMethod(dhisConfiguration.getUrl() + "/organisationUnits");
        String organizationUnits = "";

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke getting organization units");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == HttpStatus.SC_OK) {
                organizationUnits = new String(getRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while getting organization units information", e);
            organizationUnits = e.getLocalizedMessage();
        } finally {
            getRequest.releaseConnection();
        }
        return organizationUnits;
    }

    public String createEntityInstance(EntityInstance entityInstance) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/trackedEntityInstances");
        String respStr = "";
        try {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity instance");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(entityInstance)));
            int response = httpClient.executeMethod(postRequest);
            if (response == HttpStatus.SC_OK) {
                respStr = new String(postRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating entity instance", e);
            respStr = e.getLocalizedMessage();
        } finally {
            postRequest.releaseConnection();
        }
        return respStr;
    }

    public String createEnrollment(Enrollment enrollment) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/enrollments");
        String respStr = "";
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity enrollment");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(enrollment)));
            int response = httpClient.executeMethod(postRequest);
            if (response == HttpStatus.SC_OK) {
                respStr = new String(postRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while enrolling", e);
            respStr = e.getLocalizedMessage();
        } finally {
            postRequest.releaseConnection();
        }
        return respStr;
    }

    public String createEvents(Events events) {

        PostMethod postRequest = new PostMethod(dhisConfiguration.getUrl() + "/events.json");
        String respStr = "";
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Invoke create entity events");
            }
            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(postRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            postRequest.setRequestEntity(getRequestEntity(objectMapper.writeValueAsString(events)));
            int response = httpClient.executeMethod(postRequest);
            if (response == HttpStatus.SC_OK) {
                respStr = new String(postRequest.getResponseBody());
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating event", e);
            respStr = e.getLocalizedMessage();
        } finally {
            postRequest.releaseConnection();
        }
        return respStr;
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

}
