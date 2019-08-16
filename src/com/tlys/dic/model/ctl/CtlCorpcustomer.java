package com.tlys.dic.model.ctl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TbZbcCtlCorpcustomer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPCUSTOMER")
public class CtlCorpcustomer implements java.io.Serializable {

	// Fields

	private CtlCorpcustomerId id;

	// Constructors

	/** default constructor */
	public CtlCorpcustomer() {
	}

	/** full constructor */
	public CtlCorpcustomer(CtlCorpcustomerId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "corpid", column = @Column(name = "CORPID", nullable = false, length = 8)),
			@AttributeOverride(name = "customerid", column = @Column(name = "CUSTOMERID", nullable = false, length = 8)),
			@AttributeOverride(name = "records", column = @Column(name = "RECORDS", precision = 22, scale = 0)) })
	public CtlCorpcustomerId getId() {
		return this.id;
	}

	public void setId(CtlCorpcustomerId id) {
		this.id = id;
	}

}