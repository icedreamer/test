package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * EquCarRent entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_CAR_RENT")
public class EquCarRent implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6138088594411878554L;
	// Fields

	private Long id;
	private String carno;
	private String rentcorpid;
	private String startdate;
	private String enddate;
	private String isvalid;
	private String remarks;
	private Double yrental;
	private Double mavgrental;
	private Double drental;
	private String carnos;
	@SuppressWarnings("unused")
	private String isvalidDIC;

	// Constructors

	/** default constructor */
	public EquCarRent() {
	}

	/** minimal constructor */
	public EquCarRent(Long id, String carno, String rentcorpid, String isvalid) {
		this.id = id;
		this.carno = carno;
		this.rentcorpid = rentcorpid;
		this.isvalid = isvalid;
	}

	/** full constructor */
	public EquCarRent(Long id, String carno, String rentcorpid, String startdate, String enddate, String isvalid,
			String remarks) {
		this.id = id;
		this.carno = carno;
		this.rentcorpid = rentcorpid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.isvalid = isvalid;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oraSeqGenerator")
	@SequenceGenerator(name = "oraSeqGenerator", sequenceName = "SEQ_TB_ZBC_EQU_CAR_RENT", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CARNO", nullable = false, length = 8)
	public String getCarno() {
		return this.carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	@Column(name = "RENTCORPID", nullable = false, length = 8)
	public String getRentcorpid() {
		return this.rentcorpid;
	}

	public void setRentcorpid(String rentcorpid) {
		this.rentcorpid = rentcorpid;
	}

	@Column(name = "ISVALID", nullable = false, length = 1)
	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getCarnos() {
		return carnos;
	}

	public void setCarnos(String carnos) {
		this.carnos = carnos;
	}

	@Transient
	public String getIsvalidDIC() {
		if (isvalid.equals("1")) {
			return "有效";
		} else {
			return "无效";
		}
	}

	public void setIsvalidDIC(String isvalidDIC) {
		this.isvalidDIC = isvalidDIC;
	}

	public String getStartdate() {
		return startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public Double getYrental() {
		return yrental;
	}

	public Double getMavgrental() {
		return mavgrental;
	}

	public Double getDrental() {
		return drental;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setYrental(Double yrental) {
		this.yrental = yrental;
	}

	public void setMavgrental(Double mavgrental) {
		this.mavgrental = mavgrental;
	}

	public void setDrental(Double drental) {
		this.drental = drental;
	}

}