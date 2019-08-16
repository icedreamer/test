package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZbcBitMcarsrequest entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_BIT_MCARSREQUEST")
public class BitMcarsrequest implements java.io.Serializable {

	// Fields

	private Long id;
	private String datamonth;
	private String corpid;
	private String corpshrinkname;
	private String areaid;
	private String areashrinkname;
	private String kind;
	private Long requestcars;
	private Double requestamount;
	private Long acceptcars;
	private Double acceptamount;
	private Long loadcars;
	private Double loadamount;

	// Constructors

	/** default constructor */
	public BitMcarsrequest() {
	}

	/** minimal constructor */
	public BitMcarsrequest(Long id, String datamonth, String corpid, String areaid, String kind) {
		this.id = id;
		this.datamonth = datamonth;
		this.corpid = corpid;
		this.areaid = areaid;
		this.kind = kind;
	}

	/** full constructor */
	public BitMcarsrequest(Long id, String datamonth, String corpid, String corpshrinkname, String areaid, String areashrinkname, String kind,
			Long requestcars, Double requestamount, Long acceptcars, Double acceptamount, Long loadcars, Double loadamount) {
		this.id = id;
		this.datamonth = datamonth;
		this.corpid = corpid;
		this.corpshrinkname = corpshrinkname;
		this.areaid = areaid;
		this.areashrinkname = areashrinkname;
		this.kind = kind;
		this.requestcars = requestcars;
		this.requestamount = requestamount;
		this.acceptcars = acceptcars;
		this.acceptamount = acceptamount;
		this.loadcars = loadcars;
		this.loadamount = loadamount;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DATAMONTH", nullable = false, length = 8)
	public String getDatamonth() {
		return this.datamonth;
	}

	public void setDatamonth(String datamonth) {
		this.datamonth = datamonth;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPSHRINKNAME", length = 20)
	public String getCorpshrinkname() {
		return this.corpshrinkname;
	}

	public void setCorpshrinkname(String corpshrinkname) {
		this.corpshrinkname = corpshrinkname;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "AREASHRINKNAME", length = 20)
	public String getAreashrinkname() {
		return this.areashrinkname;
	}

	public void setAreashrinkname(String areashrinkname) {
		this.areashrinkname = areashrinkname;
	}

	@Column(name = "KIND", nullable = false, length = 1)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "REQUESTCARS", precision = 22, scale = 0)
	public Long getRequestcars() {
		return this.requestcars;
	}

	public void setRequestcars(Long requestcars) {
		this.requestcars = requestcars;
	}

	@Column(name = "REQUESTAMOUNT", precision = 12, scale = 4)
	public Double getRequestamount() {
		return this.requestamount;
	}

	public void setRequestamount(Double requestamount) {
		this.requestamount = requestamount;
	}

	@Column(name = "ACCEPTCARS", precision = 22, scale = 0)
	public Long getAcceptcars() {
		return this.acceptcars;
	}

	public void setAcceptcars(Long acceptcars) {
		this.acceptcars = acceptcars;
	}

	@Column(name = "ACCEPTAMOUNT", precision = 12, scale = 4)
	public Double getAcceptamount() {
		return this.acceptamount;
	}

	public void setAcceptamount(Double acceptamount) {
		this.acceptamount = acceptamount;
	}

	@Column(name = "LOADCARS", precision = 22, scale = 0)
	public Long getLoadcars() {
		return this.loadcars;
	}

	public void setLoadcars(Long loadcars) {
		this.loadcars = loadcars;
	}

	@Column(name = "LOADAMOUNT", precision = 12, scale = 4)
	public Double getLoadamount() {
		return this.loadamount;
	}

	public void setLoadamount(Double loadamount) {
		this.loadamount = loadamount;
	}

}