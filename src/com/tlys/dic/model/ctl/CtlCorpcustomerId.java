package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcCtlCorpcustomerId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CtlCorpcustomerId implements java.io.Serializable {

	// Fields

	private String corpid;
	private String customerid;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCorpcustomerId() {
	}

	/** minimal constructor */
	public CtlCorpcustomerId(String corpid, String customerid) {
		this.corpid = corpid;
		this.customerid = customerid;
	}

	/** full constructor */
	public CtlCorpcustomerId(String corpid, String customerid, Long records) {
		this.corpid = corpid;
		this.customerid = customerid;
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
		if (!(other instanceof CtlCorpcustomerId))
			return false;
		CtlCorpcustomerId castOther = (CtlCorpcustomerId) other;

		return ((this.getCorpid() == castOther.getCorpid()) || (this.getCorpid() != null && castOther.getCorpid() != null && this.getCorpid().equals(
				castOther.getCorpid())))
				&& ((this.getCustomerid() == castOther.getCustomerid()) || (this.getCustomerid() != null && castOther.getCustomerid() != null && this
						.getCustomerid().equals(castOther.getCustomerid())))
				&& ((this.getRecords() == castOther.getRecords()) || (this.getRecords() != null && castOther.getRecords() != null && this
						.getRecords().equals(castOther.getRecords())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCorpid() == null ? 0 : this.getCorpid().hashCode());
		result = 37 * result + (getCustomerid() == null ? 0 : this.getCustomerid().hashCode());
		result = 37 * result + (getRecords() == null ? 0 : this.getRecords().hashCode());
		return result;
	}

}