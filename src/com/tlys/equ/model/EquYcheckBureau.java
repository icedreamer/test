package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * EquYcheckBureau entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_YCHECK_BUREAU",  uniqueConstraints = @UniqueConstraint(columnNames = {
		"BUREAUID", "CORPID", "GOODSID" }))
public class EquYcheckBureau implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6662944034774863664L;
	private Long id;
	private String yinspectionno;
	private String corpid;
	private String goodsid;
	private String bureauid;
	private String bureaushortname;
	private String year;
	private String inspectiontype;

	// Constructors

	/** default constructor */
	public EquYcheckBureau() {
	}

	/** full constructor */
	public EquYcheckBureau(Long id, String yinspectionno, String corpid, String goodsid, String bureauid,
			String bureaushortname) {
		this.id = id;
		this.yinspectionno = yinspectionno;
		this.corpid = corpid;
		this.goodsid = goodsid;
		this.bureauid = bureauid;
		this.bureaushortname = bureaushortname;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_EQU_YCHECK_BUREAU")
	@GenericGenerator(name = "SEQ_TB_ZBC_EQU_YCHECK_BUREAU", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_EQU_YCHECK_BUREAU") })
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YINSPECTIONNO", nullable = false, length = 15)
	public String getYinspectionno() {
		return this.yinspectionno;
	}

	public void setYinspectionno(String yinspectionno) {
		this.yinspectionno = yinspectionno;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "GOODSID", nullable = false, length = 3)
	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Column(name = "BUREAUSHORTNAME", nullable = false, length = 30)
	public String getBureaushortname() {
		return this.bureaushortname;
	}

	public void setBureaushortname(String bureaushortname) {
		this.bureaushortname = bureaushortname;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getInspectiontype() {
		return inspectiontype;
	}

	public void setInspectiontype(String inspectiontype) {
		this.inspectiontype = inspectiontype;
	}

}