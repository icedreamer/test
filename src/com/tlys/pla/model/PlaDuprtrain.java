package com.tlys.pla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * PlaDuprtrain entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_DUPRTRAIN")
public class PlaDuprtrain implements java.io.Serializable {

	// Fields

	private Long id;
	private String requestdate;
	private String acceptcarno;
	private String requesttype;
	private Date indbtime;
	private Long requestcars;
	private Double requestamount;
	private Long acceptcars;
	private Double acceptamount;
	private Long loadcars;
	private Double loadamount;
	private Long mtotalcars;
	private String remarks;
	
	private String loadamountDIC;
	private String requestamountDIC;
	private String acceptamountDIC;
	private String acceptcarnos;
	
	private String requestdateDIC;

	// Constructors

	/** default constructor */
	public PlaDuprtrain() {
	}

	/** minimal constructor */
	public PlaDuprtrain(Long id, String requestdate, String acceptcarno, String requesttype) {
		this.id = id;
		this.requestdate = requestdate;
		this.acceptcarno = acceptcarno;
		this.requesttype = requesttype;
	}

	/** full constructor */
	public PlaDuprtrain(Long id, String requestdate, String acceptcarno, String requesttype, Date indbtime, Long requestcars,
			Double requestamount, Long acceptcars, Double acceptamount, Long loadcars, Double loadamount, Long mtotalcars, String remarks) {
		this.id = id;
		this.requestdate = requestdate;
		this.acceptcarno = acceptcarno;
		this.requesttype = requesttype;
		this.indbtime = indbtime;
		this.requestcars = requestcars;
		this.requestamount = requestamount;
		this.acceptcars = acceptcars;
		this.acceptamount = acceptamount;
		this.loadcars = loadcars;
		this.loadamount = loadamount;
		this.mtotalcars = mtotalcars;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 16, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REQUESTDATE", nullable = false, length = 8)
	public String getRequestdate() {
		return this.requestdate;
	}

	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}

	@Column(name = "ACCEPTCARNO", nullable = false, length = 20)
	public String getAcceptcarno() {
		return this.acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	@Column(name = "REQUESTTYPE", nullable = false, length = 1)
	public String getRequesttype() {
		return this.requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INDBTIME", length = 7)
	public Date getIndbtime() {
		return this.indbtime;
	}

	public void setIndbtime(Date indbtime) {
		this.indbtime = indbtime;
	}

	@Column(name = "REQUESTCARS", precision = 22, scale = 0)
	public Long getRequestcars() {
		return this.requestcars;
	}

	public void setRequestcars(Long requestcars) {
		this.requestcars = requestcars;
	}

	@Column(name = "REQUESTAMOUNT", precision = 12, scale = 4)
	public Double getRequestamount() {
		return this.requestamount;
	}

	public void setRequestamount(Double requestamount) {
		this.requestamount = requestamount;
	}

	@Column(name = "ACCEPTCARS", precision = 22, scale = 0)
	public Long getAcceptcars() {
		return this.acceptcars;
	}

	public void setAcceptcars(Long acceptcars) {
		this.acceptcars = acceptcars;
	}

	@Column(name = "ACCEPTAMOUNT", precision = 12, scale = 4)
	public Double getAcceptamount() {
		return this.acceptamount;
	}

	public void setAcceptamount(Double acceptamount) {
		this.acceptamount = acceptamount;
	}

	@Column(name = "LOADCARS", precision = 22, scale = 0)
	public Long getLoadcars() {
		return this.loadcars;
	}

	public void setLoadcars(Long loadcars) {
		this.loadcars = loadcars;
	}

	@Column(name = "LOADAMOUNT", precision = 12, scale = 4)
	public Double getLoadamount() {
		return this.loadamount;
	}

	public void setLoadamount(Double loadamount) {
		this.loadamount = loadamount;
	}

	@Column(name = "MTOTALCARS", precision = 22, scale = 0)
	public Long getMtotalcars() {
		return this.mtotalcars;
	}

	public void setMtotalcars(Long mtotalcars) {
		this.mtotalcars = mtotalcars;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	@Transient
	public String getLoadamountDIC() {
		loadamountDIC = loadamount==null?"0":loadamount.toString();
		int dotp = loadamountDIC.indexOf(".0");
		if(dotp > -1){
			loadamountDIC = loadamountDIC.substring(0,dotp);
		}
		return loadamountDIC;
	}

	public void setLoadamountDIC(String loadamountDIC) {
		this.loadamountDIC = loadamountDIC;
	}

	@Transient
	public String getRequestamountDIC() {
		requestamountDIC = requestamount==null?"0":requestamount.toString();
		int dotp = requestamountDIC.indexOf(".0");
		if(dotp > -1){
			requestamountDIC = requestamountDIC.substring(0,dotp);
		}
		return requestamountDIC;
	}

	public void setRequestamountDIC(String requestamountDIC) {
		this.requestamountDIC = requestamountDIC;
	}

	@Transient
	public String getAcceptamountDIC() {
		
		acceptamountDIC = acceptamount==null?"0":acceptamount.toString();
		int dotp = acceptamountDIC.indexOf(".0");
		if(dotp > -1){
			acceptamountDIC = acceptamountDIC.substring(0,dotp);
		}
		return acceptamountDIC;
	}

	public void setAcceptamountDIC(String acceptamountDIC) {
		this.acceptamountDIC = acceptamountDIC;
	}
	@Transient
	public String getAcceptcarnos() {
		return acceptcarnos;
	}

	public void setAcceptcarnos(String acceptcarnos) {
		this.acceptcarnos = acceptcarnos;
	}

	@Transient
	public String getRequestdateDIC() {
		return requestdateDIC;
	}

	public void setRequestdateDIC(String requestdateDIC) {
		this.requestdateDIC = requestdateDIC;
	}

}