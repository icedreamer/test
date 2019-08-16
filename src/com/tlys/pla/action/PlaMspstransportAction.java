package com.tlys.pla.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.model.PlaMspstransport;
import com.tlys.pla.service.PlaMspstransportService;
import com.tlys.sys.model.SysNavimenu;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMspstransportAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaMspstransport plaMspstransport = new PlaMspstransport();
	private List<PlaMspstransport> mstList;
	private List<DicSinocorp> dicSinocorps;
	/**
	 * ��ǰ�û�������ҵID
	 */
	private String corpid = "";
	/**
	 * ��ҵ�ջ���ѯ����
	 */
	private String accept = "";
	/**
	 * ��ҵ��ѯ����ʱ��Ҫ������ID������˾�û�����ʱʹ��)
	 */
	private String areaid="";
	/**
	 * ��ҵ��ѯ�ı�����ʾ����
	 */
	private String corpids="";

	@Autowired
	PlaMspstransportService plaMspstransportService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	/**
	 * �б���Ϣ �ƻ�״̬��ǰ̨���ݽ��� �ܲ��鿴���мƻ�������˾�鿴����λ�ƻ���פ�������ҵ�鿴��������˾�ƻ� ����Ȩ�� ����״̬��ҳ�������ʾ
	 * ����Ȩ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		// ������ѯ��ǰ�û�������ҵID������˾IDΪ��
		if ("".equals(accept)) {
			// ��õ�ǰ�û���Ϣ
			getCurUser();
			String userCorpid = curUser.getCorpid();
			String userCorptab = curUser.getCorptab();
			// �����û���λID�͵�λ���Ͳ�ѯ��ǰ�û�������ҵID
			// ��λIDΪ35000000��λ����ΪnullĬ��Ϊ�ܲ����ò�ѯ
			if (!"35000000".equals(userCorpid)) {
				// �����û���λ��ѯ������ҵ
				corpid = plaMspstransportService.getCorpid(userCorpid,
						userCorptab);
			}
			// �ܲ�������˾����corpidΪ""��ʾ��ҵ��ѯ��������ҵ���������ҵ�ƻ�
			if (!"".equals(corpid)) {
				plaMspstransport.setCorpid(corpid);
			} else {
				areaid = plaMspstransportService.getAreaid(userCorpid,
						userCorptab);
				dicSinocorps = dicSinocorpService.findDicSinocorp(areaid,
						corpid);
				String cors = CommUtils.getRestrictionsIn(dicSinocorps,
						"corpid");
				if (plaMspstransport.getCorpids() == null) {
					plaMspstransport.setCorpids(cors);
				}
			}
		}
		if (null == plaMspstransport.getStatuss()
				|| "".equals(plaMspstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_MSPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// �����ǰ�û����з�����Ȩ�ޣ��������Կ����ѵ���ļƻ�
			if (null != menuAuthMap.get("pla-mspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-mspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaMspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaMspstransport.setStatuss("9");
		}
		mstList = plaMspstransportService.find(plaMspstransport);
		return "list";
	}

	public String listpop() throws Exception {
		list();
		return "listpop";
	}

	/**
	 * �б���Ϣ �ƻ�״̬��ǰ̨���ݽ��� �ܲ��鿴���мƻ�������˾�鿴����λ�ƻ���פ�������ҵ�鿴��������˾�ƻ� ����Ȩ�� ����״̬��ҳ�������ʾ
	 * ����Ȩ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if (null == plaMspstransport.getStatuss()
				|| "".equals(plaMspstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_MSPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// �����ǰ�û����з�����Ȩ�ޣ��������Կ����ѵ���ļƻ�
			if (null != menuAuthMap.get("pla-mspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-mspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaMspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaMspstransport.setStatuss("9");
		}
		Map map = plaMspstransportService
				.findByAccept(corpid, plaMspstransport);
		this.mstList = (List<PlaMspstransport>) map.get("list");
		this.accept = (String) map.get("senders");
		corpid = "";
		return "list";
	}

	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMspstransport = plaMspstransportService.load(id);
		plaMspstransportService.delete(plaMspstransport);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaMspstransport = plaMspstransportService.load(id);
			isNew = false;
		} else {
			plaMspstransport = new PlaMspstransport();
			isNew = true;
		}
		PlaMspstransport markSch = new PlaMspstransport();
		mstList = plaMspstransportService.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaMspstransportService.update(plaMspstransport);
		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return MSG;
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
			plaMspstransport.setCreatedtime(new Date());
			plaMspstransportService.save(plaMspstransport);
		} else {
			// �޸ļ�¼
			plaMspstransportService.update(plaMspstransport);
		}

		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}

	// -------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaMspstransport getPlaMspstransport() {
		return plaMspstransport;
	}

	public void setPlaMspstransport(PlaMspstransport plaMspstransport) {
		this.plaMspstransport = plaMspstransport;
	}

	public List<PlaMspstransport> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaMspstransport> mstList) {
		this.mstList = mstList;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_MSPS");
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	public String getAreaid() {
		return areaid;
	}

	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

}
