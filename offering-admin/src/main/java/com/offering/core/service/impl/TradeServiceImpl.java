package com.offering.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.trade.Account;
import com.offering.bean.trade.TradeHistory;
import com.offering.bean.trade.TradeRecord;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.TradeAccountDao;
import com.offering.core.dao.TradeHistoryDao;
import com.offering.core.dao.TradeRecordDao;
import com.offering.core.service.TradeService;
import com.offering.pay.PayUtils;
import com.offering.pay.PingPay;
import com.pingplusplus.model.Charge;

/**
 * 交易service实现
 * @author surfacepro3
 *
 */
@Service
public class TradeServiceImpl implements TradeService{

	private final static Logger LOG = Logger.getLogger(TradeServiceImpl.class);
	
	@Autowired
	private TradeAccountDao accountDao;
	
	@Autowired
	private TradeRecordDao tradeDao;
	
	@Autowired
	private TradeHistoryDao tradeHistoryDao;
	
	/**
	 * 支付
	 * @param trade
	 * @return
	 */
	public Charge pay(TradeRecord trade){
		trade.setTradeTime(System.currentTimeMillis() + "");
		trade.setStatus(GloabConstant.STATUS_INVALID);
		String tradeNo = PayUtils.getTradeNo();
		trade.setTradeNo(tradeNo);
		tradeDao.insertRecord(trade, "TRADE_RECORD");
		return PingPay.pay(trade,tradeNo);
	}
	
	/**
	 * 交易成功处理
	 * @param jsonObj
	 */
	@Transactional
	public void trade_success(JSONObject jsonObj){
		
		if(jsonObj.has("data")){
			JSONObject tmpObj = jsonObj.getJSONObject("data");
			if(tmpObj.has("object")){
				String tradeNo = tmpObj.getJSONObject("object").getString("order_no");
				LOG.info("订单号:" +tradeNo);
				TradeRecord trade = tradeDao.getRecByTradeNo(tradeNo);
				//新增交易历史
				insertTradeHistory(trade);
				//更新交易记录
				tradeDao.updateStatus(tradeNo);
				//更新付款人账户
//				updateAccount();
				//更新收款人账户
				updateAccount(trade.getPayee(),trade.getAmount());
			}
		}
	}
	
	/**
	 * 新增交易历史纪录
	 * @param trade
	 */
	private void insertTradeHistory(TradeRecord trade){
		TradeHistory th = new TradeHistory();
		th.setTradeNo(trade.getTradeNo());
		th.setType(trade.getType());
		th.setPayer(trade.getPayer());
		th.setPayee(trade.getPayee());
		th.setTradeTime(trade.getTradeTime());
		th.setAmount(trade.getAmount());
		tradeHistoryDao.insertRecord(th, "TRADE_HISTORY");
	}
	
	/**
	 * 更新用户账户
	 * @param userId
	 * @param amount
	 */
	private void updateAccount(String userId,String amount){
		Account acc = accountDao.getRecByUserId(userId);
		LOG.info("用户" + userId + "的交易金额为" + amount);
		if(acc == null){
			acc = new Account();
			acc.setUserId(userId);
			acc.setBalance(amount);
			acc.setCode(PayUtils.getAccNo(userId));
			acc.setStatus(GloabConstant.STATUS_EFFECT);
			acc.setCreateTime(String.valueOf(System.currentTimeMillis()));
			LOG.info("用户" + userId + "的余额为" + amount);
			accountDao.insertRecord(acc, "TRADE_ACCOUNT");
		}else{
			BigDecimal balance = new BigDecimal(acc.getBalance());
			balance = balance.add(new BigDecimal(amount));
			LOG.info("用户" + userId + "的余额为" + balance);
			accountDao.updateBalance(acc.getId(),balance);
		}
	}
	
	/**
	 * 打赏历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<TradeHistory> rewardHistory(String userId,String type){
		return tradeHistoryDao.rewardHistory(userId,type);
	}
}
