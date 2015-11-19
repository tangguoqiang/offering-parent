package com.offering.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.offering.utils.Utils;

public class PayUtils {

	/**
	 * 商户订单号
	 * @return
	 */
	public static String getTradeNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date()) + Utils.randNum(6);
	}
	
	/**
	 * 生成账户编号
	 * @return
	 */
	public static String getAccNo(String userId){
		StringBuilder accNo = new StringBuilder();
		accNo.append("acc_");
		for(int i = 8 - userId.length();i > 0;i--){
			accNo.append("0");
		}
		accNo.append(userId);
		return accNo.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new BigDecimal("1.1").add(new BigDecimal("2.33")));
	}
}
