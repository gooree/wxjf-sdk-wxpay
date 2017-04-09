package com.wxjfkg.sdk.wxpay.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wxjfkg.sdk.wxpay.signature.Md5Signature;

/**
 * 微信支付工具类
 * 
 * @author GuoRui
 *
 */
public class WxPayUtils {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 报文加签
	 * 
	 * @param map
	 * @param appSecret
	 * @return
	 */
	public static String sign(Map<String, String> map, String appSecret) {
		Md5Signature signature = new Md5Signature(appSecret, DEFAULT_CHARSET);
        
        List<String> params = new ArrayList<String>();
		for (Map.Entry<String, String> item : map.entrySet()) {
			if(StringUtils.isNotBlank(item.getValue())) {
				params.add(item.getKey() + "=" + item.getValue() + "&");
			}
		}
		String [] arrayToSort = params.toArray(new String[params.size()]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(arrayToSort[i]);
		}
		
		String sign = signature.sign(sb.toString());
		return sign;
	}
	
	/**
	 * 报文验签
	 * @param xml
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(String xml, String appSecret) throws Exception {
		if(StringUtils.isBlank(xml)) {
			return false;
		}
		if (!xml.contains("sign")) {
			return true;
		}
		
		Map<String, String> map = getMapFromXML(xml);
		String sign = map.get("sign").toString();
		if (StringUtils.isBlank(sign)) {
			return false;
		}
		map.put("sign", "");
		
        String newSign = sign(map, appSecret);

        if(!newSign.equals(sign)){
            return false;
        }
        return true;
	}

	/**
	 * 报文转Map对象
	 * @param xmlString
	 * @return
	 * @throws Exception
	 */
    public static Map<String, String> getMapFromXML(String xmlString) throws Exception {
        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        ByteArrayInputStream bais = null;
        if (StringUtils.isNotBlank(xmlString)) {
        	bais = new ByteArrayInputStream(xmlString.getBytes());
        }
        
        Document document = builder.parse(bais);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, String> map = new HashMap<String, String>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;
    }

}
