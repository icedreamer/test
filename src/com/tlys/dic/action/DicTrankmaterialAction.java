/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;

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
import com.tlys.dic.model.DicTrankMaterial;
import com.tlys.dic.service.DicTrankMaterialService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicTrankmaterialAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicTrankMaterial dicTrankmaterial;
	private List<DicTrankMaterial> dicTrankMaterials;
	private String id;

	private boolean isNew;

	@Autowired
	DicTrankMaterialService dicTrankMaterialService;

	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// �õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicTrankMaterialService.expExcel(dicTrankMaterials, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicTrankmaterial) {
			dicTrankMaterials = dicTrankMaterialService.findDICAll();
		} else {
			dicTrankMaterials = dicTrankMaterialService.find(dicTrankmaterial);
		}
		return "list";
	}

	public String edit() {
		if (null != id) {
			this.dicTrankmaterial = dicTrankMaterialService.load(id);
			isNew = false;
		} else {
			dicTrankmaterial = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicTrankmaterial) {
			throw new Exception("����δ���յ���");
		} else {
			// System.out.println("DicTrankMaterialAction.save->dicTrankMaterial.getAddress()=="+dicTrankMaterial.getAddress());
			dicTrankMaterialService.save(dicTrankmaterial, isNew);

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
			dicTrankMaterialService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	// =================================
	/**
	 * @return the dicTrankMaterial
	 */
	public DicTrankMaterial getDicTrankmaterial() {
		return dicTrankmaterial;
	}

	/**
	 * @param dicTrankMaterial
	 *            the dicTrankMaterial to set
	 */
	public void setDicTrankmaterial(DicTrankMaterial dicTrankMaterial) {
		this.dicTrankmaterial = dicTrankMaterial;
	}

	/**
	 * @return the dicTrankMaterials
	 */
	public List<DicTrankMaterial> getDicTrankMaterials() {
		return dicTrankMaterials;
	}

	/**
	 * @param dicTrankMaterials
	 *            the dicTrankMaterials to set
	 */
	public void setDicTrankMaterials(List<DicTrankMaterial> dicTrankMaterials) {
		this.dicTrankMaterials = dicTrankMaterials;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
