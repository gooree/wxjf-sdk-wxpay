package com.wxjfkg.sdk.wxpay;

import com.wxjfkg.sdk.wxpay.core.WxPayResponse;

/**
 * 请求结果回调（用于处理特殊结构的xml报文）
 * 
 * @author GuoRui
 *
 * @param <T>
 */
public interface ResultCallback<T extends WxPayResponse> {

	public void extract(String xml, T response) throws WxPayApiException;
	
}
