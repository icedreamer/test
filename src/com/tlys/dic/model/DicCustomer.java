package com.tlys.dic.model;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DicCustomer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CUSTOMER" )
public class DicCustomer implements java.io.Serializable {

	// Fields

	private String customerid;
	private String fullname;
	private String shortname;
	private String shrinkname;
	private String provinceid;
	private String address;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private String isreceiver;
	private String issender;
	private String isactive;
	private String isbelongsino;
	private String pycode;
	private String description;
	private Date createdtime;

	private String issenderStr;
	private String isactiveStr;
	private String isbelongsinoStr;
	private String isreceiverStr;

	private String provinceidDIC;

	// Constructors

	/** default constructor */
	public DicCustomer() {
	}

	/** minimal constructor */
	public DicCustomer(String customerid, String fullname, String shortname,
			String shrinkname, String provinceid, String isreceiver,
			String issender, String isactive, String isbelongsino) {
		this.customerid = customerid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.provinceid = provinceid;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.isactive = isactive;
		this.isbelongsino = isbelongsino;
	}

	/** full constructor */
	public DicCustomer(String customerid, String fullname, String shortname,
			String shrinkname, String provinceid, String address,
			String dutyperson, String telephones, String fax,
			String contactperson, String isreceiver, String issender,
			String isactive, String isbelongsino, String description,
			Date createdtime) {
		this.customerid = customerid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.provinceid = provinceid;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.isactive = isactive;
		this.isbelongsino = isbelongsino;
		this.description = description;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "CUSTOMERID", unique = true, nullable = false, length = 8)
	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
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

	@Column(name = "SHRINKNAME", length = 20)
	public String getShrinkname() {
		return this.shrinkname;
	}

	public void setShrinkname(String shrinkname) {
		this.shrinkname = shrinkname;
	}

	@Column(name = "PROVINCEID", length = 6)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "DUTYPERSON", length = 40)
	public String getDutyperson() {
		return this.dutyperson;
	}

	public void setDutyperson(String dutyperson) {
		this.dutyperson = dutyperson;
	}

	@Column(name = "TELEPHONES", length = 60)
	public String getTelephones() {
		return this.telephones;
	}

	public void setTelephones(String telephones) {
		this.telephones = telephones;
	}

	@Column(name = "FAX", length = 60)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "CONTACTPERSON", length = 40)
	public String getContactperson() {
		return this.contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	@Column(name = "ISRECEIVER", nullable = false, length = 1)
	public String getIsreceiver() {
		return this.isreceiver;
	}

	public void setIsreceiver(String isreceiver) {
		this.isreceiver = isreceiver;
	}

	@Column(name = "ISSENDER", nullable = false, length = 1)
	public String getIssender() {
		return this.issender;
	}

	public void setIssender(String issender) {
		this.issender = issender;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "ISBELONGSINO", nullable = false, length = 1)
	public String getIsbelongsino() {
		return this.isbelongsino;
	}

	public void setIsbelongsino(String isbelongsino) {
		this.isbelongsino = isbelongsino;
	}

	@Column(name = "PYCODE", nullable = true, length = 20)
	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
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

	/**
	 * @return the issenderStr
	 */
	@Transient
	public String getIssenderStr() {
		if (issender.equals("1")) {
			return "是";
		} else {
			return "否";
		}
	}

	/**
	 * @param issenderStr
	 *            the issenderStr to set
	 */
	public void setIssenderStr(String issenderStr) {
		this.issenderStr = issenderStr;
	}

	/**
	 * @return the isactiveStr
	 */
	@Transient
	public String getIsactiveStr() {
		if (isactive.equals("1")) {
			return "启用";
		} else {
			return "停用";
		}
	}

	/**
	 * @param isactiveStr
	 *            the isactiveStr to set
	 */
	public void setIsactiveStr(String isactiveStr) {
		this.isactiveStr = isactiveStr;
	}

	/**
	 * @return the isbelongsinoStr
	 */
	@Transient
	public String getIsbelongsinoStr() {
		if (isbelongsino.equals("1")) {
			return "是";
		} else {
			return "否";
		}
	}

	/**
	 * @param isbelongsinoStr
	 *            the isbelongsinoStr to set
	 */
	public void setIsbelongsinoStr(String isbelongsinoStr) {
		this.isbelongsinoStr = isbelongsinoStr;
	}

	/**
	 * @return the isreceiverStr
	 */
	@Transient
	public String getIsreceiverStr() {
		if (isreceiver.equals("1")) {
			return "是";
		} else {
			return "否";
		}
	}

	/**
	 * @param isreceiverStr
	 *            the isreceiverStr to set
	 */
	public void setIsreceiverStr(String isreceiverStr) {
		this.isreceiverStr = isreceiverStr;
	}

	/**
	 * @return the provinceidDIC
	 */
	@Transient
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

	/**
	 * @param provinceidDIC
	 *            the provinceidDIC to set
	 */
	public void setProvinceidDIC(String provinceidDIC) {
		this.provinceidDIC = provinceidDIC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fullname;
	}

}