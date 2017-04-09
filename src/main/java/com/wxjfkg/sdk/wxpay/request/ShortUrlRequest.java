package com.wxjfkg.sdk.wxpay.request;

import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;

public class ShortUrlRequest extends WxPayRequest {

	public ShortUrlRequest() {
		super("https://api.mch.weixin.qq.com/tools/shorturl", HttpMethod.POST);
	}
	
	private String long_url;

	public String getLong_url() {
		return long_url;
	}

	public void setLong_url(String long_url) {
		this.long_url = long_url;
	}
	
}
