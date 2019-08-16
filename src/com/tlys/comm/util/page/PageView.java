package com.tlys.comm.util.page;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * ��ҳ���ݰ�װ��������ҳ��Ϣ��List����
 * 
 * @author fengym
 * @notes
 */
public class PageView<T> {
	private Log logger = LogFactory.getLog(this.getClass());
	/** ��ҳ���� * */
	private List<T> records;
	/** ҳ�뿪ʼ�����ͽ������� * */
	private PageIndex pageIndex;
	/** ��ҳ�� * */
	private int totalPage = 1;
	/** ÿҳ��ʾ��¼�� * */
	private int pageSize = 10;
	/** ��ǰҳ * */
	private int currentPage = 1;
	/** �ܼ�¼�� * */
	private int totalRecord;
	/** ÿ����ʾ����ҳ�����뱣֤����3ҳ����֤�������Ӷ�����ʹ�� * */
	private int viewPageCount = 10;
	/**
	 * ������ǰһҳ
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

	/** Ҫ��ȡ��¼�Ŀ�ʼ���� * */
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

	// ���ɷ�ҳhtml��ֱ����ҳ����ʾ
	public void toHtml() {
		// ��Action�����õ�ԭʼURL
		String prefixUrl = pageUrl;
		// �������ò���
		String mapString = "";
		// ���������
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
		// // ������
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
		// ��ҳ��ť
		buffer.append("[<span>");
		if (currentPage > 1) {

			buffer.append("<a href=\"" + prefixUrl + fix + (mapString.equals("") ? "" : "&" + mapString)
					+ "pageView.pageSize=" + pageSize + "&pageView.currentPage=1\">��ҳ</a>");

		} else {
			buffer.append("��ҳ");
		}
		buffer.append("/");
		// ��һҳ��ť
		if (previousPage) {
			buffer.append("<span><a href=\"" + pageUrl + (mapString.equals("") ? "" : "&" + mapString)
					+ (currentPage - 1) + "\">��һҳ</a></span>");
		} else {
			buffer.append("��һҳ");
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
		// ��һҳ��ť
		if (nextPage) {
			buffer.append("<a href=\"" + pageUrl + (mapString.equals("") ? "" : "&" + mapString) + (currentPage + 1)
					+ "\">��һҳ</a>");
		} else {
			buffer.append("��һҳ");
		}
		buffer.append("/");
		// ĩҳ��ť
		if (totalPage > currentPage) {
			buffer.append("");
			buffer.append("<a href=\"" + prefixUrl + fix + (mapString.equals("") ? "" : "&" + mapString)
					+ "pageView.pageSize=" + pageSize + "&pageView.currentPage=" + totalPage + "\">ĩҳ</a>");
			buffer.append("");
		} else {
			buffer.append("");
			buffer.append("ĩҳ");
			buffer.append("");
		}
		buffer.append("</span>]");

		buffer.append(" <span>����").append(totalRecord).append("����¼)</span>");
		pageUrlHtml = buffer.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("pageUrlHtml : " + pageUrlHtml);
		}
	}
}
