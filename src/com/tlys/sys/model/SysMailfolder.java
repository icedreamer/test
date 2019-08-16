package com.tlys.sys.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Parameter;

/**
 * SysMailfolder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_MAILFOLDER")
public class SysMailfolder implements java.io.Serializable {

	// Fields

	private long id;
	private SysUser sysUser;
	// private String ownerid;
	// private long mailid;
	private SysMail sysMail;
	private String folder;
	private String foldername;
	private String readflag;
	private Date readtime;
	private String ids;

	// Constructors

	/** default constructor */
	public SysMailfolder() {
	}

	/** minimal constructor */
	public SysMailfolder(long id, String folder, String readflag) {
		this.id = id;
		this.folder = folder;
		this.readflag = readflag;
	}

	/** full constructor */
	public SysMailfolder(long id, String folder, String readflag, Date readtime) {
		this.id = id;
		this.folder = folder;
		this.readflag = readflag;
		this.readtime = readtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_MAILFOLDER")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_MAILFOLDER", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_TB_ZBC_SYS_MAILFOLDER") })
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// @Column(name = "OWNERID", nullable = false, length = 6)
	// public String getOwnerid() {
	// return this.ownerid;
	// }
	//
	// public void setOwnerid(String ownerid) {
	// this.ownerid = ownerid;
	// }

	// @Column(name = "MAILID", nullable = false, precision = 6, scale = 0)
	// public long getMailid() {
	// return this.mailid;
	// }
	//
	// public void setMailid(long mailid) {
	// this.mailid = mailid;
	// }

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = SysUser.class)
	@JoinColumn(name = "ownerid")
	@NotFound(action = NotFoundAction.IGNORE)
	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "FOLDER", nullable = false, length = 7)
	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	@Column(name = "READFLAG", nullable = false, length = 1)
	public String getReadflag() {
		return this.readflag;
	}

	public void setReadflag(String readflag) {
		this.readflag = readflag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READTIME", length = 7)
	public Date getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = SysMail.class)
	@JoinColumn(name = "mailid", referencedColumnName = "mailid")
	@NotFound(action = NotFoundAction.IGNORE)
	public SysMail getSysMail() {
		return sysMail;
	}

	public void setSysMail(SysMail sysMail) {
		this.sysMail = sysMail;
	}

	@Transient
	public String getFoldername() {
		if (null != folder) {
			if ("DRAFAT".equals(folder)) {
				foldername = "草稿箱";
			} else if ("INBOX".equalsIgnoreCase(folder)) {
				foldername = "收件箱";
			} else if ("TRASH".equalsIgnoreCase(folder)) {
				foldername = "废件箱";
			} else if ("SENDBOX".equalsIgnoreCase(folder)) {
				foldername = "发件箱";
			}
		}
		return foldername;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	@Transient
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}