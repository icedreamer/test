package com.tlys.exe.dao;

/**
 * ������̬��ѯ�ֶ�
 * 
 * @author �״���
 * 
 */
public class ExeTransportSearchField {

	private String zbc_flag;;// �Ƿ��Ա���,0��������1�Ա���
	private String shipper_name;// ������
	private String shipper_entpr_id;// /��������ҵ
	private String con_name;// �ջ���
	private String con_entpr_id;// �ջ�����ҵ
	private String cdy_org_stn;// ʼ��վ
	private String dest_stn;// �յ�վ
	private String goods_type;// ��Ʒ���
	private String cdy_code;// ����Ʒ��
	private String car_no;// ���ţ��೵����,����
	private String wb_date_s;// ��Ʊ������ʼ
	private String wb_date_e;// ��Ʊ������ֹ
	private String msg_type;// ��Ϣ��Դ
	
	private int pageSize;// ��ҳÿҳ����

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public String getShipper_entpr_id() {
		return shipper_entpr_id;
	}

	public void setShipper_entpr_id(String shipper_entpr_id) {
		this.shipper_entpr_id = shipper_entpr_id;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getWb_date_s() {
		return wb_date_s;
	}

	public void setWb_date_s(String wb_date_s) {
		this.wb_date_s = wb_date_s;
	}

	public String getWb_date_e() {
		return wb_date_e;
	}

	public void setWb_date_e(String wb_date_e) {
		this.wb_date_e = wb_date_e;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getCon_name() {
		return con_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getCon_entpr_id() {
		return con_entpr_id;
	}

	public void setCon_entpr_id(String con_entpr_id) {
		this.con_entpr_id = con_entpr_id;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
