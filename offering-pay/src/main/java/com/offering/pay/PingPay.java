package com.offering.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.offering.bean.trade.TradeRecord;
import com.offering.constant.GloabConstant;
import com.offering.utils.Utils;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;

public class PingPay {
	
	private final static Logger LOG = Logger.getLogger(PingPay.class);

	private final static String TEST_SK = "sk_test_WbzHKGbzH4CO1uHq5OGiz9G8";
	
	private final static String APP_ID = "app_a90iTGqbD00Cjv5i";
	
	/**
	 * 渠道 支付宝:alipay 微信:wx
	 */
	private final static String CHANNEL_ALIPAY = "alipay";
	private final static String CHANNEL_WX = "wx";
	
	public static void init(){
		Pingpp.apiKey = TEST_SK;
	}
	
	public static Charge pay(TradeRecord trade,String orderNo){
		if(trade == null)
			return null;
		Map<String, Object> chargeParams = new HashMap<String, Object>();
	    chargeParams.put("order_no",  orderNo);
	    //单位为分
	    BigDecimal amount = new BigDecimal(trade.getAmount());
	    amount = amount.multiply(new BigDecimal(100));
	    chargeParams.put("amount", amount.toBigInteger());
	    Map<String, String> app = new HashMap<String, String>();
	    app.put("id", APP_ID);
	    chargeParams.put("app",app);
	    String channel = trade.getChannel();
	    chargeParams.put("channel",GloabConstant.CHANNEL_ALIPAY.equals(channel) ? CHANNEL_ALIPAY : CHANNEL_WX);
	    chargeParams.put("currency","cny");
	    chargeParams.put("client_ip",trade.getClientIp());
	    chargeParams.put("subject","打赏");
	    chargeParams.put("body","Offering打赏");
	    try {
	    	Charge charge = Charge.create(chargeParams);
	    	LOG.info(charge);
	    	return charge;
		} catch (AuthenticationException | InvalidRequestException
				| APIConnectionException | APIException | ChannelException e) {
			LOG.error(e.toString());
		}
	    return null;
	}
	
	public static void main(String[] args) {
		TradeRecord trade = new TradeRecord();
		trade.setAmount("1.11");
		trade.setChannel(GloabConstant.CHANNEL_ALIPAY);
		trade.setClientIp(Utils.getLocalIp());
		Charge charge = pay(trade,PayUtils.getTradeNo());
		System.out.println(charge);
	}
}
