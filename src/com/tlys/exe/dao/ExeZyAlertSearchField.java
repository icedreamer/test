package com.tlys.exe.dao;

/**
 * 运行预警查询条件
 * 
 * @author 孔垂云
 * 
 */
public class ExeZyAlertSearchField {
	// 3)始发站 4)终到站 5)车辆使用企业 6)货物品名 7)充装介质

	private String oper_flag;// 处理类型（未处理(默认)；已解决；不处理)
	private String car_no;// 车号
	private String cdy_org_stn;// 始发站
	private String dest_stn;// 终到站
	private String car_user_id;// 车辆使用企业
	private String cdy_code;// 货物品名
	private String car_medium_id;// 充装介质

	private String corpid;// 登录用户企业id，用来过滤查看的车辆动态记录

	private String s_date;//
	private String e_date;//
	private String pbl_reason;//

	public String getS_date() {
		return s_date;
	}

	public void setS_date(String s_date) {
		this.s_date = s_date;
	}

	public String getE_date() {
		return e_date;
	}

	public void setE_date(String e_date) {
		this.e_date = e_date;
	}

	public String getPbl_reason() {
		return pbl_reason;
	}

	public void setPbl_reason(String pbl_reason) {
		this.pbl_reason = pbl_reason;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(String oper_flag) {
		this.oper_flag = oper_flag;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
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

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
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
