package com.tlys.pla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * PlaMupstransport entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_MUPSTRANSPORT")
public class PlaMupstransport implements java.io.Serializable {

	// Fields

	private String planno;
	private String areaid;
	private String month;
	private String creator;
	private Date createdtime;
	private String status;
	private Date committime;
	private Date refusetime;
	private Date passtime;
	private String remarks;
	private String statusDIC;

	private String areaidDIC;
	private String monthStart;
	private String monthEnd;
	private String plannos;
	private String statuss;

	// Constructors

	/** default constructor */
	public PlaMupstransport() {
	}

	/** minimal constructor */
	public PlaMupstransport(String planno, String areaid, String month,
			String status) {
		this.planno = planno;
		this.areaid = areaid;
		this.month = month;
		this.status = status;
	}

	/** full constructor */
	public PlaMupstransport(String planno, String areaid, String month,
			String creator, Date createdtime, String status, Date committime,
			Date refusetime, Date passtime, String remarks) {
		this.planno = planno;
		this.areaid = areaid;
		this.month = month;
		this.creator = creator;
		this.createdtime = createdtime;
		this.status = status;
		this.committime = committime;
		this.refusetime = refusetime;
		this.passtime = passtime;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "PLANNO", unique = true, nullable = false, length = 15)
	public String getPlanno() {
		return this.planno;
	}

	public void setPlanno(String planno) {
		this.planno = planno;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "CREATOR", length = 6)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COMMITTIME", length = 7)
	public Date getCommittime() {
		return this.committime;
	}

	public void setCommittime(Date committime) {
		this.committime = committime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REFUSETIME", length = 7)
	public Date getRefusetime() {
		return this.refusetime;
	}

	public void setRefusetime(Date refusetime) {
		this.refusetime = refusetime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PASSTIME", length = 7)
	public Date getPasstime() {
		return this.passtime;
	}

	public void setPasstime(Date passtime) {
		this.passtime = passtime;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getAreaidDIC() {
		return areaidDIC;
	}

	public void setAreaidDIC(String areaidDIC) {
		this.areaidDIC = areaidDIC;
	}

	@Transient
	public String getMonthStart() {
		return monthStart;
	}

	public void setMonthStart(String monthStart) {
		this.monthStart = monthStart;
	}

	@Transient
	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	@Transient
	public String getPlannos() {
		return plannos;
	}

	public void setPlannos(String plannos) {
		this.plannos = plannos;
	}
	@Transient
	public String getStatusDIC() {
		return statusDIC;
	}

	public void setStatusDIC(String statusDIC) {
		this.statusDIC = statusDIC;
	}
	@Transient
	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	@Override
	public String toString() {
		return "PlaMupstransport [planno=" + planno + ", areaid=" + areaid
				+ ", month=" + month + ", creator=" + creator
				+ ", createdtime=" + createdtime + ", status=" + status
				+ ", committime=" + committime + ", refusetime=" + refusetime
				+ ", passtime=" + passtime + ", remarks=" + remarks
				+ ", statusDIC=" + statusDIC + ", areaidDIC=" + areaidDIC
				+ ", monthStart=" + monthStart + ", monthEnd=" + monthEnd
				+ ", plannos=" + plannos + ", statuss=" + statuss + "]";
	}

}