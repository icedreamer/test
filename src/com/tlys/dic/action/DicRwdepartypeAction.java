/**
 * 
 */
/**
 * @author ������
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
import com.tlys.dic.model.DicRwdepartype;
import com.tlys.dic.service.DicRwdepartypeService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicRwdepartypeAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicRwdepartype DicRwdepartype;
	private List<DicRwdepartype> DicRwdepartypes;
	private String id;
	private Map<String, List> suborgMap;

	private boolean isNew;

	@Autowired
	DicRwdepartypeService DicRwdepartypeService;
	
	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// �õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		DicRwdepartypeService.expExcel(DicRwdepartypes, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() {
		if (null == DicRwdepartype) {
			DicRwdepartypes = DicRwdepartypeService.findAll();
		} else {
			DicRwdepartypes = DicRwdepartypeService.find(DicRwdepartype);
		}
		// System.out.println(DicRwdepartypes);

		return "list";
	}

	public String edit() {
		if (null != id) {
			detail();
			isNew = false;
		} else {
			DicRwdepartype = null;
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.DicRwdepartype = DicRwdepartypeService.load(id);
		// System.out.println("DicRwdepartypeAction.detail->rmap=="+rmap);
		return "detail";
	}

	public String save() throws Exception {
		if (null == DicRwdepartype) {
			throw new Exception("����δ���յ���");
		} else {
			// System.out.println("DicRwdepartypeAction.save->DicRwdepartype.getAddress()=="+DicRwdepartype.getAddress());
			DicRwdepartypeService.save(DicRwdepartype, isNew);

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

	public String suborgs() {
		suborgMap = DicRwdepartypeService.findSuborgsMap(id);

		return "suborgs";
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			DicRwdepartypeService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	public String listpop() {
		list();
		return "listpop";
	}

	public DicRwdepartype getDicRwdepartype() {
		return DicRwdepartype;
	}

	public void setDicRwdepartype(DicRwdepartype DicRwdepartype) {
		this.DicRwdepartype = DicRwdepartype;
	}

	public List<DicRwdepartype> getDicRwdepartypes() {
		return DicRwdepartypes;
	}

	public void setDicRwdepartypes(List<DicRwdepartype> DicRwdepartypes) {
		this.DicRwdepartypes = DicRwdepartypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Map<String, List> getSuborgMap() {
		return suborgMap;
	}

	public void setNew(boolean isNew) {
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
