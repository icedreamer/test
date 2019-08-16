package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ExeZyAlertId implements Serializable {

	private String car_no;

	private Date evt_time;

	public ExeZyAlertId() {
		super();

	}

	public ExeZyAlertId(String car_no, Date evt_time) {
		super();
		this.car_no = car_no;
		this.evt_time = evt_time;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ExeZyAlertId other = (ExeZyAlertId) obj;
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
		return true;
	}

	@Column(name = "CAR_NO", nullable = false, length = 8)
	public String getCar_no() {
		return car_no;
	}

	@Column(name = "EVT_TIME", nullable = false, length = 22)
	public Date getEvt_time() {
		return evt_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_no == null) ? 0 : car_no.hashCode());
		result = prime * result + ((evt_time == null) ? 0 : evt_time.hashCode());
		return result;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public void setEvt_time(Date evt_time) {
		this.evt_time = evt_time;
	}

}
