package com.tlys.spe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * SpeCertishiptrain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_SPE_CERTISHIPTRAIN"
    ,schema="HXZBC"
)

public class SpeCertishiptrain  implements java.io.Serializable {


	
    // Fields    

     private Long id;
     private Long regid;
     private String startdate;
     private String enddate;
     private Double valiperiod;
     private String trainer;

     private String startdateStr;
     private String enddateStr;

    // Constructors

    /** default constructor */
    public SpeCertishiptrain() {
    }

	/** minimal constructor */
    public SpeCertishiptrain(Long id, Long regid, String startdate, String enddate) {
        this.id = id;
        this.regid = regid;
        this.startdate = startdate;
        this.enddate = enddate;
    }
    
    /** full constructor */
    public SpeCertishiptrain(Long id, Long regid, String startdate, String enddate, Double valiperiod, String trainer) {
        this.id = id;
        this.regid = regid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.valiperiod = valiperiod;
        this.trainer = trainer;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="oraSeqGenerator") 
   	@SequenceGenerator(name="oraSeqGenerator", 
   			sequenceName="SEQ_TB_ZBC_SPE_CERTISHIPTRAIN", allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=6, scale=0)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="REGID", nullable=false, precision=6, scale=0)

    public Long getRegid() {
        return this.regid;
    }
    
    public void setRegid(Long regid) {
        this.regid = regid;
    }
    
    @Column(name="STARTDATE", nullable=false, length=16)

    public String getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    
    @Column(name="ENDDATE", nullable=false, length=8)

    public String getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    
    @Column(name="VALIPERIOD", precision=3)

    public Double getValiperiod() {
        return this.valiperiod;
    }
    
    public void setValiperiod(Double valiperiod) {
        this.valiperiod = valiperiod;
    }
    
    @Column(name="TRAINER", length=150)

    public String getTrainer() {
        return this.trainer;
    }
    
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    @Transient
	public String getStartdateStr() {
		return startdateStr;
	}

	public void setStartdateStr(String startdateStr) {
		this.startdateStr = startdateStr;
	}

	@Transient
	public String getEnddateStr() {
		return enddateStr;
	}

	public void setEnddateStr(String enddateStr) {
		this.enddateStr = enddateStr;
	}
   








}