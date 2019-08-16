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
 * TbZbcDicTankrepcorp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_TANKREPCORP" )
public class DicTankrepcorp implements java.io.Serializable {

	// Fields
	private String repcorpid;
	private String fullname;
	private String shortname;
	private String isactive;
	private Date createdtime;
	private String description;
	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicTankrepcorp() {
	}

	/** minimal constructor */
	public DicTankrepcorp(String repcorpid, String fullname, String shortname,
			String isactive) {
		this.repcorpid = repcorpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicTankrepcorp(String repcorpid, String fullname, String shortname,
			String isactive, Date createdtime, String description) {
		this.repcorpid = repcorpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "REPCORPID", unique = true, nullable = false, length = 6)
	public String getRepcorpid() {
		return this.repcorpid;
	}

	public void setRepcorpid(String repcorpid) {
		this.repcorpid = repcorpid;
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

	@Transient
	public String getIsactiveDIC() {
		return isactiveDIC;
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

}