package com.tlys.equ.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * EquCar entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_EQU_CAR")
public class EquCar implements java.io.Serializable {

	public static SimpleDateFormat shortFormat = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
	// Fields

	private String carno;
	private String corpid;
	private String corpshrinkname;
	private String carkindid;
	private String cartypeid;
	private String isstandartrain;
	private String isdangerous;
	private String pressuretype;
	private String carmakerid;
	private String madedate;
	private String inroaddate;
	private String tankmateid;
	private String goodsid;
	private String isrent;
	private String stnid;
	private String stnshrinkname;
	private Double lightweight;
	private Double markloadweight;
	private Double capacity;
	private Double convlength;
	private Long carlength;
	private Long axisnum;
	private String isexpire;
	private Date expiredate;
	private Date createdtime;
	private String description;
	private String rentid;
	private Double buyprice;
	private String iscentralize;
	private String carnoname;
	private String carmakername;
	private String tankmatename;
	private String goodsname;
	private String carkindname;
	private String isexpirename;
	private String expiredateStr;
	private String madedateStr;
	private String corpidDIC;

	private String inroaddateStr;
	private String isstandartrainname;
	private String isdangerousname;
	private String isrentname;
	private String pressuretypename;
	private String rentidname;

	private EquCarHpinfo hp;
	private EquCarNpinfo np;
	private EquCarHpreginfo reg;

	private String carkindids;
	private String cartypeids;

	private String corpids;
	private Object[] carnos;
	
	private String isDefer;
	private Date deferDate;

	/** default constructor */
	public EquCar() {
	}

	/** query constructor */
	public EquCar(String corpid, String corpshrinkname) {
		this.corpid = corpid;
		this.corpshrinkname = corpshrinkname;
	}

	/** minimal constructor */
	public EquCar(String carno, String corpid, String corpshrinkname, String carkindid, String cartypeid,
			String isstandartrain, String isdangerous, String pressuretype, String tankmateid, String goodsid,
			String isrent, String isexpire) {
		this.carno = carno;
		this.corpid = corpid;
		this.corpshrinkname = corpshrinkname;
		this.carkindid = carkindid;
		this.cartypeid = cartypeid;
		this.isstandartrain = isstandartrain;
		this.isdangerous = isdangerous;
		this.pressuretype = pressuretype;
		this.tankmateid = tankmateid;
		this.goodsid = goodsid;
		this.isrent = isrent;
		this.isexpire = isexpire;
	}

	/** full constructor */
	public EquCar(String carno, String corpid, String corpshrinkname, String carkindid, String cartypeid,
			String isstandartrain, String isdangerous, String pressuretype, String carmakerid, String madedate,
			String inroaddate, String tankmateid, String goodsid, String isrent, String stnid, String stnshrinkname,
			Double lightweight, Double markloadweight, Double capacity, Double convlength, Long carlength,
			Long axisnum, String isexpire, Date expiredate, Date createdtime, String description, String rentid) {
		this.carno = carno;
		this.corpid = corpid;
		this.corpshrinkname = corpshrinkname;
		this.carkindid = carkindid;
		this.cartypeid = cartypeid;
		this.isstandartrain = isstandartrain;
		this.isdangerous = isdangerous;
		this.pressuretype = pressuretype;
		this.carmakerid = carmakerid;
		this.madedate = madedate;
		this.inroaddate = inroaddate;
		this.tankmateid = tankmateid;
		this.goodsid = goodsid;
		this.isrent = isrent;
		this.stnid = stnid;
		this.stnshrinkname = stnshrinkname;
		this.lightweight = lightweight;
		this.markloadweight = markloadweight;
		this.capacity = capacity;
		this.convlength = convlength;
		this.carlength = carlength;
		this.axisnum = axisnum;
		this.isexpire = isexpire;
		this.expiredate = expiredate;
		this.createdtime = createdtime;
		this.description = description;
		this.rentid = rentid;
	}

