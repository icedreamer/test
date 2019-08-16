package com.tlys.dic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * DicCartype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_CARTYPE")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class DicCartype implements java.io.Serializable {

	// Fields

	private String cartypeid;
	private String cartypename;
	private String carkindid;
	private Double lightweight;
	private Double markweight;
	private Double capacity;
	private Double convlength;
	private Double carlength;
	private Long axisnum;
	private Long orderno;
	private String isactive;
	private Date createdtime;
	private String description;
	private String isactiveDIC;
	private String carkindidDIC;

	// Constructors

	/** default constructor */
	public DicCartype() {
	}

	/** minimal constructor */
	public DicCartype(String cartypeid, String cartypename, String carkindid,
			String isactive) {
		this.cartypeid = cartypeid;
		this.cartypename = cartypename;
		this.carkindid = carkindid;
		this.isactive = isactive;
	}

	/** full constructor */
	public DicCartype(String cartypeid, String cartypename, String carkindid,
			Double lightweight, Double markweight, Double capacity,
			Double convlength, Double carlength, Long axisnum, Long orderno,
			String isactive, Date createdtime, String description) {
		this.cartypeid = cartypeid;
		this.cartypename = cartypename;
		this.carkindid = carkindid;
		this.lightweight = lightweight;
		this.markweight = markweight;
		this.capacity = capacity;
		this.convlength = convlength;
		this.carlength = carlength;
		this.axisnum = axisnum;
		this.orderno = orderno;
		this.isactive = isactive;
		this.createdtime = createdtime;
		this.description = description;
	}

	// Property accessors
	@Id
	@Column(name = "CARTYPEID", unique = true, nullable = false, length = 10)
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

	@Column(name = "CARKINDID", nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
	}

	@Column(name = "LIGHTWEIGHT", precision = 6)
	public Double getLightweight() {
		return this.lightweight;
	}

	public void setLightweight(Double lightweight) {
		this.lightweight = lightweight;
	}

	@Column(name = "MARKWEIGHT", precision = 6)
	public Double getMarkweight() {
		return this.markweight;
	}

	public void setMarkweight(Double markweight) {
		this.markweight = markweight;
	}

	@Column(name = "CAPACITY", precision = 8)
	public Double getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	@Column(name = "CONVLENGTH", precision = 4)
	public Double getConvlength() {
		return this.convlength;
	}

	public void setConvlength(Double convlength) {
		this.convlength = convlength;
	}

	@Column(name = "CARLENGTH", precision = 4)
	public Double getCarlength() {
		return this.carlength;
	}

	public void setCarlength(Double carlength) {
		this.carlength = carlength;
	}

	@Column(name = "AXISNUM", precision = 22, scale = 0)
	public Long getAxisnum() {
		return this.axisnum;
	}

	public void setAxisnum(Long axisnum) {
		this.axisnum = axisnum;
	}

	@Column(name = "ORDERNO", precision = 22, scale = 0)
	public Long getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Long orderno) {
		this.orderno = orderno;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getIsactiveDIC() {
		if (isactive.equals("1")) {
			return "∆Ù”√";
		} else {
			return "Õ£”√";
		}
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}

	@Override
	public String toString() {
		return cartypename;
	}

	@Transient
	public String getCarkindidDIC() {
		return carkindidDIC;
	}

	public void setCarkindidDIC(String carkindidDIC) {
		this.carkindidDIC = carkindidDIC;
	}

}