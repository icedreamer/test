package com.tlys.exe.dao;

/**
 * 各企业非统销化工产品出厂铁路运输量统计报表VO字段
 * 
 * @author 孔垂云
 * 
 */
public class ExeTranStasVO {

	private String areaid;// 区域名称
	private String areaname;//
	private String corpid;// 公司名称
	private String corpname;//
	private String cdy_code;// 货物品名代码
	private String cdy_name;// 品名
	private String dest_stn;// 到站码
	private String dest_name;// 到站名
	private int cur_carnum;// 当月车数
	private int cur_wgt;// 当月运量
	private int total_carnum;// 总运量
	private int total_wgt;// 总运量

	private int corprowspan = 1;// 公司合并行数
	private int cdyrowspan = 1;// 品名合计行数

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public int getCur_carnum() {
		return cur_carnum;
	}

	public void setCur_carnum(int cur_carnum) {
		this.cur_carnum = cur_carnum;
	}

	public int getCur_wgt() {
		return cur_wgt;
	}

	public void setCur_wgt(int cur_wgt) {
		this.cur_wgt = cur_wgt;
	}

	public int getTotal_carnum() {
		return total_carnum;
	}

	public void setTotal_carnum(int total_carnum) {
		this.total_carnum = total_carnum;
	}

	public int getTotal_wgt() {
		return total_wgt;
	}

	public void setTotal_wgt(int total_wgt) {
		this.total_wgt = total_wgt;
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

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCdy_name() {
		return cdy_name;
	}

	public void setCdy_name(String cdy_name) {
		this.cdy_name = cdy_name;
	}

	public int getCorprowspan() {
		return corprowspan;
	}

	public void setCorprowspan(int corprowspan) {
		this.corprowspan = corprowspan;
	}

	public int getCdyrowspan() {
		return cdyrowspan;
	}

	public void setCdyrowspan(int cdyrowspan) {
		this.cdyrowspan = cdyrowspan;
	}

	public String getDest_name() {
		return dest_name;
	}

	public void setDest_name(String dest_name) {
		this.dest_name = dest_name;
	}

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

}
