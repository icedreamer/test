package com.tlys.spe.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.spe.model.SpeLaw;
import com.tlys.spe.service.SpeLawService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeLawAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;
	protected final Log log = LogFactory.getLog(this.getClass());
	private String id;
	private SpeLaw speLaw;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private List<DicSinocorp> corpList;

	private String lawtype;

	private PageCtr pageCtr;

	private String filepath;
	private DicSinocorp dicSinocorp;
	private String filename;
	private String titleName;

	@Autowired
	SpeLawService speLawService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/spe/spe-law!list.action";
		if (null == lawtype) {
			lawtype = "01";
		}
		
		if (null == speLaw) {
			speLaw = new SpeLaw();
		}
		speLaw.setLawtype(lawtype);

		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageViewNew��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != speLaw) {
				String schObjKey = "speLaw_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, speLaw);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				speLaw = (SpeLaw) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(16);
		speLawService.findSpeLaw(speLaw, pageCtr);
//title
		if("01".equals(lawtype)){
			this.titleName = "��·����";
		}else if("02".equals(lawtype)){
			this.titleName = "��������";
		}else if("03".equals(lawtype)){
			this.titleName = "�������ļ�";
		}else if("04".equals(lawtype)){
			this.titleName = "����";
		}else if("05".equals(lawtype)){
			this.titleName = "�ڲ�����";
		}else if("06".equals(lawtype)){
			this.titleName = "HSE����";
		}
		return "list";
	}

	/**
	 * ɾ��֤��
	 * 
	 * @return
	 */
	public String delete() {
		String[] ids = id.split(",");
		for (String lid : ids) {
			speLaw = speLawService.getSpeLaw(Long.parseLong(lid));
			speLawService.delete(speLaw);
		}
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}

	/**
	 * ������Ϣ������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		String str = speLaw.getIssuedateStr().replace("-", "");
		speLaw.setIssuedate(str);
		str = speLaw.getEffectdateStr().replace("-", "");
		speLaw.setEffectdate(str);
		if (isNew) {
			// ������¼
			speLaw.setReadtimes(new Long(0));
			speLaw.setCreatedtime(new Date());
			speLawService.save(speLaw, upload, uploadFileName);
		} else {
			// �޸ļ�¼
			speLawService.update(speLaw, upload, uploadFileName);
		}
		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}

	public String edit() throws Exception {
		if (null != id) {
			speLaw = speLawService.getSpeLaw(Long.parseLong(id));
			speLaw.setIssuedateStr(FormatUtil.rectDate(speLaw.getIssuedate()));
			speLaw
					.setEffectdateStr(FormatUtil.rectDate(speLaw
							.getEffectdate()));
			isNew = false;
		} else {
			isNew = true;
		}
		return "input";
	}

	/**
	 * �������ȫ��֤��
	 * 
	 * @return
	 */
	public String downAll() {
		List<SpeLaw> speLawList = speLawService.findSpeLaw();
		filepath = speLawService.downall(speLawList);
		return "download";
	}

	public String downAttach() {
		if (null == id) {
			msg = new Msg(Msg.FAILURE, "idΪ�գ����飡");
			return "msg";
		}
		speLaw = speLawService.getSpeLaw(Long.parseLong(id));
		filepath = speLaw.getPath();
		filename = speLaw.getFilename();
		return "download";
	}

	/**
	 * Ԥ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String preview() throws Exception {
		speLaw = speLawService.getSpeLaw(Long.parseLong(id));
		filepath = speLaw.getPath();
		filepath = filepath.substring(0, filepath.lastIndexOf("/") + 1) + id
				+ ".swf";
		// �޸��Ķ�����
		Long i = speLaw.getReadtimes();
		speLaw.setReadtimes(++i);
		speLawService.update(speLaw);
		return "swfview";
	}

	// public String preshow() throws Exception {
	// speLaw = speLawService.getSpeLaw(id);
	// filepath = speLaw.getPath();
	// if (filepath == null || filepath.trim().equals("")) {
	// javax.swing.JOptionPane.showMessageDialog(null, "�ļ���ַ����Ϊ��");
	// } else {
	// ServletContext sct = ServletActionContext.getServletContext();
	// String url = sct.getRealPath(filepath);
	// Runtime.getRuntime().exec("cmd /c start " + url);
	// }
	// return null;
	// }

	public String searchpage() throws Exception {
		return "searchpage";
	}

	public void setCorpList(List<DicSinocorp> corpList) {
		this.corpList = corpList;
	}

	public void setSpeLaw(SpeLaw speLaw) {
		this.speLaw = speLaw;
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
		initOpraMap("SPE_LAW");
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

	public SpeLaw getSpeLaw() {
		return speLaw;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLawtype() {
		return lawtype;
	}

	public void setLawtype(String lawtype) {
		this.lawtype = lawtype;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
