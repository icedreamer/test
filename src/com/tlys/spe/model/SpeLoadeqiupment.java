package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * SpeLoadeqiupment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_LOADEQIUPMENT")
public class SpeLoadeqiupment implements java.io.Serializable {

	// Fields

	private String equid;
	private String linename;
	private String corpid;
	private String equname;
	private String corpname;
	private String crwid;
	private String kind;
	private String pcategoryname;
	private String psecondkindname;
	private Long positions;
	private Long dayjobs;
	private Double dayability;
	private String meastypeid;
	private String loaddepartmnet;
	private String cardispatcher;
	private String layouttype;
	private String distancetohouse;
	private String internallinename;
	private String stationid;
	private String stationname;
	private String bureauid;
	private String bureauname;
	private String distancetostation;
	private String remarks;
	private Date createdtime;

	private String kindDIC;
	private String crwidDIC;
	private String meastypeidDIC;

	/** default constructor */
	public SpeLoadeqiupment() {
	}

	/** minimal constructor */
	public SpeLoadeqiupment(String equid, String linename, String corpid,
			String equname, String crwid, String kind, String pcategoryname,
			String psecondkindname, String meastypeid, String stationid,
			String bureauid) {
		this.equid = equid;
		this.linename = linename;
		this.corpid = corpid;
		this.equname = equname;
		this.crwid = crwid;
		this.kind = kind;
		this.pcategoryname = pcategoryname;
		this.psecondkindname = psecondkindname;
		this.meastypeid = meastypeid;
		this.stationid = stationid;
		this.bureauid = bureauid;
	}

	/** full constructor */
	public SpeLoadeqiupment(String equid, String linename, String corpid,
			String equname, String corpname, String crwid, String kind,
			String pcategoryname, String psecondkindname, Long positions,
			Long dayjobs, Double dayability, String meastypeid,
			String loaddepartmnet, String cardispatcher, String layouttype,
			String distancetohouse, String internallinename, String stationid,
			String stationname, String bureauid, String bureauname,
			String distancetostation, String remarks, Date createdtime) {
		this.equid = equid;
		this.linename = linename;
		this.corpid = corpid;
		this.equname = equname;
		this.corpname = corpname;
		this.crwid = crwid;
		this.kind = kind;
		this.pcategoryname = pcategoryname;
		this.psecondkindname = psecondkindname;
		this.positions = positions;
		this.dayjobs = dayjobs;
		this.dayability = dayability;
		this.meastypeid = meastypeid;
		this.loaddepartmnet = loaddepartmnet;
		this.cardispatcher = cardispatcher;
		this.layouttype = layouttype;
		this.distancetohouse = distancetohouse;
		this.internallinename = internallinename;
		this.stationid = stationid;
		this.stationname = stationname;
		this.bureauid = bureauid;
		this.bureauname = bureauname;
		this.distancetostation = distancetostation;
		this.remarks = remarks;
		this.createdtime = createdtime;
	}

	// Property accessors
	@Id
	@Column(name = "EQUID", unique = true, nullable = false, length = 6)
	public String getEquid() {
		return this.equid;
	}

	public void setEquid(String equid) {
		this.equid = equid;
	}

	@Column(name = "LINENAME", nullable = false, length = 20)
	public String getLinename() {
		return this.linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "EQUNAME", nullable = false, length = 30)
	public String getEquname() {
		return this.equname;
	}

	public void setEquname(String equname) {
		this.equname = equname;
	}

