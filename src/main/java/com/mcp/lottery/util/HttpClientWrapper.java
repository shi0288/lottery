package com.mcp.lottery.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HttpClientWrapper {

    /* 请求超时时间 */
    private static int Request_TimeOut = 20000;
    /* 当前连接池中链接 */
    private static CloseableHttpClient httpClientCur = null;

    private synchronized static CloseableHttpClient getHttpClient() {
        if (httpClientCur == null) {
             /* Http连接池只需要创建一个*/
            PoolingHttpClientConnectionManager httpPoolManager = new PoolingHttpClientConnectionManager();
                /* 连接池最大生成连接数200 */
            httpPoolManager.setMaxTotal(200);
              /* 连接池默认路由最大连接数,默认为20 */
            httpPoolManager.setDefaultMaxPerRoute(20);

            //ConnectionConfig
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(Request_TimeOut)
                    .setConnectionRequestTimeout(Request_TimeOut)
                    .setSocketTimeout(Request_TimeOut)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(
                            Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .build();
            httpClientCur = HttpClients.custom()
                    .setConnectionManager(httpPoolManager)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();
        }
        return httpClientCur;
    }


    public static HttpResult sendPost(String url, Map<String, String> headers, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        List<NameValuePair> reqParams = new ArrayList<NameValuePair>();
        if (params != null && params.size()==0) {
            for (String key : params.keySet()) {
                reqParams.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(reqParams, Consts.UTF_8));
        return HttpClientWrapper.execute(httpPost);
    }

    public static HttpResult sendPost(String url, Map<String, String> headers, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        httpPost.setEntity(new StringEntity(jsonObject.toString(),"utf-8"));
        return HttpClientWrapper.execute(httpPost);
    }


    public static HttpResult sendGet(String url, Map<String, String> headers, Map<String, String> params) {
        String getParam = "";
        if (params != null) {
            for (String key : params.keySet()) {
                getParam += ("&" + key + "=" + params.get(key));
            }
        }
        if (url.indexOf("?") > -1) {
            url += getParam;
        } else {
            url += ("?" + getParam);
        }
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpGet.addHeader(key, headers.get(key));
            }
        }
        return HttpClientWrapper.execute(httpGet);
    }

    public static HttpResult execute(HttpGet httpGet) {
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(httpGet);
            HttpResult httpResult = new HttpResult();
            for (Header header : response.getAllHeaders()) {
                if (header.getName().toLowerCase().equals("Set-Cookie".toLowerCase())) {
                    httpResult.addCookies(header.getValue());
                }
            }
            String retString = EntityUtils.toString(response.getEntity());
            httpResult.setResult(retString);
            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static HttpResult execute(HttpPost httpPost) {
        HttpContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(httpPost, context);
            HttpResult httpResult = new HttpResult();
            for (Header header : response.getAllHeaders()) {
                if (header.getName().toLowerCase().equals("Set-Cookie".toLowerCase())) {
                    httpResult.addCookies(header.getValue());
                }
            }
            String retString = EntityUtils.toString(response.getEntity());
            System.out.println(retString);
            httpResult.setResult(retString);
            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
