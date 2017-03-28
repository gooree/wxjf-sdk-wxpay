package com.wxjfkg.sdk.wxpay;

public class WxPayApiException extends Exception {

	private static final long serialVersionUID = 4143474442018214536L;

	private String errCode;
	private String errMsg;

	public WxPayApiException() {
		super();
	}

	public WxPayApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public WxPayApiException(String message) {
		super(message);
	}

	public WxPayApiException(Throwable cause) {
		super(cause);
	}

	public WxPayApiException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

}
