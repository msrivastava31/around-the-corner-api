package edu.uw.medhas.aroundthecorner.client;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uw.medhas.aroundthecorner.exception.AppException;

import org.apache.http.client.config.RequestConfig;


public abstract class AbstractHttpJsonClient {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpJsonClient.class);
    
    private final HttpClient httpClient;
    private final String baseUrl;
    protected ObjectMapper objectMapper;
    
    public AbstractHttpJsonClient(String userAgent, String baseUrl) {
        final RequestConfig requestConfig = RequestConfig.custom()
                                                         .setConnectTimeout(120000)
                                                         .setConnectionRequestTimeout(120000)
                                                         .build();
        
        httpClient = HttpClientBuilder.create()
                                      .setUserAgent(userAgent)
                                      .setDefaultRequestConfig(requestConfig)
                                      .build();
        
        this.baseUrl = baseUrl;
    }
    
    protected abstract void setObjectManager();
    
    protected <O> O getObject(String path, Class<O> clientClass, Map<String, String> queryParams) {
        final String url = buildRequestUrl(path, queryParams);
        logger.info("Request Url: " + url);
        
        final HttpGet request = new HttpGet(url);
        request.addHeader("accept", "application/json");
        
        try {
            return getResponse(request, clientClass);
        } catch (ClientProtocolException cpex) {
            logger.error(cpex.getMessage(), cpex);
            throw new AppException("ClientProtocolException encountered sending GET request to: " + url);
        } catch (IOException ioex) {
            logger.error(ioex.getMessage(), ioex);
            throw new AppException("IOException encountered sending GET request to: " + url);
        }
    }
    
    private <O> O getResponse(HttpUriRequest request, Class<O> clientClass) throws ClientProtocolException, IOException {
        final HttpResponse response = httpClient.execute(request);
        
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new AppException("Request Unsuccessful");
        }
        
        O jsonObject;
        try {
            jsonObject = objectMapper.readValue(response.getEntity().getContent(), clientClass);
        } catch (IOException e) {
            throw new AppException("Error reading response.", e);
        }

        if (jsonObject == null) {
            throw new AppException("No response returned from service.");
        }
        
        HttpClientUtils.closeQuietly(response);

        return jsonObject;
    }
    
    private String buildRequestUrl(String path, Map<String, String> queryParams) {
        String requestUrl = baseUrl + path;
        
        if (queryParams.isEmpty()) {
            return requestUrl;
        }
        
        requestUrl += "?";
        
        for (String key : queryParams.keySet()) {
            requestUrl += "&" + key + "=" + queryParams.get(key);
        }
        
        return requestUrl;
    }
}
