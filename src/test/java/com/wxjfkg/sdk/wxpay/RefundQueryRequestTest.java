package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.RefundQueryRequest;
import com.wxjfkg.sdk.wxpay.response.RefundQueryResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class RefundQueryRequestTest {

	private static Logger logger = LoggerFactory.getLogger(RefundQueryRequestTest.class);

	@Test
	public void testRefundQuery() throws WxPayApiException {
		RefundQueryRequest request = new RefundQueryRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "WEB");
		request.setOut_trade_no("20150806125346");
		HttpApiResponse<RefundQueryResponse> response = wxPayClient.execute(request, RefundQueryResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<nonce_str><![CDATA[FFuwiefZTeDrYiY9]]></nonce_str>"
				+ "<sign><![CDATA[8C5E6B86180FDEFA7FA2CCFC8D9A05E0]]></sign>"
				+ "<err_code><![CDATA[REFUNDNOTEXIST]]></err_code>"
				+ "<err_code_des><![CDATA[not exist]]></err_code_des>"
				+ "<result_code><![CDATA[FAIL]]></result_code>"
				+ "</xml>";
		RefundQueryResponse result = CodecUtils.toObject(xml, RefundQueryResponse.class);
		Assert.assertEquals("SUCCESS", result.getReturn_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("FAIL", result.getResult_code());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("FFuwiefZTeDrYiY9", result.getNonce_str());
		Assert.assertEquals("8C5E6B86180FDEFA7FA2CCFC8D9A05E0", result.getSign());
	}
	
}
