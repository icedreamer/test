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
import com.tlys.pla.model.PlaMsprtrain;
import com.tlys.pla.service.PlaMsprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMsprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaMsprtrain plaMsprtrain;
	private List<PlaMsprtrain> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	/**
	 * ��ҵ�ջ���ѯ����
	 */
	private String accept="";
	//��������
	private String schobjkey;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaMsprtrainService plaMsprtrainService;
	
	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaMsprtrain = (PlaMsprtrain) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaMsprtrainService.expExcel(plaMsprtrain, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-msprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();
			
			//���ݵ�ǰ�û���ǰ̨�����Ĳ����Բ�ѯ����������װ
			//������plaMsprtrainһ����Ϊnull
			plaMsprtrain = plaMsprtrainService.buildAuthSch(plaMsprtrain);

			if(null == plaMsprtrain.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaMsprtrain.setMonthStr(mStr);
			}
			
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != plaMsprtrain){
				String schObjKey = "plaMsprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaMsprtrain);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMsprtrain = (PlaMsprtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(5);
		plaMsprtrainService.find(plaMsprtrain, pageCtr);
		return "list";
	}


	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMsprtrain = plaMsprtrainService.load(id);
		plaMsprtrainService.delete(plaMsprtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaMsprtrain = plaMsprtrainService.load(id);
			isNew = false;
		} else {
			plaMsprtrain = new PlaMsprtrain();
			isNew = true;
		}
		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaMsprtrainService.update(plaMsprtrain);
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
			plaMsprtrainService.save(plaMsprtrain);
		} else {
			// �޸ļ�¼
			plaMsprtrainService.update(plaMsprtrain);
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

	public PlaMsprtrain getPlaMsprtrain() {
		return plaMsprtrain;
	}

	public void setPlaMsprtrain(PlaMsprtrain plaMsprtrain) {
		this.plaMsprtrain = plaMsprtrain;
	}

	public List<PlaMsprtrain> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaMsprtrain> mstList) {
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

	
	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
