package com.wxjfkg.sdk.wxpay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wxjfkg.sdk.wxpay.model.RefundOrderData;
import com.wxjfkg.sdk.wxpay.response.RefundQueryResponse;
import com.wxjfkg.sdk.wxpay.utils.MapUtils;
import com.wxjfkg.sdk.wxpay.utils.WxPayUtils;

public class RefundQueryResultCallback implements ResultCallback<RefundQueryResponse> {

	@Override
	public void extract(String xml, RefundQueryResponse response)
			throws WxPayApiException {
		Map<String, Object> map;
		try {
			map = WxPayUtils.getMapFromXML(xml);
			
			int count = Integer.parseInt((String) map.get("refund_count"));

			if (count > 1) {
				List<RefundOrderData> refundList = new ArrayList<RefundOrderData>();
				
				for (int i = 0; i < count; i++) {
					RefundOrderData refundOrderData = new RefundOrderData();

					refundOrderData.setOutRefundNo(MapUtils.getStringFromMap(map,
							"out_refund_no_" + i, ""));
					refundOrderData.setRefundID(MapUtils.getStringFromMap(map,
							"refund_id_" + i, ""));
					refundOrderData.setRefundChannel(MapUtils.getStringFromMap(map,
							"refund_channel_" + i, ""));
					refundOrderData.setRefundFee(MapUtils.getIntFromMap(map,
							"refund_fee_" + i));
					refundOrderData.setCouponRefundFee(MapUtils.getIntFromMap(map,
							"coupon_refund_fee_" + i));
					refundOrderData.setRefundStatus(MapUtils.getStringFromMap(map,
							"refund_status_" + i, ""));
					refundList.add(refundOrderData);
				}

				response.setRefundList(refundList);
			}
		} catch (Exception e) {
			throw new WxPayApiException(e);
		}
	}

}
