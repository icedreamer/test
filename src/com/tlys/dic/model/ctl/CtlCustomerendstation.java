package com.tlys.dic.model.ctl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TbZbcCtlCustomerendstation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CUSTOMERENDSTATION")
public class CtlCustomerendstation implements java.io.Serializable {

	// Fields

	private CtlCustomerendstationId id;

	// Constructors

	/** default constructor */
	public CtlCustomerendstation() {
	}

	/** full constructor */
	public CtlCustomerendstation(CtlCustomerendstationId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "customerid", column = @Column(name = "CUSTOMERID", nullable = false, length = 8)),
			@AttributeOverride(name = "endstationid", column = @Column(name = "ENDSTATIONID", nullable = false, length = 6)),
			@AttributeOverride(name = "records", column = @Column(name = "RECORDS", precision = 22, scale = 0)) })
	public CtlCustomerendstationId getId() {
		return this.id;
	}

	public void setId(CtlCustomerendstationId id) {
		this.id = id;
	}

}