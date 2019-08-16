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
import com.tlys.pla.model.PlaMupstransport2;
import com.tlys.pla.service.PlaMupstransport2Service;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMupstransport2Action extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaMupstransport2 plaMupstransport2;

	@Autowired
	PlaMupstransport2Service plaMupstransport2Service;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-mupstransport2!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if(null==plaMupstransport2){
			String schObjKey = "plaMupstransport2_" + new Date().getTime();
			pageCtr.setSchObjKey(schObjKey);
			setSessionAttr(schObjKey, plaMupstransport2);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMupstransport2 = (PlaMupstransport2) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		plaMupstransport2Service.find(plaMupstransport2, pageCtr);
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
//		Map map = plaMupstransport2Service.findByAccept(corpid,
//				plaMupstransport2);
//		this.mstList = (List<PlaMupstransport2>) map.get("list");
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
		plaMupstransport2 = plaMupstransport2Service.load(id);
		String delRs = plaMupstransport2Service.delete(plaMupstransport2);
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
		String result = plaMupstransport2Service.update(plaMupstransport2);
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

	public PlaMupstransport2 getPlaMupstransport2() {
		return plaMupstransport2;
	}

	public void setPlaMupstransport2(PlaMupstransport2 plaMupstransport2) {
		this.plaMupstransport2 = plaMupstransport2;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_MUPS2");
	}

}
