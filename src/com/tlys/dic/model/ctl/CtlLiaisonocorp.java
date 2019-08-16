package com.tlys.dic.model.ctl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TbZbcCtlLiaisonocorp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_LIAISONOCORP",  uniqueConstraints = @UniqueConstraint(columnNames = { "BRANCHID", "CORPID" }))
public class CtlLiaisonocorp implements java.io.Serializable {

	// Fields

	private Long id;
	private String branchid;
	private String branchshortname;
	private String corpid;
	private String corpshortname;
	private String isactive;
	private Date createdtime;
	private String description;

	// Constructors

	/** default constructor */
	public CtlLiaisonocorp() {
	}

	/** minimal constructor */
	public CtlLiaisonocorp(Long id, String branchid, String corpid, String isactive) {
		this.id = id;
		this.branchid = branchid;
		this.corpid = corpid;
		this.isactive = isactive;
	}

	/** full constructor */
	public CtlLiaisonocorp(Long id, String branchid, String branchshortname, String corpid, String corpshortname, String isactive,
			Date createdtime, String description) {
		this.id = id;
		this.branchid = branchid;
		this.branchshortname = branchshortname;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BRANCHID", nullable = false, length = 8)
	public String getBranchid() {
		return this.branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	@Column(name = "BRANCHSHORTNAME", length = 30)
	public String getBranchshortname() {
		return this.branchshortname;
	}

	public void setBranchshortname(String branchshortname) {
		this.branchshortname = branchshortname;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPSHORTNAME", length = 30)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
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

}