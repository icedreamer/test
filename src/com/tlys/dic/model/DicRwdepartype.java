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
 * DicRwdepartype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_RWDEPATYPE" )
public class DicRwdepartype implements java.io.Serializable {

	// Fields

	private String tpyeid;
	private String name;
	private String isactive;
	private Date createdtime;
	private String description;

	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicRwdepartype() {
	}

	/** minimal constructor */
	public DicRwdepartype(String tpyeid, String name, String isactive) {
		this.tpyeid = tpyeid;
		this.name = name;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicRwdepartype(String tpyeid, String name, String isactive,
			Date createdtime, String description) {
		this.tpyeid = tpyeid;
		this.name = name;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "TPYEID", unique = true, nullable = false, length = 2)
	public String getTpyeid() {
		return this.tpyeid;
	}

	public void setTpyeid(String tpyeid) {
		this.tpyeid = tpyeid;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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