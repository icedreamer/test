package com.tlys.spe.model;

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
import javax.persistence.Transient;


/**
 * Certisupercargo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_SPE_CERTISUPERCARGO"
    ,schema="HXZBC"
)

public class SpeCertisupercargo  implements java.io.Serializable {


    // Fields    

     private long regid;
     private String name;
     private String certino;
     private String corpid;
     private String issuer;
     private Date expiredate;
     private String certipath;
     private String photoipath;
     private Date createdtime;
     private String remarks;
     private String filename;
     

 	 private String[] corpids;
 	 private String corpname;
     private String expiredateStart;
     private String expiredateEnd;


    // Constructors

    /** default constructor */
    public SpeCertisupercargo() {
    }

	/** minimal constructor */
    public SpeCertisupercargo(long regid, String corpid, String photoipath) {
        this.regid = regid;
        this.corpid = corpid;
        this.photoipath = photoipath;
    }
    
    /** full constructor */
    public SpeCertisupercargo(long regid, String name, String certino, String corpid, String issuer, Date expiredate, String certipath, String photoipath, Date createdtime, String remarks) {
        this.regid = regid;
        this.name = name;
        this.certino = certino;
        this.corpid = corpid;
        this.issuer = issuer;
        this.expiredate = expiredate;
        this.certipath = certipath;
        this.photoipath = photoipath;
        this.createdtime = createdtime;
        this.remarks = remarks;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="oraSeqGenerator") 
	@SequenceGenerator(name="oraSeqGenerator", 
			sequenceName="SEQ_TB_ZBC_SPE_CERTISUPERCARGO", allocationSize=1)
    @Column(name="REGID", unique=true, nullable=false, precision=6, scale=0)
    public long getRegid() {
        return this.regid;
    }
    
    public void setRegid(long regid) {
        this.regid = regid;
    }
    
    @Column(name="NAME", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="CERTINO", length=20)

    public String getCertino() {
        return this.certino;
    }
    
    public void setCertino(String certino) {
        this.certino = certino;
    }
    
    @Column(name="CORPID", nullable=false, length=8)

    public String getCorpid() {
        return this.corpid;
    }
    
    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }
    
    @Column(name="ISSUER", length=30)

    public String getIssuer() {
        return this.issuer;
    }
    
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIREDATE", length=7)

    public Date getExpiredate() {
        return this.expiredate;
    }
    
    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }
    
    @Column(name="CERTIPATH", length=200)

    public String getCertipath() {
        return this.certipath;
    }
    
    public void setCertipath(String certipath) {
        this.certipath = certipath;
    }
    
    @Column(name="PHOTOIPATH", length=200)

    public String getPhotoipath() {
        return this.photoipath;
    }
    
    public void setPhotoipath(String photoipath) {
        this.photoipath = photoipath;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATEDTIME", length=7)

    public Date getCreatedtime() {
        return this.createdtime;
    }
    
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
    
    @Column(name="REMARKS", length=60)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Transient
	public String[] getCorpids() {
		return corpids;
	}

	public void setCorpids(String[] corpids) {
		this.corpids = corpids;
	}

	@Transient
	public String getExpiredateStart() {
		return expiredateStart;
	}

	public void setExpiredateStart(String expiredateStart) {
		this.expiredateStart = expiredateStart;
	}

	@Transient
	public String getExpiredateEnd() {
		return expiredateEnd;
	}

	public void setExpiredateEnd(String expiredateEnd) {
		this.expiredateEnd = expiredateEnd;
	}

	@Transient
	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}
	
	@Column(name="FILENAME", length=100)
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
   








}