package com.wxjfkg.sdk.wxpay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.http.HttpApiResponse;
import com.wxjfkg.sdk.http.HttpException;
import com.wxjfkg.sdk.http.HttpMethod;
import com.wxjfkg.sdk.http.OkHttpTemplate;
import com.wxjfkg.sdk.http.ResponseExtractor;
import com.wxjfkg.sdk.wxpay.core.WxPayRequest;
import com.wxjfkg.sdk.wxpay.core.WxPayResponse;
import com.wxjfkg.sdk.wxpay.utils.CodecUtils;
import com.wxjfkg.sdk.wxpay.utils.RandomUtils;
import com.wxjfkg.sdk.wxpay.utils.WxPayUtils;

public class WxPayClient {
	
	private static final Logger logger = LoggerFactory.getLogger(WxPayClient.class);

	private String appId;
	
	private String appSecret;
	
	private String apiSecret;
	
	private String mchId;
	
	private String deviceInfo = "WEB";
	
	private String signType = "MD5";
	
	private static final OkHttpTemplate template = new OkHttpTemplate();
	
	public WxPayClient(String appId, String appSecret, String apiSecret, String mchId, String deviceInfo) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.apiSecret = apiSecret;
		this.mchId = mchId;
		this.deviceInfo = deviceInfo;
	}
	
	/**
	 * 执行微信支付请求（默认不带回调方法）
	 * 
	 * @param request
	 * @param entityClazz
	 * @return
	 * @throws WxPayApiException
	 */
	public <T extends WxPayResponse> HttpApiResponse<T> execute(
			WxPayRequest request, Class<T> entityClazz)
			throws WxPayApiException {
		return executeWithCallback(request, entityClazz, null);
	}
	
	/**
	 * Execute with raw response content.
	 * @param request
	 * @return
	 * @throws WxPayApiException
	 */
	public String execute(WxPayRequest request) throws WxPayApiException {
		String result = null;
		try {
			if(request.getMethod().equals(HttpMethod.GET.getMethod())) {
				result = template.get(request.getUrl(), request.getParameterMap());
			} else {
				result = template.post(request.getUrl(), request.getParameterMap());
			}
			logger.debug("http response:{}", result);
		} catch (HttpException ex) {
			logger.error("http api execute failure.", ex);
		}
		return result;
	}
	
	public InputStream executeWithRawStream(WxPayRequest request) throws WxPayApiException {
		/*
		 * 0.设置公共请求参数
		 */
		request.setParameter("appid", this.appId);
		request.setParameter("mch_id", mchId);
		request.setParameter("device_info", deviceInfo);
		request.setParameter("nonce_str",
				RandomUtils.getRandomStringByLength(32));

		/*
		 * 1.请求签名
		 */
		Map<String, String> parameters = request.getParameterMap();
		String sign = WxPayUtils.sign(parameters, apiSecret);
		parameters.put("sign", sign);

		/*
		 * 2.请求对象序列化
		 */
		String postEntity = CodecUtils.toXml(parameters, "xml");
		logger.debug("http request:{}", postEntity);

		try {
			InputStream outStream = template.post(request.getUrl(),
					OkHttpTemplate.XML, postEntity,
					new ResponseExtractor<InputStream>() {
						@Override
						public InputStream extract(Response response)
								throws IOException {
							return response.body().byteStream();
						}
					});

			return outStream;
		} catch (HttpException ex) {
			logger.error("http api execute failure.", ex);
			throw new WxPayApiException(ex);
		}
	}
	
	/**
	 * 执行微信支付请求（带回调方法）
	 * 
	 * @param request
	 * @param entityClazz
	 * @param callback
	 * @return
	 * @throws WxPayApiException
	 */
	public <T extends WxPayResponse> HttpApiResponse<T> executeWithCallback(
			WxPayRequest request, Class<T> entityClazz, ResultCallback<T> callback) throws WxPayApiException {
		/*
		 * 0.设置公共请求参数
		 */
		request.setParameter("appid", appId);
		request.setParameter("mch_id", mchId);
		request.setParameter("device_info", deviceInfo);
		request.setParameter("nonce_str", RandomUtils.getRandomStringByLength(32));
		
		/*
		 * 1.请求签名
		 */
		Map<String, String> parameters = request.getParameterMap();
		String sign = WxPayUtils.sign(parameters, apiSecret);
		parameters.put("sign", sign);
		
		/*
		 * 2.请求对象序列化
		 */
		String postEntity = CodecUtils.toXml(parameters, "xml");
		logger.debug("http request:{}", postEntity);
		
		/*
		 * 3.发送请求
		 */
		HttpApiResponse<T> response = new HttpApiResponse<T>();
		String result = null;
		try {
			result = template.postEntity(request.getUrl(), OkHttpTemplate.XML, postEntity);
			response.setBody(result);
			logger.debug("http response:{}", result);
		} catch (HttpException ex) {
			logger.error("http api execute failure.", ex);
			response.setCode(String.valueOf(ex.getStatus()));
			response.setMessage(ex.getReason());
		}
		
		/*
		 * 4.响应报文验签
		 */
		try {
			if (WxPayUtils.verifySign(result, apiSecret)) {
				/*
				 * 4.1报文验签成功，反序列化响应报文
				 */
				T entity = CodecUtils.toObject(result, entityClazz);
				if (callback != null) {
					callback.extract(result, entity);
				}
				response.setEntity(entity);
			} else {
				/*
				 * 4.2报文验签失败，返回失败信息
				 */
				logger.debug("check signature failed. response content: {}", result);
				response.setCode("500");
				response.setMessage("返回报文签名错误。");
			}
		} catch (Exception e) {
			throw new WxPayApiException(e);
		}
		
		return response;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}
	
}
