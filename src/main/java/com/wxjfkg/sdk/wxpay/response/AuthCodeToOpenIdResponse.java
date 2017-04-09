package com.wxjfkg.sdk.wxpay.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.wxjfkg.sdk.wxpay.core.WxPayResponse;

@XmlRootElement(name = "xml")
public class AuthCodeToOpenIdResponse extends WxPayResponse {

	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
