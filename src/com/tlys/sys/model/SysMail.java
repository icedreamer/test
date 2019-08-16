package com.tlys.sys.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * SysMail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_MAIL")
public class SysMail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4126836813947972666L;
	private long mailid;
	private String subject;
	private SysUser sysUser;
	private String toid;
	private String ccid;
	private String body;
	private long mailsize;
	private String mailsizeDes;
	private Date mailtime;
	private String isreply;
	private long oldmailid;

	// private SysMailfolder sysMailfolder;

	// Constructors

	/** default constructor */
	public SysMail() {
	}

	/** minimal constructor */
	public SysMail(long mailid, String subject, Date mailtime, String isreply) {
		this.mailid = mailid;
		this.subject = subject;
		this.mailtime = mailtime;
		this.isreply = isreply;
	}

	/** full constructor */
	public SysMail(long mailid, String subject, String toid, String ccid, String body,
			long mailsize, Date mailtime, String isreply, long oldmailid) {
		this.mailid = mailid;
		this.subject = subject;
		this.toid = toid;
		this.ccid = ccid;
		this.body = body;
		this.mailsize = mailsize;
		this.mailtime = mailtime;
		this.isreply = isreply;
		this.oldmailid = oldmailid;
	}

	// Property accessors
	@Id
	@Column(name = "MAILID", unique = true, nullable = false, precision = 6, scale = 0)
	public long getMailid() {
		return this.mailid;
	}

	public void setMailid(long mailid) {
		this.mailid = mailid;
	}

	@Column(name = "SUBJECT", nullable = false, length = 120)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	// @Column(name = "FROMID", nullable = false, length = 6)
	// public String getFromid() {
	// return this.fromid;
	// }
	//
	// public void setFromid(String fromid) {
	// this.fromid = fromid;
	// }

	@Column(name = "TOID", length = 240)
	public String getToid() {
		return this.toid;
	}

	public void setToid(String toid) {
		this.toid = toid;
	}

	@Column(name = "CCID", length = 240)
	public String getCcid() {
		return this.ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	@Column(name = "BODY")
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name = "MAILSIZE", precision = 22, scale = 0)
	public long getMailsize() {
		return this.mailsize;
	}

	public void setMailsize(long mailsize) {
		this.mailsize = mailsize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MAILTIME", nullable = false, length = 7)
	public Date getMailtime() {
		return this.mailtime;
	}

	public void setMailtime(Date mailtime) {
		this.mailtime = mailtime;
	}

	@Column(name = "ISREPLY", nullable = false, length = 1)
	public String getIsreply() {
		return this.isreply;
	}

	public void setIsreply(String isreply) {
		this.isreply = isreply;
	}

	@Column(name = "OLDMAILID", precision = 6, scale = 0)
	public long getOldmailid() {
		return this.oldmailid;
	}

	public void setOldmailid(long oldmailid) {
		this.oldmailid = oldmailid;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = SysUser.class)
	@JoinColumn(name = "fromid")
	@NotFound(action = NotFoundAction.IGNORE)
	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Transient
	public String getMailsizeDes() {

		if (mailsize / (1024 * 1024) > 1) {
			mailsizeDes = mailsize / (1024 * 1024) + " MB";
		} else {
			if (mailsize / 1024 > 1) {
				mailsizeDes = mailsize / 1024 + " KB";
			} else {
				if (mailsize > 1) {
					mailsizeDes = mailsize + " B";
				}
			}
		}

		return mailsizeDes;
	}

	public void setMailsizeDes(String mailsizeDes) {
		this.mailsizeDes = mailsizeDes;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = SysMailfolder.class)
	// @JoinColumn(name = "mailid", referencedColumnName = "mailid")
	// @NotFound(action = NotFoundAction.IGNORE)
	// public SysMailfolder getSysMailfolder() {
	// return sysMailfolder;
	// }
	//
	// public void setSysMailfolder(SysMailfolder sysMailfolder) {
	// this.sysMailfolder = sysMailfolder;
	// }

}