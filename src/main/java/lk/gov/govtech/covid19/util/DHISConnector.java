package lk.gov.govtech.covid19.util;

import lk.gov.govtech.covid19.config.DHISConfiguration;
import lk.gov.govtech.covid19.dto.Patient;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class DHISConnector {

    private final static Logger LOGGER = LoggerFactory.getLogger(DHISConnector.class);
    @Autowired
    private DHISConfiguration dhisConfiguration;
    private final static String URL = "https://play.dhis2.org/2.33.2/api"; //TODO need to configure this

    protected void setAuthorizationHeader(HttpMethodBase request) {

        String basicauth = "admin" + ":" + "district"; //TODO paramterize
        String encodedString = Base64.getEncoder().encodeToString(basicauth.getBytes());
        request.addRequestHeader("Authorization", "Basic " + encodedString);
    }

    //TODO Add debug logs
    public String getEntityTypes() {

        GetMethod getRequest = new GetMethod(URL + "/trackedEntityAttributes");
        String entitytypes = "";

        try {

            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == HttpStatus.SC_OK) {
                entitytypes = new String(getRequest.getResponseBody());
            }

        } catch (IOException e) {
            LOGGER.error("Error while getting entity types information", e);
        } finally {
            getRequest.releaseConnection();
        }
        return entitytypes;
    }

    public String getOrganizationUnits() {

        GetMethod getRequest = new GetMethod(URL + "/organisationUnits");
        String organizationUnits = "";

        try {

            HttpClient httpClient = getHttpClient();
            setAuthorizationHeader(getRequest);
            int response = httpClient.executeMethod(getRequest);
            if (response == HttpStatus.SC_OK) {
                organizationUnits = new String(getRequest.getResponseBody());
            }

        } catch (IOException e) {
            LOGGER.error("Error while getting organization units information", e);
        } finally {
            getRequest.releaseConnection();
        }
        return organizationUnits;
    }

    public boolean register(String jsonpayload) {

        LOGGER.info("################## dhisConfiguration : " + dhisConfiguration.getUrl());
//        PostMethod postRequest = new PostMethod("http://dihs2.lk");
//
//        try {
//
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("Creating patients information");
//            }
//            HttpClient httpClient = getHttpClient();
////            setAuthorizationHeader(postRequest);
//            postRequest.setRequestEntity(getRequestEntity(jsonpayload));
//            int response = httpClient.executeMethod(postRequest);
//            if (response == HttpStatus.SC_OK) {
//                String respStr = new String(postRequest.getResponseBody());
//            }
//
//        } catch (IOException e) {
//            LOGGER.error("Error while creating patients information", e);
//        } finally {
//            postRequest.releaseConnection();
//        }
        return true;
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
