package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ��ҵվ��AEI������Ӧ��ϵ��ÿ���ֶζ�������
 * 
 * @author �״���
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
