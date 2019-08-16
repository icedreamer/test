package com.tlys.exe.service;

/**
 * 站区现车分布
 * 
 * @author 孔垂云
 * 
 */
public class ExeZqxcDistVO {

	private String corpname;// 公司
	private String corpid;
	private String stnname;// 站区
	private String stnid;
	private int zbc;// 自备车数
	private int bsc;// 部署车数

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getStnname() {
		return stnname;
	}

	public void setStnname(String stnname) {
		this.stnname = stnname;
	}

	public String getStnid() {
		return stnid;
	}

	public void setStnid(String stnid) {
		this.stnid = stnid;
	}

	public int getZbc() {
		return zbc;
	}

	public void setZbc(int zbc) {
		this.zbc = zbc;
	}

	public int getBsc() {
		return bsc;
	}

	public void setBsc(int bsc) {
		this.bsc = bsc;
	}
}