	@Column(name = "CORPNAME", length = 20)
	public String getCorpname() {
		return this.corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	@Column(name = "CRWID", nullable = false, length = 3)
	public String getCrwid() {
		return this.crwid;
	}

	public void setCrwid(String crwid) {
		this.crwid = crwid;
	}

	@Transient
	public String getCrwidDIC() {
		return crwidDIC;
	}

	public void setCrwidDIC(String crwidDIC) {
		this.crwidDIC = crwidDIC;
	}

	@Column(name = "KIND", nullable = false, length = 1)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Transient
	public String getKindDIC() {
		// L:装U:卸A:可装可卸
		if ("L".equals(kind)) {
			return "启用";
		} else if ("U".equals(kind)) {
			return "停用";
		} else {
			return "可装可卸";
		}
	}

	public void setKindDIC(String kindDIC) {
		this.kindDIC = kindDIC;
	}

	@Column(name = "PCATEGORYNAME", nullable = false, length = 40)
	public String getPcategoryname() {
		return this.pcategoryname;
	}

	public void setPcategoryname(String pcategoryname) {
		this.pcategoryname = pcategoryname;
	}

	@Column(name = "PSECONDKINDNAME", nullable = false, length = 40)
	public String getPsecondkindname() {
		return this.psecondkindname;
	}

	public void setPsecondkindname(String psecondkindname) {
		this.psecondkindname = psecondkindname;
	}

	@Column(name = "POSITIONS", precision = 22, scale = 0)
	public Long getPositions() {
		return this.positions;
	}

	public void setPositions(Long positions) {
		this.positions = positions;
	}

	@Column(name = "DAYJOBS", precision = 22, scale = 0)
	public Long getDayjobs() {
		return this.dayjobs;
	}

	public void setDayjobs(Long dayjobs) {
		this.dayjobs = dayjobs;
	}

	@Column(name = "DAYABILITY", precision = 6)
	public Double getDayability() {
		return this.dayability;
	}

	public void setDayability(Double dayability) {
		this.dayability = dayability;
	}

	@Column(name = "MEASTYPEID", nullable = false, length = 2)
	public String getMeastypeid() {
		return this.meastypeid;
	}

	public void setMeastypeid(String meastypeid) {
		this.meastypeid = meastypeid;
	}

	@Transient
	public String getMeastypeidDIC() {
		return meastypeidDIC;
	}

	public void setMeastypeidDIC(String meastypeidDIC) {
		this.meastypeidDIC = meastypeidDIC;
	}

	@Column(name = "LOADDEPARTMNET", length = 40)
	public String getLoaddepartmnet() {
		return this.loaddepartmnet;
	}

	public void setLoaddepartmnet(String loaddepartmnet) {
		this.loaddepartmnet = loaddepartmnet;
	}

	@Column(name = "CARDISPATCHER", length = 40)
	public String getCardispatcher() {
		return this.cardispatcher;
	}

	public void setCardispatcher(String cardispatcher) {
		this.cardispatcher = cardispatcher;
	}

	@Column(name = "LAYOUTTYPE", length = 30)
	public String getLayouttype() {
		return this.layouttype;
	}

	public void setLayouttype(String layouttype) {
		this.layouttype = layouttype;
	}

	@Column(name = "DISTANCETOHOUSE", length = 30)
	public String getDistancetohouse() {
		return this.distancetohouse;
	}

	public void setDistancetohouse(String distancetohouse) {
		this.distancetohouse = distancetohouse;
	}

	@Column(name = "INTERNALLINENAME", length = 30)
	public String getInternallinename() {
		return this.internallinename;
	}

	public void setInternallinename(String internallinename) {
		this.internallinename = internallinename;
	}

	@Column(name = "STATIONID", nullable = false, length = 6)
	public String getStationid() {
		return this.stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	@Column(name = "STATIONNAME", length = 20)
	public String getStationname() {
		return this.stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Column(name = "BUREAUNAME", length = 20)
	public String getBureauname() {
		return this.bureauname;
	}

	public void setBureauname(String bureauname) {
		this.bureauname = bureauname;
	}

	@Column(name = "DISTANCETOSTATION", length = 30)
	public String getDistancetostation() {
		return this.distancetostation;
	}

	public void setDistancetostation(String distancetostation) {
		this.distancetostation = distancetostation;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

}