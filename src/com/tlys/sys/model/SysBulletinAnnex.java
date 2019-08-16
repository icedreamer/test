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
 * TbZbcSysBulletinAnnex entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_BULLETIN_ANNEX")
public class SysBulletinAnnex implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6821457512708057042L;
	private Long annexid;
	private Long bulletinid;
	private String annexname;
	private String path;
	private Date uploadtime;

	// Constructors

	/** default constructor */
	public SysBulletinAnnex() {
	}

	/** minimal constructor */
	public SysBulletinAnnex(Long annexid, Long bulletinid, String annexname, String path) {
		this.annexid = annexid;
		this.bulletinid = bulletinid;
		this.annexname = annexname;
		this.path = path;
	}

	/** full constructor */
	public SysBulletinAnnex(Long annexid, Long bulletinid, String annexname, String path, Date uploadtime) {
		this.annexid = annexid;
		this.bulletinid = bulletinid;
		this.annexname = annexname;
		this.path = path;
		this.uploadtime = uploadtime;
	}

	// Property accessors
	@Id
	@Column(name = "ANNEXID", unique = true, nullable = false, precision = 7, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_BULLETIN_ANNEX")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_BULLETIN_ANNEX", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_BULLETIN_ANNEX") })
	public Long getAnnexid() {
		return this.annexid;
	}

	public void setAnnexid(Long annexid) {
		this.annexid = annexid;
	}

	@Column(name = "BULLETINID", nullable = false, precision = 6, scale = 0)
	public Long getBulletinid() {
		return this.bulletinid;
	}

	public void setBulletinid(Long bulletinid) {
		this.bulletinid = bulletinid;
	}

	@Column(name = "ANNEXNAME", nullable = false, length = 120)
	public String getAnnexname() {
		return this.annexname;
	}

	public void setAnnexname(String annexname) {
		this.annexname = annexname;
	}

	@Column(name = "PATH", nullable = false, length = 400)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOADTIME", length = 7)
	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

}