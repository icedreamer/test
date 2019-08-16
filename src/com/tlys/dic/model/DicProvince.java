package com.tlys.dic.model;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * DicProvince entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_DIC_PROVINCE",schema="HXZBC"
)
public class DicProvince  implements java.io.Serializable {


    // Fields    

     private String provinceid;
     private String name;
     private String isactive;
     private Date createdtime;


    // Constructors

    /** default constructor */
    public DicProvince() {
    }

	/** minimal constructor */
    public DicProvince(String provinceid, String name, String isactive) {
        this.provinceid = provinceid;
        this.name = name;
        this.isactive = isactive;
    }
    
    /** full constructor */
    public DicProvince(String provinceid, String name, String isactive, Date createdtime) {
        this.provinceid = provinceid;
        this.name = name;
        this.isactive = isactive;
        this.createdtime = createdtime;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PROVINCEID", unique=true, nullable=false, length=6)

    public String getProvinceid() {
        return this.provinceid;
    }
    
    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }
    
    @Column(name="NAME", nullable=false, length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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

	@Override
	public String toString() {
		return name;
	}
   








}