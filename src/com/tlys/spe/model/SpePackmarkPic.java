package com.tlys.spe.model;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SpePackmarkPic entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_PACKMARK_PIC")
public class SpePackmarkPic implements java.io.Serializable {

	// Fields

	private String markpicid;
	private String markid;
	private String path;
	private String text;
	private String filename;
	
	private SpePackmark spePackmark;
	
	//此标志图片对应的危货项列表
	private List<SpePackmarkPicno> picnoList;
	//危货项字符串（从picnoList中提取，列表显示用 )
	private String picnostr;

	// Constructors

	/** default constructor */
	public SpePackmarkPic() {
	}

	/** minimal constructor */
	public SpePackmarkPic(String markpicid, String markid, String path) {
		this.markpicid = markpicid;
		this.markid = markid;
		this.path = path;
	}

	/** full constructor */
	public SpePackmarkPic(String markpicid, String markid, String path, String text) {
		this.markpicid = markpicid;
		this.markid = markid;
		this.path = path;
		this.text = text;
	}

	// Property accessors
	@Id
	@Column(name = "MARKPICID", unique = true, nullable = false, length = 6)
	public String getMarkpicid() {
		return this.markpicid;
	}

	public void setMarkpicid(String markpicid) {
		this.markpicid = markpicid;
	}

	@Column(name = "MARKID", nullable = false, length = 4)
	public String getMarkid() {
		return this.markid;
	}

	public void setMarkid(String markid) {
		this.markid = markid;
	}

	@Column(name = "PATH", nullable = false, length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "TEXT", length = 40)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "FILENAME", length = 100)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Transient
	public SpePackmark getSpePackmark() {
		return spePackmark;
	}

	public void setSpePackmark(SpePackmark spePackmark) {
		this.spePackmark = spePackmark;
	}
	
	@Transient
	public List<SpePackmarkPicno> getPicnoList() {
		return picnoList;
	}

	public void setPicnoList(List<SpePackmarkPicno> picnoList) {
		this.picnoList = picnoList;
	}
	
	@Transient
	public String getPicnostr() {
		if(null!=this.picnoList){
			picnostr = "";
			for (Iterator iter = picnoList.iterator(); iter.hasNext();) {
				SpePackmarkPicno picno = (SpePackmarkPicno) iter.next();
				picnostr += " " + picno.getProjectno();
			}
		}
		return picnostr;
	}

	public void setPicnostr(String picnostr) {
		this.picnostr = picnostr;
	}

}