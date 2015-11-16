package com.offering.pay;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.offering.utils.HttpUtils;
import com.offering.utils.MD5Util;
import com.offering.utils.Utils;

/**
 * 微信支付共通类
 * @author surfacepro3
 *
 */
public class WeixinPay {

	/**
	 * 公众账号ID
	 */
	private final static String APP_ID = "wx1c1fc18afdcce0ce";
	
	/**
	 * 商户号
	 */
	private final static String MCH_ID = "1259291701";
	
	/**
	 * 统一下单
	 */
	public static void unifiedorder(){
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("appid", APP_ID);
		params.put("attach", "支付测试");
		params.put("body", "offering支付测试");
		//商户号
		params.put("mch_id", MCH_ID);
		//32位随机字符串
		params.put("nonce_str", getNonceStr());
		//接收微信支付异步通知回调地址
		params.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
		//订单号
		params.put("out_trade_no", getTradeNo());
		//终端id
		params.put("spbill_create_ip", Utils.getLocalIp());
		//总金额 (单位为分)
		params.put("total_fee", "1");
		//交易类型 SAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
		params.put("trade_type", "APP");
//		params.put("openid", APP_ID);
		//签名
		params.put("sign", sign(params));
		HttpUtils.xmlPost(url, params);
	}
	
	/**
	 * 32位随机字符串
	 * @return
	 */
	private static String getNonceStr(){
		return MD5Util.string2MD5(Utils.randNum(6));
	}
	
	/**
	 * 商户订单号
	 * @return
	 */
	private static String getTradeNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date()) + Utils.randNum(6);
	}
	
	/**
	 * 签名
	 * @return
	 */
	private static String sign(Map<String, String> params){
		if(params != null){
			StringBuilder str = new StringBuilder();
			for(Entry<String, String> entry : params.entrySet()){
				if(!Utils.isEmpty(entry.getValue())){
					str.append(entry.getKey()).append("=")
					   .append(entry.getValue()).append("&");
				}
			}
			str.append("key=80e68efeea11f200ffde07cdf61197f7");
			System.out.println(str.toString());
			return MD5Util.string2MD5(str.toString(),"UTF-8").toUpperCase();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
//		System.out.println(sign(params));
		unifiedorder();
	}
}
