package com.tlys.bit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BitCarcu entity
 * @author Administrator
 *
 */
@Entity
@Table(name = "TB_ZBC_BIT_CARCU")
public class BitCarcu implements java.io.Serializable{
	
	private long id;
	private String datadate;
	private String corpid;
	private String corpshrinkname;
	private String corptype;
	private double dcu;
	private double qcu;
	private double mcu;
	private double ycu;
	
	public BitCarcu(){
		
	}
	
	/** full BitCarcu */
	public BitCarcu(long id,String datadate,String corpid,String corpshrinkname,String corptype,double dcu,double qcu,double mcu,double ycu){
		this.id = id;
		this.datadate = datadate;
		this.corpid = corpid;
		this.corpshrinkname = corpshrinkname;
		this.corptype = corptype;
		this.dcu = dcu;
		this.qcu = qcu;
		this.mcu = mcu;
		this.ycu = ycu;
		
		
	}
	@Id 
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name = "DATADATE", nullable = false, length = 8)
	public String getDatadate() {
		return datadate;
	}
	public void setDatadate(String datadate) {
		this.datadate = datadate;
	}
	@Column(name = "CORPID", nullable= false, length = 8)
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	@Column(name = "CORPSHRINKNAME",nullable = false, length = 20)
	public String getCorpshrinkname() {
		return corpshrinkname;
	}
	public void setCorpshrinkname(String corpshrinkname) {
		this.corpshrinkname = corpshrinkname;
	}
	@Column(name="CORPTYPE", nullable = false, length = 1)
	public String getCorptype() {
		return corptype;
	}
	public void setCorptype(String corptype) {
		this.corptype = corptype;
	}
	@Column(name = "DCU", nullable = false, length= 6)
	public double getDcu() {
		return dcu;
	}
	public void setDcu(double dcu) {
		this.dcu = dcu;
	}
	@Column(name = "QCU", nullable = false, length= 6)
	public double getQcu() {
		return qcu;
	}
	public void setQcu(double qcu) {
		this.qcu = qcu;
	}
	@Column(name = "MCU", nullable = false, length= 6)
	public double getMcu() {
		return mcu;
	}
	public void setMcu(double mcu) {
		this.mcu = mcu;
	}
	@Column(name = "YCU", nullable = false, length= 6)
	public double getYcu() {
		return ycu;
	}
	public void setYcu(double ycu) {
		this.ycu = ycu;
	}
	
	

}
