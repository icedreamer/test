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
 * DicRwdepartment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_RWDEPARTMENT" )
public class DicRwdepartment implements java.io.Serializable {

	// Fields

	private String rwdepaid;
	private String fullname;
	private String shortname;
	private String shrinkname;
	private String bureauid;
	private String tpyeid;
	private String parentid;
	private String provinceid;
	private String address;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private String isactive;
	private String telegramid;
	private String hasaei;
	private String israilstn;
	private String timscode;
	private String isboundary;
	private String nextbureauid;
	private String inflag;
	private String outflag;
	private String raillinename;
	private String pycode;
	private String description;
	private Date createdtime;

	// ∑≠“Î
	private String bureauidDIC;
	private String tpyeidDIC;
	private String parentidDIC;
	private String provinceidDIC;
	private String isactiveDIC;
	private String hasaeiDIC;
	private String israilstnDIC;
	private String isboundaryDIC;
	private String nextbureauidDIC;
	private String inflagDIC;
	private String outflagDIC;

	// Constructors

	/** default constructor */
	public DicRwdepartment() {
	}

	/** minimal constructor */
	public DicRwdepartment(String rwdepaid, String fullname, String shortname,
			String shrinkname, String bureauid, String tpyeid, String parentid,
			String provinceid, String isactive, String hasaei, String israilstn) {
		this.rwdepaid = rwdepaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.bureauid = bureauid;
		this.tpyeid = tpyeid;
		this.parentid = parentid;
		this.provinceid = provinceid;
		this.isactive = isactive;
		this.hasaei = hasaei;
		this.israilstn = israilstn;
	}

	/** full constructor */
	public DicRwdepartment(String rwdepaid, String fullname, String shortname,
			String shrinkname, String bureauid, String tpyeid, String parentid,
			String provinceid, String address, String dutyperson,
			String telephones, String fax, String contactperson,
			String isactive, String telegramid, String hasaei,
			String israilstn, String timscode, String description,
			Date createdtime, String isboundary, String nextbureauid,
			String inflag, String outflag, String raillinename, String pycode) {
		this.rwdepaid = rwdepaid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.bureauid = bureauid;
		this.tpyeid = tpyeid;
		this.parentid = parentid;
		this.provinceid = provinceid;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.isactive = isactive;
		this.telegramid = telegramid;
		this.hasaei = hasaei;
		this.israilstn = israilstn;
		this.timscode = timscode;
		this.description = description;
		this.createdtime = createdtime;
		this.isboundary = isboundary;
		this.nextbureauid = nextbureauid;
		this.inflag = inflag;
		this.isboundary = isboundary;
		this.outflag = outflag;
		this.raillinename = raillinename;
		this.pycode = pycode;
	}

	// Property accessors
	@Id
	@Column(name = "RWDEPAID", unique = true, nullable = false, length = 6)
	public String getRwdepaid() {
		return this.rwdepaid;
	}

	public void setRwdepaid(String rwdepaid) {
		this.rwdepaid = rwdepaid;
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

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Column(name = "TPYEID", nullable = false, length = 2)
	public String getTpyeid() {
		return this.tpyeid;
	}

	public void setTpyeid(String tpyeid) {
		this.tpyeid = tpyeid;
	}

	@Column(name = "PARENTID", nullable = false, length = 6)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
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

	@Column(name = "TELEGRAMID", length = 3)
	public String getTelegramid() {
		return this.telegramid;
	}

	public void setTelegramid(String telegramid) {
		this.telegramid = telegramid;
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

	@Column(name = "HASAEI", length = 1)
	public String getHasaei() {
		return hasaei;
	}

	public void setHasaei(String hasaei) {
		this.hasaei = hasaei;
	}

	@Column(name = "ISRAILSTN", length = 1)
	public String getIsrailstn() {
		return israilstn;
	}

	public void setIsrailstn(String israilstn) {
		this.israilstn = israilstn;
	}

	@Column(name = "TIMSCODE", length = 5)
	public String getTimscode() {
		return timscode;
	}

	public void setTimscode(String timscode) {
		this.timscode = timscode;
	}

	@Transient
	public String getBureauidDIC() {
		return bureauidDIC;
	}

	public void setBureauidDIC(String bureauidDIC) {
		this.bureauidDIC = bureauidDIC;
	}

	@Transient
	public String getTpyeidDIC() {
		return tpyeidDIC;
	}

	public void setTpyeidDIC(String tpyeidDIC) {
		this.tpyeidDIC = tpyeidDIC;
	}

	@Column(name = "ISBOUNDARY", length = 1)
	public String getIsboundary() {
		return isboundary;
	}

	public void setIsboundary(String isboundary) {
		this.isboundary = isboundary;
	}

	@Column(name = "NEXTBUREAUID", length = 3)
	public String getNextbureauid() {
		return nextbureauid;
	}

	public void setNextbureauid(String nextbureauid) {
		this.nextbureauid = nextbureauid;
	}

	@Column(name = "INFLAG", length = 1)
	public String getInflag() {
		return inflag;
	}

	public void setInflag(String inflag) {
		this.inflag = inflag;
	}

	@Column(name = "OUTFLAG", length = 1)
	public String getOutflag() {
		return outflag;
	}

	public void setOutflag(String outflag) {
		this.outflag = outflag;
	}

	@Column(name = "RAILLINENAME", length = 20)
	public String getRaillinename() {
		return raillinename;
	}

	public void setRaillinename(String raillinename) {
		this.raillinename = raillinename;
	}

	@Column(name = "PYCODE", length = 10)
	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	@Transient
	public String getParentidDIC() {
		return parentidDIC;
	}

	public void setParentidDIC(String parentidDIC) {
		this.parentidDIC = parentidDIC;
	}

	@Transient
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

	public void setProvinceidDIC(String provinceidDIC) {
		this.provinceidDIC = provinceidDIC;
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
	public String getHasaeiDIC() {
		return hasaeiDIC;
	}

	public void setHasaeiDIC(String hasaeiDIC) {
		this.hasaeiDIC = hasaeiDIC;
	}

	@Transient
	public String getIsrailstnDIC() {
		return israilstnDIC;
	}

	public void setIsrailstnDIC(String israilstnDIC) {
		this.israilstnDIC = israilstnDIC;
	}

	@Transient
	public String getIsboundaryDIC() {
		return isboundaryDIC;
	}

	public void setIsboundaryDIC(String isboundaryDIC) {
		this.isboundaryDIC = isboundaryDIC;
	}

	@Transient
	public String getNextbureauidDIC() {
		return nextbureauidDIC;
	}

	public void setNextbureauidDIC(String nextbureauidDIC) {
		this.nextbureauidDIC = nextbureauidDIC;
	}

	@Transient
	public String getInflagDIC() {
		return inflagDIC;
	}

	public void setInflagDIC(String inflagDIC) {
		this.inflagDIC = inflagDIC;
	}

	@Transient
	public String getOutflagDIC() {
		return outflagDIC;
	}

	public void setOutflagDIC(String outflagDIC) {
		this.outflagDIC = outflagDIC;
	}

}