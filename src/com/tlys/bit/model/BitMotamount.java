package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BitMotamount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_BIT_MOTAMOUNT")
public class BitMotamount implements java.io.Serializable {

	// Fields

	private long id;
	private String year;
	private String month;
	private String corpid;
	private String corpshortname;
	private String areaid;
	private String rwkindid;
	private String pcategoryid;
	private String rwkindname;
	private String kind;
	private double mpamount;
	private double ypamount;
	private double mtamount;
	private double ytamount;
	private double linkrelative;
	private double yoy;
	private double ytyoy;

	// Constructors

	/** default constructor */
	public BitMotamount() {
	}

	/** minimal constructor */
	public BitMotamount(long id, String year, String month, String corpid, String areaid,
			String rwkindid, String pcategoryid, String kind) {
		this.id = id;
		this.year = year;
		this.month = month;
		this.corpid = corpid;
		this.areaid = areaid;
		this.rwkindid = rwkindid;
		this.pcategoryid = pcategoryid;
		this.kind = kind;
	}

	/** full constructor */
	public BitMotamount(long id, String year, String month, String corpid,
			String corpshortname, String areaid, String rwkindid, String pcategoryid,
			String rwkindname, String kind, double mpamount, double ypamount, double mtamount,
			double ytamount, double linkrelative, double yoy, double ytyoy) {
		this.id = id;
		this.year = year;
		this.month = month;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.areaid = areaid;
		this.rwkindid = rwkindid;
		this.pcategoryid = pcategoryid;
		this.rwkindname = rwkindname;
		this.kind = kind;
		this.mpamount = mpamount;
		this.ypamount = ypamount;
		this.mtamount = mtamount;
		this.ytamount = ytamount;
		this.linkrelative = linkrelative;
		this.yoy = yoy;
		this.ytyoy = ytyoy;
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

	@Column(name = "YEAR", nullable = false, length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "RWKINDID", nullable = false, length = 7)
	public String getRwkindid() {
		return this.rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Column(name = "PCATEGORYID", nullable = false, length = 2)
	public String getPcategoryid() {
		return this.pcategoryid;
	}

	public void setPcategoryid(String pcategoryid) {
		this.pcategoryid = pcategoryid;
	}

	@Column(name = "RWKINDNAME", length = 20)
	public String getRwkindname() {
		return this.rwkindname;
	}

	public void setRwkindname(String rwkindname) {
		this.rwkindname = rwkindname;
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

}