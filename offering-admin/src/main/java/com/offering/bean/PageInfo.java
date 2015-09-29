package com.offering.bean;

/**
 * 分页信息
 * @author gtang
 *
 */
public class PageInfo {

	private int pageSize;
	
	private int currentPage;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		if(currentPage <= 0)
			return 1;
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
