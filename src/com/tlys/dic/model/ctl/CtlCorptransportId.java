package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcCtlCorptransportId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CtlCorptransportId implements java.io.Serializable {

	// Fields

	private String corpid;
	private String customerid;
	private String transportid;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCorptransportId() {
	}

	/** minimal constructor */
	public CtlCorptransportId(String corpid, String customerid, String transportid) {
		this.corpid = corpid;
		this.customerid = customerid;
		this.transportid = transportid;
	}

	/** full constructor */
	public CtlCorptransportId(String corpid, String customerid, String transportid, Long records) {
		this.corpid = corpid;
		this.customerid = customerid;
		this.transportid = transportid;
		this.records = records;
	}

	// Property accessors

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CUSTOMERID", nullable = false, length = 8)
	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@Column(name = "TRANSPORTID", nullable = false, length = 8)
	public String getTransportid() {
		return this.transportid;
	}

	public void setTransportid(String transportid) {
		this.transportid = transportid;
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
		if (!(other instanceof CtlCorptransportId))
			return false;
		CtlCorptransportId castOther = (CtlCorptransportId) other;

		return ((this.getCorpid() == castOther.getCorpid()) || (this.getCorpid() != null && castOther.getCorpid() != null && this.getCorpid().equals(
				castOther.getCorpid())))
				&& ((this.getCustomerid() == castOther.getCustomerid()) || (this.getCustomerid() != null && castOther.getCustomerid() != null && this
						.getCustomerid().equals(castOther.getCustomerid())))
				&& ((this.getTransportid() == castOther.getTransportid()) || (this.getTransportid() != null && castOther.getTransportid() != null && this
						.getTransportid().equals(castOther.getTransportid())))
				&& ((this.getRecords() == castOther.getRecords()) || (this.getRecords() != null && castOther.getRecords() != null && this
						.getRecords().equals(castOther.getRecords())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCorpid() == null ? 0 : this.getCorpid().hashCode());
		result = 37 * result + (getCustomerid() == null ? 0 : this.getCustomerid().hashCode());
		result = 37 * result + (getTransportid() == null ? 0 : this.getTransportid().hashCode());
		result = 37 * result + (getRecords() == null ? 0 : this.getRecords().hashCode());
		return result;
	}

}