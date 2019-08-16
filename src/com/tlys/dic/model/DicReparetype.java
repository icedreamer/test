package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TbZbcDicReparetype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_REPARETYPE")
public class DicReparetype implements java.io.Serializable {

	private String rtypeid;
	private String rtypename;
	private String suitkind;
	private String isactive;
	private Date createdtime;
	private String description;
	private String suitkindDIC;
	private String isactiveDIC;

	// Constructors

	/** default constructor */
	public DicReparetype() {
	}

	/** minimal constructor */
	public DicReparetype(String rtypeid, String rtypename, String suitkind,
			String isactive) {
		this.rtypeid = rtypeid;
		this.rtypename = rtypename;
		this.suitkind = suitkind;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicReparetype(String rtypeid, String rtypename, String suitkind,
			String isactive, Date createdtime, String description) {
		this.rtypeid = rtypeid;
		this.rtypename = rtypename;
		this.suitkind = suitkind;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "RTYPEID", unique = true, nullable = false, length = 2)
	public String getRtypeid() {
		return this.rtypeid;
	}

	public void setRtypeid(String rtypeid) {
		this.rtypeid = rtypeid;
	}

	@Column(name = "RTYPENAME", nullable = false, length = 20)
	public String getRtypename() {
		return this.rtypename;
	}

	public void setRtypename(String rtypename) {
		this.rtypename = rtypename;
	}

	@Column(name = "SUITKIND", nullable = false, length = 1)
	public String getSuitkind() {
		return this.suitkind;
	}

	public void setSuitkind(String suitkind) {
		this.suitkind = suitkind;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

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
	public String getSuitkindDIC() {
		return suitkindDIC;
	}

	public void setSuitkindDIC(String suitkindDIC) {
		this.suitkindDIC = suitkindDIC;
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

}