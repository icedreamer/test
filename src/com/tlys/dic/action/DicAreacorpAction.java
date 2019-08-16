/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author ������
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicAreacorpAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicAreacorp dicAreacorp;
	private List<DicAreacorp> dicAreacorps;
	private String id;

	private List<DicProvince> dicProvinces;

	/**
	 * ������ҵ���������ŵļ���Map
	 */
	private Map<String, List> suborgMap;

	private boolean isNew;

	@Autowired
	DicAreacorpService dicAreacorpService;

	@Autowired
	DicProvinceService dicProvinceService;

	public String list() throws Exception {

		if (null == dicAreacorp) {
			dicAreacorps = dicAreacorpService.findDICAll();
		} else {
			dicAreacorps = dicAreacorpService.find(dicAreacorp);
		}

		return "list";
	}

	public String edit() {
		dicProvinces = dicProvinceService.findAll();
		if (null != id) {
			detail();
			isNew = false;
		} else {
			dicAreacorp = null;
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.dicAreacorp = dicAreacorpService.load(id);
		return "detail";
	}

	public String save() throws Exception {
		if (null == dicAreacorp) {
			throw new Exception("����δ���յ���");
		} else {
			dicAreacorpService.save(dicAreacorp, isNew);

			// ��������msgҳ����ʾ������

			msg = new Msg(
					Msg.SUCCESS,
					"���/�༭�ɹ���",
					false,
					"alert('���/�༭�ɹ���');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			dicAreacorpService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	/**
	 * �õ�����˾������λ�Լ����������б�
	 */
	public String suborgs() {
		suborgMap = dicAreacorpService.findSuborgsMap(id);
		return "suborgs";
	}

	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// �õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicAreacorpService.expExcel(dicAreacorps, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	// =================================
	/**
	 * @return the dicAreacorp
	 */
	public DicAreacorp getDicAreacorp() {
		return dicAreacorp;
	}

	/**
	 * @param dicAreacorp
	 *            the dicAreacorp to set
	 */
	public void setDicAreacorp(DicAreacorp dicAreacorp) {
		this.dicAreacorp = dicAreacorp;
	}

	/**
	 * @return the dicAreacorps
	 */
	public List<DicAreacorp> getDicAreacorps() {
		return dicAreacorps;
	}

	/**
	 * @param dicAreacorps
	 *            the dicAreacorps to set
	 */
	public void setDicAreacorps(List<DicAreacorp> dicAreacorps) {
		this.dicAreacorps = dicAreacorps;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the dicProvinces
	 */
	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	/**
	 * @param dicProvinces
	 *            the dicProvinces to set
	 */
	public void setDicProvinces(List<DicProvince> dicProvinces) {
		this.dicProvinces = dicProvinces;
	}

	/**
	 * @return the suborgMap
	 */
	public Map<String, List> getSuborgMap() {
		return suborgMap;
	}

	/**
	 * @param suborgMap
	 *            the suborgMap to set
	 */
	public void setSuborgMap(Map<String, List> suborgMap) {
		this.suborgMap = suborgMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
