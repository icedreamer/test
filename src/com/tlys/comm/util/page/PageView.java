package com.tlys.comm.util.page;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * 分页数据包装，包括分页信息和List数据
 * 
 * @author fengym
 * @notes
 */
public class PageView<T> {
	private Log logger = LogFactory.getLog(this.getClass());
	/** 分页数据 * */
	private List<T> records;
	/** 页码开始索引和结束索引 * */
	private PageIndex pageIndex;
	/** 总页数 * */
	private int totalPage = 1;
	/** 每页显示记录数 * */
	private int pageSize = 10;
	/** 当前页 * */
	private int currentPage = 1;
	/** 总记录数 * */
	private int totalRecord;
	/** 每次显示多少页，必须保证大于3页，保证左右链接都可以使用 * */
	private int viewPageCount = 10;
	/**
	 * 是有有前一页
	 */
	private boolean previousPage = false;
	private boolean nextPage = false;

	private String pageUrl = "";

	private String pageUrlHtml;

	private Map<String, Object> searchKeyMap;

	public Map<String, Object> getSearchKeyMap() {
		return searchKeyMap;
	}

	public void setSearchKeyMap(Map<String, Object> searchKeyMap) {
		this.searchKeyMap = searchKeyMap;
	}

	public PageView() {
	}

	public PageView(Criteria criteria, String pageUrl, Integer totalRecord, Integer currentPage, Integer pageSize) {

		this.currentPage = currentPage == null ? 1 : currentPage;
		this.pageSize = pageSize == null ? 10 : pageSize;
		this.totalRecord = totalRecord == null ? 0 : totalRecord;
		int off = (currentPage - 1) * pageSize;

		criteria.setFirstResult(off);
		criteria.setMaxResults(pageSize);
		this.records = criteria.list();
		this.previousPage = this.currentPage > 1;
		this.nextPage = (this.totalRecord % this.pageSize > 0 ? (this.totalRecord / this.pageSize) + 1
				: (this.totalRecord / this.pageSize)) > this.currentPage;
		if (logger.isDebugEnabled()) {
			logger.debug("totalRecord : " + this.totalRecord);
			logger.debug("pageSize : " + this.pageSize);
			logger.debug("(this.totalRecord / this.pageSize) : " + (this.totalRecord / this.pageSize));
			logger.debug("this.totalRecord % this.pageSize : " + this.totalRecord % this.pageSize);
			logger.debug("currentPage : " + currentPage);
			logger.debug("nextPage : " + nextPage);
		}
		this.totalPage = this.totalRecord % this.pageSize > 0 ? this.totalRecord / this.pageSize + 1 : this.totalRecord
				/ this.pageSize;
		this.pageUrl = pageUrl;
		toHtml();
	}

	public PageView(int currentPage) {
		this.currentPage = (currentPage <= 0 ? 1 : currentPage);
	}

	public PageView(int maxResult, int currentPage) {
		this.pageSize = maxResult;
		this.currentPage = (currentPage <= 0 ? 1 : currentPage);
	}

	public PageView(List<T> list, String pageUrl, Integer totalRecord, Integer currentPage, Integer pageSize) {
		this.currentPage = currentPage == null ? 1 : currentPage;
		this.pageSize = pageSize == null ? 10 : pageSize;
		this.totalRecord = totalRecord == null ? 0 : totalRecord;
		this.totalRecord = totalRecord;
		this.totalPage = this.totalRecord % this.pageSize > 0 ? this.totalRecord / this.pageSize + 1 : this.totalRecord
				/ this.pageSize;
		this.previousPage = this.currentPage > 1;
		this.nextPage = (this.totalRecord / this.pageSize) + 1 > this.currentPage;
		toHtml();
	}

