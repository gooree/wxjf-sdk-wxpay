package com.wxjfkg.sdk.wxpay.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.wxjfkg.sdk.wxpay.core.WxPayResponse;

@XmlRootElement(name = "xml")
public class ReverseResponse extends WxPayResponse {

	private String recall;

	public String getRecall() {
		return recall;
	}

	public void setRecall(String recall) {
		this.recall = recall;
	}
	
}
