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
 * DicProdsecondkind entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_PRODSECONDKIND")
public class DicProdsecondkind implements java.io.Serializable {

	// Fields

	private String secondkindid;
	private String categoryid;
	private String fullname;
	private String shortname;
	private Long orderno;
	private String isactive;
	private Date createdtime;
	private String rwkindid;
	private String kind;
	private String categoryidDIC;
	private String isactiveDIC;
	private String rwkindidDIC;
	private String kindDIC;

	/** default constructor */
	public DicProdsecondkind() {
	}

	/** minimal constructor */
	public DicProdsecondkind(String secondkindid, String categoryid,
			String fullname, String shortname, String isactive) {
		this.secondkindid = secondkindid;
		this.categoryid = categoryid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicProdsecondkind(String secondkindid, String categoryid,
			String fullname, String shortname, Long orderno, String isactive,
			Date createdtime, String rwkindid, String kind) {
		this.secondkindid = secondkindid;
		this.categoryid = categoryid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.orderno = orderno;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.rwkindid = rwkindid;
		this.kind = kind;
	}

	// Property accessors
	@Id
	@Column(name = "SECONDKINDID", unique = true, nullable = false, length = 16)
	public String getSecondkindid() {
		return this.secondkindid;
	}

	public void setSecondkindid(String secondkindid) {
		this.secondkindid = secondkindid;
	}

	@Column(name = "CATEGORYID", nullable = false, length = 2)
	public String getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
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

	@Column(name = "ORDERNO", precision = 22, scale = 0)
	public Long getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Long orderno) {
		this.orderno = orderno;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortname;
	}

	@Column(name = "RWKINDID", length = 7)
	public String getRwkindid() {
		return rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Transient
	public String getCategoryidDIC() {
		return categoryidDIC;
	}

	public void setCategoryidDIC(String categoryidDIC) {
		this.categoryidDIC = categoryidDIC;
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
	public String getRwkindidDIC() {
		return rwkindidDIC;
	}

	public void setRwkindidDIC(String rwkindidDIC) {
		this.rwkindidDIC = rwkindidDIC;
	}

	@Transient
	public String getKindDIC() {
		if (kind.equals("1")) {
			return "统销化工产品";
		} else if (kind.equals("0")) {
			return "非统销化工产品";
		} else if (kind.equals("3")) {
			return "成品油";
		} else if (kind.equals("4")) {
			return "煤";
		} else if (kind.equals("5")) {
			return "原油";
		} else if (kind.equals("6")) {
			return "沥青";
		} else {
			return "其它";
		}
	}

	public void setKindDIC(String kindDIC) {
		this.kindDIC = kindDIC;
	}

	@Column(name = "kind", length = 1)
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}