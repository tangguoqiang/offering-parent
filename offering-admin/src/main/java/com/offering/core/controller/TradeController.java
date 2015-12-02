package com.offering.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.Account;
import com.offering.bean.trade.TradeHistory;
import com.offering.core.service.TradeService;

/**
 * 交易管理
 * @author surfacepro3
 *
 */
@Controller
@RequestMapping(value ="/trade")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	/**
	 * 查询账户信息
	 * @param cr
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listAccounts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listAccounts(Account account,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", tradeService.listAccounts(account,page));
		m.put("totalCount", tradeService.getAccountCount(account));
		return m;
	}

	/**
	 * 交易历史纪录
	 * @param th
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listTradeHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listTradeHistory(TradeHistory th,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", tradeService.listTradeHistory(th,page));
		m.put("totalCount", tradeService.getThCount(th));
		return m;
	}
}
