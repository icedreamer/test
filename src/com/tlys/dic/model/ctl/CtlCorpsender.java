package com.tlys.dic.model.ctl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * TbZbcCtlCorpsender entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPSENDER",  uniqueConstraints = @UniqueConstraint(columnNames = {
		"CORPID", "SENDERID" }))
public class CtlCorpsender implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762656548495506771L;
	private Long id;
	private String corpid;
	private String corpshortname;
	private String senderid;
	private String sendername;
	private String sendershortname;
	private String isactive;
	private Date createdtime;
	private String description;

	// Constructors

	/** default constructor */
	public CtlCorpsender() {
	}

	/** minimal constructor */
	public CtlCorpsender(Long id, String corpid, String sendershortname) {
		this.id = id;
		this.corpid = corpid;
		this.sendershortname = sendershortname;
	}

	/** full constructor */
	public CtlCorpsender(Long id, String corpid, String corpshortname, String senderid, String sendername,
			String sendershortname, String isactive, Date createdtime, String description) {
		this.id = id;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.senderid = senderid;
		this.sendername = sendername;
		this.sendershortname = sendershortname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_CTL_CORPSENDER")
	@GenericGenerator(name = "SEQ_TB_ZBC_CTL_CORPSENDER", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_CTL_CORPSENDER") })
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPSHORTNAME", length = 30)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
	}

	@Column(name = "SENDERID", length = 8)
	public String getSenderid() {
		return this.senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	@Column(name = "SENDERNAME", length = 100)
	public String getSendername() {
		return this.sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	@Column(name = "SENDERSHORTNAME", nullable = false, length = 30)
	public String getSendershortname() {
		return this.sendershortname;
	}

	public void setSendershortname(String sendershortname) {
		this.sendershortname = sendershortname;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Temporal(TemporalType.DATE)
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

	@Override
	public String toString() {
		return sendername;
	}

}