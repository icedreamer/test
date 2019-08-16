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
 * TbZbcPlaDrtrain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_DRTRAIN")
public class PlaDrtrain implements java.io.Serializable {

	// Fields

	private Long id;
	private String month;
	private String loaddate;
	private String corpid;
	private String areaid;
	private String corpshortname;
	private Long serial;
	private String sendername;
	private String startstationid;
	private String startstationname;
	private String endstationid;
	private String endstationname;
	private String receiverid;
	private String receivername;
	private String requestcarkindid;
	private String acceptcarkindid;
	private String rwkindname;
	private String rwkindid;
	private Long requestcars;
	private Double requestamount;
	private Long acceptcars;
	private Double acceptamount;
	private Long loadcars;
	private Double loadamount;
	private String kind;
	private String sendbureauid;
	private String arrivalbureauid;
	private String acceptcarno;
	private String requesttype;
	private Date indbtime;
	private Long mtotalcars;
	private Long planpkvalue;
	private String checkstatus;
	private Date checktime;
	private Date writetime;
	private String remarks;

	private String corpids;
	private String checkstatusids;
	private String kinds;

	// Constructors

	/** default constructor */
	public PlaDrtrain() {
	}

	/** minimal constructor */
	public PlaDrtrain(Long id, String month, String corpid, String areaid, String startstationid, String endstationid,
			String receivername, String kind, String sendbureauid, String arrivalbureauid, String requesttype,
			String checkstatus) {
		this.id = id;
		this.month = month;
		this.corpid = corpid;
		this.areaid = areaid;
		this.startstationid = startstationid;
		this.endstationid = endstationid;
		this.receivername = receivername;
		this.kind = kind;
		this.sendbureauid = sendbureauid;
		this.arrivalbureauid = arrivalbureauid;
		this.requesttype = requesttype;
		this.checkstatus = checkstatus;
	}

	/** full constructor */
	public PlaDrtrain(Long id, String month, String loaddate, String corpid, String areaid, String corpshortname,
			Long serial, String sendername, String startstationid, String startstationname, String endstationid,
			String endstationname, String receiverid, String receivername, String rwkindname, String rwkindid,
			Long requestcars, Double requestamount, Long acceptcars, Double acceptamount, Long loadcars,
			Double loadamount, String kind, String sendbureauid, String arrivalbureauid, String acceptcarno,
			String requesttype, Date indbtime, Long mtotalcars, Long planpkvalue, String checkstatus, Date checktime,
			Date writetime, String remarks) {
		this.id = id;
		this.month = month;
		this.loaddate = loaddate;
		this.corpid = corpid;
		this.areaid = areaid;
		this.corpshortname = corpshortname;
		this.serial = serial;
		this.sendername = sendername;
		this.startstationid = startstationid;
		this.startstationname = startstationname;
		this.endstationid = endstationid;
		this.endstationname = endstationname;
		this.receiverid = receiverid;
		this.receivername = receivername;
		this.rwkindname = rwkindname;
		this.rwkindid = rwkindid;
		this.requestcars = requestcars;
		this.requestamount = requestamount;
		this.acceptcars = acceptcars;
		this.acceptamount = acceptamount;
		this.loadcars = loadcars;
		this.loadamount = loadamount;
		this.kind = kind;
		this.sendbureauid = sendbureauid;
		this.arrivalbureauid = arrivalbureauid;
		this.acceptcarno = acceptcarno;
		this.requesttype = requesttype;
		this.indbtime = indbtime;
		this.mtotalcars = mtotalcars;
		this.planpkvalue = planpkvalue;
		this.checkstatus = checkstatus;
		this.checktime = checktime;
		this.writetime = writetime;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "LOADDATE", length = 8)
	public String getLoaddate() {
		return this.loaddate;
	}

