package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.model.PlaDoprtrainOld;
import com.tlys.pla.service.PlaDoprtrainServiceOld;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDoprtrainActionOld extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDoprtrainOld plaDoprtrainOld;
	private List<PlaDoprtrainOld> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	//��������
	private String schobjkey;
	
	/**
	 * ��ҵ��ѯ����ֻ�����ݲ���ʹ��
	 */
	//private String corpid="";
	/**
	 * ����˾ID
	 */
	//private String areaid="";
	
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaDoprtrainServiceOld plaDoprtrainServiceOld;
	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaDoprtrainOld = (PlaDoprtrainOld) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaDoprtrainServiceOld.expExcel(plaDoprtrainOld, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-doprtrain!list.action";
		
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();
			
			//���ݵ�ǰ�û���ǰ̨�����Ĳ����Բ�ѯ����������װ
			//������plaDoprtrainһ����Ϊnull
			plaDoprtrainOld = plaDoprtrainServiceOld.buildAuthSch(plaDoprtrainOld,curUser);
			
			//�״ν���ʱ��һ�����ϵ�ǰ�·�Ϊ��ѯ����
			if(null == plaDoprtrainOld.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaDoprtrainOld.setMonthStr(mStr);
			}

			
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != plaDoprtrainOld){
				String schObjKey = "plaDoprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDoprtrainOld);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaDoprtrainOld = (PlaDoprtrainOld) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(14);
		plaDoprtrainServiceOld.find(plaDoprtrainOld, pageCtr);
		return "list";
	}


	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDoprtrainOld = plaDoprtrainServiceOld.load(id);
		plaDoprtrainServiceOld.delete(plaDoprtrainOld);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDoprtrainOld = plaDoprtrainServiceOld.load(id);
			isNew = false;
		} else {
			plaDoprtrainOld = new PlaDoprtrainOld();
			isNew = true;
		}
		PlaDoprtrainOld markSch = new PlaDoprtrainOld();
		mstList = plaDoprtrainServiceOld.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaDoprtrainServiceOld.update(plaDoprtrainOld);
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
			plaDoprtrainServiceOld.save(plaDoprtrainOld);
		} else {
			// �޸ļ�¼
			plaDoprtrainServiceOld.update(plaDoprtrainOld);
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

	public PlaDoprtrainOld getPlaDoprtrain() {
		return plaDoprtrainOld;
	}

	public void setPlaDoprtrain(PlaDoprtrainOld plaDoprtrainOld) {
		this.plaDoprtrainOld = plaDoprtrainOld;
	}

	public List<PlaDoprtrainOld> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaDoprtrainOld> mstList) {
		this.mstList = mstList;
	}

	public List<DicAreacorp> getAreacorpList() {
		return areacorpList;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("PLA_MUPR");
	}

	
	public List<DicSinocorp> getSinocorpList() {
		return sinocorpList;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
