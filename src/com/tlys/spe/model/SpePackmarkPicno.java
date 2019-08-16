package com.tlys.spe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * SpePackmarkPicno entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ZBC_SPE_PACKMARK_PICNO")
public class SpePackmarkPicno implements java.io.Serializable {

	// Fields

	private Long id;
	private String markpicid;
	private String projectno;
	private String contents;

	// Constructors

	/** default constructor */
	public SpePackmarkPicno() {
	}

	/** minimal constructor */
	public SpePackmarkPicno(Long id, String markpicid, String projectno) {
		this.id = id;
		this.markpicid = markpicid;
		this.projectno = projectno;
	}

	/** full constructor */
	public SpePackmarkPicno(Long id, String markpicid, String projectno, String contents) {
		this.id = id;
		this.markpicid = markpicid;
		this.projectno = projectno;
		this.contents = contents;
	}

	// Property accessors
	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="oraSeqGenerator") 
		@SequenceGenerator(name="oraSeqGenerator", 
				sequenceName="SEQ_TB_ZBC_SPE_PACKMARK_PICNO", allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 7, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MARKPICID", nullable = false, length = 6)
	public String getMarkpicid() {
		return this.markpicid;
	}

	public void setMarkpicid(String markpicid) {
		this.markpicid = markpicid;
	}

	@Column(name = "PROJECTNO", nullable = false, length = 10)
	public String getProjectno() {
		return this.projectno;
	}

	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

	@Column(name = "CONTENTS", length = 120)
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}