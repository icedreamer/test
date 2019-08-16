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
 * PlaDoprtrainOld entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_DOPRTRAIN")
public class PlaDoprtrainOld implements java.io.Serializable {

	// Fields

	private Long id;
	private String month;
	private String loadtdate;
	private String corpid;
	private String areaid;
	private String requesttype;
	private String corpshortname;
	private Long serial;
	private String sendername;
	private String startstationid;
	private String endstationid;
	private String receiverid;
	private String receivername;
	private String carkindid;
	private String rwkindname;
	private String rwkindid;
	private Long requestcars;
	private Double requestamount;
	private Long acceptcars;
	private Double acceptamount;
	private String checkstatus;
	private Date checktime;
	private String productkind;
	private Long planpkvalue;
	private String acceptcarno;
	private Long loadcars;
	private Double loadamount;
	private Long mtotalcars;
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
	private String receiverids;
	private String monthStr;
	private String checkstatusDIC;
	private String requesttypeDIC;

	private String productkindDIC;

	private String loadamountDIC;
	private String requestamountDIC;
	private String acceptamountDIC;
	private String corpids;

	/**
	 * 标志当前是从查询页面提交的还是直接导航进来的 作用是用于左侧查询面板是否将条件清空
	 */
	private String schflag;

	private String productkindids;

	// Constructors

	/** default constructor */
	public PlaDoprtrainOld() {
	}

	/** minimal constructor */
	public PlaDoprtrainOld(Long id, String month, String corpid, String areaid, String requesttype, String startstationid,
			String endstationid, String receivername, String carkindid, String checkstatus, String productkind) {
		this.id = id;
		this.month = month;
		this.corpid = corpid;
		this.areaid = areaid;
		this.requesttype = requesttype;
		this.startstationid = startstationid;
		this.endstationid = endstationid;
		this.receivername = receivername;
		this.carkindid = carkindid;
		this.checkstatus = checkstatus;
		this.productkind = productkind;
	}

