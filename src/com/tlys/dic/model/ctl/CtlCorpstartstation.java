package com.tlys.dic.model.ctl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TbZbcCtlCorpsatrtstation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPSATRTSTATION")
public class CtlCorpstartstation implements java.io.Serializable {

	// Fields

	private CtlCorpstartstationId id;

	// Constructors

	/** default constructor */
	public CtlCorpstartstation() {
	}

	/** full constructor */
	public CtlCorpstartstation(CtlCorpstartstationId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "corpid", column = @Column(name = "CORPID", nullable = false, length = 8)),
			@AttributeOverride(name = "startstationid", column = @Column(name = "STARTSTATIONID", nullable = false, length = 6)),
			@AttributeOverride(name = "records", column = @Column(name = "RECORDS", precision = 22, scale = 0)) })
	public CtlCorpstartstationId getId() {
		return this.id;
	}

	public void setId(CtlCorpstartstationId id) {
		this.id = id;
	}

}