package com.tlys.dic.model;

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
import javax.persistence.UniqueConstraint;

/**
 * TbZbcDicCorprepdepot entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CORPREPDEPOT",  uniqueConstraints = @UniqueConstraint(columnNames = {
		"CORPID", "STOCKDEPOTID" }))
public class DicCorprepdepot implements java.io.Serializable {

	// Fields

	private long id;
	private String corpid;
	private String shortname;
	private String stockdepotid;
	private String isactive;
	private Date createdtime;
	private String description;
	private String bureauid;
	// ∑≠“Î
	private String corpidDIC;
	private String isactiveDIC;
	private String bureauidDIC;

	// Constructors

	/** default constructor */
	public DicCorprepdepot() {
	}

	/** minimal constructor */
	public DicCorprepdepot(long id, String corpid, String shortname,
			String bureauid, String stockdepotid, String isactive) {
		this.id = id;
		this.corpid = corpid;
		this.shortname = shortname;
		this.stockdepotid = stockdepotid;
		this.isactive = isactive;
		this.bureauid = bureauid;
	}

	/** full constructor */
	public DicCorprepdepot(long id, String corpid, String shortname,
			String bureauid, String stockdepotid, String isactive,
			Date createdtime, String description) {
		this.id = id;
		this.corpid = corpid;
		this.shortname = shortname;
		this.stockdepotid = stockdepotid;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
		this.bureauid = bureauid;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oraSeqGenerator")
	@SequenceGenerator(name = "oraSeqGenerator", sequenceName = "SEQ_TB_ZBC_DIC_CORPREPDEPOT", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 30)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "STOCKDEPOTID", nullable = false, length = 6)
	public String getStockdepotid() {
		return this.stockdepotid;
	}

	public void setStockdepotid(String stockdepotid) {
		this.stockdepotid = stockdepotid;
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
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
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

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Transient
	public String getBureauidDIC() {
		return bureauidDIC;
	}

	public void setBureauidDIC(String bureauidDIC) {
		this.bureauidDIC = bureauidDIC;
	}
}