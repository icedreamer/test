package com.tlys.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * TbZbcSysMessage entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_MESSAGE")
public class SysMessage implements java.io.Serializable {

	// Fields

	private Long id;
	private String msgtypeid;
	private String senderid;
	private String receiverid;
	private String status;
	private String msgaction;
	private Date sendtime;
	private Date readtime;
	private String msgcontent;
	private String url;
	private Date startDate;
	private Date endDate;

	private String[] receiverids;
	private String[] senderids;

	// Constructors

	/** default constructor */
	public SysMessage() {
	}

	/** minimal constructor */
	public SysMessage(Long id, String msgtypeid, String senderid, String receiverid, String status, Date sendtime,
			String msgcontent, String url) {
		this.id = id;
		this.msgtypeid = msgtypeid;
		this.senderid = senderid;
		this.receiverid = receiverid;
		this.status = status;
		this.sendtime = sendtime;
		this.msgcontent = msgcontent;
		this.url = url;
	}

	/** full constructor */
	public SysMessage(Long id, String msgtypeid, String senderid, String receiverid, String status, String msgaction,
			Date sendtime, Date readtime, String msgcontent, String url) {
		this.id = id;
		this.msgtypeid = msgtypeid;
		this.senderid = senderid;
		this.receiverid = receiverid;
		this.status = status;
		this.msgaction = msgaction;
		this.sendtime = sendtime;
		this.readtime = readtime;
		this.msgcontent = msgcontent;
		this.url = url;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_MESSAGE")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_MESSAGE", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_MESSAGE") })
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MSGTYPEID", nullable = false, length = 3)
	public String getMsgtypeid() {
		return this.msgtypeid;
	}

	public void setMsgtypeid(String msgtypeid) {
		this.msgtypeid = msgtypeid;
	}

	@Column(name = "SENDERID", nullable = false, length = 6)
	public String getSenderid() {
		return this.senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	@Column(name = "RECEIVERID", nullable = false, length = 6)
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "MSGACTION", length = 30)
	public String getMsgaction() {
		return this.msgaction;
	}

	public void setMsgaction(String msgaction) {
		this.msgaction = msgaction;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SENDTIME", nullable = false, length = 7)
	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "READTIME", length = 7)
	public Date getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	@Column(name = "MSGCONTENT", nullable = false, length = 100)
	public String getMsgcontent() {
		return this.msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}

	@Column(name = "URL", nullable = false, length = 30)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Transient
	public Date getStartDate() {
		return startDate;
	}

	@Transient
	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String[] getReceiverids() {
		return receiverids;
	}

	public void setReceiverids(String[] receiverids) {
		this.receiverids = receiverids;
	}

	@Transient
	public String[] getSenderids() {
		return senderids;
	}

	public void setSenderids(String[] senderids) {
		this.senderids = senderids;
	}

}