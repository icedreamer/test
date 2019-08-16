package com.tlys.equ.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * EquCarCertificate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_CAR_CERTIFICATE")
public class EquCarCertificate implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4074195563905470925L;
	private Long id;
	private String trainno;
	private Date certdate;
	private String saftcertcode;
	private String trancertcode;
	private String isvalid;
	private String usercertcode;
	private String carnos;
	private String isvalidDIC;

	/** default constructor */
	public EquCarCertificate() {
	}

	/** minimal constructor */
	public EquCarCertificate(Long id, String trainno, Date certdate,
			String isvalid) {
		this.id = id;
		this.trainno = trainno;
		this.certdate = certdate;
		this.isvalid = isvalid;
	}

	/** full constructor */
	public EquCarCertificate(Long id, String trainno, Date certdate,
			String saftcertcode, String trancertcode, String isvalid,
			String usercertcode) {
		this.id = id;
		this.trainno = trainno;
		this.certdate = certdate;
		this.saftcertcode = saftcertcode;
		this.trancertcode = trancertcode;
		this.isvalid = isvalid;
		this.usercertcode = usercertcode;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oraSeqGenerator")
	@SequenceGenerator(name = "oraSeqGenerator", sequenceName = "SEQ_TB_ZBC_EQU_CAR_CERTIFICATE", allocationSize = 1)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TRAINNO", nullable = false, length = 8)
	public String getTrainno() {
		return this.trainno;
	}

	public void setTrainno(String trainno) {
		this.trainno = trainno;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CERTDATE", nullable = false, length = 7)
	public Date getCertdate() {
		return this.certdate;
	}

	public void setCertdate(Date certdate) {
		this.certdate = certdate;
	}

	public void setCertdate(String certdate) throws ParseException {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		this.certdate = format1.parse(certdate);
		System.out.print(certdate);
	}

	@Column(name = "SAFTCERTCODE", length = 10)
	public String getSaftcertcode() {
		return this.saftcertcode;
	}

	public void setSaftcertcode(String saftcertcode) {
		this.saftcertcode = saftcertcode;
	}

	@Column(name = "TRANCERTCODE", length = 10)
	public String getTrancertcode() {
		return this.trancertcode;
	}

	public void setTrancertcode(String trancertcode) {
		this.trancertcode = trancertcode;
	}

	@Column(name = "ISVALID", nullable = false, length = 1)
	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getUsercertcode() {
		return usercertcode;
	}

	public void setUsercertcode(String usercertcode) {
		this.usercertcode = usercertcode;
	} // Constructors

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

}