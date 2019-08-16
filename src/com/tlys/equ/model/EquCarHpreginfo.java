package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * EquCarHpreginfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_CAR_HPREGINFO")
public class EquCarHpreginfo implements java.io.Serializable {

	// Fields

	private String carno;
	private String useregisterno;
	private String internalno;
	private String containername;
	private String makecorp;
	private String makelicense;
	private String usedate;
	private String carchassisno;
	private String cartankno;
	private String registercode;
	private String registerdepartment;
	private String remarks;
	private String usedateStr;
	private String carnos;

	// Constructors

	@Transient
	public String getUsedateStr() {
		try {
			return null != usedate && !"".equals(usedate) ? EquCar.parseFormat
					.format(EquCar.shortFormat.parse(usedate)) : "";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	/** default constructor */
	public EquCarHpreginfo() {
	}

	/** minimal constructor */
	public EquCarHpreginfo(String carno) {
		this.carno = carno;
	}

	/** full constructor */
	public EquCarHpreginfo(String carno, String useregisterno, String internalno, String containername,
			String makecorp, String makelicense, String usedate, String carchassisno, String cartankno,
			String registercode, String registerdepartment, String remarks) {
		this.carno = carno;
		this.useregisterno = useregisterno;
		this.internalno = internalno;
		this.containername = containername;
		this.makecorp = makecorp;
		this.makelicense = makelicense;
		this.usedate = usedate;
		this.carchassisno = carchassisno;
		this.cartankno = cartankno;
		this.registercode = registercode;
		this.registerdepartment = registerdepartment;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "CARNO", unique = true, nullable = false, length = 8)
	public String getCarno() {
		return this.carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	@Column(name = "USEREGISTERNO", length = 30)
	public String getUseregisterno() {
		return this.useregisterno;
	}

	public void setUseregisterno(String useregisterno) {
		this.useregisterno = useregisterno;
	}

	@Column(name = "INTERNALNO", length = 30)
	public String getInternalno() {
		return this.internalno;
	}

	public void setInternalno(String internalno) {
		this.internalno = internalno;
	}

	@Column(name = "CONTAINERNAME", length = 30)
	public String getContainername() {
		return this.containername;
	}

	public void setContainername(String containername) {
		this.containername = containername;
	}

	@Column(name = "MAKECORP", length = 60)
	public String getMakecorp() {
		return this.makecorp;
	}

	public void setMakecorp(String makecorp) {
		this.makecorp = makecorp;
	}

	@Column(name = "MAKELICENSE", length = 60)
	public String getMakelicense() {
		return this.makelicense;
	}

	public void setMakelicense(String makelicense) {
		this.makelicense = makelicense;
	}

	@Column(name = "USEDATE", length = 8)
	public String getUsedate() {
		return this.usedate;
	}

	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}

	@Column(name = "CARCHASSISNO", length = 30)
	public String getCarchassisno() {
		return this.carchassisno;
	}

	public void setCarchassisno(String carchassisno) {
		this.carchassisno = carchassisno;
	}

	@Column(name = "CARTANKNO", length = 30)
	public String getCartankno() {
		return this.cartankno;
	}

	public void setCartankno(String cartankno) {
		this.cartankno = cartankno;
	}

	@Column(name = "REGISTERCODE", length = 30)
	public String getRegistercode() {
		return this.registercode;
	}

	public void setRegistercode(String registercode) {
		this.registercode = registercode;
	}

	@Column(name = "REGISTERDEPARTMENT", length = 60)
	public String getRegisterdepartment() {
		return this.registerdepartment;
	}

	public void setRegisterdepartment(String registerdepartment) {
		this.registerdepartment = registerdepartment;
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

}