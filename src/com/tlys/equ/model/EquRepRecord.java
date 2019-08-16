package com.tlys.equ.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Parameter;

import com.tlys.dic.model.DicReparetype;

/**
 * 检修记录
 * 
 * @author ccsong 2012-1-9 上午10:57:13
 */
@Entity
@Table(name = "TB_ZBC_EQU_REP_RECORD")
public class EquRepRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4055105967929356874L;
	// Fields

	private long id;

	// public EquCar equCar;
	public String carno;

	public DicReparetype dicReparetype;
	// public String rtypeid;
	public String rtypename;

	public Date distraindate;
	public String distrainaddress;
	public Date deliverdate;
	public Date finishdate;
	public String repcorpid;
	public String repcorpname;
	public Date nextrepdate;
	public String warnstatus;
	public Date createdtime;
	public String remarks;
	public String[] carkindids;
	public String[] cartypeids;

	public String[] dicSinocorpids;

	public String repdateStr;
	public String nextrepdateStr;
	public String month;
	public String corpid;
	public String isrecently;
	public String year;

	/**
	 * 检修标记（本月待检、超期未检、正常运行)
	 */
	public String repkind;
	/**
	 * 是否提交（1：提交；0未提交)
	 */
	public String active;
	/**
	 * 标志当前是从查询页面提交的还是直接导航进来的 作用是用于左侧查询面板是否将条件清空
	 */
	private String schflag;

	/** default constructor */
	public EquRepRecord() {
	}

	/** full constructor */
	public EquRepRecord(long id, String rtypename, Date distraindate, String distrainaddress, Date deliverdate,
			Date finishdate, String repcorpid, String repcorpname, Date nextrepdate, String warnstatus,
			Date createdtime, String remarks) {
		this.id = id;
		this.rtypename = rtypename;
		this.distraindate = distraindate;
		this.distrainaddress = distrainaddress;
		this.deliverdate = deliverdate;
		this.finishdate = finishdate;
		this.repcorpid = repcorpid;
		this.repcorpname = repcorpname;
		this.nextrepdate = nextrepdate;
		this.warnstatus = warnstatus;
		this.createdtime = createdtime;
		this.remarks = remarks;
	}

	/** minimal constructor */
	public EquRepRecord(long id, String rtypename, String repcorpid, String warnstatus) {
		this.id = id;
		this.rtypename = rtypename;
		this.repcorpid = repcorpid;
		this.warnstatus = warnstatus;
	}

	public String getActive() {
		return active;
	}

	@Transient
	public String[] getCarkindids() {
		return carkindids;
	}

	public String getCarno() {
		return carno;
	}

	@Transient
	public String[] getCartypeids() {
		return cartypeids;
	}

	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	@Column(name = "DELIVERDATE", length = 7)
	public Date getDeliverdate() {
		return this.deliverdate;
	}

	// Constructors
	@Transient
	public String[] getDicSinocorpids() {
		return dicSinocorpids;
	}

	@Column(name = "DISTRAINADDRESS", length = 60)
	public String getDistrainaddress() {
		return this.distrainaddress;
	}

	@Column(name = "DISTRAINDATE", length = 7)
	public Date getDistraindate() {
		return this.distraindate;
	}

	@Column(name = "FINISHDATE", length = 7)
	public Date getFinishdate() {
		return this.finishdate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_EQU_REP_REOCRD")
	@GenericGenerator(name = "SEQ_TB_ZBC_EQU_REP_REOCRD", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_EQU_REP_REOCRD") })
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public long getId() {
		return this.id;
	}

	@Column(name = "NEXTREPDATE", length = 7)
	public Date getNextrepdate() {
		return this.nextrepdate;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = EquCar.class)
	// @JoinColumn(name = "carno")
	// @NotFound(action = NotFoundAction.EXCEPTION)
	// public EquCar getEquCar() {
	// return equCar;
	// }

	@Transient
	public String getNextrepdateStr() {
		return nextrepdateStr;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	@Column(name = "REPCORPID", nullable = false, length = 6)
	public String getRepcorpid() {
		return this.repcorpid;
	}

	@Column(name = "REPCORPNAME", length = 60)
	public String getRepcorpname() {
		return this.repcorpname;
	}

	@Transient
	public String getRepdateStr() {
		return repdateStr;
	}

	@Column(name = "RTYPENAME", nullable = false, length = 20)
	public String getRtypename() {
		return this.rtypename;
	}

	@Column(name = "WARNSTATUS", nullable = false, length = 1)
	public String getWarnstatus() {
		return this.warnstatus;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setCarkindids(String[] carkindids) {
		this.carkindids = carkindids;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public void setCartypeids(String[] cartypeids) {
		this.cartypeids = cartypeids;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	public void setDeliverdate(Date deliverdate) {
		this.deliverdate = deliverdate;
	}

	public void setDicSinocorpids(String[] dicSinocorpids) {
		this.dicSinocorpids = dicSinocorpids;
	}

	public void setDistrainaddress(String distrainaddress) {
		this.distrainaddress = distrainaddress;
	}

	public void setDistraindate(Date distraindate) {
		this.distraindate = distraindate;
	}

	// public void setEquCar(EquCar equCar) {
	// this.equCar = equCar;
	// }

	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNextrepdate(Date nextrepdate) {
		this.nextrepdate = nextrepdate;
	}

	public void setNextrepdateStr(String nextrepdateStr) {
		this.nextrepdateStr = nextrepdateStr;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRepcorpid(String repcorpid) {
		this.repcorpid = repcorpid;
	}

	public void setRepcorpname(String repcorpname) {
		this.repcorpname = repcorpname;
	}

	public void setRepdateStr(String repdateStr) {
		this.repdateStr = repdateStr;
	}

	public void setRtypename(String rtypename) {
		this.rtypename = rtypename;
	}

	public void setWarnstatus(String warnstatus) {
		this.warnstatus = warnstatus;
	}

	@Override
	public String toString() {
		return "EquRepRecord [id=" + id + ", carno=" + carno + ", rtypename=" + rtypename + ", distraindate="
				+ distraindate + ", distrainaddress=" + distrainaddress + ", deliverdate=" + deliverdate
				+ ", finishdate=" + finishdate + ", repcorpid=" + repcorpid + ", repcorpname=" + repcorpname
				+ ", nextrepdate=" + nextrepdate + ", warnstatus=" + warnstatus + ", createdtime=" + createdtime
				+ ", remarks=" + remarks + ", carkindids=" + Arrays.toString(carkindids) + ", cartypeids="
				+ Arrays.toString(cartypeids) + ", dicSinocorpids=" + Arrays.toString(dicSinocorpids) + ", repdateStr="
				+ repdateStr + ", nextrepdateStr=" + nextrepdateStr + ", repkind=" + repkind + "]";
	}

	/**
	 * 将本月待检车辆(01)、超期未检车辆(02)、正常运行车辆(00)分开，用regflag标记
	 * 
	 * @return
	 */
	@Transient
	public String getRepkind() {
		return repkind;
	}

	public void setRepkind(String repkind) {
		this.repkind = repkind;
	}

	@Transient
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getIsrecently() {
		return isrecently;
	}

	public void setIsrecently(String isrecently) {
		this.isrecently = isrecently;
	}

	@Transient
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = DicReparetype.class)
	@JoinColumn(name = "rtypeid")
	@NotFound(action = NotFoundAction.IGNORE)
	public DicReparetype getDicReparetype() {
		return dicReparetype;
	}

	public void setDicReparetype(DicReparetype dicReparetype) {
		this.dicReparetype = dicReparetype;
	}

	@Transient
	public String getSchflag() {
		return schflag;
	}

	public void setSchflag(String schflag) {
		this.schflag = schflag;
	}

	// public String getRtypeid() {
	// return rtypeid;
	// }
	//
	// public void setRtypeid(String rtypeid) {
	// this.rtypeid = rtypeid;
	// }

}