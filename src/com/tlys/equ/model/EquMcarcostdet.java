package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author ccsong 2012-6-26 ÉÏÎç10:22:12
 */
@Entity
@Table(name = "TB_ZBC_EQU_MCARCOSTDET")
public class EquMcarcostdet implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2612870671137041895L;
	private String detid;
	private String monthlyid;
	private String month;
	private String corpid;
	private String carno;
	// private DicSinocorp dicSinocorp;
	// private EquCar equCar;
	// private DicSinocorp dicRentcorp;
	private String rentcorpid;
	private double drental;
	private String mstartdate;
	private String menddate;
	private long musedays;
	private String cartypeids;
	private String corpids;
	private String rentcorpids;
	private String startdate;
	private String enddate;
	private String costids;
	private String year;
	private String carnos;
	private String costid;

	// Constructors

	/** default constructor */
	public EquMcarcostdet() {
	}

	/** minimal constructor */
	public EquMcarcostdet(String detid, String monthlyid, String month) {
		this.detid = detid;
		this.monthlyid = monthlyid;
		this.month = month;
	}

	/** full constructor */
	public EquMcarcostdet(String detid, String monthlyid, String month, double drental,
			String mstartdate, String menddate, long musedays) {
		this.detid = detid;
		this.monthlyid = monthlyid;
		this.month = month;
		this.drental = drental;
		this.mstartdate = mstartdate;
		this.menddate = menddate;
		this.musedays = musedays;
	}

	// Property accessors
	@Id
	@Column(name = "DETID", unique = true, nullable = false, length = 24)
	public String getDetid() {
		return this.detid;
	}

	public void setDetid(String detid) {
		this.detid = detid;
	}

	@Column(name = "MONTHLYID", nullable = false, length = 15)
	public String getMonthlyid() {
		return this.monthlyid;
	}

	public void setMonthlyid(String monthlyid) {
		this.monthlyid = monthlyid;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "DRENTAL", precision = 10)
	public double getDrental() {
		return this.drental;
	}

	public void setDrental(double drental) {
		this.drental = drental;
	}

	@Column(name = "MSTARTDATE", length = 8)
	public String getMstartdate() {
		return this.mstartdate;
	}

	public void setMstartdate(String mstartdate) {
		this.mstartdate = mstartdate;
	}

	@Column(name = "MENDDATE", length = 8)
	public String getMenddate() {
		return this.menddate;
	}

	public void setMenddate(String menddate) {
		this.menddate = menddate;
	}

	@Column(name = "MUSEDAYS", precision = 22, scale = 0)
	public long getMusedays() {
		return this.musedays;
	}

	public void setMusedays(long musedays) {
		this.musedays = musedays;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = DicSinocorp.class)
	// @JoinColumn(name = "corpid")
	// @NotFound(action = NotFoundAction.EXCEPTION)
	// public DicSinocorp getDicSinocorp() {
	// return dicSinocorp;
	// }
	//
	// public void setDicSinocorp(DicSinocorp dicSinocorp) {
	// this.dicSinocorp = dicSinocorp;
	// }

	@Transient
	public String getCartypeids() {
		return cartypeids;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	@Transient
	public String getRentcorpids() {
		return rentcorpids;
	}

	@Transient
	public String getCostids() {
		return costids;
	}

	public void setCartypeids(String cartypeids) {
		this.cartypeids = cartypeids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public void setRentcorpids(String rentcorpids) {
		this.rentcorpids = rentcorpids;
	}

	public void setCostids(String costids) {
		this.costids = costids;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = EquCar.class)
	// @JoinColumn(name = "carno")
	// @NotFound(action = NotFoundAction.EXCEPTION)
	// public EquCar getEquCar() {
	// return equCar;
	// }
	//
	// public void setEquCar(EquCar equCar) {
	// this.equCar = equCar;
	// }

	@Transient
	public String getStartdate() {
		return startdate;
	}

	@Transient
	public String getEnddate() {
		return enddate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	// @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,
	// targetEntity = DicSinocorp.class)
	// @JoinColumn(name = "rentcorpid")
	// @NotFound(action = NotFoundAction.IGNORE)
	// public DicSinocorp getDicRentcorp() {
	// return dicRentcorp;
	// }
	//
	// public void setDicRentcorp(DicSinocorp dicRentcorp) {
	// this.dicRentcorp = dicRentcorp;
	// }

	public String getCorpid() {
		return corpid;
	}

	public String getCarno() {
		return carno;
	}

	public String getRentcorpid() {
		return rentcorpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public void setRentcorpid(String rentcorpid) {
		this.rentcorpid = rentcorpid;
	}

	@Transient
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Transient
	public String getCarnos() {
		return carnos;
	}

	public void setCarnos(String carnos) {
		this.carnos = carnos;
	}

	@Transient
	public String getCostid() {
		return costid;
	}

	public void setCostid(String costid) {
		this.costid = costid;
	}

}