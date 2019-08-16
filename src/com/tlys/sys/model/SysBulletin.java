package com.tlys.sys.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

/**
 * TbZbcSysBulletin entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SYS_BULLETIN")
public class SysBulletin implements java.io.Serializable {

	// Fields

	private Long bulletinid;
	private String title;
	private String kind;
	private String path;
	private String status;
	private String writerid;
	private Date writetime;
	private String publisherid;
	private Date publishtime;
	private String content;
	
	private boolean readover;

	// Constructors

	/** default constructor */
	public SysBulletin() {
	}

	/** minimal constructor */
	public SysBulletin(Long bulletinid, String title, String status) {
		this.bulletinid = bulletinid;
		this.title = title;
		this.status = status;
	}

	/** full constructor */
	public SysBulletin(Long bulletinid, String title, String kind, String path, String status, String writerid,
			Date writetime, String publisherid, Date publishtime) {
		this.bulletinid = bulletinid;
		this.title = title;
		this.kind = kind;
		this.path = path;
		this.status = status;
		this.writerid = writerid;
		this.writetime = writetime;
		this.publisherid = publisherid;
		this.publishtime = publishtime;
	}

	// Property accessors
	@Id
	@Column(name = "BULLETINID", unique = true, nullable = false, precision = 6, scale = 0)
	public Long getBulletinid() {
		return this.bulletinid;
	}

	public void setBulletinid(Long bulletinid) {
		this.bulletinid = bulletinid;
	}

	@Column(name = "TITLE", nullable = false, length = 80)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "KIND", length = 20)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "WRITERID", length = 6)
	public String getWriterid() {
		return this.writerid;
	}

	public void setWriterid(String writerid) {
		this.writerid = writerid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "WRITETIME", length = 7)
	public Date getWritetime() {
		return this.writetime;
	}

	public void setWritetime(Date writetime) {
		this.writetime = writetime;
	}

	@Column(name = "PUBLISHERID", length = 6)
	public String getPublisherid() {
		return this.publisherid;
	}

	public void setPublisherid(String publisherid) {
		this.publisherid = publisherid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLISHTIME", length = 7)
	public Date getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	@Transient
	public String getContent() {
		if (null != content && !"".equals(content)) {
			return content;
		}
		StringBuffer buffer = new StringBuffer();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		if (null != path && !"".equals(path)) {
			ServletContext context = ServletActionContext.getServletContext();
			String realpath = context.getRealPath(path);
			File file = new File(realpath);
			if (!file.exists()) {
				return "";
			}
			try {
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					buffer.append(line);
				}
				content = buffer.toString();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					if (null != fileReader) {
						fileReader.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Transient
	public boolean getReadover() {
		return readover;
	}

	public void setReadover(boolean readover) {
		this.readover = readover;
	}

}