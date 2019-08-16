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
 * TbZbcDicProduct entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_PRODUCT")
public class DicProduct implements java.io.Serializable {

	// Fields

	private String prodid;
	private String fullname;
	private String shortname;
	private String categoryid;
	private String secondkindid;
	private String parentid;
	private String goodsid;
	private String kind;
	private String isactive;
	private String searchcode;
	private String liscode;
	private String sinocode;
	private Date createdtime;
	private String description;
	private String rwkindid;

	private String categoryidDIC;
	private String secondkindidDIC;
	private String parentidDIC;
	private String goodsidDIC;
	private String isactiveDIC;
	private String kindDIC;
	private String rwkindidDIC;
	private String fullnameTree;

	/** default constructor */
	public DicProduct() {
	}

	/** minimal constructor */
	public DicProduct(String prodid, String fullname, String shortname,
			String categoryid, String secondkindid, String kinf, String isactive) {
		this.prodid = prodid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.categoryid = categoryid;
		this.secondkindid = secondkindid;
		this.kind = kinf;
		this.isactive = isactive;

	}

	/** full constructor */
	public DicProduct(String prodid, String fullname, String shortname,
			String categoryid, String secondkindid, String parentid,
			String goodsid, String kinf, String isactive, String searchcode,
			String liscode, String sinocode, Date createdtime,
			String description, String rwkindid) {
		this.prodid = prodid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.categoryid = categoryid;
		this.parentid = parentid;
		this.goodsid = goodsid;
		this.kind = kinf;
		this.isactive = isactive;
		this.searchcode = searchcode;
		this.liscode = liscode;
		this.sinocode = sinocode;
		this.createdtime = createdtime;
		this.description = description;
		this.secondkindid = secondkindid;
		this.rwkindid = rwkindid;
	}

	// Property accessors
	@Id
	@Column(name = "PRODID", unique = true, nullable = false, length = 16)
	public String getProdid() {
		return this.prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	@Column(name = "FULLNAME", nullable = false, length = 100)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 30)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "CATEGORYID", nullable = false, length = 2)
	public String getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	@Column(name = "SECONDKINDID", nullable = false, length = 16)
	public String getSecondkindid() {
		return secondkindid;
	}

	public void setSecondkindid(String secondkindid) {
		this.secondkindid = secondkindid;
	}

	@Column(name = "PARENTID", length = 16)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "GOODSID", length = 3)
	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "kind", nullable = false, length = 1)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "SEARCHCODE", length = 20)
	public String getSearchcode() {
		return this.searchcode;
	}

	public void setSearchcode(String searchcode) {
		this.searchcode = searchcode;
	}

	@Column(name = "LISCODE", length = 18)
	public String getLiscode() {
		return this.liscode;
	}

	public void setLiscode(String liscode) {
		this.liscode = liscode;
	}

	@Column(name = "SINOCODE", length = 16)
	public String getSinocode() {
		return this.sinocode;
	}

	public void setSinocode(String sinocode) {
		this.sinocode = sinocode;
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
	public String getIsactiveDIC() {
		if (isactive.equals("1")) {
			return "启用";
		} else {
			return "停用";
		}
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

	@Transient
	public String getKindDIC() {
		if (kind.equals("1")) {
			return "统销化工产品";
		} else if (kind.equals("0")) {
			return "非统销化工产品";
		} else if (kind.equals("3")) {
			return "成品油";
		} else if (kind.equals("4")) {
			return "煤";
		} else if (kind.equals("5")) {
			return "原油";
		} else if (kind.equals("6")) {
			return "沥青";
		} else {
			return "其它";
		}
	}

	public void setKindDIC(String kindDIC) {
		this.kindDIC = kindDIC;
	}

	@Transient
	public String getGoodsidDIC() {
		return goodsidDIC;
	}

	public void setGoodsidDIC(String goodsidDIC) {
		this.goodsidDIC = goodsidDIC;
	}

	@Transient
	public String getFullnameTree() {
		return fullnameTree;
	}

	public void setFullnameTree(String fullnameTree) {
		this.fullnameTree = fullnameTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortname;
	}

	@Column(name = "RWKINDID", length = 7)
	public String getRwkindid() {
		return rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Transient
	public String getCategoryidDIC() {
		return categoryidDIC;
	}

	public void setCategoryidDIC(String categoryidDIC) {
		this.categoryidDIC = categoryidDIC;
	}

	@Transient
	public String getSecondkindidDIC() {
		return secondkindidDIC;
	}

	public void setSecondkindidDIC(String secondkindidDIC) {
		this.secondkindidDIC = secondkindidDIC;
	}

	@Transient
	public String getParentidDIC() {
		return parentidDIC;
	}

	public void setParentidDIC(String parentidDIC) {
		this.parentidDIC = parentidDIC;
	}

	@Transient
	public String getRwkindidDIC() {
		return rwkindidDIC;
	}

	public void setRwkindidDIC(String rwkindidDIC) {
		this.rwkindidDIC = rwkindidDIC;
	}

}