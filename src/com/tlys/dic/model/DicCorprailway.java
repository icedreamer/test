package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DicCorprailway entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CORPRAILWAY" )
public class DicCorprailway implements java.io.Serializable {

	// Fields

	private String crwid;
	private String corpid;
	private String bureauid;
	private String fullname;
	private String shortname;
	private String neareststation;
	private String ispublic;

	private String stankgoods;
	private String sntankgoods;
	private String scontainergoods;
	private String atankgoods;
	private String antankgoods;
	private String acontainergoods;
	private String ssharegoods;
	private String asharegoods;

	private String sinocode;
	private String isactive;
	private Date createdtime;
	private String description;
	// 翻译
	private String corpidDIC;
	private String neareststationDIC;
	private String ispublicDIC;
	private String isactiveDIC;
	private String bureauidDIC;
	//共用单位
	private String publiccorp;
	//共用 到发 全部链接区分标识
	private String flag;

	/** default constructor */
	public DicCorprailway() {
	}

	/** minimal constructor */
	public DicCorprailway(String crwid, String corpid, String bureauid,
			String fullname, String shortname, String neareststation,
			String ispublic, String isactive) {
		this.crwid = crwid;
		this.corpid = corpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.neareststation = neareststation;
		this.ispublic = ispublic;
		this.isactive = isactive;
		this.bureauid = bureauid;
	}

	/** full constructor */
	public DicCorprailway(String crwid, String corpid, String bureauid,
			String fullname, String shortname, String neareststation,
			String ispublic, String sinocode, String isactive,
			Date createdtime, String description, String stankgoods,
			String sntankgoods, String scontainergoods, String atankgoods,
			String antankgoods, String acontainergoods) {
		this.crwid = crwid;
		this.corpid = corpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.neareststation = neareststation;
		this.ispublic = ispublic;
		this.sinocode = sinocode;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
		this.bureauid = bureauid;
		this.stankgoods = stankgoods;
		this.sntankgoods = sntankgoods;
		this.scontainergoods = scontainergoods;
		this.atankgoods = atankgoods;
		this.antankgoods = antankgoods;
		this.acontainergoods = acontainergoods;
	}

	// Property accessors
	@Id
	@Column(name = "CRWID", unique = true, nullable = false, length = 3)
	public String getCrwid() {
		return this.crwid;
	}

	public void setCrwid(String crwid) {
		this.crwid = crwid;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "FULLNAME", nullable = false, length = 100)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 30)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "NEARESTSTATION", nullable = false, length = 6)
	public String getNeareststation() {
		return this.neareststation;
	}

	public void setNeareststation(String neareststation) {
		this.neareststation = neareststation;
	}

	@Column(name = "ISPUBLIC", nullable = false, length = 1)
	public String getIspublic() {
		return this.ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	@Column(name = "SINOCODE", length = 8)
	public String getSinocode() {
		return this.sinocode;
	}

	public void setSinocode(String sinocode) {
		this.sinocode = sinocode;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
	}

	@Transient
	public String getNeareststationDIC() {
		return neareststationDIC;
	}

	public void setNeareststationDIC(String neareststationDIC) {
		this.neareststationDIC = neareststationDIC;
	}

	@Transient
	public String getIspublicDIC() {
		return ispublicDIC;
	}

	public void setIspublicDIC(String ispublicDIC) {
		this.ispublicDIC = ispublicDIC;
	}

	@Transient
	public String getIsactiveDIC() {
		if (isactive.equals("1")) {
			return "启用";
		} else {
			return "停用";
		}
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

	@Transient
	public String getPubliccorp() {
		return publiccorp;
	}

	public void setPubliccorp(String publiccorp) {
		this.publiccorp = publiccorp;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortname;
	}

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Transient
	public String getBureauidDIC() {
		return bureauidDIC;
	}

	public void setBureauidDIC(String bureauidDIC) {
		this.bureauidDIC = bureauidDIC;
	}

	@Column(name = "STANKGOODS", length = 120)
	public String getStankgoods() {
		return stankgoods;
	}

	public void setStankgoods(String stankgoods) {
		this.stankgoods = stankgoods;
	}

	@Column(name = "SNTANKGOODS", length = 120)
	public String getSntankgoods() {
		return sntankgoods;
	}

	public void setSntankgoods(String sntankgoods) {
		this.sntankgoods = sntankgoods;
	}

	@Column(name = "SCONTAINERGOODS", length = 120)
	public String getScontainergoods() {
		return scontainergoods;
	}

	public void setScontainergoods(String scontainergoods) {
		this.scontainergoods = scontainergoods;
	}

	@Column(name = "ATANKGOODS", length = 120)
	public String getAtankgoods() {
		return atankgoods;
	}

	public void setAtankgoods(String atankgoods) {
		this.atankgoods = atankgoods;
	}

	@Column(name = "ANTANKGOODS", length = 120)
	public String getAntankgoods() {
		return antankgoods;
	}

	public void setAntankgoods(String antankgoods) {
		this.antankgoods = antankgoods;
	}

	@Column(name = "ACONTAINERGOODS", length = 120)
	public String getAcontainergoods() {
		return acontainergoods;
	}

	public void setAcontainergoods(String acontainergoods) {
		this.acontainergoods = acontainergoods;
	}

	@Column(name = "SSHAREGOODS", length = 120)
	public String getSsharegoods() {
		return ssharegoods;
	}

	public void setSsharegoods(String ssharegoods) {
		this.ssharegoods = ssharegoods;
	}

	@Column(name = "ASHAREGOODS", length = 120)
	public String getAsharegoods() {
		return asharegoods;
	}

	public void setAsharegoods(String asharegoods) {
		this.asharegoods = asharegoods;
	}
	@Transient
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}