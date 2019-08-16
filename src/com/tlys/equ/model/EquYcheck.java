package com.tlys.equ.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * EquYcheck entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_YCHECK",  uniqueConstraints = @UniqueConstraint(columnNames = { "YEAR",
		"CORPID", "INSPECTIONTYPE" }))
public class EquYcheck implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733371994527800985L;
	private String yinspectionno;
	private String year;
	private String areaid;
	private String corpid;
	private String corpfullname;
	private String regiternumber;
	private String corpcode;
	private Integer carnumber;
	private String creator;
	private Date createdtime;
	private String filename;
	private String path;
	private String readtimes;
	private String status;
	private String remarks;
	private String[] yinspectionnos;
	private String inspectiontype;
	// 过轨运输协议 合同编号
	private String contractno;
	// 过轨运输协议 合同时间
	private Date contractdate;

	private String footer;

	// Constructors

	/** default constructor */
	public EquYcheck() {
	}

	/** minimal constructor */
	public EquYcheck(String yinspectionno, String year, String areaid, String corpid, String status) {
		this.yinspectionno = yinspectionno;
		this.year = year;
		this.areaid = areaid;
		this.corpid = corpid;
		this.status = status;
	}

	/** full constructor */
	public EquYcheck(String yinspectionno, String year, String areaid, String corpid, String corpfullname,
			String regiternumber, String corpcode, Integer carnumber, String creator, Date createdtime, String filename,
			String path, String readtimes, String status, String remarks) {
		this.yinspectionno = yinspectionno;
		this.year = year;
		this.areaid = areaid;
		this.corpid = corpid;
		this.corpfullname = corpfullname;
		this.regiternumber = regiternumber;
		this.corpcode = corpcode;
		this.carnumber = carnumber;
		this.creator = creator;
		this.createdtime = createdtime;
		this.filename = filename;
		this.path = path;
		this.readtimes = readtimes;
		this.status = status;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "YINSPECTIONNO", unique = true, nullable = false, length = 13)
	public String getYinspectionno() {
		return this.yinspectionno;
	}

	public void setYinspectionno(String yinspectionno) {
		this.yinspectionno = yinspectionno;
	}

	@Column(name = "YEAR", nullable = false, length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPFULLNAME", length = 100)
	public String getCorpfullname() {
		return this.corpfullname;
	}

	public void setCorpfullname(String corpfullname) {
		this.corpfullname = corpfullname;
	}

	@Column(name = "REGITERNUMBER", length = 30)
	public String getRegiternumber() {
		return this.regiternumber;
	}

	public void setRegiternumber(String regiternumber) {
		this.regiternumber = regiternumber;
	}

	@Column(name = "CORPCODE", length = 12)
	public String getCorpcode() {
		return this.corpcode;
	}

	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}

	@Column(name = "CARNUMBER", precision = 22, scale = 0)
	public Integer getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(Integer carnumber) {
		this.carnumber = carnumber;
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

	@Column(name = "FILENAME", length = 100)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "READTIMES", length = 0)
	public String getReadtimes() {
		return this.readtimes;
	}

	public void setReadtimes(String readtimes) {
		this.readtimes = readtimes;
	}

	@Column(name = "STATUS", nullable = false, length = 1, columnDefinition = "char(1) default '0'")
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

	@Transient
	public String[] getYinspectionnos() {
		return yinspectionnos;
	}

	public void setYinspectionnos(String[] yinspectionnos) {
		this.yinspectionnos = yinspectionnos;
	}

	@Transient
	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getInspectiontype() {
		return inspectiontype;
	}

	public void setInspectiontype(String inspectiontype) {
		this.inspectiontype = inspectiontype;
	}

	public String getContractno() {
		return contractno;
	}

	public Date getContractdate() {
		return contractdate;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public void setContractdate(Date contractdate) {
		this.contractdate = contractdate;
	}

}