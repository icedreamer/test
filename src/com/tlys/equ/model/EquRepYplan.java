package com.tlys.equ.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * TbZbcEquRepYplan entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_REP_YPLAN")
public class EquRepYplan implements java.io.Serializable {

	// Fields

	private Long id;
	private String year;
	private String corpid;
	private String areaid;
	private String corpshortname;
	private String rtypeid;
	private Long amount;
	private Long gentimes;
	private Date gendate;
	private String remarks;
	private String corpids;
	private String areaids;

	// Constructors

	/** default constructor */
	public EquRepYplan() {
	}

	/** minimal constructor */
	public EquRepYplan(Long id, String year, String corpid, String rtypeid) {
		this.id = id;
		this.year = year;
		this.corpid = corpid;
		this.rtypeid = rtypeid;
	}

	/** full constructor */
	public EquRepYplan(Long id, String year, String corpid, String corpshortname, String rtypeid, Long amount,
			Long gentimes, Date gendate, String remarks) {
		this.id = id;
		this.year = year;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.rtypeid = rtypeid;
		this.amount = amount;
		this.gentimes = gentimes;
		this.gendate = gendate;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YEAR", nullable = false, length = 6)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
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

	@Column(name = "RTYPEID", nullable = false, length = 2)
	public String getRtypeid() {
		return this.rtypeid;
	}

	public void setRtypeid(String rtypeid) {
		this.rtypeid = rtypeid;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 0)
	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Column(name = "GENTIMES", precision = 22, scale = 0)
	public Long getGentimes() {
		return this.gentimes;
	}

	public void setGentimes(Long gentimes) {
		this.gentimes = gentimes;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GENDATE", length = 7)
	public Date getGendate() {
		return this.gendate;
	}

	public void setGendate(Date gendate) {
		this.gendate = gendate;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public String getAreaid() {
		return areaid;
	}

	@Transient
	public String getAreaids() {
		return areaids;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

}