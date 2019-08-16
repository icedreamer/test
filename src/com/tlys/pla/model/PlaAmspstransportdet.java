package com.tlys.pla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * PlaAmspstransportdet entity. 月度非统销产品铁轮运输调整计划明细
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_AMSPSTRANSPORTDET")
public class PlaAmspstransportdet implements java.io.Serializable {

	// Fields

	private Long id;
	private String planno;
	private String month;
	private String requestdate;
	private String areaid;
	private String areashortname;
	private String corpid;
	private String corpshortname;
	private String sendwarehouse;
	private String senderid;
	private String customerid;
	private String coustomershortname;
	private String productcategoryid;
	private String productsecondid;
	private String prodductid;
	private String productkind;
	private String rwkindid;
	private String rkindname;
	private Double amount;
	private Long carnumber;
	private String carkindid;
	private String cartypeid;
	private String crwid;
	private String startstationid;
	private String receiverid;
	private String receivername;
	private String goalprovinceid;
	private String goaladdress;
	private String endstationid;
	private Date createdtime;
	private String acceptcarno;
	private String remarks;
	private Long oldplanid;

	private String receiverids;
	public String[] corpids;
	public String[] categories;
	public String[] secondkinds;
	// bdDic
	public String senderidDIC;
	public String productcategoryidDIC;
	public String productsecondidDIC;
	public String prodductidDIC;
	public String cartypeidDIC;
	public String carkindidDIC;
	public String crwidDIC;
	public String startstationidDIC;
	public String endstationidDIC;

	public String productkindDIC;

	// Constructors

	/** default constructor */
	public PlaAmspstransportdet() {
	}

	/** minimal constructor */
	public PlaAmspstransportdet(Long id, String planno, String month,
			String areaid, String corpid, String customerid,
			String productcategoryid, String productkind, String carkindid) {
		this.id = id;
		this.planno = planno;
		this.month = month;
		this.areaid = areaid;
		this.corpid = corpid;
		this.customerid = customerid;
		this.productcategoryid = productcategoryid;
		this.productkind = productkind;
		this.carkindid = carkindid;
	}

