package com.tlys.equ.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
 * @author ccsong 2012-11-10 下午2:33:58
 */
@Entity
@Table(name = "TB_ZBC_EQU_YCHECK_DET",  uniqueConstraints = @UniqueConstraint(columnNames = { "YEAR",
		"CORPID", "CARNO", "GOODSID", "INSPECTIONTYPE" }))
public class EquYcheckDet implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -885884798923939488L;
	private Long id;
	private String yinspectionno;
	private String year;
	private String corpid;
	private String carno;
	private String carkindid;
	private String cartypeid;
	private String goodsid;
	private String saftcertcode;
	private String trancertcode;
	private String usercertcode;
	private String remarks;
	private String inspectiontype;
	// 车站所属站段(根据上一年的数据取得)
	private String stationdepot;

	private List<String> yinspectionnoList;

	// Constructors

	/** default constructor */
	public EquYcheckDet() {
	}

	/** minimal constructor */
	public EquYcheckDet(Long id, String yinspectionno, String year, String corpid, String carno, String carkindid,
			String cartypeid, String goodsid) {
		this.id = id;
		this.yinspectionno = yinspectionno;
		this.year = year;
		this.corpid = corpid;
		this.carno = carno;
		this.carkindid = carkindid;
		this.cartypeid = cartypeid;
		this.goodsid = goodsid;
	}

	/** full constructor */
	public EquYcheckDet(Long id, String yinspectionno, String year, String corpid, String carno, String carkindid,
			String cartypeid, String goodsid, String saftcertcode, String trancertcode, String usercertcode,
			String remarks) {
		this.id = id;
		this.yinspectionno = yinspectionno;
		this.year = year;
		this.corpid = corpid;
		this.carno = carno;
		this.carkindid = carkindid;
		this.cartypeid = cartypeid;
		this.goodsid = goodsid;
		this.saftcertcode = saftcertcode;
		this.trancertcode = trancertcode;
		this.usercertcode = usercertcode;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_EQU_YCHECK_DET")
	@GenericGenerator(name = "SEQ_TB_ZBC_EQU_YCHECK_DET", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_EQU_YCHECK_DET") })
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "YINSPECTIONNO", nullable = false, length = 13)
	public String getYinspectionno() {
		return this.yinspectionno;
	}

	public void setYinspectionno(String yinspectionno) {
		this.yinspectionno = yinspectionno;
	}

	@Column(name = "YEAR", nullable = false, length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
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

	@Column(name = "CARTYPEID", nullable = false, length = 10)
	public String getCartypeid() {
		return this.cartypeid;
	}

	public void setCartypeid(String cartypeid) {
		this.cartypeid = cartypeid;
	}

	@Column(name = "GOODSID", nullable = false, length = 3)
	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "SAFTCERTCODE", length = 10)
	public String getSaftcertcode() {
		return this.saftcertcode;
	}

	public void setSaftcertcode(String saftcertcode) {
		this.saftcertcode = saftcertcode;
	}

	@Column(name = "TRANCERTCODE", length = 10)
	public String getTrancertcode() {
		return this.trancertcode;
	}

	public void setTrancertcode(String trancertcode) {
		this.trancertcode = trancertcode;
	}

	@Column(name = "USERCERTCODE", length = 30)
	public String getUsercertcode() {
		return this.usercertcode;
	}

	public void setUsercertcode(String usercertcode) {
		this.usercertcode = usercertcode;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public List<String> getYinspectionnoList() {
		return yinspectionnoList;
	}

	public void setYinspectionnoList(List<String> yinspectionnoList) {
		this.yinspectionnoList = yinspectionnoList;
	}

	public String getInspectiontype() {
		return inspectiontype;
	}

	public void setInspectiontype(String inspectiontype) {
		this.inspectiontype = inspectiontype;
	}

	public String getStationdepot() {
		return stationdepot;
	}

	public void setStationdepot(String stationdepot) {
		this.stationdepot = stationdepot;
	}

}