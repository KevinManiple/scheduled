package com.kevin.demo.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 网络连接工具类
 * 
 * @author: Kai.Zhao
 */
public enum HttpConnectionUtil {
    INSTANCE;
    
    private CloseableHttpClient httpclient;
    
    private RequestConfig config;
    
    private ResponseHandler<String> responseHandler;
    
    private Properties properties;
    
    private String configPath = "config/httpConfig.properties";
    
    private HttpConnectionUtil() {
        initProperties();
        initHttpClient();
        initHttpConfig();
        initResponseHandler();
    }
    
    /**
     * GET请求
     * 
     * @param url
     *            请求地址
     * @param headerParams
     *            请求信息头
     * @return 请求结果
     * @throws RuntimeException
     *             运行时异常
     */
    public String get(String url, Map<String, String> headerParams) {
        HttpGet httpGet = new HttpGet(url);
        try {
            setHeaders(headerParams, httpGet);
            httpGet.setConfig(config);
            return httpclient.execute(httpGet, responseHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * POST请求
     * 
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param headerParams
     *            请求信息头
     * @return 请求结果
     * @throws RuntimeException
     *             运行时异常
     */
    public String post(String url, Map<String, String> params, Map<String, String> headerParams) {
        HttpPost httpPost = new HttpPost(url);
        try {
            setHeaders(headerParams, httpPost);
            setRequestParams(params, httpPost);
            httpPost.setConfig(config);
            return httpclient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 初始化配置
     */
    private void initProperties() {
        properties = new Properties();
        try {
            InputStream is = HttpConnectionUtil.class.getClassLoader().getResourceAsStream(configPath);
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 初始化返回处理器
     */
    private void initResponseHandler() {
        responseHandler = new ResponseHandler<String>() {
            
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
    }
    
    /**
     * 初始化连接配置
     */
    private void initHttpConfig() {
        config = RequestConfig.custom().setSocketTimeout(getIntVal("socketTimeout"))
                .setConnectTimeout(getIntVal("connectTimeout"))
                .setConnectionRequestTimeout(getIntVal("connectionRequestTimeout")).build();
    }
    
    /**
     * 初始化连接客户端
     */
    private void initHttpClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(getBoolVal("tcpNoDelay"))
                .setSoTimeout(getIntVal("soTimeout")).build();
        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setValidateAfterInactivity(getIntVal("validateAfterInactivity"));
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Charset.forName(getStringVal("charset"))).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(getIntVal("maxTotal"));
        connManager.setDefaultMaxPerRoute(getIntVal("defaultMaxPerRoute"));
        httpclient = HttpClients.custom().setConnectionManager(connManager).build();
    }
    
    /**
     * 读取数值
     * 
     * @param key
     *            关键字
     * @return
     */
    private Integer getIntVal(final String key) {
        return properties.getProperty(key) == null ? null : Integer.valueOf(properties.getProperty(key));
    }
    
    /**
     * 读取布尔值
     * 
     * @param key
     *            关键字
     * @return
     */
    private Boolean getBoolVal(final String key) {
        return properties.getProperty(key) == null ? null : Boolean.valueOf(properties.getProperty(key));
    }
    
    /**
     * 读取字符串
     * 
     * @param key
     *            关键字
     * @return
     */
    private String getStringVal(final String key) {
        return properties.getProperty(key);
    }
    
    /**
     * 设置信息头
     * 
     * @param headerParams
     *            请求信息头
     * @param request
     */
    private void setHeaders(Map<String, String> headerParams, HttpRequestBase request) {
        if (MapUtils.isNotEmpty(headerParams)) {
            Iterator<Entry<String, String>> it = headerParams.entrySet().iterator();
            Entry<String, String> entry;
            while (it.hasNext()) {
                entry = it.next();
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * 设置请求参数
     * 
     * @param params
     *            请求参数
     * @param httpPost
     * @throws UnsupportedEncodingException
     */
    private void setRequestParams(Map<String, String> params, HttpPost httpPost) throws UnsupportedEncodingException {
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            Entry<String, String> entry;
            while (it.hasNext()) {
                entry = it.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        }
    }
}