	public PageView(Query query, String pageUrl, Integer totalRecord, Integer currentPage, Integer pageSize) {
		this.currentPage = currentPage == null ? 1 : currentPage;
		this.pageSize = pageSize == null ? 10 : pageSize;
		this.totalRecord = totalRecord == null ? 0 : totalRecord;
		int off = (currentPage - 1) * pageSize;
		query.setFirstResult(off);
		query.setMaxResults(pageSize);
		this.records = query.list();
		this.previousPage = this.currentPage > 1;
		this.nextPage = (this.totalRecord / this.pageSize) + 1 > this.currentPage;
		this.totalPage = this.totalRecord % this.pageSize > 0 ? this.totalRecord / this.pageSize + 1 : this.totalRecord
				/ this.pageSize;
		toHtml();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	/** 要获取记录的开始索引 * */
	public int getFirstResult() {
		return (this.currentPage - 1) * this.pageSize;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * @return the pageUrlHtml
	 */
	public String getPageUrlHtml() {
		return pageUrlHtml;
	}

	public List<T> getRecords() {
		return records;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getViewPageCount() {
		return viewPageCount;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public boolean isPreviousPage() {
		return previousPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param pageUrl
	 *            the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * @param pageUrlHtml
	 *            the pageUrlHtml to set
	 */
	public void setPageUrlHtml(String pageUrlHtml) {
		this.pageUrlHtml = pageUrlHtml;
	}

	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}

	public void setQueryResult(QueryResult<T> qr) {
		setTotalRecord(qr.getTotalRecord());
		setRecords(qr.getResultList());
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(viewPageCount, currentPage, totalPage);
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.pageSize == 0 ? this.totalRecord / this.pageSize : this.totalRecord
				/ this.pageSize + 1);
	}

	public void setViewPageCount(int viewPageCount) {
		this.viewPageCount = viewPageCount;
	}

	// 生成分页html，直接在页面显示
	public void toHtml() {
		// 在Action中设置的原始URL
		String prefixUrl = pageUrl;
		// 所有设置参数
		String mapString = "";
		// 参数间隔符
		String fix = "";
		if (pageUrl != null && !pageUrl.equals("")) {
			if (pageUrl.indexOf("?") != -1) {
				fix = "&";
			} else {
				fix = "?";
			}
		}
		pageUrl += fix;
		// Map<String, Object> searchMap = pageView.getSearchKeyMap();
		// if (logger.isDebugEnabled()) {
		// logger.debug("searchKeyMap : " + searchMap);
		// }
		// if (searchMap != null && !searchMap.isEmpty()) {
		// // 参数用
		// StringBuilder searchString = new StringBuilder();
		// Set<String> keySet = searchMap.keySet();
		// for (String key : keySet) {
		// Object value = searchMap.get(key);
		// if (value != null && !value.equals("")) {
		// searchString.append("&");
		// searchString.append(key);
		// searchString.append("=");
		// try {
		// searchString.append(URLEncoder.encode(value.toString(), "UTF-8"));
		// } catch (Exception e) {
		// logger.error("URLEncoder " + value + " error.", e);
		// }
		//
		// }
		// }
		// mapString = searchString.toString();
		// if (mapString.startsWith("&")) {
		// mapString = mapString.substring(1);
		// }
		// }
		pageUrl += "pageView.pageSize=" + pageSize + "&pageView.currentPage=";

		StringBuffer buffer = new StringBuffer();
		buffer.append("");
		// 首页按钮
		buffer.append("[<span>");
		if (currentPage > 1) {

			buffer.append("<a href=\"" + prefixUrl + fix + (mapString.equals("") ? "" : "&" + mapString)
					+ "pageView.pageSize=" + pageSize + "&pageView.currentPage=1\">首页</a>");

		} else {
			buffer.append("首页");
		}
		buffer.append("/");
		// 上一页按钮
		if (previousPage) {
			buffer.append("<span><a href=\"" + pageUrl + (mapString.equals("") ? "" : "&" + mapString)
					+ (currentPage - 1) + "\">上一页</a></span>");
		} else {
			buffer.append("上一页");
		}

		buffer.append("</span>] ");
		if (totalPage > 0) {
			for (int i = 1; i <= totalPage; i++) {
				int diff = Math.abs(i - currentPage);
				if (diff <= 5 && diff >= 0) {
					if (currentPage == i) {
						buffer.append("<span>");
						buffer.append(i);
						buffer.append("</span>");
						buffer.append("&nbsp;");
					} else {
						buffer.append("<span>");
						buffer.append("<a href=\"" + pageUrl + i + "\">" + i + "</a>");
						buffer.append("</span>");
						buffer.append("&nbsp;");
					}
				}
			}
		}
		buffer.append("[<span>");
		// 下一页按钮
		if (nextPage) {
			buffer.append("<a href=\"" + pageUrl + (mapString.equals("") ? "" : "&" + mapString) + (currentPage + 1)
					+ "\">下一页</a>");
		} else {
			buffer.append("下一页");
		}
		buffer.append("/");
		// 末页按钮
		if (totalPage > currentPage) {
			buffer.append("");
			buffer.append("<a href=\"" + prefixUrl + fix + (mapString.equals("") ? "" : "&" + mapString)
					+ "pageView.pageSize=" + pageSize + "&pageView.currentPage=" + totalPage + "\">末页</a>");
			buffer.append("");
		} else {
			buffer.append("");
			buffer.append("末页");
			buffer.append("");
		}
		buffer.append("</span>]");

		buffer.append(" <span>（共").append(totalRecord).append("条记录)</span>");
		pageUrlHtml = buffer.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("pageUrlHtml : " + pageUrlHtml);
		}
	}
}
