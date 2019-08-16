package com.tlys.comm.util.page;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * 分页数据包装，包括分页信息和List数据
 * 目前提供两个buildList方法，分别接受criteria对象和query对象
 * 以适合不同的查询方式
 * 
 * 2012-02-06更新：支持标题点击排序,具体见方法：sortWrap
 * 
 * @author fengym
 * @notes
 */
public class PageCtr<T> {
	private Log logger = LogFactory.getLog(this.getClass());
	
	/** 分页数据 * */
	private List<T> records;
	

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
	 * 是否有前一页
	 */
	private boolean previousPage = false;
	private boolean nextPage = false;
	
	/**
	 * 当前排序字段
	 */
	private String currentSort;
	
	/**
	 * 解析后的当前排序字段数组
	 * [abc,desc]
	 */
	private String[] curSortArr;

	/**
	 * 当前页面的基础链接，由action中传入
	 */
	private String pageUrl;

	private String pageUrlHtml;
	
	
	/**
	 * 保存用于查询的对象的Key
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
	 * 接受Query类型的传入变量
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
	 * 返回基础链接（注意，这里已经带好参数连接符?或者&)
	 * @return
	 */
	private String getBaseUrl() {
		// 主链接
		String baseUrl = "";

		// 参数间隔符
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
	
	// 生成分页html，直接在页面显示
	public void toHtml() {
		
		String prefixUrl = getBaseUrl();
		prefixUrl += "pageCtr.currentPage=";

		StringBuffer buffer = new StringBuffer();
		buffer.append("<div class='pageurl'>");

		buffer.append("[");
		// 首页按钮
		if (currentPage > 1) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + "1\">首页</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("首页");
			buffer.append("</span>");
		}

		buffer.append("/");
		// 上一页按钮
		if (previousPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + (currentPage - 1)
					+ "\">上一页</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("上一页");
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
		// 下一页按钮
		if (nextPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + (currentPage + 1)
					+ "\">下一页</a>");
			buffer.append("<span>");
		} else {
			buffer.append("<span>");
			buffer.append("下一页");
			buffer.append("<span>");
		}

		buffer.append("/");
		// 末页按钮
		if (totalPage > currentPage) {
			buffer.append("<span>");
			buffer.append("<a href=\"" + prefixUrl + totalPage + "\">末页</a>");
			buffer.append("</span>");
		} else {
			buffer.append("<span>");
			buffer.append("末页");
			buffer.append("</span>");
		}
		buffer.append("]");

		buffer.append("（共" + getTotalRecord() + "条记录)");
		buffer.append("</div>");
		pageUrlHtml = buffer.toString();
	}
	
	/**
	 * 为表头字段加上排序链接
	 * 用法：在页面表头位置，使用标签调用此方法
	 * 例：<s:property	value="pageCtr.sortWrap('姓名','name')"	escapeHtml="false" />
	 * @param hdtxt:表头字段显示名称
	 * @param hdname：表头字段属性名
	 * @return
	 */
	public String sortWrap(String hdtxt,String hdname){
		String reLinkStr = "";
		
		String prefixUrl = getBaseUrl();
		prefixUrl += "pageCtr.currentPage="+currentPage;
		
		String sortStr = hdname;
		
		String curhd=null;//排序字段名
		String curdir=null;//排序方向（正序，倒序)
		if (null != currentSort) {//解析当前的排序字段和排序方向
			String[] csArr = parseSortStr(currentSort);
			if (null != csArr) {
				curhd = csArr[0];
				curdir = csArr[1];
			}
		}
		
		String pic = null;//排序字段后面显示的图片
		if(null!=curhd && curhd.equals(hdname)){
			if(null==curdir){//正序时不写，所以只要curdir有值，就是desc
				sortStr += " desc";
				pic = "arrow_up.png";
			}else{
				pic = "arrow_down.png";
			}
		}else{
			sortStr += " desc";//当从新启用一个排序字段时，默认是降序
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
	 * 将排序字符串解析成数组：abc desc
	 * 如果没有desc，则数组长度仍为2，但第二个元素值为null
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
