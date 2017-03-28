package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.wxpay.request.OrderQueryRequest;
import com.wxjfkg.sdk.wxpay.response.OrderQueryResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;

public class OrderQueryRequestTest {

	private static Logger logger = LoggerFactory.getLogger(OrderQueryRequestTest.class);

	@Test
	public void testUnifiedOrder() throws WxPayApiException {
		OrderQueryRequest request = new OrderQueryRequest();
		WxPayClient wxPayClient = new WxPayClient(WxPayConfig.appId,
				WxPayConfig.appSecret, WxPayConfig.mchId, "WEB");
		request.setOut_trade_no("20150806125346");
		HttpApiResponse<OrderQueryResponse> response = wxPayClient.execute(request, OrderQueryResponse.class);
		logger.debug("wxpay response: {}", response);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCodecUtils() throws Exception {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx822d716e6bf69e3a]]></appid>"
				+ "<mch_id><![CDATA[1316065901]]></mch_id>"
				+ "<nonce_str><![CDATA[S7k0EYrYEjm61zQk]]></nonce_str>"
				+ "<sign><![CDATA[D24101F3F2537964CD138ACA9F4D4CFE]]></sign>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>"
				+ "<out_trade_no><![CDATA[20150806125346]]></out_trade_no>"
				+ "<trade_state><![CDATA[NOTPAY]]></trade_state>"
				+ "<trade_state_desc><![CDATA[订单未支付]]></trade_state_desc>"
				+ "</xml>";
		OrderQueryResponse result = CodecUtils.toObject(xml, OrderQueryResponse.class);
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("OK", result.getReturn_msg());
		Assert.assertEquals("wx822d716e6bf69e3a", result.getAppid());
		Assert.assertEquals("1316065901", result.getMch_id());
		Assert.assertEquals("S7k0EYrYEjm61zQk", result.getNonce_str());
		Assert.assertEquals("D24101F3F2537964CD138ACA9F4D4CFE", result.getSign());
		Assert.assertEquals("SUCCESS", result.getResult_code());
		Assert.assertEquals("20150806125346", result.getOut_trade_no());
		Assert.assertEquals("NOTPAY", result.getTrade_state());
		Assert.assertEquals("订单未支付", result.getTrade_state_desc());
	}
	
}