	@Column(name = "AXISNUM", precision = 22, scale = 0)
	public Long getAxisnum() {
		return this.axisnum;
	}

	@Column(name = "CAPACITY", precision = 5, scale = 1)
	public Double getCapacity() {
		return this.capacity;
	}

	@Column(name = "CARKINDID", nullable = false, length = 1)
	public String getCarkindid() {
		return this.carkindid;
	}

	@Transient
	public String getCarkindids() {
		return carkindids;
	}

	/**
	 * @return the carkindname
	 */
	@Transient
	public String getCarkindname() {
		return carkindname;
	}

	@Column(name = "CARLENGTH", precision = 6, scale = 0)
	public Long getCarlength() {
		return this.carlength;
	}

	@Column(name = "CARMAKERID", length = 3)
	public String getCarmakerid() {
		return this.carmakerid;
	}

	/**
	 * @return the carmakername
	 */
	@Transient
	public String getCarmakername() {
		return carmakername;
	}

	// Property accessors
	@Id
	@Column(name = "CARNO", unique = true, nullable = false, length = 8)
	public String getCarno() {
		return this.carno;
	}

	/**
	 * @return the carnoname
	 */
	@Transient
	public String getCarnoname() {
		return "_" + carno;
	}

	@Transient
	public Object[] getCarnos() {
		return carnos;
	}

	@Column(name = "CARTYPEID", nullable = false, length = 10)
	public String getCartypeid() {
		return this.cartypeid;
	}

	@Transient
	public String getCartypeids() {
		return cartypeids;
	}

