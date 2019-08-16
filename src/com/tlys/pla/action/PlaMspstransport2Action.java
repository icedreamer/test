package com.tlys.pla.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMspstransport2;
import com.tlys.pla.service.PlaMspstransport2Service;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMspstransport2Action extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaMspstransport2 plaMspstransport2;
	@Autowired
	PlaMspstransport2Service plaMspstransport2Service;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-mspstransport2!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null == plaMspstransport2) {
				String schObjKey = "plaMspstransport2_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaMspstransport2);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMspstransport2 = (PlaMspstransport2) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		plaMspstransport2Service.find(plaMspstransport2, pageCtr);
		return "list";
	}
	
	

	/**
	 * �б���Ϣ �ƻ�״̬��ǰ̨���ݽ��� �ܲ��鿴���мƻ�������˾�鿴����λ�ƻ���פ�������ҵ�鿴��������˾�ƻ� ����Ȩ�� ����״̬��ҳ�������ʾ
	 * ����Ȩ��
	 * 
	 * @return
	 * @throws Exception
	 */
//	public String query() throws Exception {
//		Map map = plaMspstransport2Service.findByAccept(corpid,
//				plaMspstransport2);
//		this.mstList = (List<PlaMspstransport2>) map.get("list");
//		this.accept = (String) map.get("senders");
//		corpid = "";
//		return "list";
//	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMspstransport2 = plaMspstransport2Service.load(id);
		String delRs = plaMspstransport2Service.delete(plaMspstransport2);
		msg = new Msg(Msg.SUCCESS, delRs);
		return MSG;
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		
		String result  = plaMspstransport2Service.update(plaMspstransport2);
		msg = new Msg(Msg.SUCCESS, result);
		return MSG;
	}

	// -------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaMspstransport2 getPlaMspstransport2() {
		return plaMspstransport2;
	}

	public void setPlaMspstransport2(PlaMspstransport2 plaMspstransport2) {
		this.plaMspstransport2 = plaMspstransport2;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_MSPS2");
	}

}
