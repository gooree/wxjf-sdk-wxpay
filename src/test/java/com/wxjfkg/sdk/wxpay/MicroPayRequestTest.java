package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.MicroPayRequest;
import com.wxjfkg.sdk.wxpay.response.MicroPayResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class MicroPayRequestTest {

	private static Logger logger = LoggerFactory.getLogger(MicroPayRequestTest.class);

	@Test
	public void testMicroPay() throws WxPayApiException {
		MicroPayRequest request = new MicroPayRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "1000");
		request.setAttach("订单额外描述");
		request.setAuth_code("120269300684844649");
		request.setBody("刷卡支付测试");
		request.setOut_trade_no("1415757673");
		request.setSpbill_create_ip("14.17.22.52");
		request.setTotal_fee(1);
		
		HttpApiResponse<MicroPayResponse> response = wxPayClient.execute(request, MicroPayResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<device_info><![CDATA[1000]]></device_info>"
				+ "<nonce_str><![CDATA[EmR5zHJYlJIhsPZs]]></nonce_str>"
				+ "<sign><![CDATA[2969CE4E2097BF66AE6E39EB7B90FA16]]></sign>"
				+ "<result_code><![CDATA[FAIL]]></result_code>"
				+ "<err_code><![CDATA[AUTH_CODE_INVALID]]></err_code>"
				+ "<err_code_des><![CDATA[101 付款码无效，请重新扫码]]></err_code_des>"
				+ "</xml>";
		MicroPayResponse result = CodecUtils.toObject(xml, MicroPayResponse.class);
		Assert.assertEquals("SUCCESS", result.getReturn_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("1000", result.getDevice_info());
		Assert.assertEquals("EmR5zHJYlJIhsPZs", result.getNonce_str());
		Assert.assertEquals("2969CE4E2097BF66AE6E39EB7B90FA16", result.getSign());
		Assert.assertEquals("FAIL", result.getResult_code());
		Assert.assertEquals("AUTH_CODE_INVALID", result.getErr_code());
		Assert.assertEquals("101 付款码无效，请重新扫码", result.getErr_code_des());
	}
}
