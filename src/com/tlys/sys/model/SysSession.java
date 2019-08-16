package com.tlys.sys.model;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * SysSession entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_SESSION")
public class SysSession implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1269036214470330996L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_SESSION")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_SESSION", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_SESSION") })
	@Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
	private Long id;
	private String quarter;
	private String sessionid;
	private Date starttime;
	private String userid;
	private String cip;
	private String ccname;
	private String cbrower;
	private String cos;
	private String sessionstatus;
	private Date endtime;
	@Transient
	private String startDateStr;
	@Transient
	private String endDateStr;
	@Transient
	private String rqlx;

	/** default constructor */
	public SysSession() {
	}

	/** minimal constructor */
	public SysSession(Long id, String quarter, String sessionid, Date starttime, String userid, String cip,
			String sessionstatus) {
		this.id = id;
		this.quarter = quarter;
		this.sessionid = sessionid;
		this.starttime = starttime;
		this.userid = userid;
		this.cip = cip;
		this.sessionstatus = sessionstatus;
	}

	/** full constructor */
	public SysSession(Long id, String quarter, String sessionid, Date starttime, String userid, String cip,
			String ccname, String cbrower, String cos, String sessionstatus, Date endtime) {
		this.id = id;
		this.quarter = quarter;
		this.sessionid = sessionid;
		this.starttime = starttime;
		this.userid = userid;
		this.cip = cip;
		this.ccname = ccname;
		this.cbrower = cbrower;
		this.cos = cos;
		this.sessionstatus = sessionstatus;
		this.endtime = endtime;
	}

	@Column(name = "CBROWER", length = 80)
	public String getCbrower() {
		return this.cbrower;
	}

	@Column(name = "CCNAME", length = 80)
	public String getCcname() {
		return this.ccname;
	}

	@Column(name = "CIP", nullable = false, length = 15)
	public String getCip() {
		return this.cip;
	}

	// Constructors

	@Column(name = "COS", length = 80)
	public String getCos() {
		return this.cos;
	}

	@Column(name = "ENDTIME", nullable = true)
	public Date getEndtime() {
		return this.endtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	@Column(name = "QUARTER", nullable = false, length = 5)
	public String getQuarter() {
		return this.quarter;
	}

	/**
	 * @return the rqlx
	 */
	public String getRqlx() {
		return rqlx;
	}

	@Column(name = "SESSIONID", nullable = false, length = 128)
	public String getSessionid() {
		return this.sessionid;
	}

	@Column(name = "SESSIONSTATUS", nullable = false, length = 1)
	public String getSessionstatus() {
		return this.sessionstatus;
	}

	@Column(name = "STARTTIME", nullable = false)
	public Date getStarttime() {
		return this.starttime;
	}

	@Column(name = "USERID", nullable = false, length = 6)
	public String getUserid() {
		return this.userid;
	}

	public void setCbrower(String cbrower) {
		this.cbrower = cbrower;
	}

	public void setCcname(String ccname) {
		this.ccname = ccname;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public void setCos(String cos) {
		this.cos = cos;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	/**
	 * @param rqlx
	 *            the rqlx to set
	 */
	public void setRqlx(String rqlx) {
		this.rqlx = rqlx;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public void setSessionstatus(String sessionstatus) {
		this.sessionstatus = sessionstatus;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}