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
 * PlaAmupstransportdet entity. 月度统销产品铁轮运输调整计划明细
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_PLA_AMUPSTRANSPORTDET")
public class PlaAmupstransportdet implements java.io.Serializable {

	// Fields

	public Long id;
	public String planno;
	public String month;
	public String requestdate;
	public String areaid;
	public String areashortname;
	public String corpid;
	public String corpshortname;
	public String sendwarehouse;
	public String senderid;
	public String customerid;
	public String coustomershortname;
	public String productcategoryid;
	public String productsecondid;
	public String prodductid;
	public String productkind;
	public String rwkindid;
	public String rkindname;
	public Double amount;
	public Long carnumber;
	public String carkindid;
	public String cartypeid;
	public String crwid;
	public String startstationid;
	public String receiverid;
	public String receivername;
	public String goalprovinceid;
	public String goaladdress;
	public String endstationid;
	public Date createdtime;
	public String acceptcarno;
	public String remarks;
	public Long oldplanid;
	public String saledepartment;
	public String saler;

	private String receiverids;
	private String[] corpids;
	private String[] categories;
	private String[] secondkinds;
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
	public String saledepartmentDIC;

	public String productkindDIC;

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

	@Column(name = "SENDERID", length = 8)
	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	@Column(name = "RECEIVERNAME", length = 100)
	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public Long getOldplanid() {
		return oldplanid;
	}

	public void setOldplanid(Long oldplanid) {
		this.oldplanid = oldplanid;
	}

	@Transient
	public String[] getCorpids() {
		return corpids;
	}

	@Transient
	public String[] getCategories() {
		return categories;
	}

	@Transient
	public String[] getSecondkinds() {
		return secondkinds;
	}

	public void setCorpids(String[] corpids) {
		this.corpids = corpids;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public void setSecondkinds(String[] secondkinds) {
		this.secondkinds = secondkinds;
	}

	public String getSaledepartment() {
		return saledepartment;
	}

	public void setSaledepartment(String saledepartment) {
		this.saledepartment = saledepartment;
	}

	@Transient
	public String getAreashortname() {
		return areashortname;
	}

	public void setAreashortname(String areashortname) {
		this.areashortname = areashortname;
	}

	@Column(name = "SALER", length = 20)
	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
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
	public String getSaledepartmentDIC() {
		return saledepartmentDIC;
	}

	public void setSaledepartmentDIC(String saledepartmentDIC) {
		this.saledepartmentDIC = saledepartmentDIC;
	}

	@Transient
	public String getProductkindDIC() {
		return productkindDIC;
	}

	public void setProductkindDIC(String productkindDIC) {
		this.productkindDIC = productkindDIC;
	}
}