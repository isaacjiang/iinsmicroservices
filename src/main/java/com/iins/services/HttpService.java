package com.iins.services;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class HttpService {
    private URI uri;
    private CloseableHttpClient httpclient;
    private HttpCacheContext context;
    private CacheResponseStatus responseStatus;
    private HttpGet httpGet;
    private HttpPost httpPost;

    public HttpService() {
        context = HttpCacheContext.create();
        context.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
    }

    public HttpService setHttpclient(String host, String path, JSONObject parameters) {
        CacheConfig cacheConfig = CacheConfig.custom()
                .setMaxCacheEntries(1000)
                .setMaxObjectSize(8192)
                .build();
        httpclient = CachingHttpClients.custom()
                .setCacheConfig(cacheConfig)
                .build();

        this.setURI("http", host, path, parameters);
        return this;
    }

    public HttpService setHttpclient(String host, String path, JSONObject parameters, String username, String password) {

        CacheConfig cacheConfig = CacheConfig.custom()
                .setMaxCacheEntries(1000)
                .setMaxObjectSize(8192)
                .build();

        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(host, 443),
                new UsernamePasswordCredentials(username, password));
        httpclient = CachingHttpClients.custom()
                .setCacheConfig(cacheConfig)
                .setSSLContext(sslContext)
                .setDefaultCredentialsProvider(credsProvider)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        this.setURI("https", host, path, parameters);
        return this;
    }

    private void setURI(String scheme, String host, String path, JSONObject parameters) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Object key : parameters.keySet()) {
            params.add(new BasicNameValuePair(key.toString(), parameters.get(key.toString()).toString()));
        }
        try {
            uri = new URIBuilder()
                    .setScheme(scheme)
                    .setHost(host)
                    .setPath(path)
                    .setParameters(params)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    public String get() throws IOException {

        HttpGet httpget = new HttpGet(uri);
        // System.out.println("Executing request " + httpget.getRequestLine());
        // Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        String responseBody = httpclient.execute(httpget, responseHandler);
        // System.out.println("----------------------------------------");
        // System.out.println(responseBody);
        return responseBody;

    }


    public String post(String params) {
        String result = null;
        try {

            httpPost = new HttpPost(uri);
            StringEntity entity = new StringEntity(params);
            //System.out.println(EntityUtils.toString(entity));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("UserModel-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = httpclient.execute(httpPost, context);
//        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
            result = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println(httpResponse.getStatusLine());
            httpResponse.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return result;

    }


}
