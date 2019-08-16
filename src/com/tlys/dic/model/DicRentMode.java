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
 * DicRentMode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_RENTMODE")
public class DicRentMode implements java.io.Serializable {

	// Fields

	private String rentid;
	private String rentname;
	private String isactive;
	private Date createdtime;
	private String description;
	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicRentMode() {
	}

	/** minimal constructor */
	public DicRentMode(String rentid, String rentname, String isactive) {
		this.rentid = rentid;
		this.rentname = rentname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicRentMode(String rentid, String rentname, String isactive,
			Date createdtime, String description) {
		this.rentid = rentid;
		this.rentname = rentname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "RENTID", unique = true, nullable = false, length = 2)
	public String getRentid() {
		return this.rentid;
	}

	public void setRentid(String rentid) {
		this.rentid = rentid;
	}

	@Column(name = "RENTNAME", nullable = false, length = 20)
	public String getRentname() {
		return this.rentname;
	}

	public void setRentname(String rentname) {
		this.rentname = rentname;
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