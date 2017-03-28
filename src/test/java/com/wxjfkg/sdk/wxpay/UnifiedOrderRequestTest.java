package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.UnifiedOrderRequest;
import com.wxjfkg.sdk.wxpay.response.UnifiedOrderResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class UnifiedOrderRequestTest {
	
	private static Logger logger = LoggerFactory.getLogger(UnifiedOrderRequestTest.class);

	@Test
	public void testUnifiedOrder() throws WxPayApiException {
		UnifiedOrderRequest request = new UnifiedOrderRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "WEB");
		request.setBody("JSAPI支付测试");
		request.setAttach("支付测试");
		request.setOut_trade_no("20150806125346");
		request.setFee_type("CNY");
		request.setTotal_fee(1);
		request.setSpbill_create_ip("14.23.150.211");
		request.setNotify_url("http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
		request.setTrade_type("JSAPI");
		request.setOpenid("owiuzvy4QvYAN4zxteVxZD8WbUKo");
		HttpApiResponse<UnifiedOrderResponse> response = wxPayClient.execute(request, UnifiedOrderResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<device_info><![CDATA[WEB]]></device_info>"
				+ "<nonce_str><![CDATA[L3EjWQ3PaMs03JtT]]></nonce_str>"
				+ "<sign><![CDATA[C2B728439FF14BD64F657A9B2B0914FE]]></sign>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<prepay_id><![CDATA[wx201703201539506ccab48a6d0705107119]]></prepay_id>"
				+ "<trade_type><![CDATA[JSAPI]]></trade_type>"
				+ "</xml>";
		UnifiedOrderResponse result = CodecUtils.toObject(xml, UnifiedOrderResponse.class);
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("WEB", result.getDevice_info());
		Assert.assertEquals("L3EjWQ3PaMs03JtT", result.getNonce_str());
		Assert.assertEquals("C2B728439FF14BD64F657A9B2B0914FE", result.getSign());
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("wx201703201539506ccab48a6d0705107119", result.getPrepay_id());
		Assert.assertEquals("JSAPI", result.getTrade_type());
	}
	
}