	/** full constructor */
	public PlaAmspstransportdet(Long id, String planno, String month,
			String requestdate, String areaid, String corpid,
			String corpshortname, String sendwarehouse, String senderid,
			String customerid, String coustomershortname,
			String productcategoryid, String productsecondid,
			String prodductid, String productkind, String rwkindid,
			String rkindname, Double amount, Long carnumber, String carkindid,
			String cartypeid, String crwid, String startstationid,
			String receiverid, String receivername, String goalprovinceid,
			String goaladdress, String endstationid, Date createdtime,
			String acceptcarno, String remarks) {
		this.id = id;
		this.planno = planno;
		this.month = month;
		this.requestdate = requestdate;
		this.areaid = areaid;
		this.corpid = corpid;
		this.corpshortname = corpshortname;
		this.sendwarehouse = sendwarehouse;
		this.senderid = senderid;
		this.customerid = customerid;
		this.coustomershortname = coustomershortname;
		this.productcategoryid = productcategoryid;
		this.productsecondid = productsecondid;
		this.prodductid = prodductid;
		this.productkind = productkind;
		this.rwkindid = rwkindid;
		this.rkindname = rkindname;
		this.amount = amount;
		this.carnumber = carnumber;
		this.carkindid = carkindid;
		this.cartypeid = cartypeid;
		this.crwid = crwid;
		this.startstationid = startstationid;
		this.receiverid = receiverid;
		this.receivername = receivername;
		this.goalprovinceid = goalprovinceid;
		this.goaladdress = goaladdress;
		this.endstationid = endstationid;
		this.createdtime = createdtime;
		this.acceptcarno = acceptcarno;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PLANNO", nullable = false, length = 15)
	public String getPlanno() {
		return this.planno;
	}

	public void setPlanno(String planno) {
		this.planno = planno;
	}

	@Column(name = "MONTH", nullable = false, length = 6)
	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "REQUESTDATE", length = 8)
	public String getRequestdate() {
		return this.requestdate;
	}

	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}

	@Column(name = "AREAID", nullable = false, length = 8)
	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

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

	@Column(name = "SENDWAREHOUSE", length = 60)
	public String getSendwarehouse() {
		return this.sendwarehouse;
	}

	public void setSendwarehouse(String sendwarehouse) {
		this.sendwarehouse = sendwarehouse;
	}

	@Column(name = "SENDERID", length = 8)
	public String getSenderid() {
		return this.senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	@Column(name = "CUSTOMERID", nullable = false, length = 8)
	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@Column(name = "COUSTOMERSHORTNAME", length = 30)
	public String getCoustomershortname() {
		return this.coustomershortname;
	}

	public void setCoustomershortname(String coustomershortname) {
		this.coustomershortname = coustomershortname;
	}

	@Column(name = "PRODUCTCATEGORYID", nullable = false, length = 2)
	public String getProductcategoryid() {
		return this.productcategoryid;
	}

	public void setProductcategoryid(String productcategoryid) {
		this.productcategoryid = productcategoryid;
	}

	@Column(name = "PRODUCTSECONDID", length = 16)
	public String getProductsecondid() {
		return this.productsecondid;
	}

	public void setProductsecondid(String productsecondid) {
		this.productsecondid = productsecondid;
	}

	@Column(name = "PRODDUCTID", length = 16)
	public String getProdductid() {
		return this.prodductid;
	}

	public void setProdductid(String prodductid) {
		this.prodductid = prodductid;
	}

	@Column(name = "PRODUCTKIND", nullable = false, length = 1)
	public String getProductkind() {
		return this.productkind;
	}

	public void setProductkind(String productkind) {
		this.productkind = productkind;
	}

	@Column(name = "RWKINDID", length = 7)
	public String getRwkindid() {
		return this.rwkindid;
	}

	public void setRwkindid(String rwkindid) {
		this.rwkindid = rwkindid;
	}

	@Column(name = "RKINDNAME", length = 20)
	public String getRkindname() {
		return this.rkindname;
	}

	public void setRkindname(String rkindname) {
		this.rkindname = rkindname;
	}

	@Column(name = "AMOUNT", precision = 12, scale = 4)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "CARNUMBER", precision = 22, scale = 0)
	public Long getCarnumber() {
		return this.carnumber;
	}

	public void setCarnumber(Long carnumber) {
		this.carnumber = carnumber;
	}

	@Column(name = "CARKINDID", nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
	}

	@Column(name = "CARTYPEID", length = 10)
	public String getCartypeid() {
		return this.cartypeid;
	}

	public void setCartypeid(String cartypeid) {
		this.cartypeid = cartypeid;
	}

	@Column(name = "CRWID", length = 3)
	public String getCrwid() {
		return this.crwid;
	}

	public void setCrwid(String crwid) {
		this.crwid = crwid;
	}

	@Column(name = "STARTSTATIONID", length = 6)
	public String getStartstationid() {
		return this.startstationid;
	}

	public void setStartstationid(String startstationid) {
		this.startstationid = startstationid;
	}

	@Column(name = "RECEIVERID", length = 8)
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "RECEIVERNAME", length = 100)
	public String getReceivername() {
		return this.receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	@Column(name = "GOALPROVINCEID", length = 6)
	public String getGoalprovinceid() {
		return this.goalprovinceid;
	}

	public void setGoalprovinceid(String goalprovinceid) {
		this.goalprovinceid = goalprovinceid;
	}

	@Column(name = "GOALADDRESS", length = 60)
	public String getGoaladdress() {
		return this.goaladdress;
	}

	public void setGoaladdress(String goaladdress) {
		this.goaladdress = goaladdress;
	}

	@Column(name = "ENDSTATIONID", length = 6)
	public String getEndstationid() {
		return this.endstationid;
	}

	public void setEndstationid(String endstationid) {
		this.endstationid = endstationid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	@Column(name = "ACCEPTCARNO", length = 20)
	public String getAcceptcarno() {
		return this.acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	@Column(name = "REMARKS", length = 60)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getReceiverids() {
		return receiverids;
	}

	public void setReceiverids(String receiverids) {
		this.receiverids = receiverids;
	}

	@Transient
	public String[] getCorpids() {
		return corpids;
	}

	public void setCorpids(String[] corpids) {
		this.corpids = corpids;
	}

	@Transient
	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	@Transient
	public String[] getSecondkinds() {
		return secondkinds;
	}

	public void setSecondkinds(String[] secondkinds) {
		this.secondkinds = secondkinds;
	}

	@Column(name = "AREASHORTNAME", length = 30)
	public String getAreashortname() {
		return areashortname;
	}

	public void setAreashortname(String areashortname) {
		this.areashortname = areashortname;
	}

	public Long getOldplanid() {
		return oldplanid;
	}

	public void setOldplanid(Long oldplanid) {
		this.oldplanid = oldplanid;
	}

	@Transient
	public String getSenderidDIC() {
		return senderidDIC;
	}

	public void setSenderidDIC(String senderidDIC) {
		this.senderidDIC = senderidDIC;
	}

	@Transient
	public String getProductcategoryidDIC() {
		return productcategoryidDIC;
	}

	public void setProductcategoryidDIC(String productcategoryidDIC) {
		this.productcategoryidDIC = productcategoryidDIC;
	}

	@Transient
	public String getProductsecondidDIC() {
		return productsecondidDIC;
	}

	public void setProductsecondidDIC(String productsecondidDIC) {
		this.productsecondidDIC = productsecondidDIC;
	}

	@Transient
	public String getProdductidDIC() {
		return prodductidDIC;
	}

	public void setProdductidDIC(String prodductidDIC) {
		this.prodductidDIC = prodductidDIC;
	}

	@Transient
	public String getCartypeidDIC() {
		return cartypeidDIC;
	}

	public void setCartypeidDIC(String cartypeidDIC) {
		this.cartypeidDIC = cartypeidDIC;
	}

	@Transient
	public String getCarkindidDIC() {
		return carkindidDIC;
	}

	public void setCarkindidDIC(String carkindidDIC) {
		this.carkindidDIC = carkindidDIC;
	}

	@Transient
	public String getCrwidDIC() {
		return crwidDIC;
	}

	public void setCrwidDIC(String crwidDIC) {
		this.crwidDIC = crwidDIC;
	}

	@Transient
	public String getStartstationidDIC() {
		return startstationidDIC;
	}

	public void setStartstationidDIC(String startstationidDIC) {
		this.startstationidDIC = startstationidDIC;
	}

	@Transient
	public String getEndstationidDIC() {
		return endstationidDIC;
	}

	public void setEndstationidDIC(String endstationidDIC) {
		this.endstationidDIC = endstationidDIC;
	}

	@Transient
	public String getProductkindDIC() {
		return productkindDIC;
	}

	public void setProductkindDIC(String productkindDIC) {
		this.productkindDIC = productkindDIC;
	}

}