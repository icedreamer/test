package com.tlys.spe.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.spe.model.SpeLoadMeastype;
import com.tlys.spe.service.SpeLoadMeastypeService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeLoadMeastypeAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	private SpeLoadMeastype speLoadMeastype;
	private List<SpeLoadMeastype> speLoadMeastypes;

	@Autowired
	SpeLoadMeastypeService speLoadMeastypeService;

	/**
	 * �б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {

		if (null == speLoadMeastype) {
			speLoadMeastypes = speLoadMeastypeService.findAll();
		} else {
			speLoadMeastypes = speLoadMeastypeService.find(speLoadMeastype);
		}
		return "list";
	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() {
		speLoadMeastype = speLoadMeastypeService.getSpeLoadMeastype(id);
		speLoadMeastypeService.delete(speLoadMeastype);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			speLoadMeastype = speLoadMeastypeService.getSpeLoadMeastype(id);
			isNew = false;
		} else {
			speLoadMeastype = new SpeLoadMeastype();
			isNew = true;
		}
		return "input";
	}

	/**
	 * ������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// ������¼
			getCurUser();
			speLoadMeastype.setCreator(curUser.getUserid());
			speLoadMeastypeService.save(speLoadMeastype);
		} else {
			// �޸ļ�¼
			speLoadMeastypeService.update(speLoadMeastype);
		}
		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}

	public void setSpeLoadMeastype(SpeLoadMeastype speLoadMeastype) {
		this.speLoadMeastype = speLoadMeastype;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_MANAGE");
	}

	public String getId() {
		return id;
	}

	public SpeLoadMeastype getSpeLoadMeastype() {
		return speLoadMeastype;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SpeLoadMeastype> getSpeLoadMeastypes() {
		return speLoadMeastypes;
	}
}
