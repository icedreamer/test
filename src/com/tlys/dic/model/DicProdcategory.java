package com.tlys.dic.model;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * DicProdcategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_DIC_PRODCATEGORY")

public class DicProdcategory  implements java.io.Serializable {


    // Fields    

     private String categoryid;
     private String fullname;
     private String shortname;
     private Long orderno;
     private String isactive;
     private Date createdtime;
     private String isactiveDIC;


    // Constructors

    /** default constructor */
    public DicProdcategory() {
    }

	/** minimal constructor */
    public DicProdcategory(String categoryid, String fullname, String shortname, String isactive) {
        this.categoryid = categoryid;
        this.fullname = fullname;
        this.shortname = shortname;
        this.isactive = isactive;
    }
    
    /** full constructor */
    public DicProdcategory(String categoryid, String fullname, String shortname, Long orderno, String isactive, Date createdtime) {
        this.categoryid = categoryid;
        this.fullname = fullname;
        this.shortname = shortname;
        this.orderno = orderno;
        this.isactive = isactive;
        this.createdtime = createdtime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="CATEGORYID", unique=true, nullable=false, length=2)

    public String getCategoryid() {
        return this.categoryid;
    }
    
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
    
    @Column(name="FULLNAME", nullable=false, length=100)

    public String getFullname() {
        return this.fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    @Column(name="SHORTNAME", nullable=false, length=30)

    public String getShortname() {
        return this.shortname;
    }
    
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    
    @Column(name="ORDERNO", precision=22, scale=0)

    public Long getOrderno() {
        return this.orderno;
    }
    
    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }
    
    @Column(name="ISACTIVE", nullable=false, length=1)

    public String getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
@Temporal(TemporalType.DATE)
    @Column(name="CREATEDTIME", length=7)

    public Date getCreatedtime() {
        return this.createdtime;
    }
    
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
    @Transient
	public String getIsactiveDIC() {
		if (isactive.equals("1")) {
			return "∆Ù”√";
		} else {
			return "Õ£”√";
		}
	}

	public void setIsactiveDIC(String isactiveDIC) {
		this.isactiveDIC = isactiveDIC;
	}
   



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortname;
	}





}