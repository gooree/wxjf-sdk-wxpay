package com.wxjfkg.sdk.wxpay.request;

import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;

public class AuthCodeToOpenIdRequest extends WxPayRequest {

	public AuthCodeToOpenIdRequest() {
		super("https://api.mch.weixin.qq.com/tools/authcodetoopenid",
				HttpMethod.POST);
	}
	
	private String auth_code;

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	
}
