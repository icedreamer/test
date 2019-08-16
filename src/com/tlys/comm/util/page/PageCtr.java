package com.tlys.comm.util.page;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * ��ҳ���ݰ�װ��������ҳ��Ϣ��List����
 * Ŀǰ�ṩ����buildList�������ֱ����criteria�����query����
 * ���ʺϲ�ͬ�Ĳ�ѯ��ʽ
 * 
 * 2012-02-06���£�֧�ֱ���������,�����������sortWrap
 * 
 * @author fengym
 * @notes
 */
public class PageCtr<T> {
	private Log logger = LogFactory.getLog(this.getClass());
	
	/** ��ҳ���� * */
	private List<T> records;
	

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
	 * �Ƿ���ǰһҳ
	 */
	private boolean previousPage = false;
	private boolean nextPage = false;
	
	/**
	 * ��ǰ�����ֶ�
	 */
	private String currentSort;
	
	/**
	 * ������ĵ�ǰ�����ֶ�����
	 * [abc,desc]
	 */
	private String[] curSortArr;

	/**
	 * ��ǰҳ��Ļ������ӣ���action�д���
	 */
	private String pageUrl;

	private String pageUrlHtml;
	
	
	/**
	 * �������ڲ�ѯ�Ķ����Key
	 */
	private String schObjKey;

	
	public PageCtr() {
	}
	
	public void buildList(Criteria crit) {
		int off = (currentPage - 1) * pageSize;
		crit.setFirstResult(off);
		crit.setMaxResults(pageSize);
		this.records = crit.list();
		this.previousPage = this.currentPage > 1;
		this.nextPage = (this.totalRecord % this.pageSize > 0 ? (this.totalRecord / this.pageSize) + 1
				: (this.totalRecord / this.pageSize)) > this.currentPage;
		this.totalPage = this.totalRecord % this.pageSize > 0 ? this.totalRecord / this.pageSize + 1 : this.totalRecord
						/ this.pageSize;

		if (logger.isDebugEnabled()) {
			logger.debug("totalPage : " + totalPage);
		}
		toHtml();
	}
	
	/**
	 * ����Query���͵Ĵ������
	 * @param query
	 */
	public void buildList(Query query) {
		int off = (currentPage - 1) * pageSize;
		query.setFirstResult(off);
		query.setMaxResults(pageSize);
		this.records = query.list();
		this.previousPage = this.currentPage > 1;
		this.nextPage = (this.totalRecord % this.pageSize > 0 ? (this.totalRecord / this.pageSize) + 1
				: (this.totalRecord / this.pageSize)) > this.currentPage;
		this.totalPage = this.totalRecord % this.pageSize > 0 ? this.totalRecord / this.pageSize + 1 : this.totalRecord
						/ this.pageSize;

		if (logger.isDebugEnabled()) {
			logger.debug("totalPage : " + totalPage);
		}
		toHtml();
	}
	
	
	/**
	 * ���ػ������ӣ�ע�⣬�����Ѿ����ò������ӷ�?����&)
	 * @return
	 */
	private String getBaseUrl() {
		// ������
		String baseUrl = "";

		// ���������
		String fix = "";
		if (pageUrl != null && !pageUrl.equals("")) {
			if (pageUrl.indexOf("?") != -1) {
				fix = "&";
			} else {
				fix = "?";
			}
		}
		if (null != getSchObjKey()) {
			baseUrl = pageUrl + fix + "pageCtr.schObjKey=" + getSchObjKey()
					+ "&";
		} else {
			baseUrl = pageUrl + fix;
		}
		return baseUrl;
	}
	
	// ���ɷ�ҳhtml��ֱ����ҳ����ʾ
	public void toHtml() {
		
		String prefixUrl = getBaseUrl();
		prefixUrl += "pageCtr.currentPage=";

		StringBuffer buffer = new StringBuffer();
		buffer.append("<div class='pageurl'>");

		buffer.append("[");
		// ��ҳ��ť
		if (currentPage > 1) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + "1\">��ҳ</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("��ҳ");
			buffer.append("</span>");
		}

