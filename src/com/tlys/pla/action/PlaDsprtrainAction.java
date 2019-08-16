package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaDsprtrain;
import com.tlys.pla.service.PlaDsprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDsprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDsprtrain plaDsprtrain;
	private List<PlaDsprtrain> plaDsprtrains;
	@Autowired
	PlaDsprtrainService plaDsprtrainService;

	
	/**
	 * �б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-dsprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != plaDsprtrain) {
				String schObjKey = "plaDsprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDsprtrain);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				plaDsprtrain = (PlaDsprtrain) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(100);//����ҳ
		plaDsprtrainService.find(plaDsprtrain, pageCtr);
		return "list";
	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDsprtrain = plaDsprtrainService.load(id);
		plaDsprtrainService.delete(plaDsprtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDsprtrain = plaDsprtrainService.load(id);
			isNew = false;
		} else {
			plaDsprtrain = new PlaDsprtrain();
			isNew = true;
		}
		return "input";
	}

	/**
	 * ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// ������¼
			plaDsprtrainService.save(plaDsprtrain);
		} else {
			// �޸ļ�¼
			plaDsprtrainService.update(plaDsprtrain);
		}

		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaDsprtrain getPlaDsprtrain() {
		return plaDsprtrain;
	}

	public void setPlaDsprtrain(PlaDsprtrain plaDsprtrain) {
		this.plaDsprtrain = plaDsprtrain;
	}

	public List<PlaDsprtrain> getPlaDsprtrains() {
		return plaDsprtrains;
	}
}