	public void setLoaddate(String loaddate) {
		this.loaddate = loaddate;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "CORPSHORTNAME", length = 30)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
	}

	@Column(name = "SERIAL", precision = 22, scale = 0)
	public Long getSerial() {
		return this.serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	@Column(name = "SENDERNAME", length = 120)
	public String getSendername() {
		return this.sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	@Column(name = "STARTSTATIONID", nullable = false, length = 6)
	public String getStartstationid() {
		return this.startstationid;
	}

	public void setStartstationid(String startstationid) {
		this.startstationid = startstationid;
	}

	@Column(name = "STARTSTATIONNAME", length = 30)
	public String getStartstationname() {
		return this.startstationname;
	}

	public void setStartstationname(String startstationname) {
		this.startstationname = startstationname;
	}

	@Column(name = "ENDSTATIONID", nullable = false, length = 6)
	public String getEndstationid() {
		return this.endstationid;
	}

	public void setEndstationid(String endstationid) {
		this.endstationid = endstationid;
	}

	@Column(name = "ENDSTATIONNAME", length = 30)
	public String getEndstationname() {
		return this.endstationname;
	}

	public void setEndstationname(String endstationname) {
		this.endstationname = endstationname;
	}

	@Column(name = "RECEIVERID", length = 8)
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "RECEIVERNAME", nullable = false, length = 120)
	public String getReceivername() {
		return this.receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	@Column(name = "RWKINDNAME", length = 20)
	public String getRwkindname() {
		return this.rwkindname;
	}

	public void setRwkindname(String rwkindname) {
		this.rwkindname = rwkindname;
	}

	@Column(name = "RWKINDID", length = 7)
	public String getRwkindid() {
		return this.rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Column(name = "REQUESTCARS", precision = 22, scale = 0)
	public Long getRequestcars() {
		return this.requestcars;
	}

	public void setRequestcars(Long requestcars) {
		this.requestcars = requestcars;
	}

	@Column(name = "REQUESTAMOUNT", precision = 12, scale = 4)
	public Double getRequestamount() {
		return this.requestamount;
	}

	public void setRequestamount(Double requestamount) {
		this.requestamount = requestamount;
	}

	@Column(name = "ACCEPTCARS", precision = 22, scale = 0)
	public Long getAcceptcars() {
		return this.acceptcars;
	}

	public void setAcceptcars(Long acceptcars) {
		this.acceptcars = acceptcars;
	}

	@Column(name = "ACCEPTAMOUNT", precision = 12, scale = 4)
	public Double getAcceptamount() {
		return this.acceptamount;
	}

	public void setAcceptamount(Double acceptamount) {
		this.acceptamount = acceptamount;
	}

	@Column(name = "LOADCARS", precision = 22, scale = 0)
	public Long getLoadcars() {
		return this.loadcars;
	}

	public void setLoadcars(Long loadcars) {
		this.loadcars = loadcars;
	}

	@Column(name = "LOADAMOUNT", precision = 12, scale = 4)
	public Double getLoadamount() {
		return this.loadamount;
	}

	public void setLoadamount(Double loadamount) {
		this.loadamount = loadamount;
	}

	@Column(name = "KIND", nullable = false, length = 1)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "SENDBUREAUID", nullable = false, length = 3)
	public String getSendbureauid() {
		return this.sendbureauid;
	}

	public void setSendbureauid(String sendbureauid) {
		this.sendbureauid = sendbureauid;
	}

	@Column(name = "ARRIVALBUREAUID", nullable = false, length = 3)
	public String getArrivalbureauid() {
		return this.arrivalbureauid;
	}

	public void setArrivalbureauid(String arrivalbureauid) {
		this.arrivalbureauid = arrivalbureauid;
	}

	@Column(name = "ACCEPTCARNO", length = 20)
	public String getAcceptcarno() {
		return this.acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	@Column(name = "REQUESTTYPE", nullable = false, length = 1)
	public String getRequesttype() {
		return this.requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INDBTIME", length = 7)
	public Date getIndbtime() {
		return this.indbtime;
	}

	public void setIndbtime(Date indbtime) {
		this.indbtime = indbtime;
	}

	@Column(name = "MTOTALCARS", precision = 22, scale = 0)
	public Long getMtotalcars() {
		return this.mtotalcars;
	}

	public void setMtotalcars(Long mtotalcars) {
		this.mtotalcars = mtotalcars;
	}

	@Column(name = "PLANPKVALUE", precision = 12, scale = 0)
	public Long getPlanpkvalue() {
		return this.planpkvalue;
	}

	public void setPlanpkvalue(Long planpkvalue) {
		this.planpkvalue = planpkvalue;
	}

	@Column(name = "CHECKSTATUS", nullable = false, length = 2)
	public String getCheckstatus() {
		return this.checkstatus;
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CHECKTIME", length = 7)
	public Date getChecktime() {
		return this.checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "WRITETIME", length = 7)
	public Date getWritetime() {
		return this.writetime;
	}

	public void setWritetime(Date writetime) {
		this.writetime = writetime;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	@Transient
	public String getCheckstatusids() {
		return checkstatusids;
	}

	public void setCheckstatusids(String checkstatusids) {
		this.checkstatusids = checkstatusids;
	}

	@Transient
	public String getKinds() {
		return kinds;
	}

	public void setKinds(String kinds) {
		this.kinds = kinds;
	}

	@Column(name = "REQUESTCARKINDID", nullable = false, length = 1)
	public String getRequestcarkindid() {
		return requestcarkindid;
	}

	@Column(name = "ACCEPTCARKINDID", nullable = false, length = 1)
	public String getAcceptcarkindid() {
		return acceptcarkindid;
	}

	public void setRequestcarkindid(String requestcarkindid) {
		this.requestcarkindid = requestcarkindid;
	}

	public void setAcceptcarkindid(String acceptcarkindid) {
		this.acceptcarkindid = acceptcarkindid;
	}

}