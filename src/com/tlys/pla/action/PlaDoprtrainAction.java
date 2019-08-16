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
import com.tlys.pla.model.PlaDoprtrain;
import com.tlys.pla.service.PlaDoprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDoprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDoprtrain plaDoprtrain;
	private List<PlaDoprtrain> plaDoprtrains;
	@Autowired
	PlaDoprtrainService plaDoprtrainService;

	
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
			if (null != plaDoprtrain) {
				String schObjKey = "plaDoprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDoprtrain);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				plaDoprtrain = (PlaDoprtrain) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(100);//����ҳ
		plaDoprtrainService.find(plaDoprtrain, pageCtr);
		return "list";
	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDoprtrain = plaDoprtrainService.load(id);
		plaDoprtrainService.delete(plaDoprtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDoprtrain = plaDoprtrainService.load(id);
			isNew = false;
		} else {
			plaDoprtrain = new PlaDoprtrain();
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
			plaDoprtrainService.save(plaDoprtrain);
		} else {
			// �޸ļ�¼
			plaDoprtrainService.update(plaDoprtrain);
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

	public PlaDoprtrain getPlaDoprtrain() {
		return plaDoprtrain;
	}

	public void setPlaDoprtrain(PlaDoprtrain plaDoprtrain) {
		this.plaDoprtrain = plaDoprtrain;
	}

	public List<PlaDoprtrain> getPlaDoprtrains() {
		return plaDoprtrains;
	}
}
