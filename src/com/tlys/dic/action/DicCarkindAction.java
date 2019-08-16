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
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicCarkindService;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author ������
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCarkindAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicCarkind dicCarkind;
	private List<DicCarkind> dicCarkinds;
	private String id;

	private List<DicProvince> dicProvinces;

	private boolean isNew;

	@Autowired
	DicCarkindService dicCarkindService;

	@Autowired
	DicProvinceService dicProvinceService;

	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// �õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicCarkindService.expExcel(dicCarkinds, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicCarkind) {
			dicCarkinds = dicCarkindService.findAll();
		} else {
			dicCarkinds = dicCarkindService.find(dicCarkind);
		}

		return "left";
	}

	public String edit() {
		dicProvinces = dicProvinceService.findAll();
		if (null != id) {
			detail();
			isNew = false;
		} else {
			dicCarkind = new DicCarkind();
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.dicCarkind = dicCarkindService.load(id);
		return "detail";
	}

	public String save() throws Exception {
		if (null == dicCarkind) {
			throw new Exception("����δ���յ���");
		} else {
			// System.out.println("DicCarkindAction.save->dicCarkind=="+dicCarkind);
			dicCarkindService.save(dicCarkind, isNew);

			// ��������msgҳ����ʾ������
			msg = new Msg(Msg.SUCCESS, "���/�༭�ɹ���", false, buildScript(), null);
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			dicCarkindService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���", buildScript());
		}
		return MSG;
	}

	public String listpop() throws Exception {
		list();
		return "listpop";
	}

	// =================================
	/**
	 * @return the dicCarkind
	 */
	public DicCarkind getDicCarkind() {
		return dicCarkind;
	}

	/**
	 * @param dicCarkind
	 *            the dicCarkind to set
	 */
	public void setDicCarkind(DicCarkind dicCarkind) {
		this.dicCarkind = dicCarkind;
	}

	/**
	 * @return the dicCarkinds
	 */
	public List<DicCarkind> getDicCarkinds() {
		return dicCarkinds;
	}

	/**
	 * @param dicCarkinds
	 *            the dicCarkinds to set
	 */
	public void setDicCarkinds(List<DicCarkind> dicCarkinds) {
		this.dicCarkinds = dicCarkinds;
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
	 * ��������msgҳ���script ��Ϊ��ҳ�����lhgdialog���ɵĵ����У���˿�����Ը��� ���Է��ڴ˴�����
	 * 
	 * @return
	 */
	private String buildScript() {
		String script = "frameElement.lhgDG.SetCancelBtn('�ر�',function(){frameElement.lhgDG.curWin.location.reload();});";
		script += "frameElement.lhgDG.SetXbtn(function(){frameElement.lhgDG.curWin.location.reload();},false);";
		return script;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
