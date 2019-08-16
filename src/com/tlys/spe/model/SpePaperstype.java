package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SpePaperstype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_PAPERS_TYPE")
public class SpePaperstype implements java.io.Serializable {

	// Fields

	private String ptypeid;
	private String name;
	private String creator;
	private Date createdtime;

	// Constructors

	/** default constructor */
	public SpePaperstype() {
	}

	/** minimal constructor */
	public SpePaperstype(String ptypeid, String name, String creator) {
		this.ptypeid = ptypeid;
		this.name = name;
		this.creator = creator;
	}

	/** full constructor */
	public SpePaperstype(String ptypeid, String name, String creator, Date createdtime) {
		this.ptypeid = ptypeid;
		this.name = name;
		this.creator = creator;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "PTYPEID", unique = true, nullable = false, length = 2)
	public String getPtypeid() {
		return this.ptypeid;
	}

	public void setPtypeid(String ptypeid) {
		this.ptypeid = ptypeid;
	}

	@Column(name = "NAME", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CREATOR", nullable = false, length = 6)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

}