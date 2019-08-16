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
 * DicSinodepartment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_SINODEPARTMENT" )
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DicSinodepartment implements java.io.Serializable {

	// Fields

	private String sinodepaid;
	private String fullname;
	private String shortname;
	private String address;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private String issalecorp;
	private String isreceiver;
	private String issender;
	private String parentid;
	private String isactive;
	private String description;
	private Date createdtime;

	private String issalecorpDIC;
	private String isreceiverDIC;
	private String issenderDIC;
	private String parentidDIC;
	private String isactiveDIC;

	/** default constructor */
	public DicSinodepartment() {
	}

	/** minimal constructor */
	public DicSinodepartment(String sinodepaid, String fullname,
			String shortname, String issalecorp, String isreceiver,
			String issender, String parentid, String isactive) {
		this.sinodepaid = sinodepaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.issalecorp = issalecorp;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.parentid = parentid;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicSinodepartment(String sinodepaid, String fullname,
			String shortname, String address, String dutyperson,
			String telephones, String fax, String contactperson,
			String issalecorp, String isreceiver, String issender,
			String parentid, String isactive, String description,
			Date createdtime) {
		this.sinodepaid = sinodepaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.issalecorp = issalecorp;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.parentid = parentid;
		this.isactive = isactive;
		this.description = description;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "SINODEPAID", unique = true, nullable = false, length = 6)
	public String getSinodepaid() {
		return this.sinodepaid;
	}

	public void setSinodepaid(String sinodepaid) {
		this.sinodepaid = sinodepaid;
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

	@Column(name = "ISSALECORP", nullable = false, length = 1)
	public String getIssalecorp() {
		return this.issalecorp;
	}

	public void setIssalecorp(String issalecorp) {
		this.issalecorp = issalecorp;
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

	@Column(name = "PARENTID", nullable = false, length = 8)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
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

	@Transient
	public String getIssalecorpDIC() {
		return issalecorpDIC;
	}

	public void setIssalecorpDIC(String issalecorpDIC) {
		this.issalecorpDIC = issalecorpDIC;
	}

	@Transient
	public String getIsreceiverDIC() {
		return isreceiverDIC;
	}

	public void setIsreceiverDIC(String isreceiverDIC) {
		this.isreceiverDIC = isreceiverDIC;
	}

	@Transient
	public String getIssenderDIC() {
		return issenderDIC;
	}

	public void setIssenderDIC(String issenderDIC) {
		this.issenderDIC = issenderDIC;
	}

	@Transient
	public String getParentidDIC() {
		return parentidDIC;
	}

	public void setParentidDIC(String parentidDIC) {
		this.parentidDIC = parentidDIC;
	}

	@Transient
	public String getIsactiveDIC() {
		return isactiveDIC;
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

	@Override
	public String toString() {
		return shortname;
	}
	
	

}