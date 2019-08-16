package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * SpePapers entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_PAPERS")
public class SpePapers implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -733405419455855623L;
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	private Long id;
	private String corpid;
	private String corpname;
	private String carno;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = SpePaperstype.class)
	@JoinColumn(name = "ptypeid")
	@NotFound(action = NotFoundAction.IGNORE)
	private SpePaperstype spePaperstype;

	private String papersname;

	private String formattype;

	private String path;
	private String remarks;
	private Date createdtime;
	private String filename;

	@Transient
	private String startTime;
	@Transient
	private String endTime;

	@Transient
	private String[] corpids;

	@Transient
	private String[] carkindids;

	@Transient
	private String[] cartypeids;

	@Transient
	private String[] goodsids;

	/** default constructor */
	public SpePapers() {
	}

	/** minimal constructor */
	public SpePapers(Long id, String corpid, String carno, String papersname, String formattype) {
		this.id = id;
		this.corpid = corpid;
		this.carno = carno;
		this.papersname = papersname;
		this.formattype = formattype;
	}

	/** full constructor */
	public SpePapers(Long id, String corpid, String corpname, String carno, String papersname, String formattype,
			String path, String remarks, Date createdtime) {
		this.id = id;
		this.corpid = corpid;
		this.corpname = corpname;
		this.carno = carno;
		this.papersname = papersname;
		this.formattype = formattype;
		this.path = path;
		this.remarks = remarks;
		this.createdtime = createdtime;
	}

	@Column(name = "CARNO", nullable = false, length = 8)
	public String getCarno() {
		return null != this.carno && !"".equals(this.carno) ? this.carno.trim() : "";
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public String[] getCorpids() {
		return corpids;
	}

	@Column(name = "CORPNAME", length = 20)
	public String getCorpname() {
		return this.corpname;
	}

	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	// Constructors

	public String getEndTime() {
		return endTime;
	}

	@Column(name = "FORMATTYPE", length = 10)
	public String getFormattype() {
		return this.formattype;
	}

	public Long getId() {
		return this.id;
	}

	// Property accessors

	@Column(name = "PAPERSNAME", nullable = false, length = 30)
	public String getPapersname() {
		return this.papersname;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public SpePaperstype getSpePaperstype() {
		return spePaperstype;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public void setCorpids(String[] corpids) {
		this.corpids = corpids;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setFormattype(String formattype) {
		this.formattype = formattype;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPapersname(String papersname) {
		this.papersname = papersname;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setSpePaperstype(SpePaperstype spePaperstype) {
		this.spePaperstype = spePaperstype;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String[] getCarkindids() {
		return carkindids;
	}

	public void setCarkindids(String[] carkindids) {
		this.carkindids = carkindids;
	}

	public String[] getCartypeids() {
		return cartypeids;
	}

	public void setCartypeids(String[] cartypeids) {
		this.cartypeids = cartypeids;
	}

	public String[] getGoodsids() {
		return goodsids;
	}

	public void setGoodsids(String[] goodsids) {
		this.goodsids = goodsids;
	}

}