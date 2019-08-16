package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SpePackmark entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_PACKMARK")
public class SpePackmark implements java.io.Serializable {

	// Fields

	private String markid;
	private String markname;
	private String kind;
	private String remarks;
	private Date createdtime;

	// Constructors

	/** default constructor */
	public SpePackmark() {
	}

	/** minimal constructor */
	public SpePackmark(String markid, String markname, String kind) {
		this.markid = markid;
		this.markname = markname;
		this.kind = kind;
	}

	/** full constructor */
	public SpePackmark(String markid, String markname, String kind, String remarks, Date createdtime) {
		this.markid = markid;
		this.markname = markname;
		this.kind = kind;
		this.remarks = remarks;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "MARKID", unique = true, nullable = false, length = 4)
	public String getMarkid() {
		return this.markid;
	}

	public void setMarkid(String markid) {
		this.markid = markid;
	}

	@Column(name = "MARKNAME", nullable = false, length = 60)
	public String getMarkname() {
		return this.markname;
	}

	public void setMarkname(String markname) {
		this.markname = markname;
	}

	@Column(name = "KIND", nullable = false, length = 2)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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