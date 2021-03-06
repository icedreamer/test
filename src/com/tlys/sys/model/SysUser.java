package com.tlys.sys.model;

// Generated 2011-9-20 14:19:26 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * TbZbcSysUser generated by hbm2java
 */
@Entity
@Table(name = "TB_ZBC_SYS_USER",  uniqueConstraints = @UniqueConstraint(columnNames = "LOGINID"))
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class SysUser implements java.io.Serializable {

	private String userid;
	private String loginid;
	private String username;
	private String corpid;
	private String corptab;
	private String loginpwd;
	private String islocked;
	private Date lockedtime;
	private String firstpage;
	private Date pwdresettime;
	private String email;
	private String biuser;
	private String bipwd;
	private String description;
	private Date createdtime;
	private Set<String> roles;
	
	private String corpname;
	private String corptabname;

	/**
	 * 标志是否属于当前组，不参与数据库持久化
	 */
	private boolean cgroup;
	

	public SysUser() {
	}

	public SysUser(String userid, String loginid, String username, String corpid, String loginpwd, String islocked,
			Date lockedtime, String firstpage, Date pwdresettime, String email, String biuser, String bipwd,
			String description, Date createdtime) {
		this.userid = userid;
		this.loginid = loginid;
		this.username = username;
		this.corpid = corpid;
		this.loginpwd = loginpwd;
		this.islocked = islocked;
		this.lockedtime = lockedtime;
		this.firstpage = firstpage;
		this.pwdresettime = pwdresettime;
		this.email = email;
		this.biuser = biuser;
		this.bipwd = bipwd;
		this.description = description;
		this.createdtime = createdtime;
	}

	public SysUser(String userid, String loginid, String username, String corpid, String loginpwd, String islocked,
			String firstpage) {
		this.userid = userid;
		this.loginid = loginid;
		this.username = username;
		this.corpid = corpid;
		this.loginpwd = loginpwd;
		this.islocked = islocked;
		this.firstpage = firstpage;
	}

	@Column(name = "BIPWD", length = 40)
	public String getBipwd() {
		return this.bipwd;
	}

	@Column(name = "BIUSER", length = 60)
	public String getBiuser() {
		return this.biuser;
	}

	/**
	 * @return the cgroup
	 */
	@Transient
	public boolean getCgroup() {
		return cgroup;
	}

	@Column(name = "CORPID", nullable = false, length = 8)
	public String getCorpid() {
		return this.corpid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDTIME", length = 7)
	public Date getCreatedtime() {
		return this.createdtime;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return this.email;
	}

	@Column(name = "FIRSTPAGE", nullable = false, length = 200)
	public String getFirstpage() {
		return this.firstpage;
	}

	@Column(name = "ISLOCKED", nullable = false, length = 1)
	public String getIslocked() {
		return this.islocked;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LOCKEDTIME", length = 7)
	public Date getLockedtime() {
		return this.lockedtime;
	}

	@Column(name = "LOGINID", unique = true, nullable = false, length = 30)
	public String getLoginid() {
		return this.loginid;
	}

	@Column(name = "LOGINPWD", nullable = false, length = 20)
	public String getLoginpwd() {
		return this.loginpwd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PWDRESETTIME", length = 7)
	public Date getPwdresettime() {
		return this.pwdresettime;
	}

	@Id
	@Column(name = "USERID", unique = true, nullable = false, length = 6)
	public String getUserid() {
		if (null != userid && "".equals(userid)) {
			userid = null;
		}
		return this.userid;
	}

	@Column(name = "USERNAME", nullable = false, length = 60)
	public String getUsername() {
		return this.username;
	}

	public void setBipwd(String bipwd) {
		this.bipwd = bipwd;
	}

	public void setBiuser(String biuser) {
		this.biuser = biuser;
	}

	/**
	 * @param cgroup
	 *            the cgroup to set
	 */
	public void setCgroup(boolean cgroup) {
		this.cgroup = cgroup;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstpage(String firstpage) {
		this.firstpage = firstpage;
	}

	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}

	public void setLockedtime(Date lockedtime) {
		this.lockedtime = lockedtime;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

	public void setPwdresettime(Date pwdresettime) {
		this.pwdresettime = pwdresettime;
	}
	@Transient
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysUser [bipwd=" + bipwd + ", biuser=" + biuser + ", corpid=" + corpid + ", createdtime=" + createdtime
				+ ", description=" + description + ", email=" + email + ", firstpage=" + firstpage + ", islocked="
				+ islocked + ", lockedtime=" + lockedtime + ", loginid=" + loginid + ", loginpwd=" + loginpwd
				+ ", pwdresettime=" + pwdresettime + ", userid=" + userid + ", username=" + username + "]";
	}

	@Column(name = "CORPTAB", nullable = true, length = 30)
	public String getCorptab() {
		return corptab;
	}

	public void setCorptab(String corptab) {
		this.corptab = corptab;
	}

	@Transient
	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	@Transient
	public String getCorptabname() {
		if(null == corptab){
			corptabname = "未定义";
		}else if("0".equals(corptab)){
			corptabname = "总部机关";
		}else if("1".equals(corptab)){
			corptabname = "区域公司";
		}else if("2".equals(corptab)){
			corptabname = "下属企业";
		}else if("3".equals(corptab)){
			corptabname = "下辖部门";
		}else if("9".equals(corptab)){
			corptabname = "lis查询用户";
		}
		return corptabname;
	}

	public void setCorptabname(String corptabname) {
		this.corptabname = corptabname;
	}

}
