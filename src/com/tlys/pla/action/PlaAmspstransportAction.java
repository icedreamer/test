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
import com.tlys.pla.model.PlaAmspstransport;
import com.tlys.pla.service.PlaAmspstransportService;
import com.tlys.sys.model.SysNavimenu;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaAmspstransportAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaAmspstransport plaAmspstransport = new PlaAmspstransport();
	private List<PlaAmspstransport> mstList;
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
	PlaAmspstransportService plaAmspstransportService;
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
			// �����û���λID�͵�λ���Ͳ�ѯ��ǰ�û�������ҵID����������˾ID
			// ��λIDΪ35000000��λ����ΪnullĬ��Ϊ�ܲ����ò�ѯ
			if (!"35000000".equals(userCorpid)) {
				// �����û���λ��ѯ������ҵ
				corpid = plaAmspstransportService.getCorpid(userCorpid,
						userCorptab);
			}
			// �ܲ�����areaidΪ""
			if (!"".equals(corpid)){
				plaAmspstransport.setCorpid(corpid);
			} else {
				areaid = plaAmspstransportService.getAreaid(userCorpid,
						userCorptab);
				dicSinocorps = dicSinocorpService.findDicSinocorp(areaid,
						corpid);
				String cors = CommUtils.getRestrictionsIn(dicSinocorps,
						"corpid");
				if (plaAmspstransport.getCorpids() == null) {
					plaAmspstransport.setCorpids(cors);
				}
			}
		}
		if (null == plaAmspstransport.getStatuss()
				|| "".equals(plaAmspstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_AMSPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// �����ǰ�û����з�����Ȩ�ޣ��������Կ����ѵ���ļƻ� 
			if (null != menuAuthMap.get("pla-amspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-amspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmspstransport.setStatuss("9");
		}
		mstList = plaAmspstransportService.find(plaAmspstransport);
		return "list";
	}

	/**
	 * �б���Ϣ �ƻ�״̬��ǰ̨���ݽ��� �ܲ��鿴���мƻ�������˾�鿴����λ�ƻ���פ�������ҵ�鿴��������˾�ƻ� ����Ȩ�� ����״̬��ҳ�������ʾ
	 * ����Ȩ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if (null == plaAmspstransport.getStatuss()
				|| "".equals(plaAmspstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_AMSPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// �����ǰ�û����з�����Ȩ�ޣ��������Կ����ѵ���ļƻ� 
			if (null != menuAuthMap.get("pla-amspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-amspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmspstransport.setStatuss("9");
		}
		Map map = plaAmspstransportService
				.findByAccept(corpid, plaAmspstransport);
		this.mstList = (List<PlaAmspstransport>) map.get("list");
		this.accept = (String) map.get("senders");
		corpid = "";
		return "list";
	}
	public String listpop() throws Exception {
		list();
		return "listpop";
	}
	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaAmspstransport = plaAmspstransportService.load(id);
		plaAmspstransportService.delete(plaAmspstransport);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaAmspstransport = plaAmspstransportService.load(id);
			isNew = false;
		} else {
			plaAmspstransport = new PlaAmspstransport();
			isNew = true;
		}
		PlaAmspstransport markSch = new PlaAmspstransport();
		mstList = plaAmspstransportService.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaAmspstransportService.update(plaAmspstransport);
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
			plaAmspstransport.setCreatedtime(new Date());
			plaAmspstransportService.save(plaAmspstransport);
		} else {
			// �޸ļ�¼
			plaAmspstransportService.update(plaAmspstransport);
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

	public PlaAmspstransport getPlaAmspstransport() {
		return plaAmspstransport;
	}

	public void setPlaAmspstransport(PlaAmspstransport plaAmspstransport) {
		this.plaAmspstransport = plaAmspstransport;
	}

	public List<PlaAmspstransport> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaAmspstransport> mstList) {
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
		initOpraMap("PLA_AMSPS");
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

}
