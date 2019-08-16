package com.tlys.sys.model;

// Generated 2011-9-20 14:19:26 by Hibernate Tools 3.3.0.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TbZbcSysRoleperm generated by hbm2java
 */
@Entity
@Table(name = "TB_ZBC_SYS_ROLEPERM",  uniqueConstraints = @UniqueConstraint(columnNames = { "ROLEID", "OPRAID" }))
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true) 
public class SysRoleperm implements java.io.Serializable {

	private int id;
	private String roleid;
	private String opraid;
	private Date grantedtime;

	public SysRoleperm() {
	}

	public SysRoleperm(int id, String roleid, String opraid) {
		this.id = id;
		this.roleid = roleid;
		this.opraid = opraid;
	}

	public SysRoleperm(int id, String roleid, String opraid, Date grantedtime) {
		this.id = id;
		this.roleid = roleid;
		this.opraid = opraid;
		this.grantedtime = grantedtime;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="oraSeqGenerator") 
	@SequenceGenerator(name="oraSeqGenerator", 
			sequenceName="SEQ_TB_ZBC_SYS_ROLEPERM", allocationSize=1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "ROLEID", nullable = false, length = 6)
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "OPRAID", nullable = false, length = 6)
	public String getOpraid() {
		return this.opraid;
	}

	public void setOpraid(String opraid) {
		this.opraid = opraid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GRANTEDTIME", length = 7)
	public Date getGrantedtime() {
		return this.grantedtime;
	}

	public void setGrantedtime(Date grantedtime) {
		this.grantedtime = grantedtime;
	}

}
