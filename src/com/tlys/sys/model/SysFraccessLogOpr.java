package com.tlys.sys.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * SysFraccessLogOpr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_FRACCESSLOGOPR")
public class SysFraccessLogOpr implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922475774783057897L;
	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_FRACCESSLOGOPR")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_FRACCESSLOGOPR", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_FRACCESSLOGOPR") })
	@Column(name = "ID", unique = true, nullable = false, precision = 24, scale = 0)
	private Long id;
	private Long logid;
	private String quarter;
	private String operid;
	private String dbtname;
	private String oprtype;
	private String opercode;

	/** default constructor */
	public SysFraccessLogOpr() {
	}

	/** full constructor */
	public SysFraccessLogOpr(Long id, Long logid, String quarter, String operid) {
		this.id = id;
		this.logid = logid;
		this.quarter = quarter;
		this.operid = operid;
	}

	public String getDbtname() {
		return dbtname;
	}

	public Long getId() {
		return this.id;
	}

	@Column(name = "LOGID", nullable = false, precision = 24, scale = 0)
	public Long getLogid() {
		return this.logid;
	}

	public String getOpercode() {
		return opercode;
	}

	@Column(name = "OPERID", nullable = false, length = 3)
	public String getOperid() {
		return this.operid;
	}

	public String getOprtype() {
		return oprtype;
	}

	// Property accessors

	@Column(name = "QUARTER", nullable = false, length = 5)
	public String getQuarter() {
		return this.quarter;
	}

	public void setDbtname(String dbtname) {
		this.dbtname = dbtname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public void setOpercode(String opercode) {
		this.opercode = opercode;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public void setOprtype(String oprtype) {
		this.oprtype = oprtype;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

}