package com.tlys.exe.dao;

/**
 * ����Ԥ����ѯ����
 * 
 * @author �״���
 * 
 */
public class ExeZyAlertSearchField {
	// 3)ʼ��վ 4)�յ�վ 5)����ʹ����ҵ 6)����Ʒ�� 7)��װ����

	private String oper_flag;// �������ͣ�δ����(Ĭ��)���ѽ����������)
	private String car_no;// ����
	private String cdy_org_stn;// ʼ��վ
	private String dest_stn;// �յ�վ
	private String car_user_id;// ����ʹ����ҵ
	private String cdy_code;// ����Ʒ��
	private String car_medium_id;// ��װ����

	private String corpid;// ��¼�û���ҵid���������˲鿴�ĳ�����̬��¼

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
