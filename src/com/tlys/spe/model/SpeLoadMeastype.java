package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SpeLoadMeastype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_LOAD_MEASTYPE")
public class SpeLoadMeastype implements java.io.Serializable {

	// Fields

	private String meastypeid;
	private String name;
	private String creator;
	private Date createdtime;

	// Constructors

	/** default constructor */
	public SpeLoadMeastype() {
	}

	/** minimal constructor */
	public SpeLoadMeastype(String meastypeid, String name, String creator) {
		this.meastypeid = meastypeid;
		this.name = name;
		this.creator = creator;
	}

	/** full constructor */
	public SpeLoadMeastype(String meastypeid, String name, String creator,
			Date createdtime) {
		this.meastypeid = meastypeid;
		this.name = name;
		this.creator = creator;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "MEASTYPEID", unique = true, nullable = false, precision = 2)
	public String getMeastypeid() {
		return this.meastypeid;
	}

	public void setMeastypeid(String meastypeid) {
		this.meastypeid = meastypeid;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

}