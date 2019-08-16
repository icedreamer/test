package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcCtlCustomerendstationId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CtlCustomerendstationId implements java.io.Serializable {

	// Fields

	private String customerid;
	private String endstationid;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCustomerendstationId() {
	}

	/** minimal constructor */
	public CtlCustomerendstationId(String customerid, String endstationid) {
		this.customerid = customerid;
		this.endstationid = endstationid;
	}

	/** full constructor */
	public CtlCustomerendstationId(String customerid, String endstationid, Long records) {
		this.customerid = customerid;
		this.endstationid = endstationid;
		this.records = records;
	}

	// Property accessors

	@Column(name = "CUSTOMERID", nullable = false, length = 8)
	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@Column(name = "ENDSTATIONID", nullable = false, length = 6)
	public String getEndstationid() {
		return this.endstationid;
	}

	public void setEndstationid(String endstationid) {
		this.endstationid = endstationid;
	}

	@Column(name = "RECORDS", precision = 22, scale = 0)
	public Long getRecords() {
		return this.records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CtlCustomerendstationId))
			return false;
		CtlCustomerendstationId castOther = (CtlCustomerendstationId) other;

		return ((this.getCustomerid() == castOther.getCustomerid()) || (this.getCustomerid() != null && castOther.getCustomerid() != null && this
				.getCustomerid().equals(castOther.getCustomerid())))
				&& ((this.getEndstationid() == castOther.getEndstationid()) || (this.getEndstationid() != null && castOther.getEndstationid() != null && this
						.getEndstationid().equals(castOther.getEndstationid())))
				&& ((this.getRecords() == castOther.getRecords()) || (this.getRecords() != null && castOther.getRecords() != null && this
						.getRecords().equals(castOther.getRecords())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCustomerid() == null ? 0 : this.getCustomerid().hashCode());
		result = 37 * result + (getEndstationid() == null ? 0 : this.getEndstationid().hashCode());
		result = 37 * result + (getRecords() == null ? 0 : this.getRecords().hashCode());
		return result;
	}

}