package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZbcBitMscitamount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_BIT_MSCITAMOUNT")
public class BitMscitamount implements java.io.Serializable {

	// Fields

	private Long id;
	private String year;
	private String month;
	private String corpid;
	private String corpshortname;
	private String areaid;
	private String rwkindid;
	private String pcategoryid;
	private String rwkindname;
	private String kind;
	private Double mpamount;
	private Double ypamount;
	private Double mtamount;
	private Double ytamount;
	private Double linkrelative;
	private Double yoy;
	private Double ytyoy;

	// Constructors

	/** default constructor */
	public BitMscitamount() {
	}

	/** minimal constructor */
	public BitMscitamount(Long id, String year, String month, String corpid, String areaid, String rwkindid, String pcategoryid, String kind) {
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
	public BitMscitamount(Long id, String year, String month, String corpid, String corpshortname, String areaid, String rwkindid,
			String pcategoryid, String rwkindname, String kind, Double mpamount, Double ypamount, Double mtamount, Double ytamount,
			Double linkrelative, Double yoy, Double ytyoy) {
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
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

}