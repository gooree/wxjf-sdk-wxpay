package com.wxjfkg.sdk.wxpay.utils;

import java.io.StringReader;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * 序列化工具类
 * 
 * @author GuoRui
 *
 */
public class CodecUtils {

	/**
	 * Map对象转成Xml报文
	 * 
	 * @param map
	 * @param root
	 * @return
	 */
	public static String toXml(Map<String, String> map, String root) {
		StringBuffer sb = new StringBuffer("<" + root + ">");
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("<" + entry.getKey() + ">");
				sb.append(entry.getValue());
				sb.append("</" + entry.getKey() + ">");
			}
		}
		sb.append("</" + root + ">");
		return sb.toString();
	}
	
	/**
	 * Xml报文转成指定类型对象
	 * 
	 * @param xml
	 * @param entityClass
	 * @return
	 */
	public static <T> T toObject(String xml, Class<T> entityClass)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		T entity = (T)jaxbUnmarshaller.unmarshal(new StringReader(xml));
		return entity;
	}
	
}
