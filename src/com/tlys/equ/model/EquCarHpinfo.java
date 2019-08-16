package com.tlys.equ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * EquCarHpinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="TB_ZBC_EQU_CAR_HPINFO"
    ,schema="HXZBC"
)

public class EquCarHpinfo  implements java.io.Serializable {


    // Fields    

     private String carno;
     private Long fullloadweight;
     private String redirectno;
     private String brakeequipmentno;
     private String tightnesstest;
     private Double designpressure;
     private String designtemperature;
     private String tankmateid;
     private Double tankweight;
     private String rustydegree;
     private String hydrostatictest;
     private String tanksize;
     private String liquidno;
     private String liquidtype;
     private Long validHeight;
     private String liquidvalveno;
     private String gasvalveno;
     private Double nominalpressure;
     private Long nominaldiameter;
     private String thermotype;
     private String safetyvalveno;
     private String safetyvalvetype;
     private Double openpressure;
     private Double fopenpressure;
     private Double backpressure;
     private String magnetType;	
 	private String carnos;


    // Constructors

    /** default constructor */
    public EquCarHpinfo() {
    }

	/** minimal constructor */
    public EquCarHpinfo(String carno, String tankmateid) {
        this.carno = carno;
        this.tankmateid = tankmateid;
    }
    
    /** full constructor */
    public EquCarHpinfo(String carno, Long fullloadweight, String redirectno, String brakeequipmentno, String tightnesstest, Double designpressure, String designtemperature, String tankmateid, Double tankweight, String rustydegree, String hydrostatictest, String tanksize, String liquidno, String liquidtype, Long validHeight, String liquidvalveno, String gasvalveno, Double nominalpressure, Long nominaldiameter, String thermotype, String safetyvalveno, String safetyvalvetype, Double openpressure, Double fopenpressure, Double backpressure, String magnetType) {
        this.carno = carno;
        this.fullloadweight = fullloadweight;
        this.redirectno = redirectno;
        this.brakeequipmentno = brakeequipmentno;
        this.tightnesstest = tightnesstest;
        this.designpressure = designpressure;
        this.designtemperature = designtemperature;
        this.tankmateid = tankmateid;
        this.tankweight = tankweight;
        this.rustydegree = rustydegree;
        this.hydrostatictest = hydrostatictest;
        this.tanksize = tanksize;
        this.liquidno = liquidno;
        this.liquidtype = liquidtype;
        this.validHeight = validHeight;
        this.liquidvalveno = liquidvalveno;
        this.gasvalveno = gasvalveno;
        this.nominalpressure = nominalpressure;
        this.nominaldiameter = nominaldiameter;
        this.thermotype = thermotype;
        this.safetyvalveno = safetyvalveno;
        this.safetyvalvetype = safetyvalvetype;
        this.openpressure = openpressure;
        this.fopenpressure = fopenpressure;
        this.backpressure = backpressure;
        this.magnetType = magnetType;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="CARNO", unique=true, nullable=false, length=8)

    public String getCarno() {
        return this.carno;
    }
    
    public void setCarno(String carno) {
        this.carno = carno;
    }
    
    @Column(name="FULLLOADWEIGHT", precision=4, scale=0)

    public Long getFullloadweight() {
        return this.fullloadweight;
    }
    
    public void setFullloadweight(Long fullloadweight) {
        this.fullloadweight = fullloadweight;
    }
    
    @Column(name="REDIRECTNO", length=30)

    public String getRedirectno() {
        return this.redirectno;
    }
    
    public void setRedirectno(String redirectno) {
        this.redirectno = redirectno;
    }
    
    @Column(name="BRAKEEQUIPMENTNO", length=30)

    public String getBrakeequipmentno() {
        return this.brakeequipmentno;
    }
    
    public void setBrakeequipmentno(String brakeequipmentno) {
        this.brakeequipmentno = brakeequipmentno;
    }
    
    @Column(name="TIGHTNESSTEST", length=60)

    public String getTightnesstest() {
        return this.tightnesstest;
    }
    
    public void setTightnesstest(String tightnesstest) {
        this.tightnesstest = tightnesstest;
    }
    
    @Column(name="DESIGNPRESSURE", precision=6)

    public Double getDesignpressure() {
        return this.designpressure;
    }
    
    public void setDesignpressure(Double designpressure) {
        this.designpressure = designpressure;
    }
    
    @Column(name="DESIGNTEMPERATURE", length=30)

    public String getDesigntemperature() {
        return this.designtemperature;
    }
    
    public void setDesigntemperature(String designtemperature) {
        this.designtemperature = designtemperature;
    }
    
    @Column(name="TANKMATEID", nullable=false, length=2)

