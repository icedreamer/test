package com.tlys.exe.service;


/**
 * 站区进出车辆统计信息
 * 
 * @author 孔垂云
 * 
 */
public class ExeZqjcclVO {
	// 总辆数 已匹配车辆数 未匹配车辆数 匹配率 总辆数 已匹配辆数 未匹配 匹配率

	private String corpname;//
	private String corpid;// 企业id
	private String stnid;// 站区id
	private String stnname;//
	private int in_zls;// 驶入总辆数
	private int in_yppcls;// 驶入已匹配车辆数
	private int in_wppcls;// 驶入未匹配车辆数
	private float in_ppl;// 驶入匹配率
	private int out_zls;// 驶出总辆数
	private int out_yppcls;// 驶出已匹配车辆数
	private int out_wppcls;// 驶出未匹配车辆数
	private float out_ppl;// 驶出匹配率

	private int rowspan;// 企业合并行

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
