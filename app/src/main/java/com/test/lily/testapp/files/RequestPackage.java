package com.test.lily.testapp.files;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Lily on 03/08/2015.
 */
public class RequestPackage {

    private String uri;
    private String requestMethod;
    private Map<String, String> params;

    public RequestPackage() {
        requestMethod = "GET";
        params = null;
    }

    public String getUri() {

        StringBuilder sb = new StringBuilder();
        String s = uri + "?";
        for (String key : params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }

        return s + sb.toString();
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
