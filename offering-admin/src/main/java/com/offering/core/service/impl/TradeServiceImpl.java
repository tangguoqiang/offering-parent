package com.offering.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.TradeHistory;
import com.offering.core.dao.TradeAccountDao;
import com.offering.core.dao.TradeHistoryDao;
import com.offering.core.dao.TradeRecordDao;
import com.offering.core.service.TradeService;

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
	 * 打赏历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<TradeHistory> rewardHistory(String userId,String type,PageInfo pageInfo){
		return tradeHistoryDao.rewardHistory(userId,type,pageInfo);
	}
	
	/**
	 * 获取总金额
	 * @param userId
	 * @param type
	 * @return
	 */
	public BigDecimal totalAmount(String userId,String type){
		String totalAmount = tradeHistoryDao.totalAmount(userId,type);
		return new BigDecimal(totalAmount).setScale(2);
	}
}
