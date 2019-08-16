package com.tlys.sys.model;

// Generated 2011-9-20 14:19:26 by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tlys.comm.util.CommUtils;


/**
 * TbZbcSysOprationtype generated by hbm2java
 */
@Entity
@Table(name = "TB_ZBC_SYS_OPRATIONTYPE")
public class SysOprationtype implements java.io.Serializable,java.lang.Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6962460203145090827L;
	private String operid;
	private String opername;
	private String iconpath;
	private char isactive;
	private String description;
	
	/**
	 * ���ڱ�ʶ��ǰ�˵��Ĵ�������Ƿ���Ȩ����ɫ��������־û�
	 */
	private boolean checked;
	
	/**
	 * ���ڱ�ʶ������������ɫʱ�Ĳ˵����ϲ���ID����SysMenuopra��ID)
	 * ֻ���ڴ˶�����SysMenuopra����һ���ѯʱ����ֵ
	 */
	private String curOpraid;

	public SysOprationtype() {
	}

	public SysOprationtype(String operid, String opername, char isactive) {
		this.operid = operid;
		this.opername = opername;
		this.isactive = isactive;
	}

	public SysOprationtype(String operid, String opername,
			String iconpath, char isactive, String description) {
		this.operid = operid;
		this.opername = opername;
		this.iconpath = iconpath;
		this.isactive = isactive;
		this.description = description;
	}

	@Id
	@Column(name = "OPERID", unique = true, nullable = false, length = 3)
	public String getOperid() {
		return this.operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	@Column(name = "OPERNAME", nullable = false, length = 20)
	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	@Column(name = "ICONPATH", length = 120)
	public String getIconpath() {
		return this.iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	@Column(name = "ISACTIVE", nullable = false, length = 1)
	public char getIsactive() {
		return this.isactive;
	}

	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}

	@Column(name = "DESCRIPTION", length = 120)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the checked
	 */
	@Transient
	public boolean getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	/**
	 * ��дObject��equals��������Ϊ���������������
	 * ��operid+curOpraid��Ϊ��������ȵķ���
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof SysOprationtype)){
			return false;
		}
		if(obj==this){
			return true;
		}
		SysOprationtype otherOp = (SysOprationtype)obj;
		
		/**
		 * ֻҪ��һ�������IDֵΪnull��������������ж�Ϊ�����
		*/
		if(otherOp.getOperid()==null || this.getOperid()==null)
			return false;
		
		String curOpraidStr = this.curOpraid == null?"0":this.curOpraid;
		String otherOpraidStr = otherOp.curOpraid==null?"0":otherOp.curOpraid;
		
		String s1 = curOpraidStr + this.getOperid();
		String s2 = otherOpraidStr + otherOp.getOperid();
		
		return s1.equals(s2);
	}
	
	/**
	 * ʹ��������Ϊhashcode
	 * ǰ���Ƕ���δ�־û�֮ǰ����ʹ�õ��˷���
	*/
	public int hashCode(){
		String s1 = this.operid==null?"0":this.operid;
		String s2 = this.curOpraid==null?"0":this.curOpraid;
		return CommUtils.toInt(s1+s2);
	}

	/**
	 * @return the curOpraid
	 */
	@Transient
	public String getCurOpraid() {
		return curOpraid;
	}

	/**
	 * @param curOpraid the curOpraid to set
	 */
	public void setCurOpraid(String curOpraid) {
		this.curOpraid = curOpraid;
	}
	
	/**
	 * ����Object�е�clone���� 
	 */
	public SysOprationtype clone(){
		SysOprationtype newop = null;
		try {
			newop = (SysOprationtype)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newop;
	}

}