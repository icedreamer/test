package com.tlys.spe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * SpeRentcontract entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_SPE_RENTCONTRACT"
    ,schema="HXZBC"
)

public class SpeRentcontract  implements java.io.Serializable {


    // Fields    

     private long id;
     private String firstpartid;
     private String secondpartid;
     private String carno;
     private String startmonth;
     private String endmonth;
     private String contractno;
     private String contractname;
     private String formattype;
     private String path;
     private String readtimes;
     private String remarks;
     private Date createdtime;


    // Constructors

    /** default constructor */
    public SpeRentcontract() {
    }

	/** minimal constructor */
    public SpeRentcontract(long id, String firstpartid, String secondpartid, String carno, String formattype) {
        this.id = id;
        this.firstpartid = firstpartid;
        this.secondpartid = secondpartid;
        this.carno = carno;
        this.formattype = formattype;
    }
    
    /** full constructor */
    public SpeRentcontract(long id, String firstpartid, String secondpartid, String carno, String startmonth, String endmonth, String contractno, String contractname, String formattype, String path, String readtimes, String remarks, Date createdtime) {
        this.id = id;
        this.firstpartid = firstpartid;
        this.secondpartid = secondpartid;
        this.carno = carno;
        this.startmonth = startmonth;
        this.endmonth = endmonth;
        this.contractno = contractno;
        this.contractname = contractname;
        this.formattype = formattype;
        this.path = path;
        this.readtimes = readtimes;
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
    
    @Column(name="FIRSTPARTID", nullable=false, length=8)

    public String getFirstpartid() {
        return this.firstpartid;
    }
    
    public void setFirstpartid(String firstpartid) {
        this.firstpartid = firstpartid;
    }
    
    @Column(name="SECONDPARTID", nullable=false, length=8)

    public String getSecondpartid() {
        return this.secondpartid;
    }
    
    public void setSecondpartid(String secondpartid) {
        this.secondpartid = secondpartid;
    }
    
    @Column(name="CARNO", nullable=false, length=8)

    public String getCarno() {
        return this.carno;
    }
    
    public void setCarno(String carno) {
        this.carno = carno;
    }
    
    @Column(name="STARTMONTH", length=6)

    public String getStartmonth() {
        return this.startmonth;
    }
    
    public void setStartmonth(String startmonth) {
        this.startmonth = startmonth;
    }
    
    @Column(name="ENDMONTH", length=6)

    public String getEndmonth() {
        return this.endmonth;
    }
    
    public void setEndmonth(String endmonth) {
        this.endmonth = endmonth;
    }
    
    @Column(name="CONTRACTNO", length=20)

    public String getContractno() {
        return this.contractno;
    }
    
    public void setContractno(String contractno) {
        this.contractno = contractno;
    }
    
    @Column(name="CONTRACTNAME", length=100)

    public String getContractname() {
        return this.contractname;
    }
    
    public void setContractname(String contractname) {
        this.contractname = contractname;
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
    
    @Column(name="READTIMES", length=0)

    public String getReadtimes() {
        return this.readtimes;
    }
    
    public void setReadtimes(String readtimes) {
        this.readtimes = readtimes;
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