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
 * DicAreacorp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_AREACORP" )
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DicAreacorp implements java.io.Serializable {

	// Fields

	private String areaid;
	private String fullname;
	private String shortname;
	private String shrinkname;
	private String address;
	private String provinceid;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private Long orderno;
	private String isactive;
	private String description;
	private Date createdtime;

	private String isactiveStr;

	private String provinceidDIC;

	// Constructors

	/** default constructor */
	public DicAreacorp() {
	}

	/** minimal constructor */
	public DicAreacorp(String areaid, String fullname, String shortname,
			String shrinkname, String provinceid, String isactive) {
		this.areaid = areaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.provinceid = provinceid;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicAreacorp(String areaid, String fullname, String shortname,
			String shrinkname, String address, String provinceid,
			String dutyperson, String telephones, String fax,
			String contactperson, Long orderno, String isactive,
			String description, Date createdtime) {
		this.areaid = areaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.address = address;
		this.provinceid = provinceid;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.orderno = orderno;
		this.isactive = isactive;
		this.description = description;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "AREAID", unique = true, nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
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

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PROVINCEID", nullable = false, length = 6)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
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

	@Column(name = "ORDERNO", precision = 22, scale = 0)
	public Long getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Long orderno) {
		this.orderno = orderno;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
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

	/**
	 * @return the isactiveStr
	 */
	@Transient
	public String getIsactiveStr() {
		if (isactive.equals("1")) {
			return "∆Ù”√";
		} else {
			return "Õ£”√";
		}
	}

	/**
	 * @param isactiveStr
	 *            the isactiveStr to set
	 */
	public void setIsactiveStr(String isactiveStr) {
		this.isactiveStr = isactiveStr;
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

	@Transient
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

	public void setProvinceidDIC(String provinceidDIC) {
		this.provinceidDIC = provinceidDIC;
	}

}