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
 * PlaMuprtrain entity. 月度铁路统销产品运输请车及批复计划
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_MUPRTRAIN")
public class PlaMuprtrain implements java.io.Serializable {

	// Fields

	private Long id;
	private String month;
	private String requestdate;
	private String corpid;
	private String areaid;
	private String corpshortname;
	private String acceptno;
	private Long serial;
	private String startdate;
	private String enddate;
	private String startstationid;
	private String endstationid;
	private String sendername;
	private String receivername;
	private String carkindid;
	private String receiverid;
	private String rwkindname;
	private String rwkindid;
	private Long requestcars;
	private Double requestamount;
	private Long acceptcars;
	private Double acceptamount;
	private String checkstatus;
	private Date checktime;
	private Long planpkvalue;
	private String acceptcarno;
	private Date writetime;
	private String remarks;
	
	private String startstationname;
	private String endstationname;

	private String corpidDIC;
	private String areaidDIC;
	private String monthEnd;
	private String carkindidDIC;
	private String startstationidDIC;
	private String endstationidDIC;
	public String receiverids;
	private String monthStr;
	private String checkstatusDIC;
	
	private String requestamountDIC;
	private String acceptamountDIC;
	
	
	private String corpids;
	private String goodsids;
	private String stationids;
	private String senderids;
	
	private String checkstatusids;
	
	/**
	 * 标志当前是从查询页面提交的还是直接导航进来的
	 * 作用是用于左侧查询面板是否将条件清空
	 */
	private String schflag;

	// Constructors

	/** default constructor */
	public PlaMuprtrain() {
	}

	public PlaMuprtrain(String areaid, String month) {
		this.month = month;
		this.areaid = areaid;
	}

	/** minimal constructor */
	public PlaMuprtrain(Long id, String month, String corpid, String areaid,
			String startstationid, String endstationid, String receivername,
			String carkindid, String checkstatus) {
		this.id = id;
		this.month = month;
		this.corpid = corpid;
		this.startstationid = startstationid;
		this.endstationid = endstationid;
		this.receivername = receivername;
		this.carkindid = carkindid;
		this.checkstatus = checkstatus;
		this.areaid = areaid;
	}

	/** full constructor */
	public PlaMuprtrain(Long id, String month, String requestdate,
			String corpid, String areaid, String corpshortname,
			String acceptno, Long serial, String startdate, String enddate,
			String startstationid, String endstationid, String receiverid,
			String receivername, String carkindid, String rwkindname,
			String rwkindid, Long requestcars, Double requestamount,
			Long acceptcars, Double acceptamount, String checkstatus,
			Date checktime, Long planpkvalue, Date writetime, String remarks) {
		this.id = id;
		this.month = month;
		this.requestdate = requestdate;
		this.corpid = corpid;
		this.areaid = areaid;
		this.corpshortname = corpshortname;
		this.acceptno = acceptno;
		this.serial = serial;
		this.startdate = startdate;
		this.enddate = enddate;
		this.startstationid = startstationid;
		this.endstationid = endstationid;
		this.receiverid = receiverid;
		this.receivername = receivername;
		this.carkindid = carkindid;
		this.rwkindname = rwkindname;
		this.rwkindid = rwkindid;
		this.requestcars = requestcars;
		this.requestamount = requestamount;
		this.acceptcars = acceptcars;
		this.acceptamount = acceptamount;
		this.checkstatus = checkstatus;
		this.checktime = checktime;
		this.planpkvalue = planpkvalue;
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

	@Column(name = "REQUESTDATE", length = 8)
	public String getRequestdate() {
		return this.requestdate;
	}

	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPSHORTNAME", length = 30)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
	}

	@Column(name = "ACCEPTNO", length = 14)
	public String getAcceptno() {
		return this.acceptno;
	}

	public void setAcceptno(String acceptno) {
		this.acceptno = acceptno;
	}

