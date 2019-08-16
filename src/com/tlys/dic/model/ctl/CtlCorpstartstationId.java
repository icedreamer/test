package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcCtlCorpsatrtstationId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CtlCorpstartstationId implements java.io.Serializable {

	// Fields

	private String corpid;
	private String startstationid;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCorpstartstationId() {
	}

	/** minimal constructor */
	public CtlCorpstartstationId(String corpid, String startstationid) {
		this.corpid = corpid;
		this.startstationid = startstationid;
	}

	/** full constructor */
	public CtlCorpstartstationId(String corpid, String startstationid, Long records) {
		this.corpid = corpid;
		this.startstationid = startstationid;
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

	@Column(name = "STARTSTATIONID", nullable = false, length = 6)
	public String getStartstationid() {
		return this.startstationid;
	}

	public void setStartstationid(String startstationid) {
		this.startstationid = startstationid;
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
		if (!(other instanceof CtlCorpstartstationId))
			return false;
		CtlCorpstartstationId castOther = (CtlCorpstartstationId) other;

		return ((this.getCorpid() == castOther.getCorpid()) || (this.getCorpid() != null && castOther.getCorpid() != null && this.getCorpid().equals(
				castOther.getCorpid())))
				&& ((this.getStartstationid() == castOther.getStartstationid()) || (this.getStartstationid() != null
						&& castOther.getStartstationid() != null && this.getStartstationid().equals(castOther.getStartstationid())))
				&& ((this.getRecords() == castOther.getRecords()) || (this.getRecords() != null && castOther.getRecords() != null && this
						.getRecords().equals(castOther.getRecords())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCorpid() == null ? 0 : this.getCorpid().hashCode());
		result = 37 * result + (getStartstationid() == null ? 0 : this.getStartstationid().hashCode());
		result = 37 * result + (getRecords() == null ? 0 : this.getRecords().hashCode());
		return result;
	}

}