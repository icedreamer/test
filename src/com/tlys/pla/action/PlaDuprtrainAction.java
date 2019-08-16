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
import com.tlys.pla.model.PlaDuprtrain;
import com.tlys.pla.service.PlaDuprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDuprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDuprtrain plaDuprtrain;
	private List<PlaDuprtrain> plaDuprtrains;
	private String acceptcarno;
	@Autowired
	PlaDuprtrainService plaDuprtrainService;

	/**
	 * �б���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String _list() throws Exception {
		if (null == plaDuprtrain) {
			if (null == acceptcarno) {
				// ��׼Ҫ���ֺ�Ϊ�ղ�ѯȫ��
				plaDuprtrains = plaDuprtrainService.findAll();
			} else {
				// ������׼Ҫ���ֺŲ�ѯ���
				plaDuprtrains = plaDuprtrainService.find(acceptcarno);
			}
		} else {
			// ����plaDuprtrain�������������ѯ���
			plaDuprtrains = plaDuprtrainService.find(plaDuprtrain);
		}
		return "list";
	}*/

	/**
	 * �б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-duprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != plaDuprtrain) {
				String schObjKey = "plaDuprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDuprtrain);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				plaDuprtrain = (PlaDuprtrain) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(100);//����ҳ
		plaDuprtrainService.find(plaDuprtrain, pageCtr);
		return "list";
	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDuprtrain = plaDuprtrainService.load(id);
		plaDuprtrainService.delete(plaDuprtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDuprtrain = plaDuprtrainService.load(id);
			isNew = false;
		} else {
			plaDuprtrain = new PlaDuprtrain();
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
			plaDuprtrainService.save(plaDuprtrain);
		} else {
			// �޸ļ�¼
			plaDuprtrainService.update(plaDuprtrain);
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

	public PlaDuprtrain getPlaDuprtrain() {
		return plaDuprtrain;
	}

	public void setPlaDuprtrain(PlaDuprtrain plaDuprtrain) {
		this.plaDuprtrain = plaDuprtrain;
	}

	public String getAcceptcarno() {
		return acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	public List<PlaDuprtrain> getPlaDuprtrains() {
		return plaDuprtrains;
	}
}
