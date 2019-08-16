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
import com.tlys.spe.model.SpeCertishipping;
import com.tlys.spe.model.SpeCertishiptrain;
import com.tlys.spe.service.SpeCertishippingService;
import com.tlys.spe.service.SpeCertishiptrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeCertishippingAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	private SpeCertishipping speCertishipping;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private List<DicSinocorp> corpList;
	private List<SpeCertishiptrain> certTrainList;

	private SpeCertishiptrain speCertishiptrain;

	private PageCtr pageCtr;

	private String filepath;
	private String filename;

	private DicSinocorp dicSinocorp;

	@Autowired
	SpeCertishippingService speCertishippingService;

	@Autowired
	SpeCertishiptrainService speCertishiptrainService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/spe/spe-certishipping!list.action";

		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != speCertishipping) {
				String schObjKey = "speCertishipping_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, speCertishipping);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				speCertishipping = (SpeCertishipping) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(8);

		speCertishippingService.findSpeCertishipping(speCertishipping, pageCtr);

		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		String[] ids = id.split(",");
		for (String lid : ids) {
			speCertishipping = speCertishippingService.getSpeCertishipping(Long
					.parseLong(lid));
			speCertishippingService.delete(speCertishipping);
		}
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			speCertishipping = speCertishippingService.getSpeCertishipping(Long
					.parseLong(id));
			isNew = false;
		} else {
			speCertishipping = new SpeCertishipping();
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
	 * 打包下载全部
	 * 
	 * @return
	 */
	public String downAll() {
		List<SpeCertishipping> speCertishippingList = speCertishippingService
				.findAll();
		filepath = speCertishippingService.downAll(speCertishippingList);

		return "download";
	}

	public String downAttach() throws Exception {
		if (null == id) {
			msg = new Msg(Msg.FAILURE, "id为空，请检查！");
			return "msg";
		}
		speCertishipping = speCertishippingService.getSpeCertishipping(Long
				.parseLong(id));
		filepath = speCertishipping.getCertipath();
		filename = speCertishipping.getFilename();
		return "download";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// 新增记录
			speCertishipping.setCreatedtime(new Date());
			speCertishippingService.save(speCertishipping, upload,
					uploadFileName);
		} else {
			// 修改记录
			speCertishipping.setRegid(Long.parseLong(id));
			speCertishippingService.update(speCertishipping, upload,
					uploadFileName);
		}
		id = String.valueOf(speCertishipping.getRegid());

		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}

	public String searchpage() throws Exception {
		corpList = dicSinocorpService.findDicSinocorpAll();
		return "searchpage";
	}

	public String preview() throws Exception {
		speCertishipping = speCertishippingService.getSpeCertishipping(Long
				.parseLong(id));
		return "preview";
	}

	/**
	 * 导航到培训记录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(results = { @Result(name = "subTrains", location = "/spe/spe-certishiptrain-list.jsp") })
	public String subTrains() throws Exception {
		// id = speCertishiptrain.getRegid();
		if (null == speCertishiptrain) {
			speCertishiptrain = new SpeCertishiptrain();
			speCertishiptrain.setRegid(Long.parseLong(id));
		}
		speCertishipping = speCertishippingService.getSpeCertishipping(Long
				.parseLong(id));
		certTrainList = speCertishiptrainService.find(speCertishiptrain);
		return "subTrains";
	}

	// -------------------------------------------------

	public void setCorpList(List<DicSinocorp> corpList) {
		this.corpList = corpList;
	}

	public void setSpeCertishipping(SpeCertishipping speCertishipping) {
		this.speCertishipping = speCertishipping;
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

	public SpeCertishipping getSpeCertishipping() {
		return speCertishipping;
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

	public List<SpeCertishiptrain> getCertTrainList() {
		return certTrainList;
	}

	public void setCertTrainList(List<SpeCertishiptrain> certTrainList) {
		this.certTrainList = certTrainList;
	}

	public SpeCertishiptrain getSpeCertishiptrain() {
		return speCertishiptrain;
	}

	public void setSpeCertishiptrain(SpeCertishiptrain speCertishiptrain) {
		this.speCertishiptrain = speCertishiptrain;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
