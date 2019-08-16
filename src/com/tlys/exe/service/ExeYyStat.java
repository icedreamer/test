package com.tlys.exe.service;



/**
 * 自备车运用统计VO
 * 
 * @author 孔垂云
 * 
 */
public class ExeYyStat {

	private String area_id;// 区域ID
	private String area_name;// 区域名称
	private String corp_id;// 公司ID
	private String corp_name;// 公司名
	private int car_count;// 车数
	private int cycle_count;// 周转次数
	private float ever_cycle_count;// 平均周转次数，周转次数/车数S
	private int rowspan;// 合并行数

	private int noCycle_count;//未运行车数
	public int getNoCycle_count() {
		return noCycle_count;
	}

	public void setNoCycle_count(int noCycle_count) {
		this.noCycle_count = noCycle_count;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}

	public String getCorp_name() {
		return corp_name;
	}

	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}

	public int getCar_count() {
		return car_count;
	}

	public void setCar_count(int car_count) {
		this.car_count = car_count;
	}

	public int getCycle_count() {
		return cycle_count;
	}

	public void setCycle_count(int cycle_count) {
		this.cycle_count = cycle_count;
	}

	public float getEver_cycle_count() {
		return ever_cycle_count;
	}

	public void setEver_cycle_count(float ever_cycle_count) {
		this.ever_cycle_count = ever_cycle_count;
	}
}
