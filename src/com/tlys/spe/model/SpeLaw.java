package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * SpeLaw entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_LAW")
public class SpeLaw implements java.io.Serializable {

	// Fields

	private Long id;
	private String issuer;
	private String lawtype;
	private String lawname;
	private String keywords;
	private String issuedate;
	private String effectdate;
	private String formattype;
	private String path;
	private Long readtimes;
	private String remarks;
	private Date createdtime;
	private String filename;

	private String expiredateStart;
	private String expiredateEnd;
	private String issuedateStr;
	private String effectdateStr;

	// Constructors
	@Transient
	public String getIssuedateStr() {
		return issuedateStr;
	}

	public void setIssuedateStr(String issuedateStr) {
		this.issuedateStr = issuedateStr;
	}

	/** default constructor */
	public SpeLaw() {
	}

	/** minimal constructor */
	public SpeLaw(Long id, String issuer, String lawtype, String lawname,
			String keywords, String formattype) {
		this.id = id;
		this.issuer = issuer;
		this.lawtype = lawtype;
		this.lawname = lawname;
		this.keywords = keywords;
		this.formattype = formattype;
	}

	/** full constructor */
	public SpeLaw(Long id, String issuer, String lawtype, String lawname,
			String keywords, String issuedate, String effectdate,
			String formattype, String path, Long readtimes, String remarks,
			Date createdtime, String filename) {
		this.id = id;
		this.issuer = issuer;
		this.lawtype = lawtype;
		this.lawname = lawname;
		this.keywords = keywords;
		this.issuedate = issuedate;
		this.effectdate = effectdate;
		this.formattype = formattype;
		this.path = path;
		this.readtimes = readtimes;
		this.remarks = remarks;
		this.createdtime = createdtime;
		this.filename = filename;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oraSeqGenerator")
	@SequenceGenerator(name = "oraSeqGenerator", sequenceName = "SEQ_TB_ZBC_SPE_LAW", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ISSUER", nullable = false, length = 80)
	public String getIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@Column(name = "LAWTYPE", nullable = false, length = 2)
	public String getLawtype() {
		return this.lawtype;
	}

	public void setLawtype(String lawtype) {
		this.lawtype = lawtype;
	}

	@Column(name = "LAWNAME", nullable = false, length = 80)
	public String getLawname() {
		return this.lawname;
	}

	public void setLawname(String lawname) {
		this.lawname = lawname;
	}

	@Column(name = "KEYWORDS", nullable = false, length = 80)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "ISSUEDATE", length = 8)
	public String getIssuedate() {
		return this.issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	@Column(name = "EFFECTDATE", length = 8)
	public String getEffectdate() {
		return this.effectdate;
	}

	public void setEffectdate(String effectdate) {
		this.effectdate = effectdate;
	}

	@Column(name = "FORMATTYPE", nullable = false, length = 10)
	public String getFormattype() {
		return this.formattype;
	}

	public void setFormattype(String formattype) {
		this.formattype = formattype;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "READTIMES", length = 0)
	public Long getReadtimes() {
		return this.readtimes;
	}

	public void setReadtimes(Long readtimes) {
		this.readtimes = readtimes;
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

	@Transient
	public String getExpiredateStart() {
		return expiredateStart;
	}

	public void setExpiredateStart(String expiredateStart) {
		this.expiredateStart = expiredateStart;
	}

	@Transient
	public String getExpiredateEnd() {
		return expiredateEnd;
	}

	public void setExpiredateEnd(String expiredateEnd) {
		this.expiredateEnd = expiredateEnd;
	}

	@Column(name = "FILENAME", length = 100)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Transient
	public String getEffectdateStr() {
		return effectdateStr;
	}

	public void setEffectdateStr(String effectdateStr) {
		this.effectdateStr = effectdateStr;
	}

}