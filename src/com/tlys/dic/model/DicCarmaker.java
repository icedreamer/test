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
 * DicCarmarker entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CARMAKER")
public class DicCarmaker implements java.io.Serializable {

	// Fields

	private String carmakerid;
	private String fullname;
	private String shortname;
	private String provinceid;
	private String sinocode;
	private String isactive;
	private Date createdtime;
	private String description;
	
	private String provinceidDIC;
	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicCarmaker() {
	}

	/** minimal constructor */
	public DicCarmaker(String carmakerid, String fullname, String shortname,
			String provinceid, String isactive) {
		this.carmakerid = carmakerid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.provinceid = provinceid;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicCarmaker(String carmakerid, String fullname, String shortname,
			String provinceid, String sinocode, String isactive,
			Date createdtime, String description) {
		this.carmakerid = carmakerid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.provinceid = provinceid;
		this.sinocode = sinocode;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "CARMAKERID", unique = true, nullable = false, length = 3)
	public String getCarmakerid() {
		return this.carmakerid;
	}

	public void setCarmakerid(String carmakerid) {
		this.carmakerid = carmakerid;
	}

	@Column(name = "FULLNAME", nullable = false, length = 100)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 40)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "PROVINCEID", nullable = false, length = 6)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
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
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

	public void setProvinceidDIC(String provinceidDIC) {
		this.provinceidDIC = provinceidDIC;
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