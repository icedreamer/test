package com.tlys.exe.dao;

/**
 * ����ҵ��ͳ��������Ʒ������·������ͳ�Ʊ���VO�ֶ�
 * 
 * @author �״���
 * 
 */
public class ExeTranStasVO {

	private String areaid;// ��������
	private String areaname;//
	private String corpid;// ��˾����
	private String corpname;//
	private String cdy_code;// ����Ʒ������
	private String cdy_name;// Ʒ��
	private String dest_stn;// ��վ��
	private String dest_name;// ��վ��
	private int cur_carnum;// ���³���
	private int cur_wgt;// ��������
	private int total_carnum;// ������
	private int total_wgt;// ������

	private int corprowspan = 1;// ��˾�ϲ�����
	private int cdyrowspan = 1;// Ʒ���ϼ�����

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
