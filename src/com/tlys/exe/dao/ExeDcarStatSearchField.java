package com.tlys.exe.dao;

/**
 * 车辆动态查询字段
 * 
 * @author 孔垂云
 * 
 */
public class ExeDcarStatSearchField {
	private String zbc_flag;// 查询类型
	private String con_name;// 收货人
	private String shipper_name;// 发货人
	private String cdy_org_stn;// 始发站
	private String dest_stn;// 终到站
	private String car_rent_flag;// 车属
	private String goods_type;// 产品类别
	private String car_no;// 车号
	private String car_user_id;// 车辆使用企业
	private String car_type;// 车型
	private String le_code;// 重空
	private String cdy_code;// 货物品名
	private String car_medium_id;// 充装介质

	private String corpid;// 登录用户企业id，用来过滤查看的车辆动态记录

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCon_name() {
		return con_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public String getCar_rent_flag() {
		return car_rent_flag;
	}

	public void setCar_rent_flag(String car_rent_flag) {
		this.car_rent_flag = car_rent_flag;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getLe_code() {
		return le_code;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}
}
