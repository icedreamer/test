package com.tlys.exe.dao;

/**
 * ������Ϣͳ�Ʊ�VO
 * 
 * @author �״���
 * 
 */
public class ExeAllocationStatVO {

	private String lessor_id;// ���޷�
	private String lessor;
	private String lessee_id;// ���÷�
	private String lessee;
	private String medium_id;// ��װ����
	private String medium;
	private int num;// ����

	public String getLessor_id() {
		return lessor_id;
	}

	public void setLessor_id(String lessor_id) {
		this.lessor_id = lessor_id;
	}

	public String getLessor() {
		return lessor;
	}

	public void setLessor(String lessor) {
		this.lessor = lessor;
	}

	public String getLessee_id() {
		return lessee_id;
	}

	public void setLessee_id(String lessee_id) {
		this.lessee_id = lessee_id;
	}

	public String getLessee() {
		return lessee;
	}

	public void setLessee(String lessee) {
		this.lessee = lessee;
	}

	public String getMedium_id() {
		return medium_id;
	}

	public void setMedium_id(String medium_id) {
		this.medium_id = medium_id;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
