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
 * PlaMspstransport2 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_MSPSTRANSPORT2")
public class PlaMspstransport2 implements java.io.Serializable {

	// Fields

	private String planno;
	private String corpid;
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
	// 字典翻译
	private String creatorDIC;
	private String adjusterDIC;
	private String adjustnumberDIC;
	private String statusDIC;
	private String corpidDIC;
	// 查询条件
	private String monthStart;
	private String monthEnd;
	private String corpids;
	private String plannos;
	private String statuss;
	// 调整信息
	private List<PlaMspstransport2> objs;
	
	/**
	 * 本条计划之前一条调整计划的状态
	 * 用于在页面判断是否显示提交\发布\驳回等按钮
	 */
	private String preStatus;

	/** default constructor */
	public PlaMspstransport2() {
	}

	/** minimal constructor */
	public PlaMspstransport2(String planno, String corpid, String month,
			String status) {
		this.planno = planno;
		this.corpid = corpid;
		this.month = month;
		this.status = status;
	}

	/** full constructor */
	public PlaMspstransport2(String planno, String corpid, String month,
			String creator, Date createdtime, String status, String adjuster,
			Long adjustnumber, Date adjusttime, Date committime,
			Date refusetime, Date passtime, String remarks) {
		this.planno = planno;
		this.corpid = corpid;
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

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
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

	public void setAdjustnumber(Long dadjustnumberjustnumber) {
		this.adjustnumber = dadjustnumberjustnumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ADJUSTTIME", length =10)
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
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
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
	public List<PlaMspstransport2> getObjs() {
		return objs;
	}

	public void setObjs(List<PlaMspstransport2> objs) {
		this.objs = objs;
	}

	@Transient
	public String getStatusDIC() {
		if("0".equals(status)){
			statusDIC = "未发布";
		}else if("3".equals(status)){
			statusDIC = "已发布";
		}else{
			statusDIC = status;
		}
		return statusDIC;
	}

	public void setStatusDIC(String statusDIC) {
		this.statusDIC = statusDIC;
	}

	@Transient
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
	}

	@Transient
	public String getAdjustnumberDIC() {
		return adjustnumberDIC;
	}

	public void setAdjustnumberDIC(String adjustnumberDIC) {
		this.adjustnumberDIC = adjustnumberDIC;
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
	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

}