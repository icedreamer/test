package com.tlys.exe.dao;

/**
 * 调配信息查询字段
 * 
 * @author 孔垂云
 * 
 */
public class ExeAllocSearchField {
	private String carLessor;
	private String carLessee;
	private String rentStartDate;
	private String rentEndDate;
	private String carNo;
	private String contractNo;
	private String car_medium_id;// 充装介质

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	public String getCarLessor() {
		return carLessor;
	}

	public void setCarLessor(String carLessor) {
		this.carLessor = carLessor;
	}

	public String getCarLessee() {
		return carLessee;
	}

	public void setCarLessee(String carLessee) {
		this.carLessee = carLessee;
	}

	public String getRentStartDate() {
		return rentStartDate;
	}

	public void setRentStartDate(String rentStartDate) {
		this.rentStartDate = rentStartDate;
	}

	public String getRentEndDate() {
		return rentEndDate;
	}

	public void setRentEndDate(String rentEndDate) {
		this.rentEndDate = rentEndDate;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

}
