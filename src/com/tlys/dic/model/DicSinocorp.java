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
 * DicSinocorp entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_SINOCORP" )
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DicSinocorp implements java.io.Serializable {

	// Fields

	private String corpid;
	private String fullname;
	private String shortname;
	private String shrinkname;
	private String address;
	private String dutyperson;
	private String telephones;
	private String fax;
	private String contactperson;
	private String issalecorp;
	private String isreceiver;
	private String issender;
	private String provinceid;
	private String areaid;
	private Long inareaorder;
	private String bureauid;
	private String corptype;
	
	private String rwcode;
	private String isactive;
	private String description;
	private Date createdtime;

	private String issalecorpDIC;
	private String isreceiverDIC;
	private String issenderDIC;
	private String provinceidDIC;
	private String areaidDIC;
	private String isactiveDIC;
	private String bureauidDIC;
	/**
	 * 通用标志，不持久化
	 */
	private String flag;
	
	/**
	 * 省份查询id列表
	 */
	private String provids;

	// Constructors

	/** default constructor */
	public DicSinocorp() {
	}

	/** minimal constructor */
	public DicSinocorp(String corpid, String fullname, String shortname,
			String shrinkname, String issalecorp, String isreceiver,
			String issender, String provinceid, String areaid, String isactive) {
		this.corpid = corpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.issalecorp = issalecorp;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.provinceid = provinceid;
		this.areaid = areaid;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicSinocorp(String corpid, String fullname, String shortname,
			String shrinkname, String address, String dutyperson,
			String telephones, String fax, String contactperson,
			String issalecorp, String isreceiver, String issender,
			String provinceid, String areaid, Long inareaorder,
			String isactive, String description, Date createdtime,
			String bureauid, String rwcode) {
		this.corpid = corpid;
		this.fullname = fullname;
		this.shortname = shortname;
		this.shrinkname = shrinkname;
		this.address = address;
		this.dutyperson = dutyperson;
		this.telephones = telephones;
		this.fax = fax;
		this.contactperson = contactperson;
		this.issalecorp = issalecorp;
		this.isreceiver = isreceiver;
		this.issender = issender;
		this.provinceid = provinceid;
		this.areaid = areaid;
		this.inareaorder = inareaorder;
		this.isactive = isactive;
		this.description = description;
		this.createdtime = createdtime;
		this.bureauid = bureauid;
		this.rwcode = rwcode;
	}

	// Property accessors
	@Id
	@Column(name = "CORPID", unique = true, nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
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

	@Column(name = "PROVINCEID", nullable = false, length = 6)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "INAREAORDER", precision = 22, scale = 0)
	public Long getInareaorder() {
		return this.inareaorder;
	}

	public void setInareaorder(Long inareaorder) {
		this.inareaorder = inareaorder;
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

	@Column(name = "BUREAUID", length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Transient
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return shortname;
	}

	@Column(name = "RWCODE", length = 7)
	public String getRwcode() {
		return rwcode;
	}

	public void setRwcode(String rwcode) {
		this.rwcode = rwcode;
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
	public String getProvinceidDIC() {
		return provinceidDIC;
	}

	public void setProvinceidDIC(String provinceidDIC) {
		this.provinceidDIC = provinceidDIC;
	}

	@Transient
	public String getAreaidDIC() {
		return areaidDIC;
	}

	public void setAreaidDIC(String areaidDIC) {
		this.areaidDIC = areaidDIC;
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
	public String getBureauidDIC() {
		return bureauidDIC;
	}

	public void setBureauidDIC(String bureauidDIC) {
		this.bureauidDIC = bureauidDIC;
	}
	
	@Column(name = "CORPTYPE", length = 1)
	public String getCorptype() {
		return corptype;
	}

	public void setCorptype(String corptype) {
		this.corptype = corptype;
	}

	@Transient
	public String getProvids() {
		return provids;
	}

	public void setProvids(String provids) {
		this.provids = provids;
	}

}