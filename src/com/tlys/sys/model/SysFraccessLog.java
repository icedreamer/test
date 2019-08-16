package com.tlys.sys.model;

// default package

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
 * SysFraccessLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_FRACCESSLOG")
public class SysFraccessLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167762500741067758L;
	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_FRACCESSLOG")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_FRACCESSLOG", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_FRACCESSLOG") })
	@Column(name = "ID", unique = true, nullable = false, precision = 24, scale = 0)
	private Long id;
	private String sessionid;
	private String quarter;
	private String menuid;
	private Date oprtime;

	// Constructors

	/** default constructor */
	public SysFraccessLog() {
	}

	/** minimal constructor */
	public SysFraccessLog(Long id, String sessionid, String quarter, String menuid) {
		this.id = id;
		this.sessionid = sessionid;
		this.quarter = quarter;
		this.menuid = menuid;
	}

	/** full constructor */
	public SysFraccessLog(Long id, String sessionid, String quarter, String menuid, Date oprtime) {
		this.id = id;
		this.sessionid = sessionid;
		this.quarter = quarter;
		this.menuid = menuid;
		this.oprtime = oprtime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SESSIONID", nullable = false, length = 128)
	public String getSessionid() {
		return this.sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	@Column(name = "QUARTER", nullable = false, length = 5)
	public String getQuarter() {
		return this.quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	@Column(name = "MENUID", nullable = false, length = 5)
	public String getMenuid() {
		return this.menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OPRTIME", length = 7)
	public Date getOprtime() {
		return this.oprtime;
	}

	public void setOprtime(Date oprtime) {
		this.oprtime = oprtime;
	}

}