package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.RefundRequest;
import com.wxjfkg.sdk.wxpay.response.RefundResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class RefundRequestTest {

	private static Logger logger = LoggerFactory.getLogger(RefundRequestTest.class);

	@Test
	public void testRefund() throws WxPayApiException {
		RefundRequest request = new RefundRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "WEB");
		request.setOut_trade_no("20150806125346");
		request.setOut_refund_no("20150806125346");
		request.setTotal_fee(1);
		request.setRefund_fee(1);
		request.setOp_user_id("1316065901");
		HttpApiResponse<RefundResponse> response = wxPayClient.execute(request, RefundResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<sub_mch_id><![CDATA[]]></sub_mch_id>"
				+ "<nonce_str><![CDATA[z6utIGUSdsBnADC7]]></nonce_str>"
				+ "<sign><![CDATA[576B31FAB4AC2063FD1862738EE75A14]]></sign>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "</xml>";
		RefundResponse result = CodecUtils.toObject(xml, RefundResponse.class);
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("z6utIGUSdsBnADC7", result.getNonce_str());
		Assert.assertEquals("576B31FAB4AC2063FD1862738EE75A14", result.getSign());
		Assert.assertEquals("SUCCESS", result.getResult_code());
	}
	
}
