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
 * DicTransporter entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_TRANSPORTER" )
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DicTransporter implements java.io.Serializable {

	// Fields

	private String transportid;
	private String fullname;
	private String shortname;
	private String shrinkname;
	private String provinceid;
	private String address;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private String isactive;
	private String isbelongsino;
	private String description;
	private Date createdtime;

	private String isactiveStr;
	private String isbelongsinoStr;

	/**
	 * 省份名称，不参与持久化
	 */
	private String provinceidDIC;

	// Constructors

	/** default constructor */
	public DicTransporter() {
	}

	/** minimal constructor */
	public DicTransporter(String fullname, String shortname, String shrinkname,
			String provinceid, String isactive, String isbelongsino) {
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.provinceid = provinceid;
		this.isactive = isactive;
		this.isbelongsino = isbelongsino;
	}

	/** full constructor */
	public DicTransporter(String fullname, String shortname, String shrinkname,
			String provinceid, String address, String dutyperson,
			String telephones, String fax, String contactperson,
			String isactive, String isbelongsino, String description,
			Date createdtime) {
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.provinceid = provinceid;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.isactive = isactive;
		this.isbelongsino = isbelongsino;
		this.description = description;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "TRANSPORTID", unique = true, nullable = false, length = 8)
	public String getTransportid() {
		return this.transportid;
	}

	public void setTransportid(String transportid) {
		this.transportid = transportid;
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

	@Column(name = "SHRINKNAME", nullable = false, length = 20)
	public String getShrinkname() {
		return this.shrinkname;
	}

	public void setShrinkname(String shrinkname) {
		this.shrinkname = shrinkname;
	}

	@Column(name = "PROVINCEID", nullable = false, length = 6)
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
	 * @return the isbelongsinoStr
	 */
	@Transient
	public String getIsbelongsinoStr() {
		return isbelongsinoStr;
	}

	/**
	 * @param isbelongsinoStr
	 *            the isbelongsinoStr to set
	 */
	public void setIsbelongsinoStr(String isbelongsinoStr) {
		this.isbelongsinoStr = isbelongsinoStr;
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
	 * @return the provinceidDIC
	 */
	@Transient
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

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
		return shortname;
	}

}