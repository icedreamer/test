package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 铁路货物品名字典表
 * 
 * @author 孔垂云
 * 
 */
@Entity
@Table(name = "TB_ZBC_DIC_GOODS")
public class DicGoods implements Serializable {

	@Id
	@Column(name = "DM", unique = true, nullable = false, length = 7)
	private String dm;// 品名代码

	@Column(name = "PMHZ", length = 20)
	private String pmhz;// 品名汉字

	@Column(name = "PYM", length = 5)
	private String pym;// 品名拼音码

	@Column(name = "DZ", length = 4)
	private String dz;//

	@Column(name = "JZZ")
	private int jzz;//

	@Column(name = "ZCJH", length = 2)
	private String zcjh;//

	@Column(name = "LDJH", length = 2)
	private String ldjh;//

	@Column(name = "ZCXS", length = 3)
	private String zcxs;//

	@Column(name = "LDXS", length = 3)
	private String ldxs;//

	@Column(name = "PLDM", length = 3)
	private String pldm;//

	@Column(name = "WPDM", length = 3)
	private String wpdm;//

	public String getPmhz() {
		return pmhz;
	}

	public void setPmhz(String pmhz) {
		this.pmhz = pmhz;
	}

	public String getPldm() {
		return pldm;
	}

	public void setPldm(String pldm) {
		this.pldm = pldm;
	}

	public String getWpdm() {
		return wpdm;
	}

	public void setWpdm(String wpdm) {
		this.wpdm = wpdm;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getPym() {
		return pym;
	}

	public void setPym(String pym) {
		this.pym = pym;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public int getJzz() {
		return jzz;
	}

	public void setJzz(int jzz) {
		this.jzz = jzz;
	}

	public String getZcjh() {
		return zcjh;
	}

	public void setZcjh(String zcjh) {
		this.zcjh = zcjh;
	}

	public String getLdjh() {
		return ldjh;
	}

	public void setLdjh(String ldjh) {
		this.ldjh = ldjh;
	}

	public String getZcxs() {
		return zcxs;
	}

	public void setZcxs(String zcxs) {
		this.zcxs = zcxs;
	}

	public String getLdxs() {
		return ldxs;
	}

	public void setLdxs(String ldxs) {
		this.ldxs = ldxs;
	}

	@Override
	public String toString() {
		return this.pmhz;
	}
}
