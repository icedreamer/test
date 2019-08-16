package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SpeWorkdocement entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_SPE_WORKDOCEMENT"
    ,schema="HXZBC"
)

public class SpeWorkdocement  implements java.io.Serializable {


    // Fields    

     private long id;
     private String corpid;
     private String corpname;
     private String doctype;
     private String docname;
     private String keywords;
     private String formattype;
     private String path;
     private String remarks;
     private Date createdtime;


    // Constructors

    /** default constructor */
    public SpeWorkdocement() {
    }

	/** minimal constructor */
    public SpeWorkdocement(long id, String corpid, String doctype, String docname, String keywords, String formattype) {
        this.id = id;
        this.corpid = corpid;
        this.doctype = doctype;
        this.docname = docname;
        this.keywords = keywords;
        this.formattype = formattype;
    }
    
    /** full constructor */
    public SpeWorkdocement(long id, String corpid, String corpname, String doctype, String docname, String keywords, String formattype, String path, String remarks, Date createdtime) {
        this.id = id;
        this.corpid = corpid;
        this.corpname = corpname;
        this.doctype = doctype;
        this.docname = docname;
        this.keywords = keywords;
        this.formattype = formattype;
        this.path = path;
        this.remarks = remarks;
        this.createdtime = createdtime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ID", unique=true, nullable=false, precision=6, scale=0)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="CORPID", nullable=false, length=8)

    public String getCorpid() {
        return this.corpid;
    }
    
    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }
    
    @Column(name="CORPNAME", length=20)

    public String getCorpname() {
        return this.corpname;
    }
    
    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }
    
    @Column(name="DOCTYPE", nullable=false, length=20)

    public String getDoctype() {
        return this.doctype;
    }
    
    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }
    
    @Column(name="DOCNAME", nullable=false, length=60)

    public String getDocname() {
        return this.docname;
    }
    
    public void setDocname(String docname) {
        this.docname = docname;
    }
    
    @Column(name="KEYWORDS", nullable=false, length=80)

    public String getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    @Column(name="FORMATTYPE", nullable=false, length=10)

    public String getFormattype() {
        return this.formattype;
    }
    
    public void setFormattype(String formattype) {
        this.formattype = formattype;
    }
    
    @Column(name="PATH", length=200)

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column(name="REMARKS", length=60)

    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATEDTIME", length=7)

    public Date getCreatedtime() {
        return this.createdtime;
    }
    
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
   








}