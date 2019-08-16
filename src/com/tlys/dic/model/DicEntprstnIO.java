package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 企业站区AEI进出对应关系表，每个字段都是主键
 * 
 * @author 孔垂云
 * 
 */
@Entity
@Table(name = "TB_ZBC_DIC_ENTPRSTN_IO")
public class DicEntprstnIO implements Serializable {
	@EmbeddedId
	private DicEntprstnIOID id;

	public DicEntprstnIOID getId() {
		return id;
	}

	public void setId(DicEntprstnIOID id) {
		this.id = id;
	}
}
