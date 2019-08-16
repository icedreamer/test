package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * EquCarNpinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_CAR_NPINFO")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class EquCarNpinfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4104851651882803375L;
	// Fields
	@Id
	@Column(name = "CARNO", unique = true, nullable = false, length = 8)
	private String carno;
	private Long fixeddist;
	private Long maxwidth;
	private Long maxhight;
	private Long centerlinehight;
	private Long axisdist;
	private Long wheelidameter;
	private Double aixweight;
	private Long ecoghigh;
	private Long ecoghight;
	private String brakeequipmentno;
	private String redirectno;
	private Double semidiameter;
	private Long maxloadweight;
	private Long minloadweight;
	private Double totalcapacity;
	private Double validcapacity;
	private Double workpressure;
	private String worktemperature;
	private String tankmateid;
	private Long thickness;
	@Transient
	private String carnos;

	// Constructors

	/** default constructor */
	public EquCarNpinfo() {
	}

	/** minimal constructor */
	public EquCarNpinfo(String carno, String tankmateid) {
		this.carno = carno;
		this.tankmateid = tankmateid;
	}

	/** full constructor */
	public EquCarNpinfo(String carno, Long fixeddist, Long maxwidth, Long maxhight, Long centerlinehight,
			Long axisdist, Long wheelidameter, Double aixweight, Long ecoghigh, Long ecoghight,
			String brakeequipmentno, String redirectno, Double semidiameter, Long maxloadweight, Long minloadweight,
			Double totalcapacity, Double validcapacity, Double workpressure, String worktemperature, String tankmateid,
			Long thickness) {
		this.carno = carno;
		this.fixeddist = fixeddist;
		this.maxwidth = maxwidth;
		this.maxhight = maxhight;
		this.centerlinehight = centerlinehight;
		this.axisdist = axisdist;
		this.wheelidameter = wheelidameter;
		this.aixweight = aixweight;
		this.ecoghigh = ecoghigh;
		this.ecoghight = ecoghight;
		this.brakeequipmentno = brakeequipmentno;
		this.redirectno = redirectno;
		this.semidiameter = semidiameter;
		this.maxloadweight = maxloadweight;
		this.minloadweight = minloadweight;
		this.totalcapacity = totalcapacity;
		this.validcapacity = validcapacity;
		this.workpressure = workpressure;
		this.worktemperature = worktemperature;
		this.tankmateid = tankmateid;
		this.thickness = thickness;
	}

	// Property accessors

	public String getCarno() {
		return this.carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	@Column(name = "FIXEDDIST", precision = 5, scale = 0)
	public Long getFixeddist() {
		return this.fixeddist;
	}

	public void setFixeddist(Long fixeddist) {
		this.fixeddist = fixeddist;
	}

	@Column(name = "MAXWIDTH", precision = 6, scale = 0)
	public Long getMaxwidth() {
		return this.maxwidth;
	}

	public void setMaxwidth(Long maxwidth) {
		this.maxwidth = maxwidth;
	}

	@Column(name = "MAXHIGHT", precision = 6, scale = 0)
	public Long getMaxhight() {
		return this.maxhight;
	}

	public void setMaxhight(Long maxhight) {
		this.maxhight = maxhight;
	}

	@Column(name = "CENTERLINEHIGHT", precision = 4, scale = 0)
	public Long getCenterlinehight() {
		return this.centerlinehight;
	}

	public void setCenterlinehight(Long centerlinehight) {
		this.centerlinehight = centerlinehight;
	}

	@Column(name = "AXISDIST", precision = 5, scale = 0)
	public Long getAxisdist() {
		return this.axisdist;
	}

	public void setAxisdist(Long axisdist) {
		this.axisdist = axisdist;
	}

	@Column(name = "WHEELIDAMETER", precision = 4, scale = 0)
	public Long getWheelidameter() {
		return this.wheelidameter;
	}

	public void setWheelidameter(Long wheelidameter) {
		this.wheelidameter = wheelidameter;
	}

	@Column(name = "AIXWEIGHT", precision = 5)
	public Double getAixweight() {
		return this.aixweight;
	}

	public void setAixweight(Double aixweight) {
		this.aixweight = aixweight;
	}

	@Column(name = "ECOGHIGH", precision = 4, scale = 0)
	public Long getEcoghigh() {
		return this.ecoghigh;
	}

	public void setEcoghigh(Long ecoghigh) {
		this.ecoghigh = ecoghigh;
	}

	@Column(name = "ECOGHIGHT", precision = 4, scale = 0)
	public Long getEcoghight() {
		return this.ecoghight;
	}

	public void setEcoghight(Long ecoghight) {
		this.ecoghight = ecoghight;
	}

	@Column(name = "BRAKEEQUIPMENTNO", length = 30)
	public String getBrakeequipmentno() {
		return this.brakeequipmentno;
	}

	public void setBrakeequipmentno(String brakeequipmentno) {
		this.brakeequipmentno = brakeequipmentno;
	}

	@Column(name = "REDIRECTNO", length = 30)
	public String getRedirectno() {
		return this.redirectno;
	}

	public void setRedirectno(String redirectno) {
		this.redirectno = redirectno;
	}

	@Column(name = "SEMIDIAMETER", precision = 6)
	public Double getSemidiameter() {
		return this.semidiameter;
	}

	public void setSemidiameter(Double semidiameter) {
		this.semidiameter = semidiameter;
	}

	@Column(name = "MAXLOADWEIGHT", precision = 4, scale = 0)
	public Long getMaxloadweight() {
		return this.maxloadweight;
	}

	public void setMaxloadweight(Long maxloadweight) {
		this.maxloadweight = maxloadweight;
	}

	@Column(name = "MINLOADWEIGHT", precision = 4, scale = 0)
	public Long getMinloadweight() {
		return this.minloadweight;
	}

	public void setMinloadweight(Long minloadweight) {
		this.minloadweight = minloadweight;
	}

	@Column(name = "TOTALCAPACITY", precision = 5, scale = 1)
	public Double getTotalcapacity() {
		return this.totalcapacity;
	}

	public void setTotalcapacity(Double totalcapacity) {
		this.totalcapacity = totalcapacity;
	}

	@Column(name = "VALIDCAPACITY", precision = 5, scale = 1)
	public Double getValidcapacity() {
		return this.validcapacity;
	}

	public void setValidcapacity(Double validcapacity) {
		this.validcapacity = validcapacity;
	}

	@Column(name = "WORKPRESSURE", precision = 4)
	public Double getWorkpressure() {
		return this.workpressure;
	}

	public void setWorkpressure(Double workpressure) {
		this.workpressure = workpressure;
	}

	@Column(name = "WORKTEMPERATURE", length = 30)
	public String getWorktemperature() {
		return this.worktemperature;
	}

	public void setWorktemperature(String worktemperature) {
		this.worktemperature = worktemperature;
	}

	@Column(name = "TANKMATEID", nullable = false, length = 2)
	public String getTankmateid() {
		return this.tankmateid;
	}

	public void setTankmateid(String tankmateid) {
		this.tankmateid = tankmateid;
	}

	@Column(name = "THICKNESS", precision = 4, scale = 0)
	public Long getThickness() {
		return this.thickness;
	}

	public void setThickness(Long thickness) {
		this.thickness = thickness;
	}

	public String getCarnos() {
		return carnos;
	}

	public void setCarnos(String carnos) {
		this.carnos = carnos;
	}

}