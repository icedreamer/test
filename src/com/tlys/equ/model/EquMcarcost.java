package com.tlys.equ.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author ccsong 2012-6-26 ÉÏÎç10:22:21
 */
@Entity
@Table(name = "TB_ZBC_EQU_MCARCOST")
public class EquMcarcost implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7157040776538557988L;
	private String monthlyid;
	private String month;
	// private DicSinocorp dicSinocorp;
	private String corpid;
	private String creator;
	private Date createdtime;
	private Date committime;
	private String status;
	private String remarks;

	// Constructors

	/** default constructor */
	public EquMcarcost() {
	}

	/** minimal constructor */
	public EquMcarcost(String monthlyid, String month, String status) {
		this.monthlyid = monthlyid;
		this.month = month;
		this.status = status;
	}

	/** full constructor */
	public EquMcarcost(String monthlyid, String month, String creator, Date createdtime,
			Date committime, String status, String remarks) {
		this.monthlyid = monthlyid;
		this.month = month;
		this.creator = creator;
		this.createdtime = createdtime;
		this.committime = committime;
		this.status = status;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "MONTHLYID", unique = true, nullable = false, length = 15)
	public String getMonthlyid() {
		return this.monthlyid;
	}

	public void setMonthlyid(String monthlyid) {
		this.monthlyid = monthlyid;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	// @Column(name = "CORPID", nullable = false, length = 8)
	// public String getCorpid() {
	// return this.corpid;
	// }
	//
	// public void setCorpid(String corpid) {
	// this.corpid = corpid;
	// }

	@Column(name = "CREATOR", length = 6)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMITTIME", length = 7)
	public Date getCommittime() {
		return this.committime;
	}

	public void setCommittime(Date committime) {
		this.committime = committime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = DicSinocorp.class)
	// @JoinColumn(name = "corpid")
	// @NotFound(action = NotFoundAction.IGNORE)
	// public DicSinocorp getDicSinocorp() {
	// return dicSinocorp;
	// }
	//
	// public void setDicSinocorp(DicSinocorp dicSinocorp) {
	// this.dicSinocorp = dicSinocorp;
	// }

}