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
import com.tlys.comm.util.FormatUtil;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.pla.model.PlaAmupstransport;
import com.tlys.pla.service.PlaAmupstransportService;
import com.tlys.sys.model.SysNavimenu;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaAmupstransportAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaAmupstransport plaAmupstransport = new PlaAmupstransport();
	private List<PlaAmupstransport> mstList;
	private List<DicAreacorp> areacorpList;
	/**
	 * ��ǰ�û�������ҵID
	 */
	private String corpid = "";
	/**
	 * ��ǰ�û���������˾ID
	 */
	private String areaid = "";
	/**
	 * ��ҵ�ջ���ѯ����
	 */
	private String accept = "";
	@Autowired
	DicAreacorpService dicAreacorpService;

	@Autowired
	PlaAmupstransportService plaAmupstransportService;

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
				corpid = plaAmupstransportService.getCorpid(userCorpid,
						userCorptab);
				// �����û���λ��ѯ��������˾
				areaid = plaAmupstransportService.getAreaid(userCorpid,
						userCorptab);
			}
			// �ܲ�����areaidΪ""
			if (!"".equals(areaid))
				plaAmupstransport.setAreaid(areaid);
		}
		if (null == plaAmupstransport.getStatuss()
				|| "".equals(plaAmupstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService
					.getSysNavimenuByMenuCode("PLA_AMUPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);
			

			// �����ǰ�û������ύ��Ȩ�ޣ��������Կ���δ�ύ�Ͳ��صļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_1")) {
				statuss += "0,2,";
			}
			// �����ǰ�û����з�����Ȩ�ޣ��������Կ������ύ�ļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_3")) {
				statuss += "1,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmupstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmupstransport.setStatuss("9");
		}
		// ��������ѯ����
		DicAreacorp dicAreacorp = new DicAreacorp();
		dicAreacorp.setAreaid(areaid);
		areacorpList = dicAreacorpService.findByAreaid(dicAreacorp);
		if ("".equals(areaid)) {// �ܲ��û��������ҳ���ѯȫ��������
			DicAreacorp obj = new DicAreacorp();
			obj.setAreaid("");
			obj.setShortname("ȫ��");
			areacorpList.add(0, obj);
		}
		mstList = plaAmupstransportService.find(plaAmupstransport);
		
		//�ڴ˵��ô洢����,�����������ǰ�·�(yyyyMM)
		plaAmupstransportService.syncPlaAmup(FormatUtil.nowDateStr(FormatUtil.COMPACT_MONTH));
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
		if (null == plaAmupstransport.getStatuss()
				|| "".equals(plaAmupstransport.getStatuss())) {
			String statuss = "";
			// �����û�Ȩ�޹��˼ƻ�״̬
			SysNavimenu menu = menuService
					.getSysNavimenuByMenuCode("PLA_AMUPS");
			if (null == menu) {
				throw new Exception("Ȩ�޲˵�����ʧ��");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// �����ǰ�û������ύ��Ȩ�ޣ��������Կ���δ�ύ�Ͳ��صļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_1")) {
				statuss += "0,2,";
			}
			// �����ǰ�û����з�����Ȩ�ޣ��������Կ������ύ�ļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_3")) {
				statuss += "1,";
			}
			// �����ǰ�û��������Ȩ�ޣ�����Կ��������ѷ����ļƻ�
			if (null != menuAuthMap.get("pla-amupstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmupstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmupstransport.setStatuss("9");
		}
		// ��ҵ������ѯ��ʾȫ������˾
		areacorpList = dicAreacorpService.findAll();
		DicAreacorp obj = new DicAreacorp();
		obj.setAreaid("");
		obj.setShortname("ȫ��");
		areacorpList.add(0, obj);
		Map map = plaAmupstransportService.findByAccept(corpid,
				plaAmupstransport);
		this.mstList = (List<PlaAmupstransport>) map.get("list");
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
		plaAmupstransport = plaAmupstransportService.load(id);
		plaAmupstransportService.delete(plaAmupstransport);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaAmupstransport = plaAmupstransportService.load(id);
			isNew = false;
		} else {
			plaAmupstransport = new PlaAmupstransport();
			isNew = true;
		}
		PlaAmupstransport markSch = new PlaAmupstransport();
		mstList = plaAmupstransportService.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaAmupstransportService.update(plaAmupstransport);
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
			plaAmupstransport.setCreatedtime(new Date());
			plaAmupstransportService.save(plaAmupstransport);
		} else {
			// �޸ļ�¼
			plaAmupstransportService.update(plaAmupstransport);
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

	public PlaAmupstransport getPlaAmupstransport() {
		return plaAmupstransport;
	}

	public void setPlaAmupstransport(PlaAmupstransport plaAmupstransport) {
		this.plaAmupstransport = plaAmupstransport;
	}

	public List<PlaAmupstransport> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaAmupstransport> mstList) {
		this.mstList = mstList;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public List<DicAreacorp> getAreacorpList() {
		return areacorpList;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_AMUPS");
	}

}
