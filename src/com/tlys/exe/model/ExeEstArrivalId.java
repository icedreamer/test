package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

/**
 * 企业前方有AEI车站及预计到达时间中间表联合主键
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class ExeEstArrivalId implements Serializable {

	private String car_no;// 车号
	private Date evt_time;// 事件时间
	private String stn_code;// 过轨站编码

	public ExeEstArrivalId() {
		super();
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public Date getEvt_time() {
		return evt_time;
	}

	public void setEvt_time(Date evt_time) {
		this.evt_time = evt_time;
	}

	public String getStn_code() {
		return stn_code;
	}

	public void setStn_code(String stn_code) {
		this.stn_code = stn_code;
	}

	public ExeEstArrivalId(String car_no, Date evt_time, String stn_code) {
		super();
		this.car_no = car_no;
		this.evt_time = evt_time;
		this.stn_code = stn_code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_no == null) ? 0 : car_no.hashCode());
		result = prime * result + ((evt_time == null) ? 0 : evt_time.hashCode());
		result = prime * result + ((stn_code == null) ? 0 : stn_code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ExeEstArrivalId other = (ExeEstArrivalId) obj;
		if (car_no == null) {
			if (other.car_no != null)
				return false;
		} else if (!car_no.equals(other.car_no))
			return false;
		if (evt_time == null) {
			if (other.evt_time != null)
				return false;
		} else if (!evt_time.equals(other.evt_time))
			return false;
		if (stn_code == null) {
			if (other.stn_code != null)
				return false;
		} else if (!stn_code.equals(other.stn_code))
			return false;
		return true;
	}

}
