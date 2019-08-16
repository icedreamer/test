package com.tlys.dic.model.ctl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TbZbcCtlCorptransport entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPTRANSPORT")
public class CtlCorptransport implements java.io.Serializable {

	// Fields

	private CtlCorptransportId id;

	// Constructors

	/** default constructor */
	public CtlCorptransport() {
	}

	/** full constructor */
	public CtlCorptransport(CtlCorptransportId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "corpid", column = @Column(name = "CORPID", nullable = false, length = 8)),
			@AttributeOverride(name = "customerid", column = @Column(name = "CUSTOMERID", nullable = false, length = 8)),
			@AttributeOverride(name = "transportid", column = @Column(name = "TRANSPORTID", nullable = false, length = 8)),
			@AttributeOverride(name = "records", column = @Column(name = "RECORDS", precision = 22, scale = 0)) })
	public CtlCorptransportId getId() {
		return this.id;
	}

	public void setId(CtlCorptransportId id) {
		this.id = id;
	}

}