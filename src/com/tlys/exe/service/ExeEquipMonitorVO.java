package com.tlys.exe.service;

/**
 * 站区AEI设备监控
 * 
 * @author 孔垂云
 * 
 */
public class ExeEquipMonitorVO {

	private String areaid;// 区域
	private String areaname;//
	private String corpid;// 企业
	private String corpname;
	private String stnid;// 站区
	private String stnname;
	private int rptCount;// 报文数量
	private String gz;// 是否故障
	private int rowspan;// 区域合并行

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getStnid() {
		return stnid;
	}

	public void setStnid(String stnid) {
		this.stnid = stnid;
	}

	public String getStnname() {
		return stnname;
	}

	public void setStnname(String stnname) {
		this.stnname = stnname;
	}

	public int getRptCount() {
		return rptCount;
	}

	public void setRptCount(int rptCount) {
		this.rptCount = rptCount;
	}

	public String getGz() {
		return gz;
	}

	public void setGz(String gz) {
		this.gz = gz;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
}
