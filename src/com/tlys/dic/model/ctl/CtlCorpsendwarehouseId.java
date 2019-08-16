package com.tlys.dic.model.ctl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcCtlCorpsendwarehouseId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class CtlCorpsendwarehouseId implements java.io.Serializable {

	// Fields

	private String corpid;
	private String sendwarehouse;
	private Long records;

	// Constructors

	/** default constructor */
	public CtlCorpsendwarehouseId() {
	}

	/** minimal constructor */
	public CtlCorpsendwarehouseId(String corpid, String sendwarehouse) {
		this.corpid = corpid;
		this.sendwarehouse = sendwarehouse;
	}

	/** full constructor */
	public CtlCorpsendwarehouseId(String corpid, String sendwarehouse, Long records) {
		this.corpid = corpid;
		this.sendwarehouse = sendwarehouse;
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

	@Column(name = "SENDWAREHOUSE", nullable = false, length = 60)
	public String getSendwarehouse() {
		return this.sendwarehouse;
	}

	public void setSendwarehouse(String sendwarehouse) {
		this.sendwarehouse = sendwarehouse;
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
		if (!(other instanceof CtlCorpsendwarehouseId))
			return false;
		CtlCorpsendwarehouseId castOther = (CtlCorpsendwarehouseId) other;

		return ((this.getCorpid() == castOther.getCorpid()) || (this.getCorpid() != null && castOther.getCorpid() != null && this.getCorpid().equals(
				castOther.getCorpid())))
				&& ((this.getSendwarehouse() == castOther.getSendwarehouse()) || (this.getSendwarehouse() != null
						&& castOther.getSendwarehouse() != null && this.getSendwarehouse().equals(castOther.getSendwarehouse())))
				&& ((this.getRecords() == castOther.getRecords()) || (this.getRecords() != null && castOther.getRecords() != null && this
						.getRecords().equals(castOther.getRecords())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCorpid() == null ? 0 : this.getCorpid().hashCode());
		result = 37 * result + (getSendwarehouse() == null ? 0 : this.getSendwarehouse().hashCode());
		result = 37 * result + (getRecords() == null ? 0 : this.getRecords().hashCode());
		return result;
	}

}