package com.tlys.exe.service;


/**
 * վ����������ͳ����Ϣ
 * 
 * @author �״���
 * 
 */
public class ExeZqjcclVO {
	// ������ ��ƥ�䳵���� δƥ�䳵���� ƥ���� ������ ��ƥ������ δƥ�� ƥ����

	private String corpname;//
	private String corpid;// ��ҵid
	private String stnid;// վ��id
	private String stnname;//
	private int in_zls;// ʻ��������
	private int in_yppcls;// ʻ����ƥ�䳵����
	private int in_wppcls;// ʻ��δƥ�䳵����
	private float in_ppl;// ʻ��ƥ����
	private int out_zls;// ʻ��������
	private int out_yppcls;// ʻ����ƥ�䳵����
	private int out_wppcls;// ʻ��δƥ�䳵����
	private float out_ppl;// ʻ��ƥ����

	private int rowspan;// ��ҵ�ϲ���

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

	public String getStnid() {
		return stnid;
	}

	public void setStnid(String stnid) {
		this.stnid = stnid;
	}

	public String getStnname() {
		return stnname;
	}

	public void setStnname(String stnname) {
		this.stnname = stnname;
	}

	public int getIn_zls() {
		return in_zls;
	}

	public void setIn_zls(int in_zls) {
		this.in_zls = in_zls;
	}

	public int getIn_yppcls() {
		return in_yppcls;
	}

	public void setIn_yppcls(int in_yppcls) {
		this.in_yppcls = in_yppcls;
	}

	public int getIn_wppcls() {
		return in_wppcls;
	}

	public void setIn_wppcls(int in_wppcls) {
		this.in_wppcls = in_wppcls;
	}

	public float getIn_ppl() {
		return in_ppl;
	}

	public void setIn_ppl(float in_ppl) {
		this.in_ppl = in_ppl;
	}

	public int getOut_zls() {
		return out_zls;
	}

	public void setOut_zls(int out_zls) {
		this.out_zls = out_zls;
	}

	public int getOut_yppcls() {
		return out_yppcls;
	}

	public void setOut_yppcls(int out_yppcls) {
		this.out_yppcls = out_yppcls;
	}

	public int getOut_wppcls() {
		return out_wppcls;
	}

	public void setOut_wppcls(int out_wppcls) {
		this.out_wppcls = out_wppcls;
	}

	public float getOut_ppl() {
		return out_ppl;
	}

	public void setOut_ppl(float out_ppl) {
		this.out_ppl = out_ppl;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
}
