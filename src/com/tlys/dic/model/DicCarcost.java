package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author ccsong 2012-6-26 ÉÏÎç10:21:01
 */
@Entity
@Table(name = "TB_ZBC_DIC_CARCOST" )
public class DicCarcost implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896340111318842674L;
	private String costid;
	private String costname;
	private String isactive;
	private String sinocode;
	private Date createdtime;
	private String description;

	// Constructors

	/** default constructor */
	public DicCarcost() {
	}

	/** minimal constructor */
	public DicCarcost(String costid, String costname, String isactive) {
		this.costid = costid;
		this.costname = costname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicCarcost(String costid, String costname, String isactive, String sinocode, Date createdtime,
			String description) {
		this.costid = costid;
		this.costname = costname;
		this.isactive = isactive;
		this.sinocode = sinocode;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "COSTID", unique = true, nullable = false, length = 2)
	public String getCostid() {
		return this.costid;
	}

	public void setCostid(String costid) {
		this.costid = costid;
	}

	@Column(name = "COSTNAME", nullable = false, length = 30)
	public String getCostname() {
		return this.costname;
	}

	public void setCostname(String costname) {
		this.costname = costname;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "SINOCODE", length = 16)
	public String getSinocode() {
		return this.sinocode;
	}

	public void setSinocode(String sinocode) {
		this.sinocode = sinocode;
	}

	@Temporal(TemporalType.TIMESTAMP)
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

}