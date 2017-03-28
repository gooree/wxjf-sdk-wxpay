package com.wxjfkg.sdk.wxpay.response;

import com.wxjfkg.sdk.wxpay.core.WxPayResponse;

public class OpenIdResponse extends WxPayResponse {

	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
