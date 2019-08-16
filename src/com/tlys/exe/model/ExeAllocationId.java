package com.tlys.exe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 调配信息联合主键
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class ExeAllocationId implements Serializable {

	public ExeAllocationId() {
		super();
	}

	private String car_no;
	private String contract_no;

	@Column(name = "CAR_NO", nullable = false, length = 8)
	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	@Column(name = "CONTRACT_NO", nullable = false, length = 7)
	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public ExeAllocationId(String car_no, String contract_no) {
		super();
		this.car_no = car_no;
		this.contract_no = contract_no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_no == null) ? 0 : car_no.hashCode());
		result = prime * result + ((contract_no == null) ? 0 : contract_no.hashCode());
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
		final ExeAllocationId other = (ExeAllocationId) obj;
		if (car_no == null) {
			if (other.car_no != null)
				return false;
		} else if (!car_no.equals(other.car_no))
			return false;
		if (contract_no == null) {
			if (other.contract_no != null)
				return false;
		} else if (!contract_no.equals(other.contract_no))
			return false;
		return true;
	}
}