	@Column(name = "SERIAL", precision = 22, scale = 0)
	public Long getSerial() {
		return this.serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	@Column(name = "STARTDATE", length = 8)
	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	@Column(name = "ENDDATE", length = 8)
	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@Column(name = "STARTSTATIONID", nullable = false, length = 6)
	public String getStartstationid() {
		return this.startstationid;
	}

	public void setStartstationid(String startstationid) {
		this.startstationid = startstationid;
	}

	@Column(name = "ENDSTATIONID", nullable = false, length = 6)
	public String getEndstationid() {
		return this.endstationid;
	}

	public void setEndstationid(String endstationid) {
		this.endstationid = endstationid;
	}

	@Column(name = "RECEIVERNAME", nullable = false, length = 120)
	public String getReceivername() {
		return this.receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	@Column(name = "CARKINDID", nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
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

	@Column(name = "PLANPKVALUE", precision = 12, scale = 0)
	public Long getPlanpkvalue() {
		return this.planpkvalue;
	}

	public void setPlanpkvalue(Long planpkvalue) {
		this.planpkvalue = planpkvalue;
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

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "RECEIVERID", length = 8)
	public String getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	
	
	@Column(name = "STARTSTATIONNAME", length = 8)
	public String getStartstationname() {
		return startstationname;
	}

	public void setStartstationname(String startstationname) {
		this.startstationname = startstationname;
	}
	
	@Column(name = "ENDSTATIONNAME", length = 8)
	public String getEndstationname() {
		return endstationname;
	}

	public void setEndstationname(String endstationname) {
		this.endstationname = endstationname;
	}

	@Transient
	public String getReceiverids() {
		return receiverids;
	}

	public void setReceiverids(String receiverids) {
		this.receiverids = receiverids;
	}

	@Transient
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
	}

	@Transient
	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	@Transient
	public String getAreaidDIC() {
		return areaidDIC;
	}

	public void setAreaidDIC(String areaidDIC) {
		this.areaidDIC = areaidDIC;
	}

	@Column(name = "ACCEPTCARNO", length = 20)
	public String getAcceptcarno() {
		return acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	@Transient
	public String getCarkindidDIC() {
		return carkindidDIC;
	}

	public void setCarkindidDIC(String carkindidDIC) {
		this.carkindidDIC = carkindidDIC;
	}

	@Column(name = "SENDERNAME", length = 120)
	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	@Transient
	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	@Transient
	public String getStartstationidDIC() {
		return startstationidDIC;
	}

	public void setStartstationidDIC(String startstationidDIC) {
		this.startstationidDIC = startstationidDIC;
	}

	@Transient
	public String getEndstationidDIC() {
		return endstationidDIC;
	}

	public void setEndstationidDIC(String endstationidDIC) {
		this.endstationidDIC = endstationidDIC;
	}

	@Transient
	public String getCheckstatusDIC() {
		if(null==checkstatus){
			return "未知";
		}else if("00".equals(checkstatus)){
			return "原提";
		}else if("01".equals(checkstatus)){
			return "待批";
		}else if("02".equals(checkstatus)){
			return "已批";
		}else if("03".equals(checkstatus)){
			return "暂缓";
		}else if("04".equals(checkstatus)){
			return "拒批";
		}else if("07".equals(checkstatus)){
			return "锁定";
		}else{
			return "未知";
		}
	}

	public void setCheckstatusDIC(String checkstatusDIC) {
		this.checkstatusDIC = checkstatusDIC;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	@Transient
	public String getGoodsids() {
		return goodsids;
	}

	public void setGoodsids(String goodsids) {
		this.goodsids = goodsids;
	}

	@Transient
	public String getStationids() {
		return stationids;
	}

	public void setStationids(String stationids) {
		this.stationids = stationids;
	}

	@Transient
	public String getSenderids() {
		return senderids;
	}

	public void setSenderids(String senderids) {
		this.senderids = senderids;
	}
	

	@Transient
	public String getCheckstatusids() {
		return checkstatusids;
	}

	public void setCheckstatusids(String checkstatusids) {
		this.checkstatusids = checkstatusids;
	}
	

	@Transient
	public String getRequestamountDIC() {
		requestamountDIC = requestamount==null?"0":requestamount.toString();
		int dotp = requestamountDIC.indexOf(".0");
		if(dotp > -1){
			requestamountDIC = requestamountDIC.substring(0,dotp);
		}
		return requestamountDIC;
	}

	public void setRequestamountDIC(String requestamountDIC) {
		this.requestamountDIC = requestamountDIC;
	}

	@Transient
	public String getAcceptamountDIC() {
		
		acceptamountDIC = acceptamount==null?"0":acceptamount.toString();
		int dotp = acceptamountDIC.indexOf(".0");
		if(dotp > -1){
			acceptamountDIC = acceptamountDIC.substring(0,dotp);
		}
		return acceptamountDIC;
	}

	public void setAcceptamountDIC(String acceptamountDIC) {
		this.acceptamountDIC = acceptamountDIC;
	}

	@Override
	public String toString() {
		return "PlaMuprtrain [id=" + id + ", month=" + month + ", requestdate="
				+ requestdate + ", corpid=" + corpid + ", areaid=" + areaid
				+ ", corpshortname=" + corpshortname + ", acceptno=" + acceptno
				+ ", serial=" + serial + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", startstationid=" + startstationid
				+ ", endstationid=" + endstationid + ", sendername="
				+ sendername + ", receivername=" + receivername
				+ ", carkindid=" + carkindid + ", receiverid=" + receiverid
				+ ", rwkindname=" + rwkindname + ", rwkindid=" + rwkindid
				+ ", requestcars=" + requestcars + ", requestamount="
				+ requestamount + ", acceptcars=" + acceptcars
				+ ", acceptamount=" + acceptamount + ", checkstatus="
				+ checkstatus + ", checktime=" + checktime + ", planpkvalue="
				+ planpkvalue + ", acceptcarno=" + acceptcarno + ", writetime="
				+ writetime + ", remarks=" + remarks + ", startstationname="
				+ startstationname + ", endstationname=" + endstationname
				+ ", corpidDIC=" + corpidDIC + ", areaidDIC=" + areaidDIC
				+ ", monthEnd=" + monthEnd + ", carkindidDIC=" + carkindidDIC
				+ ", startstationidDIC=" + startstationidDIC
				+ ", endstationidDIC=" + endstationidDIC + ", receiverids="
				+ receiverids + ", monthStr=" + monthStr + ", checkstatusDIC="
				+ checkstatusDIC + ", corpids=" + corpids + ", goodsids="
				+ goodsids + ", stationids=" + stationids + ", senderids="
				+ senderids + ", checkstatusids=" + checkstatusids + "]";
	}

	@Transient
	public String getSchflag() {
		return schflag;
	}

	public void setSchflag(String schflag) {
		this.schflag = schflag;
	}

}