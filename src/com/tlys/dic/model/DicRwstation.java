package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ZBC_DIC_RWSTATION")
public class DicRwstation implements Serializable {
	@Id
	@Column(name = "STATIONID", nullable = false, unique = true, length = 6)
	private String stationid;

	@Column(name = "TELEGRAMID", length = 3)
	private String telegramid;

	@Column(name = "SHORTNAME", nullable = false, length = 30)
	private String shortname;

	@Column(name = "BUREAUID", nullable = false, length = 3)
	private String bureauid;

	@Column(name = "PROVINCEID", nullable = false, length = 6)
	private String proviceid;

	@Column(name = "TIMSCODE", length = 5)
	private String tmiscode;

	@Column(name = "PYCODE", length = 2)
	private String pycode;

	@Column(name = "HASAEI", nullable = false, length = 1)
	private String hasaei;

	@Column(name = "ISRAILSTN", nullable = false, length = 1)
	private String israilstn;

	@Column(name = "RAILLINENAME", length = 20)
	private String raillinename;

	@Column(name = "STATIONPYCODE", length = 10)
	private String stationpycode;

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public String getTelegramid() {
		return telegramid;
	}

	public void setTelegramid(String telegramid) {
		this.telegramid = telegramid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getBureauid() {
		return bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	public String getProviceid() {
		return proviceid;
	}

	public void setProviceid(String proviceid) {
		this.proviceid = proviceid;
	}

	public String getTmiscode() {
		return tmiscode;
	}

	public void setTmiscode(String tmiscode) {
		this.tmiscode = tmiscode;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public String getHasaei() {
		return hasaei;
	}

	public void setHasaei(String hasaei) {
		this.hasaei = hasaei;
	}

	public String getIsrailstn() {
		return israilstn;
	}

	public void setIsrailstn(String israilstn) {
		this.israilstn = israilstn;
	}

	public String getRaillinename() {
		return raillinename;
	}

	public void setRaillinename(String raillinename) {
		this.raillinename = raillinename;
	}

	public String getStationpycode() {
		return stationpycode;
	}

	public void setStationpycode(String stationpycode) {
		this.stationpycode = stationpycode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortname;
	}

}
