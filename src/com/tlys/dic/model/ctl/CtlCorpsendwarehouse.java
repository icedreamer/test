package com.tlys.dic.model.ctl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TbZbcCtlCorpsendwarehouse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_CTL_CORPSENDWAREHOUSE")
public class CtlCorpsendwarehouse implements java.io.Serializable {

	// Fields

	private CtlCorpsendwarehouseId id;

	// Constructors

	/** default constructor */
	public CtlCorpsendwarehouse() {
	}

	/** full constructor */
	public CtlCorpsendwarehouse(CtlCorpsendwarehouseId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "corpid", column = @Column(name = "CORPID", nullable = false, length = 8)),
			@AttributeOverride(name = "sendwarehouse", column = @Column(name = "SENDWAREHOUSE", nullable = false, length = 60)),
			@AttributeOverride(name = "records", column = @Column(name = "RECORDS", precision = 22, scale = 0)) })
	public CtlCorpsendwarehouseId getId() {
		return this.id;
	}

	public void setId(CtlCorpsendwarehouseId id) {
		this.id = id;
	}

}