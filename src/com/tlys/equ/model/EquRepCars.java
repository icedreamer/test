package com.tlys.equ.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TbZbcEquRepCars entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_REP_CARS" )
public class EquRepCars implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5768405013163368913L;
	private EquRepCarsId id;
	private String carkindids;
	private String cartypeids;
	private String areaids;
	private String corpids;
	private Object[] carnos;
	private String goodsids;

	// Constructors

	/** default constructor */
	public EquRepCars() {
	}

	/** full constructor */
	public EquRepCars(EquRepCarsId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "corpid", column = @Column(name = "CORPID", nullable = false, length = 8)),
			@AttributeOverride(name = "corpshortname", column = @Column(name = "CORPSHORTNAME", length = 30)),
			@AttributeOverride(name = "carno", column = @Column(name = "CARNO", nullable = false, length = 8)),
			@AttributeOverride(name = "carkindid", column = @Column(name = "CARKINDID", nullable = false, length = 1)),
			@AttributeOverride(name = "carkindname", column = @Column(name = "CARKINDNAME", nullable = false,
					length = 30)),
			@AttributeOverride(name = "cartypeid", column = @Column(name = "CARTYPEID", nullable = false, length = 10)),
			@AttributeOverride(name = "cartypename", column = @Column(name = "CARTYPENAME", nullable = false,
					length = 20)),
			@AttributeOverride(name = "mname", column = @Column(name = "MNAME", nullable = false, length = 20)),
			@AttributeOverride(name = "goodsname", column = @Column(name = "GOODSNAME", nullable = false, length = 20)),
			@AttributeOverride(name = "pressuretype", column = @Column(name = "PRESSURETYPE", nullable = false,
					length = 2)),
			@AttributeOverride(name = "repkind", column = @Column(name = "REPKIND", nullable = false, length = 2)) })
	public EquRepCarsId getId() {
		return this.id;
	}

	public void setId(EquRepCarsId id) {
		this.id = id;
	}

	@Transient
	public String getCarkindids() {
		return carkindids;
	}

	@Transient
	public String getCartypeids() {
		return cartypeids;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	public void setCarkindids(String carkindids) {
		this.carkindids = carkindids;
	}

	public void setCartypeids(String cartypeids) {
		this.cartypeids = cartypeids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	@Transient
	public Object[] getCarnos() {
		return carnos;
	}

	public void setCarnos(Object[] carnos) {
		this.carnos = carnos;
	}

	@Transient
	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	@Transient
	public String getGoodsids() {
		return goodsids;
	}

	public void setGoodsids(String goodsids) {
		this.goodsids = goodsids;
	}

}