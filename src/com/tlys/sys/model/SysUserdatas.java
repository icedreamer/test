package com.tlys.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * SysUserdatas entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_USERDATAS")
public class SysUserdatas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5932534413735247667L;
	// Fields

	private long id;
	private String userid;
	private String datacorpid;
	private int menuid;

	// Constructors

	/** default constructor */
	public SysUserdatas() {
	}

	/** full constructor */
	public SysUserdatas(long id, String userid, String datacorpid) {
		this.id = id;
		this.userid = userid;
		this.datacorpid = datacorpid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oraSeqGenerator")
	@SequenceGenerator(name = "oraSeqGenerator", sequenceName = "SEQ_TB_ZBC_SYS_USERDATAS", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 6, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "USERID", nullable = false, length = 6)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "DATACORPID", nullable = false, length = 8)
	public String getDatacorpid() {
		return this.datacorpid;
	}

	public void setDatacorpid(String datacorpid) {
		this.datacorpid = datacorpid;
	}

	public int getMenuid() {
		return menuid;
	}

	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}

}