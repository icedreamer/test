package com.tlys.sys.model;

public class Contact implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2066229499463189586L;
	// ��������˾����
	String areaname;
	// // ��ҵ��������(��������Ӫ��������)
	// String corptype;
	// // ������ҵ����
	// String corpname;
	// ����
	String department;
	// ��Ա����
	String name;
	// ͷ��
	String title;
	// �칫�绰
	String officephone;
	// �ƶ��绰
	String mobile;
	// �� ״̬
	String state;

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	// public String getCorptype() {
	// return corptype;
	// }
	//
	// public void setCorptype(String corptype) {
	// this.corptype = corptype;
	// }
	//
	// public String getCorpname() {
	// return corpname;
	// }
	//
	// public void setCorpname(String corpname) {
	// this.corpname = corpname;
	// }

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
