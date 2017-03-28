package com.wxjfkg.sdk.wxpay.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.ApiConstants;
import com.wxjfkg.sdk.annotation.HttpParam;
import com.wxjfkg.sdk.http.BasicHttpApiRequest;
import com.wxjfkg.sdk.http.HttpApiRequest;
import com.wxjfkg.sdk.http.HttpMethod;

/**
 * 微信支付公共请求父类
 * 
 * @author GuoRui
 *
 */
public abstract class WxPayRequest implements HttpApiRequest {

	private static final Logger logger = LoggerFactory.getLogger(WxPayRequest.class);
	
	private BasicHttpApiRequest request;
	
	public WxPayRequest(String url) {
		this(url, HttpMethod.GET);
	}

	public WxPayRequest(String url, HttpMethod method) {
		this.request = new BasicHttpApiRequest(url, method);
	}
	
	public WxPayRequest(BasicHttpApiRequest request) {
		this.request = request;
	}
	
	public void addHeader(String name, Object value) {
		this.request.addHeader(name, value);
	}
	
	public Map<String, String> getParameterMap() {
		Map<String, String> parameterMap = this.request.getParameterMap();
		parameterMap.putAll(getParameters());
		return parameterMap;
	}
	
	public void setParameter(String name, String value) {
		this.request.setParameter(name, value);
	}
	
	public void setParameters(Map<String, String> params) {
		this.request.setParameters(params);
	}
	
	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public String getHeader(String name) {
		return request.getHeader(name);
	}

	public Enumeration<String> getHeaders(String name) {
		return request.getHeaders(name);
	}

	public Enumeration<String> getHeaderNames() {
		return request.getHeaderNames();
	}
	
	public String getProtocol() {
		return request.getProtocol();
	}

	public String getUrl() {
		return request.getUrl();
	}

	public String getMethod() {
		return request.getMethod();
	}

	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}
	
	public boolean isMultipartRequest() {
		return request.isMultipartRequest();
	}
	
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		Field[] fields = this.getClass().getDeclaredFields();
		
		try {
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				
				HttpParam param = field.getAnnotation(HttpParam.class);
				String paramName = (param == null) ? field.getName() : param.value();
	
				field.setAccessible(true);
				put(parameters, paramName, field.get(this));
			}
		} catch (Exception e) {
			logger.error("prepare parameters error,cannot access object field.", e);
			throw new RuntimeException(e);
		}
		return parameters;
	}
	
	private void put(Map<String, String> map, String key, Object value) {
		if(value != null) {
			String strValue;

			if (value instanceof String) {
				strValue = (String) value;
			} else if (value instanceof Integer) {
				strValue = ((Integer) value).toString();
			} else if (value instanceof Long) {
				strValue = ((Long) value).toString();
			} else if (value instanceof Float) {
				strValue = ((Float) value).toString();
			} else if (value instanceof Double) {
				strValue = ((Double) value).toString();
			} else if (value instanceof Boolean) {
				strValue = ((Boolean) value).toString();
			} else if (value instanceof Date) {
	            DateFormat format = new SimpleDateFormat(ApiConstants.DATE_TIME_FORMAT);
	            format.setTimeZone(TimeZone.getTimeZone(ApiConstants.DATE_TIME_FORMAT));
				strValue = format.format((Date) value);
			} else {
				strValue = value.toString();
			}
			
			map.put(key, strValue);
		}
	}
	
}
