package com.wxjfkg.sdk.wxpay.request;

import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;

public class ReportRequest extends WxPayRequest {

	public ReportRequest() {
		super("https://api.mch.weixin.qq.com/payitil/report", HttpMethod.POST);
	}

	private String interface_url;
	
	private int execute_time;
	
	private int execute_time_;
	
	private String return_code;
	
	private String return_msg;
	
	private String result_code;
	
	private String err_code;
	
	private String err_code_des;
	
	private String out_trade_no;
	
	private String user_ip;
	
	private String time;

	public String getInterface_url() {
		return interface_url;
	}

	public void setInterface_url(String interface_url) {
		this.interface_url = interface_url;
	}

	public int getExecute_time() {
		return execute_time;
	}

	public void setExecute_time(int execute_time) {
		this.execute_time = execute_time;
	}

	public int getExecute_time_() {
		return execute_time_;
	}

	public void setExecute_time_(int execute_time_) {
		this.execute_time_ = execute_time_;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
