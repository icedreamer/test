package com.tlys.exe.dao;

import java.util.Date;

/**
 * Ҫ��ʾ�ĳ�����ϢVO
 * 
 * @author �״���
 * 
 */
public class ExeStrCarVO {

	// ������ҵ����������ʾ��) ���� ���ֳ��� ��װ���ʣ���������ʾ��) ������ʶ ������ʶ ˳λ �������� ����ʱ�� ��Ʊ�� ����Ʒ�� ��Ʒ���
	// �Ʒ����� ʵ������ ��վ�� ��վ�� ��Ʊ���� ������ �ջ���

	private String corpname;// ������ҵ
	private String car_no;//
	private String car_kind;// ����
	private String car_type;// ����
	private String car_medium;// ��װ����
	private String in_out_flag;// ������ʶ
	private int position;// ����˳λ
	private Date arr_time;// ��������
	private String wb_nbr;// ��Ʊ��
	private String cdy_name;// ����Ʒ��
	private String goods_type;// ��Ʒ���
	private double car_cap_wgt;// �Ʒ�����
	private double car_actual_wgt;// ʵ������
	private String cdy_o_stn_name;// ��վ��
	private String dest_stn_name;// ��վ��
	private Date wb_date;// ��Ʊ����
	private String shipper_name;// ������
	private String con_name;// �ջ���
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