	@Column(name = "CONVLENGTH", precision = 6, scale = 0)
	public Double getConvlength() {
		return this.convlength;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	@Transient
	public String getCorpids() {
		return corpids;
	}

	@Column(name = "CORPSHRINKNAME", nullable = false, length = 20)
	public String getCorpshrinkname() {
		return this.corpshrinkname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIREDATE", length = 7)
	public Date getExpiredate() {
		return this.expiredate;
	}

	// Constructors
	@Transient
	public String getExpiredateStr() {
		return null != expiredate && !"".equals(expiredate) ? parseFormat.format(expiredate) : "";
	}

	@Column(name = "GOODSID", nullable = false, length = 3)
	public String getGoodsid() {
		return this.goodsid;
	}

	/**
	 * @return the goodsname
	 */
	@Transient
	public String getGoodsname() {
		return goodsname;
	}

	/**
	 * @return the hp
	 */
	@Transient
	public EquCarHpinfo getHp() {
		return hp;
	}

	@Column(name = "INROADDATE", length = 6)
	public String getInroaddate() {
		return this.inroaddate;
	}

	@Transient
	public String getInroaddateStr() {
		try {
			return parseFormat.format(shortFormat.parse(inroaddate));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	@Column(name = "ISDANGEROUS", nullable = false, length = 1)
	public String getIsdangerous() {
		return this.isdangerous;
	}

	/**
	 * @return the isdangerousname
	 */
	@Transient
	public String getIsdangerousname() {
		if ("0".equals(isdangerous)) {
			return "否";
		} else if ("1".equals(isdangerous)) {
			return "是";
		} else {
			return "";
		}
	}

	@Column(name = "ISEXPIRE", nullable = false, length = 1)
	public String getIsexpire() {
		return this.isexpire;
	}

	/**
	 * @return the isexpirename
	 */
	@Transient
	public String getIsexpirename() {
		if (isexpire.equals("0"))
			return "否";
		else if (isexpire.equals("1"))
			return "是";
		else {
			return "";
		}
	}

	@Column(name = "ISRENT", nullable = false, length = 1)
	public String getIsrent() {
		return this.isrent;
	}

	/**
	 * @return the isrentname
	 */
	@Transient
	public String getIsrentname() {
		if ("0".equals(isrent)) {
			return "否";
		} else if ("1".equals(isrent)) {
			return "是";
		} else {
			return "";
		}
	}

	@Column(name = "ISSTANDARTRAIN", nullable = false, length = 1)
	public String getIsstandartrain() {
		return this.isstandartrain;
	}

	/**
	 * @return the isstandartrainname
	 */
	@Transient
	public String getIsstandartrainname() {
		if ("0".equals(isstandartrain)) {
			return "否";
		} else if ("1".equals(isstandartrain)) {
			return "是";
		} else {
			return "";
		}
	}

	@Column(name = "LIGHTWEIGHT", precision = 6)
	public Double getLightweight() {
		return this.lightweight;
	}

	@Column(name = "MADEDATE", length = 6)
	public String getMadedate() {
		return this.madedate;
	}

	@Transient
	public String getMadedateStr() {
		try {
			return parseFormat.format(shortFormat.parse(madedate));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	@Column(name = "MARKLOADWEIGHT", precision = 6)
	public Double getMarkloadweight() {
		return this.markloadweight;
	}

	/**
	 * @return the np
	 */
	@Transient
	public EquCarNpinfo getNp() {
		return np;
	}

	@Column(name = "PRESSURETYPE", nullable = false, length = 2)
	public String getPressuretype() {
		return this.pressuretype;
	}

	/**
	 * @return the pressuretypename
	 */
	@Transient
	public String getPressuretypename() {
		if (pressuretype.equals("NP")) {
			return "常压";
		} else if (pressuretype.equals("HP")) {
			return "高压";
		} else {
			return "无";
		}
	}

	/**
	 * @return the reg
	 */
	@Transient
	public EquCarHpreginfo getReg() {
		return reg;
	}

	@Column(name = "rentid", length = 2)
	public String getRentid() {
		return rentid;
	}

	/**
	 * @return the rentidname
	 */
	@Transient
	public String getRentidname() {
		if ("01".equals(rentid)) {
			return "自用 ";
		} else if ("02".equals(rentid)) {
			return "租赁";
		} else if ("03".equals(rentid)) {
			return "租用";
		} else if ("05".equals(rentid)) {
			return "部属车";
		} else {
			return "";
		}
	}

	@Column(name = "STNID", length = 6)
	public String getStnid() {
		return this.stnid;
	}

	@Column(name = "STNSHRINKNAME", length = 20)
	public String getStnshrinkname() {
		return this.stnshrinkname;
	}

	@Column(name = "TANKMATEID", nullable = false, length = 2)
	public String getTankmateid() {
		return this.tankmateid;
	}

	/**
	 * @return the tankmatename
	 */
	@Transient
	public String getTankmatename() {
		return tankmatename;
	}

	public void setAxisnum(Long axisnum) {
		this.axisnum = axisnum;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
	}

	public void setCarkindids(String carkindids) {
		this.carkindids = carkindids;
	}

	/**
	 * @param carkindname
	 *            the carkindname to set
	 */
	public void setCarkindname(String carkindname) {
		this.carkindname = carkindname;
	}

	public void setCarlength(Long carlength) {
		this.carlength = carlength;
	}

	public void setCarmakerid(String carmakerid) {
		this.carmakerid = carmakerid;
	}

	/**
	 * @param carmakername
	 *            the carmakername to set
	 */
	public void setCarmakername(String carmakername) {
		this.carmakername = carmakername;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	/**
	 * @param carnoname
	 *            the carnoname to set
	 */
	public void setCarnoname(String carnoname) {
		this.carnoname = carnoname;
	}

	public void setCarnos(Object[] carnos) {
		this.carnos = carnos;
	}

	public void setCartypeid(String cartypeid) {
		this.cartypeid = cartypeid;
	}

	public void setCartypeids(String cartypeids) {
		this.cartypeids = cartypeids;
	}

	public void setConvlength(Double convlength) {
		this.convlength = convlength;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public void setCorpshrinkname(String corpshrinkname) {
		this.corpshrinkname = corpshrinkname;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * @param goodsname
	 *            the goodsname to set
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(EquCarHpinfo hp) {
		this.hp = hp;
	}

	public void setInroaddate(String inroaddate) {
		this.inroaddate = inroaddate;
	}

	public void setIsdangerous(String isdangerous) {
		this.isdangerous = isdangerous;
	}

	/**
	 * @param isdangerousname
	 *            the isdangerousname to set
	 */
	public void setIsdangerousname(String isdangerousname) {
		this.isdangerousname = isdangerousname;
	}

	public void setIsexpire(String isexpire) {
		this.isexpire = isexpire;
	}

	/**
	 * @param isexpirename
	 *            the isexpirename to set
	 */
	public void setIsexpirename(String isexpirename) {
		this.isexpirename = isexpirename;
	}

	public void setIsrent(String isrent) {
		this.isrent = isrent;
	}

	/**
	 * @param isrentname
	 *            the isrentname to set
	 */
	public void setIsrentname(String isrentname) {
		this.isrentname = isrentname;
	}

	public void setIsstandartrain(String isstandartrain) {
		this.isstandartrain = isstandartrain;
	}

	/**
	 * @param isstandartrainname
	 *            the isstandartrainname to set
	 */
	public void setIsstandartrainname(String isstandartrainname) {
		this.isstandartrainname = isstandartrainname;
	}

	public void setLightweight(Double lightweight) {
		this.lightweight = lightweight;
	}

	public void setMadedate(String madedate) {
		this.madedate = madedate;
	}

	public void setMarkloadweight(Double markloadweight) {
		this.markloadweight = markloadweight;
	}

	/**
	 * @param np
	 *            the np to set
	 */
	public void setNp(EquCarNpinfo np) {
		this.np = np;
	}

	public void setPressuretype(String pressuretype) {
		this.pressuretype = pressuretype;
	}

	/**
	 * @param pressuretypename
	 *            the pressuretypename to set
	 */
	public void setPressuretypename(String pressuretypename) {
		this.pressuretypename = pressuretypename;
	}

	/**
	 * @param reg
	 *            the reg to set
	 */
	public void setReg(EquCarHpreginfo reg) {
		this.reg = reg;
	}

	public void setRentid(String rentid) {
		this.rentid = rentid;
	}

	/**
	 * @param rentidname
	 *            the rentidname to set
	 */
	public void setRentidname(String rentidname) {
		this.rentidname = rentidname;
	}

	public void setStnid(String stnid) {
		this.stnid = stnid;
	}

	public void setStnshrinkname(String stnshrinkname) {
		this.stnshrinkname = stnshrinkname;
	}

	public void setTankmateid(String tankmateid) {
		this.tankmateid = tankmateid;
	}

	/**
	 * @param tankmatename
	 *            the tankmatename to set
	 */
	public void setTankmatename(String tankmatename) {
		this.tankmatename = tankmatename;
	}

	@Transient
	public String getCorpidDIC() {
		return corpidDIC;
	}

	public void setCorpidDIC(String corpidDIC) {
		this.corpidDIC = corpidDIC;
	}

	public Double getBuyprice() {
		return buyprice;
	}

	public void setBuyprice(Double buyprice) {
		this.buyprice = buyprice;
	}

	public void setExpiredateStr(String expiredateStr) {
		this.expiredateStr = expiredateStr;
	}

	public void setMadedateStr(String madedateStr) {
		this.madedateStr = madedateStr;
	}

	public void setInroaddateStr(String inroaddateStr) {
		this.inroaddateStr = inroaddateStr;
	}

	public String getIscentralize() {
		return iscentralize;
	}

	public void setIscentralize(String iscentralize) {
		this.iscentralize = iscentralize;
	}

	public String getIsDefer() {
		return isDefer;
	}

	public void setIsDefer(String isDefer) {
		this.isDefer = isDefer;
	}

	public Date getDeferDate() {
		return deferDate;
	}

	public void setDeferDate(Date deferDate) {
		this.deferDate = deferDate;
	}

}