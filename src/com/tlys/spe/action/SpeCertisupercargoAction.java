package com.tlys.spe.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.spe.model.SpeCerticargotrain;
import com.tlys.spe.model.SpeCertisupercargo;
import com.tlys.spe.service.SpeCerticargotrainService;
import com.tlys.spe.service.SpeCertisupercargoService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeCertisupercargoAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	private SpeCertisupercargo speCertisupercargo;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private List<DicSinocorp> corpList;
	private List<SpeCerticargotrain> certTrainList;

	private SpeCerticargotrain speCerticargotrain;

	private PageCtr pageCtr;

	private String filepath;
	private String filename;
	private DicSinocorp dicSinocorp;

	@Autowired
	SpeCertisupercargoService speCertisupercargoService;

	@Autowired
	SpeCerticargotrainService speCerticargotrainService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	/**
	 * Ѻ��Ա֤�б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/spe/spe-certisupercargo!list.action";

		if (null == pageCtr) {// ��ʾ��ǰ̨�״ν����б�ҳ�棨���ǵ�ҳ�룬Ҳ���ǵ������ֶ�)
			pageCtr = new PageCtr();
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != speCertisupercargo) {
				String schObjKey = "speCertisupercargo_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, speCertisupercargo);
			}
		} else {
			// System.out.println("SpeCertisupercargoAction.list->pageCtr.getSchObjKey()=="+pageCtr.getSchObjKey());
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				speCertisupercargo = (SpeCertisupercargo) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(8);

		speCertisupercargoService.findSpeCertisupercargo(speCertisupercargo,
				pageCtr);

		return "list";
	}

	/**
	 * ɾ��֤��
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		String[] ids = id.split(",");
		for (String lid : ids) {
			speCertisupercargo = speCertisupercargoService
					.getSpeCertisupercargo(Long.parseLong(lid));
			speCertisupercargoService.delete(speCertisupercargo);
		}
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			speCertisupercargo = speCertisupercargoService
					.getSpeCertisupercargo(Long.parseLong(id));
			isNew = false;
		} else {
			speCertisupercargo = new SpeCertisupercargo();
			isNew = true;
		}

		// SysUser sysUser = getCurUser();
		// String corpid = sysUser.getCorpid();
		//		
		// if (null != corpid && "00000000".equals(corpid)) {
		// corpList = dicSinocorpService.findAll();
		// } else {
		// DicSinocorp corp = dicSinocorpService.load(corpid);
		// corpList = dicSinocorpService.find(corp);
		// }
		// if (null != corpList && !corpList.isEmpty()) {
		// corpList.remove(0);
		// }
		corpList = dicSinocorpService.findDicSinocorpAll();
		return "input";
	}

	/**
	 * �������ȫ��֤��
	 * 
	 * @return
	 */
	public String downAll() {
		List<SpeCertisupercargo> speCertisupercargoList = speCertisupercargoService
				.findAll();
		filepath = speCertisupercargoService.downAll(speCertisupercargoList);

		return "download";
	}

	public String downAttach() throws Exception {
		if (null == id) {
			msg = new Msg(Msg.FAILURE, "idΪ�գ����飡");
			return "msg";
		}
		speCertisupercargo = speCertisupercargoService
				.getSpeCertisupercargo(Long.parseLong(id));
		filepath = speCertisupercargo.getCertipath();
		filename = speCertisupercargo.getFilename();
		return "download";
	}

	/**
	 * ������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// ������¼
			speCertisupercargo.setCreatedtime(new Date());
			speCertisupercargoService.save(speCertisupercargo, upload,
					uploadFileName);
		} else {
			// �޸ļ�¼
			speCertisupercargo.setRegid(Long.parseLong(id));
			speCertisupercargoService.update(speCertisupercargo, upload,
					uploadFileName);
		}
		id = String.valueOf(speCertisupercargo.getRegid());
		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}

	public String searchpage() throws Exception {
		// HttpServletRequest request = ServletActionContext.getRequest();
		// HttpSession session = request.getSession();
		// SysUser sysUser = (SysUser) session.getAttribute("sysUserSess");
		// String corpid = sysUser.getCorpid();
		//
		// if (null != corpid && "00000000".equals(corpid)) {
		// corpList = dicSinocorpService.findAll();
		// } else {
		// DicSinocorp corp = dicSinocorpService.load(corpid);
		// corpList = dicSinocorpService.find(corp);
		// }
		corpList = dicSinocorpService.findDicSinocorpAll();
		return "searchpage";
	}

	public String preview() throws Exception {
		speCertisupercargo = speCertisupercargoService
				.getSpeCertisupercargo(Long.parseLong(id));
		return "preview";
	}

	/**
	 * ������Ѻ��Ա֤��ѵ��¼ҳ��
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(results = { @Result(name = "subTrains", location = "/spe/spe-certicargotrain-list.jsp") })
	public String subTrains() throws Exception {
		if (null == speCerticargotrain) {
			speCerticargotrain = new SpeCerticargotrain();
			speCerticargotrain.setRegid(Long.parseLong(id));
		}
		speCertisupercargo = speCertisupercargoService
				.getSpeCertisupercargo(Long.parseLong(id));
		certTrainList = speCerticargotrainService.find(speCerticargotrain);
		return "subTrains";
	}

	// -------------------------------------------------

	public void setCorpList(List<DicSinocorp> corpList) {
		this.corpList = corpList;
	}

	public void setSpeCertisupercargo(SpeCertisupercargo speCertisupercargo) {
		this.speCertisupercargo = speCertisupercargo;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_CERT");
	}

	public PageCtr getPageCtr() {
		return pageCtr;
	}

	public void setPageCtr(PageCtr pageCtr) {
		this.pageCtr = pageCtr;
	}

	public List<DicSinocorp> getCorpList() {
		return corpList;
	}

	public String getId() {
		return id;
	}

	public SpeCertisupercargo getSpeCertisupercargo() {
		return speCertisupercargo;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public List<SpeCerticargotrain> getCertTrainList() {
		return certTrainList;
	}

	public void setCertTrainList(List<SpeCerticargotrain> certTrainList) {
		this.certTrainList = certTrainList;
	}

	public SpeCerticargotrain getSpeCerticargotrain() {
		return speCerticargotrain;
	}

	public void setSpeCerticargotrain(SpeCerticargotrain speCerticargotrain) {
		this.speCerticargotrain = speCerticargotrain;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
