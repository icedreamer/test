package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CtlCustomerreceiver entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CUSTOMERRECEIVER")
public class CtlCustomerreceiver implements java.io.Serializable {

	// Fields

	private Long id;
	private String customerid;
	private String goalprovinceid;
	private String goaladdress;
	private String receiverid;
	private String receivername;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCustomerreceiver() {
	}

	/** minimal constructor */
	public CtlCustomerreceiver(Long id, String customerid, String receiverid, String receivername) {
		this.id = id;
		this.customerid = customerid;
		this.receiverid = receiverid;
		this.receivername = receivername;
	}

	/** full constructor */
	public CtlCustomerreceiver(Long id, String customerid, String goalprovinceid, String goaladdress, String receiverid, String receivername,
			Long records) {
		this.id = id;
		this.customerid = customerid;
		this.goalprovinceid = goalprovinceid;
		this.goaladdress = goaladdress;
		this.receiverid = receiverid;
		this.receivername = receivername;
		this.records = records;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CUSTOMERID", nullable = false, length = 8)
	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@Column(name = "GOALPROVINCEID", length = 6)
	public String getGoalprovinceid() {
		return this.goalprovinceid;
	}

	public void setGoalprovinceid(String goalprovinceid) {
		this.goalprovinceid = goalprovinceid;
	}

	@Column(name = "GOALADDRESS", length = 60)
	public String getGoaladdress() {
		return this.goaladdress;
	}

	public void setGoaladdress(String goaladdress) {
		this.goaladdress = goaladdress;
	}

	@Column(name = "RECEIVERID", nullable = false, length = 8)
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "RECEIVERNAME", nullable = false, length = 100)
	public String getReceivername() {
		return this.receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	@Column(name = "RECORDS", precision = 22, scale = 0)
	public Long getRecords() {
		return this.records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return this.receivername;
	}
	
	

}