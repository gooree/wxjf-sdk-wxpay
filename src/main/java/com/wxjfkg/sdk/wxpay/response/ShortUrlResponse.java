package com.wxjfkg.sdk.wxpay.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.wxjfkg.sdk.wxpay.core.WxPayResponse;

@XmlRootElement(name = "xml")
public class ShortUrlResponse extends WxPayResponse {

	private String short_url;

	public String getShort_url() {
		return short_url;
	}

	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	
}
