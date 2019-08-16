package com.tlys.sys.model;

// Generated 2011-9-20 14:19:26 by Hibernate Tools 3.3.0.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * TbZbcSysBkerrorlog generated by hbm2java
 */
@Entity
@Table(name = "TB_ZBC_SYS_BKERRORLOG")
public class SysBkerrorlog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663547212474172300L;
	private long id;
	private String objname;
	private String objtype;
	private Date rectime;
	private String description;
	@Transient
	private String startTime;
	@Transient
	private String endTime;
	@Transient
	private String rqlx;

	/**
	 * @return the rqlx
	 */
	@Transient
	public String getRqlx() {
		return rqlx;
	}

	/**
	 * @param rqlx
	 *            the rqlx to set
	 */
	public void setRqlx(String rqlx) {
		this.rqlx = rqlx;
	}

	public SysBkerrorlog() {
	}

	/**
	 * @return the startTime
	 */
	@Transient
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	@Transient
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public SysBkerrorlog(long id) {
		this.id = id;
	}

	public SysBkerrorlog(long id, String objname, String objtype, Date rectime, String description) {
		this.id = id;
		this.objname = objname;
		this.objtype = objtype;
		this.rectime = rectime;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TB_ZBC_SYS_BKERRORLOG")
	@GenericGenerator(name = "SEQ_TB_ZBC_SYS_BKERRORLOG", strategy = "sequence", parameters = { @Parameter(
			name = "sequence", value = "SEQ_TB_ZBC_SYS_BKERRORLOG") })
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "OBJNAME", length = 30)
	public String getObjname() {
		return this.objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	@Column(name = "OBJTYPE", length = 20)
	public String getObjtype() {
		return this.objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECTIME", length = 7)
	public Date getRectime() {
		return this.rectime;
	}

	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}

	@Column(name = "DESCRIPTION", length = 400)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
