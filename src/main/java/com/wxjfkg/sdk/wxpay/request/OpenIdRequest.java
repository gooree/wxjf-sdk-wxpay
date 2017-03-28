package com.wxjfkg.sdk.wxpay.request;

import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;

public class OpenIdRequest extends WxPayRequest {

	public OpenIdRequest() {
		super(
				"https://api.weixin.qq.com/sns/oauth2/access_token",
				HttpMethod.GET);
	}
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
