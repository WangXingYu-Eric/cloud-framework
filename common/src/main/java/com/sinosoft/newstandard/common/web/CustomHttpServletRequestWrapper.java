package com.sinosoft.newstandard.common.web;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> customHeaders;

    private String url;

    private String method;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public CustomHttpServletRequestWrapper(HttpServletRequest request, String url, String method) {
        super(request);
        this.url = url;
        this.method = method;
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = this.customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Enumeration<String> headers = ((HttpServletRequest) getRequest()).getHeaders(name);
        String headerValue = this.customHeaders.get(name);
        if (headerValue != null) {
            Set<String> set = new HashSet<>();
            set.add(headerValue);
            if (!"Authorization".equals(name)) { // 'Authorization' 请求头需唯一,以本类中的值为准.
                while (headers.hasMoreElements()) {
                    set.add(headers.nextElement());
                }
            }
            return (Collections.enumeration(set));
        }
        return headers;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<>(customHeaders.keySet());
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        return Collections.enumeration(set);
    }

    @Override
    public String getMethod() {
        return StringUtils.isEmpty(this.method) ? super.getMethod() : this.method;
    }

    @Override
    public String getServletPath() {
        return StringUtils.isEmpty(this.url) ? super.getServletPath() : this.url;
    }

}
