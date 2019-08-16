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
 * DicCarkind entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CARKIND" )
public class DicCarkind implements java.io.Serializable {

	// Fields

	private String carkindid;
	private String fullname;
	private String shortname;
	private String isactive;
	private Date createdtime;
	private String description;
	
	private String isactiveStr;

	// Constructors

	/** default constructor */
	public DicCarkind() {
	}

	/** minimal constructor */
	public DicCarkind(String carkindid, String fullname, String shortname, String isactive) {
		this.carkindid = carkindid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicCarkind(String carkindid, String fullname, String shortname, String isactive, Date createdtime, String description) {
		this.carkindid = carkindid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "CARKINDID", unique = true, nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
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

	/**
	 * @return the isactiveStr
	 */
	@Transient
	public String getIsactiveStr() {
		if(isactive.equals("1")){
    		return "∆Ù”√";
    	}else{
    		return "Õ£”√";
    	}
	}

	/**
	 * @param isactiveStr the isactiveStr to set
	 */
	public void setIsactiveStr(String isactiveStr) {
		this.isactiveStr = isactiveStr;
	}
	
	@Override
	public String toString() {
		return shortname;
	}

}