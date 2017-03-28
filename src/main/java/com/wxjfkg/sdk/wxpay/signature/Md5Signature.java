package com.wxjfkg.sdk.wxpay.signature;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wxjfkg.sdk.sign.AbstractSignature;

/**
 * 微信支付Md5签名算法工具类
 * 
 * @author GuoRui
 *
 */
public class Md5Signature extends AbstractSignature {
	
	private static Logger logger = LoggerFactory.getLogger(Md5Signature.class);
	
	public Md5Signature(String privateKey, String charset) {
		super(privateKey, charset);
	}

	@Override
	public String sign(String content) {
		if(StringUtils.isBlank(content)) {
			return "";
		}
		String param = content + "key=" + privateKey;
		logger.debug("sign before: {}", param);
		String result = DigestUtils.md5Hex(param).toUpperCase();
		logger.debug("sign after: {}", result);
		return result;
	}
	
}
