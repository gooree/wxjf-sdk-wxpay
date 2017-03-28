package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.ReverseRequest;
import com.wxjfkg.sdk.wxpay.response.ReverseResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class ReverseRequestTest {

	private static Logger logger = LoggerFactory.getLogger(ReverseRequestTest.class);

	@Test
	public void testReverse() throws WxPayApiException {
		ReverseRequest request = new ReverseRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "WEB");
		request.setOut_trade_no("1415717424");
		HttpApiResponse<ReverseResponse> response = wxPayClient.execute(
				request, ReverseResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<nonce_str><![CDATA[z6utIGUSdsBnADC7]]></nonce_str>"
				+ "<sign><![CDATA[576B31FAB4AC2063FD1862738EE75A14]]></sign>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<recall><![CDATA[N]]></recall>"
				+ "</xml>";
		ReverseResponse result = CodecUtils.toObject(xml, ReverseResponse.class);
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("z6utIGUSdsBnADC7", result.getNonce_str());
		Assert.assertEquals("576B31FAB4AC2063FD1862738EE75A14", result.getSign());
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("N", result.getRecall());
	}
}
