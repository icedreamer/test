package com.tlys.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbZbcSysMsgtype entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_MSGTYPE")
public class SysMsgtype implements java.io.Serializable {

	// Fields

	private String msgtypeid;
	private String msgtypename;
	private String isactive;

	// Constructors

	/** default constructor */
	public SysMsgtype() {
	}

	/** full constructor */
	public SysMsgtype(String msgtypeid, String msgtypename, String isactive) {
		this.msgtypeid = msgtypeid;
		this.msgtypename = msgtypename;
		this.isactive = isactive;
	}

	// Property accessors
	@Id
	@Column(name = "MSGTYPEID", unique = true, nullable = false, length = 3)
	public String getMsgtypeid() {
		return this.msgtypeid;
	}

	public void setMsgtypeid(String msgtypeid) {
		this.msgtypeid = msgtypeid;
	}

	@Column(name = "MSGTYPENAME", nullable = false, length = 20)
	public String getMsgtypename() {
		return this.msgtypename;
	}

	public void setMsgtypename(String msgtypename) {
		this.msgtypename = msgtypename;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

}