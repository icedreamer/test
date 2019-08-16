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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * SysMailattach entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_MAILATTACH")
public class SysMailattach implements java.io.Serializable {

	// Fields

	private long id;
	public long mailid;
	public String filename;
	public String formattype;
	public String path;
	public Date uploadtime;

	// Constructors

	/** default constructor */
	public SysMailattach() {
	}

	/** minimal constructor */
	public SysMailattach(long id, long mailid, String filename, String formattype) {
		this.id = id;
		this.mailid = mailid;
		this.filename = filename;
		this.formattype = formattype;
	}

	/** full constructor */
	public SysMailattach(long id, long mailid, String filename, String formattype, String path, Date uploadtime) {
		this.id = id;
		this.mailid = mailid;
		this.filename = filename;
		this.formattype = formattype;
		this.path = path;
		this.uploadtime = uploadtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_MAILATTACH")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_MAILATTACH", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_TB_ZBC_SYS_MAILATTACH") })
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "MAILID", nullable = false, precision = 6, scale = 0)
	public long getMailid() {
		return this.mailid;
	}

	public void setMailid(long mailid) {
		this.mailid = mailid;
	}

	@Column(name = "FILENAME", nullable = false, length = 100)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "FORMATTYPE", nullable = false, length = 10)
	public String getFormattype() {
		return this.formattype;
	}

	public void setFormattype(String formattype) {
		this.formattype = formattype;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME", length = 7)
	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

}