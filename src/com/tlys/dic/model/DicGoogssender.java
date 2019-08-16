package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DicGoogssender entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_DIC_GOOGSSENDER",schema="HXZBC")
public class DicGoogssender implements java.io.Serializable {

	// Fields

	private String id;
	private String shortname;
	private String dutyperson;
	private String address;
	private String telephones;
	private String fax;
	private String description;
	private Date createdtime;
	private String sourcetab;
	private String dataid;

	// Constructors

	/** default constructor */
	public DicGoogssender() {
	}

	/** full constructor */
	public DicGoogssender(String id, String shortname, String dutyperson, String address, String telephones, String fax, String description,
			Date createdtime, String sourcetab, String dataid) {
		this.id = id;
		this.shortname = shortname;
		this.dutyperson = dutyperson;
		this.address = address;
		this.telephones = telephones;
		this.fax = fax;
		this.description = description;
		this.createdtime = createdtime;
		this.sourcetab = sourcetab;
		this.dataid = dataid;
	}

	// Property accessors

	@Column(name = "ID", length = 8)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SHORTNAME", length = 30)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "DUTYPERSON", length = 40)
	public String getDutyperson() {
		return this.dutyperson;
	}

	public void setDutyperson(String dutyperson) {
		this.dutyperson = dutyperson;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Column(name = "SOURCETAB", length = 3)
	public String getSourcetab() {
		return this.sourcetab;
	}

	public void setSourcetab(String sourcetab) {
		this.sourcetab = sourcetab;
	}

	@Id
	@Column(name = "DATAID", length = 12)
	public String getDataid() {
		return this.dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

}