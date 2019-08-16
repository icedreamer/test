package com.tlys.spe.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.service.EquCarService;
import com.tlys.spe.model.SpePapers;
import com.tlys.spe.model.SpePaperstype;
import com.tlys.spe.service.SpePapersService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpePapersAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private SpePapers spePapers;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private List<DicSinocorp> corpList;
	private List<EquCar> equCarList;
	private List<SpePaperstype> spePaperstypeList;
	// 多选删除时使用
	private String id;
	// 车种
	private Map<String, DicCarkind> carkindMap;
	// 车种
	private Map<String, DicCartype> cartypeMap;
	// 车种
	private Map<String, DicGoodscategory> goodscategoryMap;
	private EquCar equCar = new EquCar();

	/**
	 * 当前列表的证照类型
	 */
	private String ptypename;
	private String ptypeid;

	private String filepath;
	private DicSinocorp dicSinocorp;

	@Autowired
	SpePapersService spePapersService;

	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	EquCarService equCarService;	
	@Autowired
	DicMap dicMap;

	public String list() throws Exception {
		String pageUrl = "/spe/spe-papers!list.action";

		if (null == pageCtr) {
			pageCtr = new PageCtr<SpePapers>();
			String schObjKey = "spe_papers_" + new Date().getTime();
			pageCtr.setSchObjKey(schObjKey);
			setSessionAttr(schObjKey, spePapers);
		} else {
			if (null != pageCtr.getSchObjKey()) {
				spePapers = (SpePapers) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(18);
		// 同时取到当前的证照类型，为列表标题显示需要
		ptypename = spePapersService.findSpePapers(spePapers, pageCtr);
		return "list";
	}

	/**
	 * 删除证照
	 * 
	 * @return
	 */
	public String delete() {
		String[] ids = id.split(",");
		for (String lid : ids) {
			spePapers = spePapersService.getSpePapers(Long.parseLong(lid));
			spePapersService.delete(spePapers);
		}
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	/**
	 * 打包下载全部证照
	 * 
	 * @return
	 */
	public String downall() {
		List<SpePapers> spePapersList = spePapersService.findSpePapers(spePapers);
		filepath = spePapersService.downall(spePapersList);
		if (logger.isDebugEnabled()) {
			logger.debug("filepath : " + filepath);
		}
		return "download";
	}

	/**
	 * 下载单个证照
	 * 
	 * @return
	 */
	public String downpapers() {
		spePapers = spePapersService.getSpePapers(spePapers.getId());
		filepath = spePapers.getPath();
		return "download";
	}

	/**
	 * 编辑（新增)页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		corpList = dicSinocorpService.findDicSinocorp();
		if (!isNew) {
			spePapers = spePapersService.getSpePapers(spePapers.getId());
		}
		if (isNew) {
			String corpid = (null != corpList && !corpList.isEmpty() ? corpList.get(0).getCorpid() : "");
			// 车辆列表
			equCarList = equCarService.findEquCarByCorpid(corpid);
		} else {
			// 车辆列表
			equCarList = equCarService.findEquCarByCorpid(spePapers.getCorpid());
		}
		// 证照类型
		// spePaperstypeList =
		// spePapersService.findSpePaperstypeByTpid(ptypeid);
		return "edit";
	}

	public List<DicSinocorp> getCorpList() {
		return corpList;
	}

	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public List<EquCar> getEquCarList() {
		return equCarList;
	}

	/**
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}

	public SpePapers getSpePapers() {
		return spePapers;
	}

	public List<SpePaperstype> getSpePaperstypeList() {
		return spePaperstypeList;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String left() throws Exception {
		return "left";
	}

	public String listequcar() {
		String corpid = dicSinocorp.getCorpid();
		equCarList = equCarService.findEquCarByCorpid(corpid);
		return "listequcar";
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_CERT");
	}

	public String preview() {
		spePapers = spePapersService.getSpePapers(spePapers.getId());
		return "preview";
	}

	/**
	 * 保存证照信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("upload : " + upload);
			logger.debug("uploadFileName : " + uploadFileName);
			logger.debug("uploadContentType : " + uploadContentType);
		}
		Long spePapersId = spePapers.getId();
		if (isNew) {
			// 新增记录
			spePapersId = spePapersService.getNextSpePapersSeqId();
			spePapers.setId(spePapersId);
			spePapers.setCreatedtime(new Date());
			String suffix = CommUtils.getSuffix(uploadFileName);
			if (logger.isDebugEnabled()) {
				logger.debug("suffix : " + suffix);
			}
			String fileUrl = spePapersService.getFileUrl(spePapers, spePapersId, upload, suffix);
			String formattype = "";
			if (null != uploadContentType) {
				int index = uploadContentType.indexOf("/");
				if (-1 != index) {
					formattype = uploadContentType.substring(index + 1);
				}
			}
			spePapers.setFormattype(formattype);
			spePapers.setPath(fileUrl);
			DicSinocorp dicSinocorp = dicSinocorpService.load(spePapers.getCorpid());
			spePapers.setCorpname(dicSinocorp.getShortname());
			if (logger.isDebugEnabled()) {
				logger.debug("spePapers.corpid : " + spePapers.getCorpid());
			}
			spePapers.setFilename(uploadFileName);
			spePapersService.save(spePapers);
		} else {
			// 修改记录
			SpePapers papers = spePapersService.getSpePapers(spePapersId);
			if (null != upload && null != uploadFileName && !"".equals(uploadFileName) && null != uploadContentType
					&& !"".equals(uploadContentType)) {
				String suffix = CommUtils.getSuffix(uploadFileName);
				if (logger.isDebugEnabled()) {
					logger.debug("suffix : " + suffix);
				}
				String fileUrl = spePapersService.getFileUrl(papers, spePapersId, upload, suffix);
				String formattype = "";
				if (null != uploadContentType) {
					int index = uploadContentType.indexOf("/");
					if (-1 != index) {
						formattype = uploadContentType.substring(index + 1);
					}
				}
				papers.setFormattype(formattype);
				papers.setPath(fileUrl);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("spePapers.corpid : " + spePapers.getCorpid());
				logger.debug("spePapers.spePaperstype : " + spePapers.getSpePaperstype());
				logger.debug("spePapers.spePaperstype.ptypeid : " + spePapers.getSpePaperstype().getPtypeid());
			}
			papers.setSpePaperstype(spePapers.getSpePaperstype());
			papers.setRemarks(spePapers.getRemarks());
			papers.setCarno(spePapers.getCarno());
			papers.setCorpid(spePapers.getCorpid());
			DicSinocorp dicSinocorp = dicSinocorpService.load(spePapers.getCorpid());
			papers.setCorpname(dicSinocorp.getShortname());
			papers.setPapersname(spePapers.getPapersname());
			spePapersService.update(papers);
		}
		msg = new Msg(Msg.SUCCESS, "操作成功！", false, "alert('操作成功！');frameElement.lhgDG.curWin.location.reload();",
				new String[] { "curTitle", "" });
		return MSG;
	}

	public String searchpage() throws Exception {
		corpList = dicSinocorpService.findDicSinocorp();
		cartypeMap = dicMap.getCartypeMap();
		goodscategoryMap = dicMap.getGoodscategoryMap();
		carkindMap = dicMap.getCarkindMap();
		return "searchpage";
	}
	
	public String s() throws Exception{
		String filePath = "D:\\WorkDocument\\铁路自备车\\许可证相片\\许可证相片";
		File file = new File(filePath);
		SpePaperstype spePaperstype = spePapersService.getSpePaperstype("04");//.load("04");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				Long spePapersId = spePapersService.getNextSpePapersSeqId();
				System.out.println(files[i].getName());
				File f = files[i];
				ServletContext context = ServletActionContext.getServletContext();
				String destFilePath = context.getRealPath("/attach/PAPER/" + spePapersId + "/");
				File destFile = new File(destFilePath);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				String fileName = f.getName();
				File destF = new File(destFilePath + fileName);
				FileCommUtil.copyFile(f, destF);
				int index = fileName.lastIndexOf(".");
				String carno = fileName.substring(0, index);
				SpePapers spePapers = new SpePapers();
				spePapers.setCarno(carno);
				spePapers.setCorpid("11700000");
				spePapers.setCorpname("南化公司");
				spePapers.setCreatedtime(new Date());
				spePapers.setFilename(fileName);
				spePapers.setFormattype("pjpeg");
				spePapers.setPapersname(carno);
				spePapers.setId(spePapersId);
				spePapers.setSpePaperstype(spePaperstype);
				spePapers.setPath("/attach/PAPER/" + spePapersId + "/" + fileName);
				spePapersService.save(spePapers);
			}
		}
		logger.debug("save success.");
		msg = new Msg(Msg.SUCCESS,"成功");
		return MSG;
	}

	public void setCorpList(List<DicSinocorp> corpList) {
		this.corpList = corpList;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	public void setEquCarList(List<EquCar> equCarList) {
		this.equCarList = equCarList;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public void setSpePapers(SpePapers spePapers) {
		this.spePapers = spePapers;
	}

	public void setSpePaperstypeList(List<SpePaperstype> spePaperstypeList) {
		this.spePaperstypeList = spePaperstypeList;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getPtypename() {
		return ptypename;
	}

	public void setPtypename(String ptypename) {
		this.ptypename = ptypename;
	}

	public String getPtypeid() {
		return ptypeid;
	}

	public void setPtypeid(String ptypeid) {
		this.ptypeid = ptypeid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEquCar(EquCar equCar) {
		this.equCar = equCar;
	}

	public Map<String, DicCarkind> getCarkindMap() {
		return carkindMap;
	}

	public Map<String, DicCartype> getCartypeMap() {
		return cartypeMap;
	}

	public Map<String, DicGoodscategory> getGoodscategoryMap() {
		return goodscategoryMap;
	}
}
