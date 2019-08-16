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
import com.tlys.pla.model.PlaMuprtrain;
import com.tlys.pla.service.PlaMuprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMuprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private final Logger log = Logger.getLogger(this.getClass());
	private String id;
	private PlaMuprtrain plaMuprtrain;
	private List<PlaMuprtrain> mstList;
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
	/**
	 * ��ҵ�ջ���ѯ����
	 */
	private String accept="";
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaMuprtrainService plaMuprtrainService;
	
	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaMuprtrain = (PlaMuprtrain) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaMuprtrainService.expExcel(plaMuprtrain, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	public String list() throws Exception {
		String pageUrl = "/pla/pla-muprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();

			
			//���ݵ�ǰ�û���ǰ̨�����Ĳ����Բ�ѯ����������װ
			//������plaMuprtrainһ����Ϊnull
			plaMuprtrain = plaMuprtrainService.buildAuthSch(plaMuprtrain);
			if(null == plaMuprtrain.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaMuprtrain.setMonthStr(mStr);
			}
			
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != plaMuprtrain){
				String schObjKey = "plaMuprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaMuprtrain);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMuprtrain = (PlaMuprtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(5);
		plaMuprtrainService.find(plaMuprtrain, pageCtr);
		return "list";
	}


	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMuprtrain = plaMuprtrainService.load(id);
		plaMuprtrainService.delete(plaMuprtrain);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaMuprtrain = plaMuprtrainService.load(id);
			isNew = false;
		} else {
			plaMuprtrain = new PlaMuprtrain();
			isNew = true;
		}
		PlaMuprtrain markSch = new PlaMuprtrain();
		mstList = plaMuprtrainService.find(markSch);

		return "input";
	}

	/**
	 * ����״̬��AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaMuprtrainService.update(plaMuprtrain);
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
			plaMuprtrainService.save(plaMuprtrain);
		} else {
			// �޸ļ�¼
			plaMuprtrainService.update(plaMuprtrain);
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

	public PlaMuprtrain getPlaMuprtrain() {
		return plaMuprtrain;
	}

	public void setPlaMuprtrain(PlaMuprtrain plaMuprtrain) {
		this.plaMuprtrain = plaMuprtrain;
	}

	public List<PlaMuprtrain> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaMuprtrain> mstList) {
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
