package com.wxjfkg.sdk.wxpay.request;

import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;

public class CloseOrderRequest extends WxPayRequest {

	public CloseOrderRequest() {
		super("https://api.mch.weixin.qq.com/pay/closeorder", HttpMethod.POST);
	}

	private String out_trade_no;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
}
