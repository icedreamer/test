package com.tlys.exe.service;

/**
 * 自备车利用率VO
 * 
 * @author 孔垂云
 * 
 */
public class ExeZbclylVO {

	private String areaid;// 区域
	private String areaname;
	private String corpname;//
	private String corpid;// 公司
	private int zcs;// 总车数
	private long zyxsj;// 总运行时间
	private long zzqzsj;// 在站区总时间
	private long zzqsj;// 在站区时间
	private float lyl;// 利用率
	private int rowspan;// 区域合并行

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

	public int getZcs() {
		return zcs;
	}

	public void setZcs(int zcs) {
		this.zcs = zcs;
	}

	public long getZyxsj() {
		return zyxsj;
	}

	public void setZyxsj(long zyxsj) {
		this.zyxsj = zyxsj;
	}

	public long getZzqzsj() {
		return zzqzsj;
	}

	public void setZzqzsj(long zzqzsj) {
		this.zzqzsj = zzqzsj;
	}

	public long getZzqsj() {
		return zzqsj;
	}

	public void setZzqsj(long zzqsj) {
		this.zzqsj = zzqsj;
	}

	public float getLyl() {
		return lyl;
	}

	public void setLyl(float lyl) {
		this.lyl = lyl;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

}
