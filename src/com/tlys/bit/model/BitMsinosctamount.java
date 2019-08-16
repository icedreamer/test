package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZbcBitMsinosctamount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_BIT_MSINOSCTAMOUNT")
public class BitMsinosctamount implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3206023028654128333L;
	private Long id;
	private String month;
	private Double mpamount;
	private Double ypamount;
	private Double mtamount;
	private Double ytamount;
	private Double linkrelative;
	private Double yoy;
	private Double ytyoy;
	private String remarks;
	private String kind;

	// Constructors

	/** default constructor */
	public BitMsinosctamount() {
	}

	/** minimal constructor */
	public BitMsinosctamount(Long id, String month) {
		this.id = id;
		this.month = month;
	}

	/** full constructor */
	public BitMsinosctamount(Long id, String month, Double mpamount, Double ypamount, Double mtamount, Double ytamount,
			Double linkrelative, Double yoy, Double ytyoy, String remarks) {
		this.id = id;
		this.month = month;
		this.mpamount = mpamount;
		this.ypamount = ypamount;
		this.mtamount = mtamount;
		this.ytamount = ytamount;
		this.linkrelative = linkrelative;
		this.yoy = yoy;
		this.ytyoy = ytyoy;
		this.remarks = remarks;
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

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "MPAMOUNT", precision = 16, scale = 4)
	public Double getMpamount() {
		return this.mpamount;
	}

	public void setMpamount(Double mpamount) {
		this.mpamount = mpamount;
	}

	@Column(name = "YPAMOUNT", precision = 16, scale = 4)
	public Double getYpamount() {
		return this.ypamount;
	}

	public void setYpamount(Double ypamount) {
		this.ypamount = ypamount;
	}

	@Column(name = "MTAMOUNT", precision = 16, scale = 4)
	public Double getMtamount() {
		return this.mtamount;
	}

	public void setMtamount(Double mtamount) {
		this.mtamount = mtamount;
	}

	@Column(name = "YTAMOUNT", precision = 16, scale = 4)
	public Double getYtamount() {
		return this.ytamount;
	}

	public void setYtamount(Double ytamount) {
		this.ytamount = ytamount;
	}

	@Column(name = "LINKRELATIVE", precision = 12, scale = 4)
	public Double getLinkrelative() {
		return this.linkrelative;
	}

	public void setLinkrelative(Double linkrelative) {
		this.linkrelative = linkrelative;
	}

	@Column(name = "YOY", precision = 12, scale = 4)
	public Double getYoy() {
		return this.yoy;
	}

	public void setYoy(Double yoy) {
		this.yoy = yoy;
	}

	@Column(name = "YTYOY", precision = 12, scale = 4)
	public Double getYtyoy() {
		return this.ytyoy;
	}

	public void setYtyoy(Double ytyoy) {
		this.ytyoy = ytyoy;
	}

	@Column(name = "REMARKS", length = 2000)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}