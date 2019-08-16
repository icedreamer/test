package com.tlys.pla.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;

/**
 * ��Action���������ɷ�ͳ��(��ͳ������)�ƻ���ѯ��ҵ��ѡҳ��
 * 
 * @author guojj
 * 
 */

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaSinocorpAction extends _BaseAction {
	private static final long serialVersionUID = 431299588520414901L;
	/**
	 * ����˾��ʶ
	 */
	private String areaid;
	/**
	 * ��ҵ��ʶ��������ҵ�û�����ʱ��ѯҳ��Ҫ��ʾ��ҵ��ѯ������Ĭ��Ϊ"")
	 */
	private String corpid;
	/**
	 * ��ѡ����ҵ
	 */
	private String corpids;
	private List<DicSinocorp> dicSinocorps;
	@Autowired
	DicSinocorpService dicSinocorpService;

	public String list() throws Exception {
		dicSinocorps = dicSinocorpService.findDicSinocorp(areaid, corpid);
		return "listpop";
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}
}
