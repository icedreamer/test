package com.tlys.exe.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * AEI������������
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class ExeStncarId implements Serializable {

	private String rptname;// ������
	private Integer car_position;// ����˳λ

	public ExeStncarId() {
		super();
	}

	public String getRptname() {
		return rptname;
	}

	public void setRptname(String rptname) {
		this.rptname = rptname;
	}

	public int getCar_position() {
		return car_position;
	}

	public ExeStncarId(String rptname, Integer car_position) {
		super();
		this.rptname = rptname;
		this.car_position = car_position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + car_position;
		result = prime * result + ((rptname == null) ? 0 : rptname.hashCode());
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
		final ExeStncarId other = (ExeStncarId) obj;
		if (car_position != other.car_position)
			return false;
		if (rptname == null) {
			if (other.rptname != null)
				return false;
		} else if (!rptname.equals(other.rptname))
			return false;
		return true;
	}

	public void setCar_position(Integer car_position) {
		this.car_position = car_position;
	}
}
