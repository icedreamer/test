package com.tlys.pla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * PlaYuprtamount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_YUPRTAMOUNT")
public class PlaYuprtamount implements java.io.Serializable {

	// Fields

	private Long id;
	private String year;
	private String areaid;
	private String corpid;
	private String corpshortname;
	private String productcategoryid;
	private String productcategoryname;
	private String productsecondid;
	private String productsecondname;
	private String rwkindid;
	private String rkindname;
	private Double amount;
	private Double lyamount;
	private Double varyamount;
	private Date createdtime;
	private String remarks;
	
	private String areaidDIC;
	
	private String areaids;
	private String corpids;
	private String productcategoryids;
	private String productsecondids;

	

	// Constructors

	/** default constructor */
	public PlaYuprtamount() {
	}

	/** minimal constructor */
	public PlaYuprtamount(Long id, String year, String areaid, String corpid, String productcategoryid, String productsecondid) {
		this.id = id;
		this.year = year;
		this.areaid = areaid;
		this.corpid = corpid;
		this.productcategoryid = productcategoryid;
		this.productsecondid = productsecondid;
	}

	/** full constructor */
	public PlaYuprtamount(Long id, String year, String areaid, String corpid, String corpshortname, String productcategoryid,
			String productcategoryname, String productsecondid, String productsecondname, String rwkindid, String rkindname, Double amount,
			Double lyamount, Double varyamount, Date createdtime, String remarks) {
		this.id = id;
		this.year = year;
		this.areaid = areaid;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.productcategoryid = productcategoryid;
		this.productcategoryname = productcategoryname;
		this.productsecondid = productsecondid;
		this.productsecondname = productsecondname;
		this.rwkindid = rwkindid;
		this.rkindname = rkindname;
		this.amount = amount;
		this.lyamount = lyamount;
		this.varyamount = varyamount;
		this.createdtime = createdtime;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
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

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
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

	@Column(name = "PRODUCTCATEGORYID", nullable = false, length = 2)
	public String getProductcategoryid() {
		return this.productcategoryid;
	}

	public void setProductcategoryid(String productcategoryid) {
		this.productcategoryid = productcategoryid;
	}

	@Column(name = "PRODUCTCATEGORYNAME", length = 30)
	public String getProductcategoryname() {
		return this.productcategoryname;
	}

	public void setProductcategoryname(String productcategoryname) {
		this.productcategoryname = productcategoryname;
	}

	@Column(name = "PRODUCTSECONDID", nullable = false, length = 16)
	public String getProductsecondid() {
		return this.productsecondid;
	}

	public void setProductsecondid(String productsecondid) {
		this.productsecondid = productsecondid;
	}

	@Column(name = "PRODUCTSECONDNAME", length = 60)
	public String getProductsecondname() {
		return this.productsecondname;
	}

	public void setProductsecondname(String productsecondname) {
		this.productsecondname = productsecondname;
	}

	@Column(name = "RWKINDID", length = 7)
	public String getRwkindid() {
		return this.rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Column(name = "RKINDNAME", length = 20)
	public String getRkindname() {
		return this.rkindname;
	}

	public void setRkindname(String rkindname) {
		this.rkindname = rkindname;
	}

	@Column(name = "AMOUNT", precision = 12, scale = 4)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "LYAMOUNT", precision = 12, scale = 4)
	public Double getLyamount() {
		return this.lyamount;
	}

	public void setLyamount(Double lyamount) {
		this.lyamount = lyamount;
	}

	@Column(name = "VARYAMOUNT", precision = 12, scale = 4)
	public Double getVaryamount() {
		return this.varyamount;
	}

	public void setVaryamount(Double varyamount) {
		this.varyamount = varyamount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getAreaidDIC() {
		return areaidDIC;
	}

	public void setAreaidDIC(String areaidDIC) {
		this.areaidDIC = areaidDIC;
	}

	@Transient
	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	@Transient
	public String getProductcategoryids() {
		return productcategoryids;
	}

	public void setProductcategoryids(String productcategoryids) {
		this.productcategoryids = productcategoryids;
	}

	@Transient
	public String getProductsecondids() {
		return productsecondids;
	}

	public void setProductsecondids(String productsecondids) {
		this.productsecondids = productsecondids;
	}

}