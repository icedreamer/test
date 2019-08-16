package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.tlys.pla.model.PlaDtrtrain;
import com.tlys.pla.service.PlaDtrtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDtrtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private final Logger log = Logger.getLogger(this.getClass());
	private String id;
	private PlaDtrtrain plaDtrtrain;
	private List<PlaDtrtrain> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	//��������
	private String schobjkey;
	
	/**
	 * ��Ʒ����(ͳ������ͳ��...)
	 */
	private String prkind;
	
	private String prkindname;
	/**
	 * ��ҵ��ѯ����ֻ�����ݲ���ʹ��
	 */
	//private String corpid="";
	/**
	 * ����˾ID
	 */
	//private String areaid="";
	/**
	 * ��ҵ�ջ���ѯ����
	 */
	private String accept="";
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaDtrtrainService plaDtrtrainService;
	
	
	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaDtrtrain = (PlaDtrtrain) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaDtrtrainService.expExcel(plaDtrtrain, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-dtrtrain!list.action";
		if(null == prkind){
			prkind = "1";//Ĭ��Ϊͳ��
		}
		
		if("1".equals(prkind)){
			prkindname = "ͳ��";
		}else if("0".equals(prkind)){
			prkindname = "��ͳ��";
		}else if("-1".equals(prkind)){
			prkindname = "������Ʒ";
		}
		
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			
			//���ݵ�ǰ�û���ǰ̨�����Ĳ����Բ�ѯ����������װ
			//������plaDtrtrainһ����Ϊnull
			plaDtrtrain = plaDtrtrainService.buildAuthSch(plaDtrtrain);
			if("-1".equals(prkind)){
				plaDtrtrain.setExPrkind("0,1");
			}else{
				plaDtrtrain.setProductkind(prkind);
			}
			
			//�״ν���ʱ��һ�����ϵ�ǰ�·�Ϊ��ѯ����
			if(null == plaDtrtrain.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaDtrtrain.setMonthStr(mStr);
			}
			

			
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != plaDtrtrain){
				String schObjKey = "plaDtrtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDtrtrain);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaDtrtrain = (PlaDtrtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(14);
		plaDtrtrainService.find(plaDtrtrain, pageCtr);
		return "list";
	}


	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDtrtrain = plaDtrtrainService.load(id);
		plaDtrtrainService.delete(plaDtrtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDtrtrain = plaDtrtrainService.load(id);
			isNew = false;
		} else {
			plaDtrtrain = new PlaDtrtrain();
			isNew = true;
		}
		PlaDtrtrain markSch = new PlaDtrtrain();
		mstList = plaDtrtrainService.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaDtrtrainService.update(plaDtrtrain);
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
			plaDtrtrainService.save(plaDtrtrain);
		} else {
			// �޸ļ�¼
			plaDtrtrainService.update(plaDtrtrain);
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

	public PlaDtrtrain getPlaDtrtrain() {
		return plaDtrtrain;
	}

	public void setPlaDtrtrain(PlaDtrtrain plaDtrtrain) {
		this.plaDtrtrain = plaDtrtrain;
	}

	public List<PlaDtrtrain> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaDtrtrain> mstList) {
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


	public String getPrkind() {
		return prkind;
	}


	public void setPrkind(String prkind) {
		this.prkind = prkind;
	}


	public String getPrkindname() {
		return prkindname;
	}


	public void setPrkindname(String prkindname) {
		this.prkindname = prkindname;
	}


	public String getSchobjkey() {
		return schobjkey;
	}


	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
