package com.tlys.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SysDatafillinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_DATAFILLINFO")
public class SysDatafillinfo implements java.io.Serializable {

	// Fields

	private long id;
	private String statidate;
	private String datamonth;
	private String databus;
	private String bussubject;
	private String corpid;
	private Long valuen;
	private Long valuem;
	private Long valuet;
	private String subjecttips;
	
	
	private String corpname;

	// Constructors

	/** default constructor */
	public SysDatafillinfo() {
	}

	/** minimal constructor */
	public SysDatafillinfo(long id, String statidate, String datamonth, String databus, String bussubject, String corpid) {
		this.id = id;
		this.statidate = statidate;
		this.datamonth = datamonth;
		this.databus = databus;
		this.bussubject = bussubject;
		this.corpid = corpid;
	}

	/** full constructor */
	public SysDatafillinfo(long id, String statidate, String datamonth, String databus, String bussubject, String corpid, Long valuen,
			Long valuem, Long valuet, String subjecttips) {
		this.id = id;
		this.statidate = statidate;
		this.datamonth = datamonth;
		this.databus = databus;
		this.bussubject = bussubject;
		this.corpid = corpid;
		this.valuen = valuen;
		this.valuem = valuem;
		this.valuet = valuet;
		this.subjecttips = subjecttips;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "STATIDATE", nullable = false, length = 8)
	public String getStatidate() {
		return this.statidate;
	}

	public void setStatidate(String statidate) {
		this.statidate = statidate;
	}

	@Column(name = "DATAMONTH", nullable = false, length = 6)
	public String getDatamonth() {
		return this.datamonth;
	}

	public void setDatamonth(String datamonth) {
		this.datamonth = datamonth;
	}

	@Column(name = "DATABUS", nullable = false, length = 4)
	public String getDatabus() {
		return this.databus;
	}

	public void setDatabus(String databus) {
		this.databus = databus;
	}

	@Column(name = "BUSSUBJECT", nullable = false, length = 40)
	public String getBussubject() {
		return this.bussubject;
	}

	public void setBussubject(String bussubject) {
		this.bussubject = bussubject;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	@Column(name = "VALUEN", precision = 6, scale = 0)
	public Long getValuen() {
		return this.valuen;
	}

	public void setValuen(Long valuen) {
		this.valuen = valuen;
	}

	@Column(name = "VALUEM", precision = 6, scale = 0)
	public Long getValuem() {
		return this.valuem;
	}

	public void setValuem(Long valuem) {
		this.valuem = valuem;
	}

	@Column(name = "VALUET", precision = 6, scale = 0)
	public Long getValuet() {
		return this.valuet;
	}

	public void setValuet(Long valuet) {
		this.valuet = valuet;
	}

	@Column(name = "SUBJECTTIPS", length = 60)
	public String getSubjecttips() {
		return this.subjecttips;
	}

	public void setSubjecttips(String subjecttips) {
		this.subjecttips = subjecttips;
	}

	@Transient
	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

}