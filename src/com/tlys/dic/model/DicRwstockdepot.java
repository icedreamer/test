package com.tlys.dic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DicRwstockdepot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_DIC_RWSTOCKDEPOT" )
public class DicRwstockdepot implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3030109159581824475L;
	@Id
	private String stockdepotid;
	private String telegramid;
	private String shortname;
	private String bureauid;
	private String provinceid;
	private String timscode;
	private String pycode;

	@Column(name = "STOCKDEPOTID", nullable = false, length = 6)
	public String getStockdepotid() {
		return this.stockdepotid;
	}

	public void setStockdepotid(String stockdepotid) {
		this.stockdepotid = stockdepotid;
	}

	@Column(name = "TELEGRAMID", length = 3)
	public String getTelegramid() {
		return this.telegramid;
	}

	public void setTelegramid(String telegramid) {
		this.telegramid = telegramid;
	}

	@Column(name = "SHORTNAME", nullable = false, length = 30)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "BUREAUID", nullable = false, length = 3)
	public String getBureauid() {
		return this.bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	@Column(name = "PROVINCEID", nullable = false, length = 6)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	@Column(name = "TIMSCODE", length = 5)
	public String getTimscode() {
		return this.timscode;
	}

	public void setTimscode(String timscode) {
		this.timscode = timscode;
	}

	@Column(name = "PYCODE", length = 2)
	public String getPycode() {
		return this.pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}
}