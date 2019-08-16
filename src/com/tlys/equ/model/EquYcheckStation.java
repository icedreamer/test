package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * EquYcheckStation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_YCHECK_STATION")
public class EquYcheckStation implements java.io.Serializable {

	// Fields

	private Long id;
	private String yinspectionbureauid;
	private String yinspectionno;
	private String stationid;
	private String bureauid;
	private String datatype;
	private String[] loadstationids;
	private String[] unloadstationids;

	// Constructors

	/** default constructor */
	public EquYcheckStation() {
	}

	/** full constructor */
	public EquYcheckStation(Long id, String yinspectionbureauid, String yinspectionno,
			String stationid, String bureauid, String datatype) {
		this.id = id;
		this.yinspectionbureauid = yinspectionbureauid;
		this.yinspectionno = yinspectionno;
		this.stationid = stationid;
		this.bureauid = bureauid;
		this.datatype = datatype;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_EQU_YCHECK_STATION")
	@GenericGenerator(name = "SEQ_TB_ZBC_EQU_YCHECK_STATION", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_TB_ZBC_EQU_YCHECK_STATION") })
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YINSPECTIONBUREAUID", nullable = false, length = 23)
	public String getYinspectionbureauid() {
		return this.yinspectionbureauid;
	}

	public void setYinspectionbureauid(String yinspectionbureauid) {
		this.yinspectionbureauid = yinspectionbureauid;
	}

	@Column(name = "YINSPECTIONNO", nullable = false, length = 15)
	public String getYinspectionno() {
		return this.yinspectionno;
	}

	public void setYinspectionno(String yinspectionno) {
		this.yinspectionno = yinspectionno;
	}

	@Column(name = "STATIONID", nullable = false, length = 6)
	public String getStationid() {
		return this.stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Column(name = "DATATYPE", nullable = false, length = 1)
	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	@Transient
	public String[] getLoadstationids() {
		return loadstationids;
	}

	public void setLoadstationids(String[] loadstationids) {
		this.loadstationids = loadstationids;
	}

	@Transient
	public String[] getUnloadstationids() {
		return unloadstationids;
	}

	public void setUnloadstationids(String[] unloadstationids) {
		this.unloadstationids = unloadstationids;
	}

}