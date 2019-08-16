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
 * TbZbcCtlCorpreceiver entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPRECEIVER",  uniqueConstraints = @UniqueConstraint(columnNames = {
		"CORPID", "RECEIVERID" }))
public class CtlCorpreceiver implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1749411208952321759L;
	private Long id;
	private String corpid;
	private String corpshortname;
	private String receiverid;
	private String receivername;
	private String receivershortname;
	private String isactive;
	private Date createdtime;
	private String description;

	// Constructors

	/** default constructor */
	public CtlCorpreceiver() {
	}

	/** minimal constructor */
	public CtlCorpreceiver(Long id, String corpid, String receivershortname) {
		this.id = id;
		this.corpid = corpid;
		this.receivershortname = receivershortname;
	}

	/** full constructor */
	public CtlCorpreceiver(Long id, String corpid, String corpshortname, String receiverid, String receivername,
			String receivershortname, String isactive, Date createdtime, String description) {
		this.id = id;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.receiverid = receiverid;
		this.receivername = receivername;
		this.receivershortname = receivershortname;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_CTL_CORPRECEIVER")
	@GenericGenerator(name = "SEQ_TB_ZBC_CTL_CORPRECEIVER", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_CTL_CORPRECEIVER") })
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

	@Column(name = "CORPSHORTNAME", length = 100)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
	}

	@Column(name = "RECEIVERID", length = 8)
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "RECEIVERNAME", length = 100)
	public String getReceivername() {
		return this.receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	@Column(name = "RECEIVERSHORTNAME", nullable = false, length = 30)
	public String getReceivershortname() {
		return this.receivershortname;
	}

	public void setReceivershortname(String receivershortname) {
		this.receivershortname = receivershortname;
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
		return this.receivername;
	}

}