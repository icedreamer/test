package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BitMsinoctamount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_BIT_MSINOCTAMOUNT")
public class BitMsinoctamount implements java.io.Serializable {

	// Fields

	private long id;
	private String month;
	private String kind;
	private double mpamount;
	private double ypamount;
	private double mtamount;
	private double ytamount;
	private double linkrelative;
	private double yoy;
	private double ytyoy;
	private String remarks;

	// Constructors

	/** default constructor */
	public BitMsinoctamount() {
	}

	/** minimal constructor */
	public BitMsinoctamount(long id, String month, String kind) {
		this.id = id;
		this.month = month;
		this.kind = kind;
	}

	/** full constructor */
	public BitMsinoctamount(long id, String month, String kind, double mpamount,
			double ypamount, double mtamount, double ytamount, double linkrelative, double yoy,
			double ytyoy, String remarks) {
		this.id = id;
		this.month = month;
		this.kind = kind;
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
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "KIND", nullable = false, length = 1)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "MPAMOUNT", precision = 16, scale = 4)
	public double getMpamount() {
		return this.mpamount;
	}

	public void setMpamount(double mpamount) {
		this.mpamount = mpamount;
	}

	@Column(name = "YPAMOUNT", precision = 16, scale = 4)
	public double getYpamount() {
		return this.ypamount;
	}

	public void setYpamount(double ypamount) {
		this.ypamount = ypamount;
	}

	@Column(name = "MTAMOUNT", precision = 16, scale = 4)
	public double getMtamount() {
		return this.mtamount;
	}

	public void setMtamount(double mtamount) {
		this.mtamount = mtamount;
	}

	@Column(name = "YTAMOUNT", precision = 16, scale = 4)
	public double getYtamount() {
		return this.ytamount;
	}

	public void setYtamount(double ytamount) {
		this.ytamount = ytamount;
	}

	@Column(name = "LINKRELATIVE", precision = 12, scale = 4)
	public double getLinkrelative() {
		return this.linkrelative;
	}

	public void setLinkrelative(double linkrelative) {
		this.linkrelative = linkrelative;
	}

	@Column(name = "YOY", precision = 12, scale = 4)
	public double getYoy() {
		return this.yoy;
	}

	public void setYoy(double yoy) {
		this.yoy = yoy;
	}

	@Column(name = "YTYOY", precision = 12, scale = 4)
	public double getYtyoy() {
		return this.ytyoy;
	}

	public void setYtyoy(double ytyoy) {
		this.ytyoy = ytyoy;
	}

	@Column(name = "REMARKS", length = 2000)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}