	/** full constructor */
	public PlaDoprtrainOld(Long id, String month, String loadtdate, String corpid, String areaid, String requesttype,
			String corpshortname, Long serial, String sendername, String startstationid, String endstationid,
			String receiverid, String receivername, String carkindid, String rwkindname, String rwkindid,
			Long requestcars, Double requestamount, Long acceptcars, Double acceptamount, String checkstatus,
			Date checktime, String productkind, Long planpkvalue, String acceptcarno, Long loadcars, Double loadamount,
			Long mtotalcars, Date writetime, String remarks) {
		this.id = id;
		this.month = month;
		this.loadtdate = loadtdate;
		this.corpid = corpid;
		this.areaid = areaid;
		this.requesttype = requesttype;
		this.corpshortname = corpshortname;
		this.serial = serial;
		this.sendername = sendername;
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
		this.productkind = productkind;
		this.planpkvalue = planpkvalue;
		this.acceptcarno = acceptcarno;
		this.loadcars = loadcars;
		this.loadamount = loadamount;
		this.mtotalcars = mtotalcars;
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

	@Column(name = "LOADTDATE", length = 8)
	public String getLoadtdate() {
		return this.loadtdate;
	}

	public void setLoadtdate(String loadtdate) {
		this.loadtdate = loadtdate;
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

	@Column(name = "REQUESTTYPE", nullable = false, length = 1)
	public String getRequesttype() {
		return this.requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
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

	@Column(name = "ENDSTATIONID", nullable = false, length = 6)
	public String getEndstationid() {
		return this.endstationid;
	}

	public void setEndstationid(String endstationid) {
		this.endstationid = endstationid;
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

	@Column(name = "PRODUCTKIND", nullable = false, length = 1)
	public String getProductkind() {
		return this.productkind;
	}

	public void setProductkind(String productkind) {
		this.productkind = productkind;
	}

	@Column(name = "PLANPKVALUE", precision = 12, scale = 0)
	public Long getPlanpkvalue() {
		return this.planpkvalue;
	}

	public void setPlanpkvalue(Long planpkvalue) {
		this.planpkvalue = planpkvalue;
	}

	@Column(name = "ACCEPTCARNO", length = 20)
	public String getAcceptcarno() {
		return this.acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
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

	@Column(name = "MTOTALCARS", precision = 22, scale = 0)
	public Long getMtotalcars() {
		return this.mtotalcars;
	}

	public void setMtotalcars(Long mtotalcars) {
		this.mtotalcars = mtotalcars;
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
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
	}

	@Transient
	public String getAreaidDIC() {
		return areaidDIC;
	}

	public void setAreaidDIC(String areaidDIC) {
		this.areaidDIC = areaidDIC;
	}

	@Transient
	public String getMonthEnd() {
		return monthEnd;
	}

	public void setMonthEnd(String monthEnd) {
		this.monthEnd = monthEnd;
	}

	@Transient
	public String getCarkindidDIC() {
		return carkindidDIC;
	}

	public void setCarkindidDIC(String carkindidDIC) {
		this.carkindidDIC = carkindidDIC;
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
	public String getReceiverids() {
		return receiverids;
	}

	public void setReceiverids(String receiverids) {
		this.receiverids = receiverids;
	}

	@Transient
	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	@Transient
	public String getCheckstatusDIC() {
		if (null == checkstatus) {
			return "原提";
		} else if ("00".equals(checkstatus)) {
			return "";
		} else if ("01".equals(checkstatus)) {
			return "待批";
		} else if ("02".equals(checkstatus)) {
			return "已批";
		} else if ("03".equals(checkstatus)) {
			return "暂缓";
		} else if ("04".equals(checkstatus)) {
			return "拒批";
		} else if ("07".equals(checkstatus)) {
			return "锁定";
		} else {
			return "未知";
		}
	}

	public void setCheckstatusDIC(String checkstatusDIC) {
		this.checkstatusDIC = checkstatusDIC;
	}

	@Transient
	public String getRequesttypeDIC() {
		if (null == requesttype) {
			return "日请";
		} else if ("0".equals(requesttype)) {
			return "日请";
		} else if ("1".equals(requesttype)) {
			return "临请";
		} else {
			return "未知";
		}
	}

	public void setRequesttypeDIC(String requesttypeDIC) {
		this.requesttypeDIC = requesttypeDIC;
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
	public String getProductkindids() {
		return productkindids;
	}

	public void setProductkindids(String productkindids) {
		this.productkindids = productkindids;
	}

	@Transient
	public String getProductkindDIC() {
		if (null == productkind) {
			productkindDIC = "其他";
		} else if ("3".equals(productkind)) {
			productkindDIC = "成品油";
		} else if ("4".equals(productkind)) {
			productkindDIC = "煤";
		} else if ("5".equals(productkind)) {
			productkindDIC = "原油";
		} else if ("6".equals(productkind)) {
			productkindDIC = "沥青";
		} else if ("9".equals(productkind)) {
			productkindDIC = "其他";
		} else if ("1".equals(productkind)) {
			productkindDIC = "统销";
		} else if ("0".equals(productkind)) {
			productkindDIC = "非统销";
		}
		return productkindDIC;
	}

	public void setProductkindDIC(String productkindDIC) {
		this.productkindDIC = productkindDIC;
	}

	@Transient
	public String getLoadamountDIC() {
		loadamountDIC = loadamount == null ? "0" : loadamount.toString();
		int dotp = loadamountDIC.indexOf(".0");
		if (dotp > -1) {
			loadamountDIC = loadamountDIC.substring(0, dotp);
		}
		return loadamountDIC;
	}

	public void setLoadamountDIC(String loadamountDIC) {
		this.loadamountDIC = loadamountDIC;
	}

	@Transient
	public String getRequestamountDIC() {
		requestamountDIC = requestamount == null ? "0" : requestamount.toString();
		int dotp = requestamountDIC.indexOf(".0");
		if (dotp > -1) {
			requestamountDIC = requestamountDIC.substring(0, dotp);
		}
		return requestamountDIC;
	}

	public void setRequestamountDIC(String requestamountDIC) {
		this.requestamountDIC = requestamountDIC;
	}

	@Transient
	public String getAcceptamountDIC() {

		acceptamountDIC = acceptamount == null ? "0" : acceptamount.toString();
		int dotp = acceptamountDIC.indexOf(".0");
		if (dotp > -1) {
			acceptamountDIC = acceptamountDIC.substring(0, dotp);
		}
		return acceptamountDIC;
	}

	public void setAcceptamountDIC(String acceptamountDIC) {
		this.acceptamountDIC = acceptamountDIC;
	}

	@Transient
	public String getSchflag() {
		return schflag;
	}

	public void setSchflag(String schflag) {
		this.schflag = schflag;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

}