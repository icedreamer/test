package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TbZbcEquRepCarsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class EquRepCarsId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1300094320810465117L;
	private String corpid;
	private String corpshortname;
	private String carno;
	private String carkindid;
	private String carkindname;
	private String cartypeid;
	private String cartypename;
	private String mname;
	private String goodsid;
	private String goodsname;
	private String pressuretype;
	private String repkind;
	private String areaid;

	// Constructors

	/** default constructor */
	public EquRepCarsId() {
	}

	/** minimal constructor */
	public EquRepCarsId(String corpid, String carno, String carkindid, String carkindname, String cartypeid,
			String cartypename, String mname, String goodsname, String pressuretype, String repkind) {
		this.corpid = corpid;
		this.carno = carno;
		this.carkindid = carkindid;
		this.carkindname = carkindname;
		this.cartypeid = cartypeid;
		this.cartypename = cartypename;
		this.mname = mname;
		this.goodsname = goodsname;
		this.pressuretype = pressuretype;
		this.repkind = repkind;
	}

	/** full constructor */
	public EquRepCarsId(String corpid, String corpshortname, String carno, String carkindid, String carkindname,
			String cartypeid, String cartypename, String mname, String goodsname, String pressuretype, String repkind) {
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.carno = carno;
		this.carkindid = carkindid;
		this.carkindname = carkindname;
		this.cartypeid = cartypeid;
		this.cartypename = cartypename;
		this.mname = mname;
		this.goodsname = goodsname;
		this.pressuretype = pressuretype;
		this.repkind = repkind;
	}

	// Property accessors

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "CORPSHORTNAME", length = 30)
	public String getCorpshortname() {
		return this.corpshortname;
	}

	public void setCorpshortname(String corpshortname) {
		this.corpshortname = corpshortname;
	}

	@Column(name = "CARNO", nullable = false, length = 8)
	public String getCarno() {
		return this.carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	@Column(name = "CARKINDID", nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
	}

	@Column(name = "CARKINDNAME", nullable = false, length = 30)
	public String getCarkindname() {
		return this.carkindname;
	}

	public void setCarkindname(String carkindname) {
		this.carkindname = carkindname;
	}

	@Column(name = "CARTYPEID", nullable = false, length = 10)
	public String getCartypeid() {
		return this.cartypeid;
	}

	public void setCartypeid(String cartypeid) {
		this.cartypeid = cartypeid;
	}

	@Column(name = "CARTYPENAME", nullable = false, length = 20)
	public String getCartypename() {
		return this.cartypename;
	}

	public void setCartypename(String cartypename) {
		this.cartypename = cartypename;
	}

	@Column(name = "MNAME", nullable = false, length = 20)
	public String getMname() {
		return this.mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	@Column(name = "GOODSNAME", nullable = false, length = 20)
	public String getGoodsname() {
		return this.goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	@Column(name = "PRESSURETYPE", nullable = false, length = 2)
	public String getPressuretype() {
		return this.pressuretype;
	}

	public void setPressuretype(String pressuretype) {
		this.pressuretype = pressuretype;
	}

	@Column(name = "REPKIND", nullable = false, length = 2)
	public String getRepkind() {
		return this.repkind;
	}

	public void setRepkind(String repkind) {
		this.repkind = repkind;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EquRepCarsId))
			return false;
		EquRepCarsId castOther = (EquRepCarsId) other;

		return ((this.getCorpid() == castOther.getCorpid()) || (this.getCorpid() != null
				&& castOther.getCorpid() != null && this.getCorpid().equals(castOther.getCorpid())))
				&& ((this.getCorpshortname() == castOther.getCorpshortname()) || (this.getCorpshortname() != null
						&& castOther.getCorpshortname() != null && this.getCorpshortname().equals(
						castOther.getCorpshortname())))
				&& ((this.getCarno() == castOther.getCarno()) || (this.getCarno() != null
						&& castOther.getCarno() != null && this.getCarno().equals(castOther.getCarno())))
				&& ((this.getCarkindid() == castOther.getCarkindid()) || (this.getCarkindid() != null
						&& castOther.getCarkindid() != null && this.getCarkindid().equals(castOther.getCarkindid())))
				&& ((this.getCarkindname() == castOther.getCarkindname()) || (this.getCarkindname() != null
						&& castOther.getCarkindname() != null && this.getCarkindname().equals(
						castOther.getCarkindname())))
				&& ((this.getCartypeid() == castOther.getCartypeid()) || (this.getCartypeid() != null
						&& castOther.getCartypeid() != null && this.getCartypeid().equals(castOther.getCartypeid())))
				&& ((this.getCartypename() == castOther.getCartypename()) || (this.getCartypename() != null
						&& castOther.getCartypename() != null && this.getCartypename().equals(
						castOther.getCartypename())))
				&& ((this.getMname() == castOther.getMname()) || (this.getMname() != null
						&& castOther.getMname() != null && this.getMname().equals(castOther.getMname())))
				&& ((this.getGoodsname() == castOther.getGoodsname()) || (this.getGoodsname() != null
						&& castOther.getGoodsname() != null && this.getGoodsname().equals(castOther.getGoodsname())))
				&& ((this.getPressuretype() == castOther.getPressuretype()) || (this.getPressuretype() != null
						&& castOther.getPressuretype() != null && this.getPressuretype().equals(
						castOther.getPressuretype())))
				&& ((this.getRepkind() == castOther.getRepkind()) || (this.getRepkind() != null
						&& castOther.getRepkind() != null && this.getRepkind().equals(castOther.getRepkind())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCorpid() == null ? 0 : this.getCorpid().hashCode());
		result = 37 * result + (getCorpshortname() == null ? 0 : this.getCorpshortname().hashCode());
		result = 37 * result + (getCarno() == null ? 0 : this.getCarno().hashCode());
		result = 37 * result + (getCarkindid() == null ? 0 : this.getCarkindid().hashCode());
		result = 37 * result + (getCarkindname() == null ? 0 : this.getCarkindname().hashCode());
		result = 37 * result + (getCartypeid() == null ? 0 : this.getCartypeid().hashCode());
		result = 37 * result + (getCartypename() == null ? 0 : this.getCartypename().hashCode());
		result = 37 * result + (getMname() == null ? 0 : this.getMname().hashCode());
		result = 37 * result + (getGoodsname() == null ? 0 : this.getGoodsname().hashCode());
		result = 37 * result + (getPressuretype() == null ? 0 : this.getPressuretype().hashCode());
		result = 37 * result + (getRepkind() == null ? 0 : this.getRepkind().hashCode());
		return result;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Column(name = "GOODSID", nullable = false, length = 3)
	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

}