		buffer.append("/");
		// ��һҳ��ť
		if (previousPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + (currentPage - 1)
					+ "\">��һҳ</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("��һҳ");
			buffer.append("</span>");
		}
		buffer.append("]");
		buffer.append("&nbsp;");

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
						buffer.append("<a href=\"" + prefixUrl + i + "\">" + i
								+ "</a>");
						buffer.append("</span>");
						buffer.append("&nbsp;");
					}
				}
			}
		}

		buffer.append("[");
		// ��һҳ��ť
		if (nextPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + (currentPage + 1)
					+ "\">��һҳ</a>");
			buffer.append("<span>");
		} else {
			buffer.append("<span>");
			buffer.append("��һҳ");
			buffer.append("<span>");
		}

		buffer.append("/");
		// ĩҳ��ť
		if (totalPage > currentPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + totalPage + "\">ĩҳ</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("ĩҳ");
			buffer.append("</span>");
		}
		buffer.append("]");

		buffer.append("����" + getTotalRecord() + "����¼)");
		buffer.append("</div>");
		pageUrlHtml = buffer.toString();
	}
	
	/**
	 * Ϊ��ͷ�ֶμ�����������
	 * �÷�����ҳ���ͷλ�ã�ʹ�ñ�ǩ���ô˷���
	 * ����<s:property	value="pageCtr.sortWrap('����','name')"	escapeHtml="false" />
	 * @param hdtxt:��ͷ�ֶ���ʾ����
	 * @param hdname����ͷ�ֶ�������
	 * @return
	 */
	public String sortWrap(String hdtxt,String hdname){
		String reLinkStr = "";
		
		String prefixUrl = getBaseUrl();
		prefixUrl += "pageCtr.currentPage="+currentPage;
		
		String sortStr = hdname;
		
		String curhd=null;//�����ֶ���
		String curdir=null;//���������򣬵���)
		if (null != currentSort) {//������ǰ�������ֶκ�������
			String[] csArr = parseSortStr(currentSort);
			if (null != csArr) {
				curhd = csArr[0];
				curdir = csArr[1];
			}
		}
		
		String pic = null;//�����ֶκ�����ʾ��ͼƬ
		if(null!=curhd && curhd.equals(hdname)){
			if(null==curdir){//����ʱ��д������ֻҪcurdir��ֵ������desc
				sortStr += " desc";
				pic = "arrow_up.png";
			}else{
				pic = "arrow_down.png";
			}
		}else{
			sortStr += " desc";//����������һ�������ֶ�ʱ��Ĭ���ǽ���
		}
		
		prefixUrl += "&pageCtr.currentSort="+sortStr;
		
		String picSrc = "";
		if(null!=pic){
			picSrc = "<img src='/_inc/images/"+pic+"'>";
		}
		
		reLinkStr = "<a href=\"" + prefixUrl + "\">"+hdtxt+"</a>"+picSrc;
		
		
		return reLinkStr;
	}
	
	/**
	 * �������ַ������������飺abc desc
	 * ���û��desc�������鳤����Ϊ2�����ڶ���Ԫ��ֵΪnull
	 * @return
	 */
	public static String[] parseSortStr(String sort){
		if(null==sort) {
			return null;
		}else{
			sort = sort.trim();
			if("".equals(sort)){
				return null;
			}
		}
		
		String[] reArr = new String[2];
		String[] csArr = sort.split(" ",2);
		if (null != csArr) {
			for (int i = 0; i < csArr.length; i++) {
				reArr[i] = csArr[i].trim();
			}
		}
		return reArr;
	}

	
	//=================================================
	

	public int getCurrentPage() {
		return currentPage;
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


	public void setRecords(List<T> records) {
		this.records = records;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.pageSize == 0 ? this.totalRecord / this.pageSize : this.totalRecord
				/ this.pageSize + 1);
	}

	public void setViewPageCount(int viewPageCount) {
		this.viewPageCount = viewPageCount;
	}

	

	@Override
	public String toString() {
		return "PageCtr [totalPage="
				+ totalPage + ", pageSize=" + pageSize + ", currentPage="
				+ currentPage + ", totalRecord=" + totalRecord
				+ ", viewPageCount=" + viewPageCount + ", previousPage="
				+ previousPage + ", nextPage=" + nextPage + ", pageUrl="
				+ pageUrl + ", pageUrlHtml=" + pageUrlHtml + "]";
	}

	public String getSchObjKey() {
		return schObjKey;
	}

	public void setSchObjKey(String schObjKey) {
		this.schObjKey = schObjKey;
	}

	public String getCurrentSort() {
		return currentSort;
	}

	public void setCurrentSort(String currentSort) {
		this.currentSort = currentSort;
	}

	public String[] getCurSortArr() {
		curSortArr = parseSortStr(currentSort);
		return curSortArr;
	}

	public void setCurSortArr(String[] curSortArr) {
		this.curSortArr = curSortArr;
	}

	

}
