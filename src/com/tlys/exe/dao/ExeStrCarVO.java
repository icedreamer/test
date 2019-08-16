package com.tlys.exe.dao;

import java.util.Date;

/**
 * 要显示的车辆信息VO
 * 
 * @author 孔垂云
 * 
 */
public class ExeStrCarVO {

	// 所属企业（部属车显示空) 车号 车种车型 充装介质（部属车显示空) 进出标识 车辆标识 顺位 到达日期 到达时间 货票号 货物品名 产品类别
	// 计费重量 实际载重 发站名 到站名 制票日期 发货人 收货人

	private String corpname;// 所属企业
	private String car_no;//
	private String car_kind;// 车种
	private String car_type;// 车型
	private String car_medium;// 充装介质
	private String in_out_flag;// 进出标识
	private int position;// 车辆顺位
	private Date arr_time;// 到达日期
	private String wb_nbr;// 货票号
	private String cdy_name;// 货物品名
	private String goods_type;// 产品类别
	private double car_cap_wgt;// 计费重量
	private double car_actual_wgt;// 实际载重
	private String cdy_o_stn_name;// 发站名
	private String dest_stn_name;// 到站名
	private Date wb_date;// 制票日期
	private String shipper_name;// 发货人
	private String con_name;// 收货人
	public String getCorpname() {
		return corpname;
	}
	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getCar_kind() {
		return car_kind;
	}
	public void setCar_kind(String car_kind) {
		this.car_kind = car_kind;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getCar_medium() {
		return car_medium;
	}
	public void setCar_medium(String car_medium) {
		this.car_medium = car_medium;
	}
	public String getIn_out_flag() {
		return in_out_flag;
	}
	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Date getArr_time() {
		return arr_time;
	}
	public void setArr_time(Date arr_time) {
		this.arr_time = arr_time;
	}
	public String getWb_nbr() {
		return wb_nbr;
	}
	public void setWb_nbr(String wb_nbr) {
		this.wb_nbr = wb_nbr;
	}
	public String getCdy_name() {
		return cdy_name;
	}
	public void setCdy_name(String cdy_name) {
		this.cdy_name = cdy_name;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public double getCar_cap_wgt() {
		return car_cap_wgt;
	}
	public void setCar_cap_wgt(double car_cap_wgt) {
		this.car_cap_wgt = car_cap_wgt;
	}
	public double getCar_actual_wgt() {
		return car_actual_wgt;
	}
	public void setCar_actual_wgt(double car_actual_wgt) {
		this.car_actual_wgt = car_actual_wgt;
	}
	public String getCdy_o_stn_name() {
		return cdy_o_stn_name;
	}
	public void setCdy_o_stn_name(String cdy_o_stn_name) {
		this.cdy_o_stn_name = cdy_o_stn_name;
	}
	public String getDest_stn_name() {
		return dest_stn_name;
	}
	public void setDest_stn_name(String dest_stn_name) {
		this.dest_stn_name = dest_stn_name;
	}
	public Date getWb_date() {
		return wb_date;
	}
	public void setWb_date(Date wb_date) {
		this.wb_date = wb_date;
	}
	public String getShipper_name() {
		return shipper_name;
	}
	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}
	public String getCon_name() {
		return con_name;
	}
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

}
