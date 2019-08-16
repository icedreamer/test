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
 * 公告阅读信息
 * 
 * @author ccsong 2012-5-30 下午2:30:41
 */
@Entity
@Table(name = "TB_ZBC_SYS_BULLETIN_READINFO")
public class SysBulletinReadinfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1242973215880262304L;
	private Long id;
	private Long bulletinid;
	private String readerid;
	private Date readtime;

	// Constructors

	/** default constructor */
	public SysBulletinReadinfo() {
	}

	/** minimal constructor */
	public SysBulletinReadinfo(Long id, Long bulletinid, String readerid) {
		this.id = id;
		this.bulletinid = bulletinid;
		this.readerid = readerid;
	}

	/** full constructor */
	public SysBulletinReadinfo(Long id, Long bulletinid, String readerid, Date readtime) {
		this.id = id;
		this.bulletinid = bulletinid;
		this.readerid = readerid;
		this.readtime = readtime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_BULLETIN_RDINFO")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_BULLETIN_RDINFO", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_BULLETIN_RDINFO") })
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BULLETINID", nullable = false, precision = 6, scale = 0)
	public Long getBulletinid() {
		return this.bulletinid;
	}

	public void setBulletinid(Long bulletinid) {
		this.bulletinid = bulletinid;
	}

	@Column(name = "READERID", nullable = false, length = 6)
	public String getReaderid() {
		return this.readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "READTIME", length = 7)
	public Date getReadtime() {
		return this.readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

}