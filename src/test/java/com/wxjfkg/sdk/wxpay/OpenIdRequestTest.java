package com.wxjfkg.sdk.wxpay;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.wxpay.request.OpenIdRequest;

public class OpenIdRequestTest {

	private static Logger logger = LoggerFactory.getLogger(OpenIdRequestTest.class);
	
	@Test
	public void testOpenId() throws WxPayApiException {
		OpenIdRequest request = new OpenIdRequest();
		request.setCode("01101MHp0d6I6r1RGIGp0nDyHp001MHQ");
		WxPayClient wxPayClient = new WxPayClient("wx822d716e6bf69e3a",
				"liuxiangchenxi123456789009876543", "1316065901", "1000");
		
		String result = wxPayClient.execute(request);
		logger.debug("wxpay response: {}", result);
		Assert.assertNotNull(result);
	}
	
}
