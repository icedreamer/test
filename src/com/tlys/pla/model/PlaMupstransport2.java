package com.tlys.pla.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * TbZbcPlaMupstransport2 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_MUPSTRANSPORT2")
public class PlaMupstransport2 implements java.io.Serializable {

	// Fields

	private String planno;
	private String areaid;
	private String month;
	private String creator;
	private Date createdtime;
	private String status;
	private String adjuster;
	private Long adjustnumber;
	private Date adjusttime;
	private Date committime;
	private Date refusetime;
	private Date passtime;
	private String remarks;
	// �ֵ䷭��
	private String creatorDIC;
	private String adjusterDIC;
	private String adjustnumberDIC;
	private String statusDIC;
	private String areaidDIC;
	// ��ѯ����
	private String monthStart;
	private String monthEnd;
	private String areaids;
	private String plannos;
	private String statuss;
	
	// �����ƻ������б�
	private List<PlaMupstransport2> objs;
	
	/**
	 * �����ƻ�֮ǰһ�������ƻ���״̬
	 * ������ҳ���ж��Ƿ���ʾ�ύ\����\���صȰ�ť
	 */
	private String preStatus;

	// Constructors

	/** default constructor */
	public PlaMupstransport2() {
	}

	/** minimal constructor */
	public PlaMupstransport2(String planno, String areaid, String month,
			String status) {
		this.planno = planno;
		this.areaid = areaid;
		this.month = month;
		this.status = status;
	}

	/** full constructor */
	public PlaMupstransport2(String planno, String areaid, String month,
			String creator, Date createdtime, String status, String adjuster,
			Long adjustnumber, Date adjusttime, Date committime,
			Date refusetime, Date passtime, String remarks) {
		this.planno = planno;
		this.areaid = areaid;
		this.month = month;
		this.creator = creator;
		this.createdtime = createdtime;
		this.status = status;
		this.adjuster = adjuster;
		this.adjustnumber = adjustnumber;
		this.adjusttime = adjusttime;
		this.committime = committime;
		this.refusetime = refusetime;
		this.passtime = passtime;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "PLANNO", unique = true, nullable = false, length = 18)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDTIME", length = 10)
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

	@Column(name = "ADJUSTER", length = 6)
	public String getAdjuster() {
		return this.adjuster;
	}

	public void setAdjuster(String adjuster) {
		this.adjuster = adjuster;
	}

	@Column(name = "ADJUSTNUMBER", precision = 22, scale = 0)
	public Long getAdjustnumber() {
		return this.adjustnumber;
	}

	public void setAdjustnumber(Long adjustnumber) {
		this.adjustnumber = adjustnumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ADJUSTTIME", length = 10)
	public Date getAdjusttime() {
		return this.adjusttime;
	}

	public void setAdjusttime(Date adjusttime) {
		this.adjusttime = adjusttime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMITTIME", length = 10)
	public Date getCommittime() {
		return this.committime;
	}

	public void setCommittime(Date committime) {
		this.committime = committime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REFUSETIME", length = 10)
	public Date getRefusetime() {
		return this.refusetime;
	}

	public void setRefusetime(Date refusetime) {
		this.refusetime = refusetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PASSTIME", length = 10)
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
	public String getCreatorDIC() {
		return creatorDIC;
	}

	public void setCreatorDIC(String creatorDIC) {
		this.creatorDIC = creatorDIC;
	}

	@Transient
	public String getAdjusterDIC() {
		return adjusterDIC;
	}

	public void setAdjusterDIC(String adjusterDIC) {
		this.adjusterDIC = adjusterDIC;
	}

	@Transient
	public String getAdjustnumberDIC() {
		return adjustnumberDIC;
	}

	public void setAdjustnumberDIC(String adjustnumberDIC) {
		this.adjustnumberDIC = adjustnumberDIC;
	}

	@Transient
	public String getStatusDIC() {
		return statusDIC;
	}

	public void setStatusDIC(String statusDIC) {
		this.statusDIC = statusDIC;
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
	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	@Transient
	public String getPlannos() {
		return plannos;
	}

	public void setPlannos(String plannos) {
		this.plannos = plannos;
	}

	@Transient
	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	@Transient
	public List<PlaMupstransport2> getObjs() {
		return objs;
	}

	public void setObjs(List<PlaMupstransport2> objs) {
		this.objs = objs;
	}

	@Transient
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

}