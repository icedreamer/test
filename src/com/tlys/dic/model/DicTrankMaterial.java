package com.tlys.dic.model;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DicTrankMaterial entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_TANKMATERIAL")
public class DicTrankMaterial implements java.io.Serializable {

	// Fields

	private String tankmateid;
	private String mname;
	private String isactive;
	private Date createdtime;
	private String description;
	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicTrankMaterial() {
	}

	/** minimal constructor */
	public DicTrankMaterial(String tankmateid, String mname, String isactive) {
		this.tankmateid = tankmateid;
		this.mname = mname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicTrankMaterial(String tankmateid, String mname, String isactive,
			Date createdtime, String description) {
		this.tankmateid = tankmateid;
		this.mname = mname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "TANKMATEID", unique = true, nullable = false, length = 2)
	public String getTankmateid() {
		return this.tankmateid;
	}

	public void setTankmateid(String tankmateid) {
		this.tankmateid = tankmateid;
	}

	@Column(name = "MNAME", nullable = false, length = 20)
	public String getMname() {
		return this.mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
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
	public String getIsactiveDIC() {
		if (isactive.equals("1")) {
			return "∆Ù”√";
		} else {
			return "Õ£”√";
		}
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

}