package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DicGoogssender entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_GOODSCATEGORY")
public class DicGoodscategory implements java.io.Serializable {

	// Fields

	private String goodsid;
	private String goodsname;
	private String parentid;
	private Long orderno;
	private String isactive;
	private Date createdtime;
	private String description;
	private String isactiveDIC;
	private String parentidDIC;

	// Constructors

	/** default constructor */
	public DicGoodscategory() {
	}

	/** full constructor */
	public DicGoodscategory(String goodsid, String goodsname, String parentid,
			Long orderno, String isactive, String description, Date createdtime) {
		this.goodsid = goodsid;
		this.goodsname = goodsname;
		this.parentid = parentid;
		this.orderno = orderno;
		this.isactive = isactive;
		this.description = description;
		this.createdtime = createdtime;
	}

	@Id
	@Column(name = "GOODSID", unique = true, nullable = false, length = 3)
	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "GOODSNAME", nullable = false, length = 20)
	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	@Column(name = "PARENTID", nullable = false, length = 3)
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ORDERNO", precision = 22, scale = 0)
	public Long getOrderno() {
		return orderno;
	}

	public void setOrderno(Long orderno) {
		this.orderno = orderno;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
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

	@Transient
	public String getParentidDIC() {
		return parentidDIC;
	}

	public void setParentidDIC(String parentidDIC) {
		this.parentidDIC = parentidDIC;
	}

}