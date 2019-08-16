package com.tlys.equ.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.tlys.dic.model.DicSinocorp;

/**
 * 
 * @author ccsong 2012-6-26 ÉÏÎç10:22:43
 */
@Entity
@Table(name = "TB_ZBC_EQU_MCARCOSTDETITEM")
public class EquMcarcostdetitem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947223059497860975L;
	private long id;
	private String month;
	public DicSinocorp dicSinocorp;
	// private String corpid;
	private String carno;
	private String detid;
	private String costid;
	private String costname;
	private double dueamount;
	private double realamount;
	private Date filltime;
	private String remarks;

	private long[] ids;
	private double[] dueamounts;
	private double[] realamounts;
	private String[] remarkses;

	// Constructors

	/** default constructor */
	public EquMcarcostdetitem() {
	}

	/** minimal constructor */
	public EquMcarcostdetitem(long id, String month, String carno, String detid, String costid) {
		this.id = id;
		this.month = month;
		this.carno = carno;
		this.detid = detid;
		this.costid = costid;
	}

	/** full constructor */
	public EquMcarcostdetitem(long id, String month, String carno, String detid, String costid, String costname,
			double dueamount, double realamount, Date filltime, String remarks) {
		this.id = id;
		this.month = month;
		this.carno = carno;
		this.detid = detid;
		this.costid = costid;
		this.costname = costname;
		this.dueamount = dueamount;
		this.realamount = realamount;
		this.filltime = filltime;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "CARNO", nullable = false, length = 8)
	public String getCarno() {
		return this.carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	@Column(name = "DETID", nullable = false, length = 24)
	public String getDetid() {
		return this.detid;
	}

	public void setDetid(String detid) {
		this.detid = detid;
	}

	@Column(name = "COSTID", nullable = false, length = 2)
	public String getCostid() {
		return this.costid;
	}

	public void setCostid(String costid) {
		this.costid = costid;
	}

	@Column(name = "COSTNAME", length = 30)
	public String getCostname() {
		return this.costname;
	}

	public void setCostname(String costname) {
		this.costname = costname;
	}

	@Column(name = "DUEAMOUNT", precision = 12)
	public double getDueamount() {
		return this.dueamount;
	}

	public void setDueamount(double dueamount) {
		this.dueamount = dueamount;
	}

	@Column(name = "REALAMOUNT", precision = 12)
	public double getRealamount() {
		return this.realamount;
	}

	public void setRealamount(double realamount) {
		this.realamount = realamount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FILLTIME", length = 7)
	public Date getFilltime() {
		return this.filltime;
	}

	public void setFilltime(Date filltime) {
		this.filltime = filltime;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = DicSinocorp.class)
	@JoinColumn(name = "corpid")
	@NotFound(action = NotFoundAction.EXCEPTION)
	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	@Transient
	public double[] getDueamounts() {
		return dueamounts;
	}

	public void setDueamounts(double[] dueamounts) {
		this.dueamounts = dueamounts;
	}

	@Transient
	public double[] getRealamounts() {
		return realamounts;
	}

	public void setRealamounts(double[] realamounts) {
		this.realamounts = realamounts;
	}

	@Transient
	public String[] getRemarkses() {
		return remarkses;
	}

	public void setRemarkses(String[] remarkses) {
		this.remarkses = remarkses;
	}

	@Transient
	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	// public String getCorpid() {
	// return corpid;
	// }
	//
	// public void setCorpid(String corpid) {
	// this.corpid = corpid;
	// }

}