    public String getTankmateid() {
        return this.tankmateid;
    }
    
    public void setTankmateid(String tankmateid) {
        this.tankmateid = tankmateid;
    }
    
    @Column(name="TANKWEIGHT", precision=6)

    public Double getTankweight() {
        return this.tankweight;
    }
    
    public void setTankweight(Double tankweight) {
        this.tankweight = tankweight;
    }
    
    @Column(name="RUSTYDEGREE", length=60)

    public String getRustydegree() {
        return this.rustydegree;
    }
    
    public void setRustydegree(String rustydegree) {
        this.rustydegree = rustydegree;
    }
    
    @Column(name="HYDROSTATICTEST", length=60)

    public String getHydrostatictest() {
        return this.hydrostatictest;
    }
    
    public void setHydrostatictest(String hydrostatictest) {
        this.hydrostatictest = hydrostatictest;
    }
    
    @Column(name="TANKSIZE", length=60)

    public String getTanksize() {
        return this.tanksize;
    }
    
    public void setTanksize(String tanksize) {
        this.tanksize = tanksize;
    }
    
    @Column(name="LIQUIDNO", length=20)

    public String getLiquidno() {
        return this.liquidno;
    }
    
    public void setLiquidno(String liquidno) {
        this.liquidno = liquidno;
    }
    
    @Column(name="LIQUIDTYPE", length=30)

    public String getLiquidtype() {
        return this.liquidtype;
    }
    
    public void setLiquidtype(String liquidtype) {
        this.liquidtype = liquidtype;
    }
    
    @Column(name="VALID_HEIGHT", precision=6, scale=0)

    public Long getValidHeight() {
        return this.validHeight;
    }
    
    public void setValidHeight(Long validHeight) {
        this.validHeight = validHeight;
    }
    
    @Column(name="LIQUIDVALVENO", length=30)

    public String getLiquidvalveno() {
        return this.liquidvalveno;
    }
    
    public void setLiquidvalveno(String liquidvalveno) {
        this.liquidvalveno = liquidvalveno;
    }
    
    @Column(name="GASVALVENO", length=30)

    public String getGasvalveno() {
        return this.gasvalveno;
    }
    
    public void setGasvalveno(String gasvalveno) {
        this.gasvalveno = gasvalveno;
    }
    
    @Column(name="NOMINALPRESSURE", precision=5)

    public Double getNominalpressure() {
        return this.nominalpressure;
    }
    
    public void setNominalpressure(Double nominalpressure) {
        this.nominalpressure = nominalpressure;
    }
    
    @Column(name="NOMINALDIAMETER", precision=6, scale=0)

    public Long getNominaldiameter() {
        return this.nominaldiameter;
    }
    
    public void setNominaldiameter(Long nominaldiameter) {
        this.nominaldiameter = nominaldiameter;
    }
    
    @Column(name="THERMOTYPE", length=20)

    public String getThermotype() {
        return this.thermotype;
    }
    
    public void setThermotype(String thermotype) {
        this.thermotype = thermotype;
    }
    
    @Column(name="SAFETYVALVENO", length=20)

    public String getSafetyvalveno() {
        return this.safetyvalveno;
    }
    
    public void setSafetyvalveno(String safetyvalveno) {
        this.safetyvalveno = safetyvalveno;
    }
    
    @Column(name="SAFETYVALVETYPE", length=30)

    public String getSafetyvalvetype() {
        return this.safetyvalvetype;
    }
    
    public void setSafetyvalvetype(String safetyvalvetype) {
        this.safetyvalvetype = safetyvalvetype;
    }
    
    @Column(name="OPENPRESSURE", precision=6)

    public Double getOpenpressure() {
        return this.openpressure;
    }
    
    public void setOpenpressure(Double openpressure) {
        this.openpressure = openpressure;
    }
    
    @Column(name="FOPENPRESSURE", precision=6)

    public Double getFopenpressure() {
        return this.fopenpressure;
    }
    
    public void setFopenpressure(Double fopenpressure) {
        this.fopenpressure = fopenpressure;
    }
    
    @Column(name="BACKPRESSURE", precision=6)

    public Double getBackpressure() {
        return this.backpressure;
    }
    
    public void setBackpressure(Double backpressure) {
        this.backpressure = backpressure;
    }
    
    @Column(name="MAGNET_TYPE", length=30)

    public String getMagnetType() {
        return this.magnetType;
    }
    
    public void setMagnetType(String magnetType) {
        this.magnetType = magnetType;
    }
    @Transient
	public String getCarnos() {
		return carnos;
	}

	public void setCarnos(String carnos) {
		this.carnos = carnos;
	}
   








}