package com.tlys.sys.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.model.SysBulletin;
import com.tlys.sys.model.SysBulletinAnnex;
import com.tlys.sys.model.SysBulletinReadinfo;
import com.tlys.sys.model.SysSession;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysBulletinService;

/**
 * 通知公告
 * 
 * @author ccsong 2012-5-29 下午3:41:40
 */
@Controller
// by spring,定义bean
@Scope("prototype")
// by struts2，定义action为原型模式
@ParentPackage("tlys-default")
// //by struts2，定义此pachage继承tlys-default(tlys-default已在struts.xml中定义)
@Namespace("/sys")
public class SysBulletinAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6795296457591607211L;

	private SysBulletin sysBulletin;
	private SysBulletinAnnex sysBulletinAnnex;
	/**
	 * 撰写人
	 */
	private List<Object[]> writerList;
	/**
	 * 发布人
	 */
	private List<Object[]> publisherList;
	// private File upload;
	// private String uploadFileName;
	// private String uploadContentType;
	private List<File> uploads;
	private List<String> fileNames;
	private List<String> uploadContentTypes;
	/**
	 * 每条公告记录的阅读次数
	 */
	private Map<Long, Integer> countOfReadMap;
	/**
	 * 用户字典
	 */
	private Map<String, SysUser> userMap;
	/**
	 * 前台提交过来的公告id数组
	 */
	private Long[] bulletinIds;
	/**
	 * 公告附件
	 */
	private List<SysBulletinAnnex> sysBulletinAnnexs;

	private boolean isPreview;
	private String filepath;
	private String fileName;
	@Autowired
	SysBulletinService sysBulletinService;
	@Autowired
	DicMap dicMap;

	/**
	 * 左侧查询面板
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {

		writerList = sysBulletinService.findWriter();
		publisherList = sysBulletinService.findPublisher();
		return "left";
	}

	public String left4corp() throws Exception {
		return "left4corp";
	}

	/**
	 * 公告列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/sys/sys-bulletin!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<SysSession>();
			if (null != sysBulletin) {
				String schObjKey = "sysBulletin_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysBulletin);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysBulletin = (SysBulletin) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		// 分页查询通知公告
		sysBulletinService.findSysBulletin(sysBulletin, pageCtr);
		// 通知公告的id列表
		List<Long> bulletinIdList = null;
		List<SysBulletin> list = pageCtr.getRecords();
		if (null != list && !list.isEmpty()) {
			bulletinIdList = new ArrayList<Long>();

			for (int i = 0; i < list.size(); i++) {
				SysBulletin sysBulletin = list.get(i);
				bulletinIdList.add(sysBulletin.getBulletinid());
			}
		}
		if (null != bulletinIdList && !bulletinIdList.isEmpty()) {
			// 根据通知公告Id的列表获取阅读次数
			List<Object[]> readList = sysBulletinService.getSysBulletinReadCount(bulletinIdList);
			if (null != readList && !readList.isEmpty()) {
				countOfReadMap = new HashMap<Long, Integer>();
				for (int i = 0; i < readList.size(); i++) {
					Object[] o = readList.get(i);
					countOfReadMap.put(Long.valueOf(o[0].toString()), Integer.parseInt(o[1]
							.toString()));
				}
			}
		}
		userMap = dicMap.getAllUserMap();
		return "list";
	}

	/**
	 * 企业用户查看通知公告
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list4corp() throws Exception {
		String pageUrl = "/sys/sys-bulletin!list4corp.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<SysSession>();
			if (null != sysBulletin) {
				String schObjKey = "sysBulletin_corp_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysBulletin);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysBulletin = (SysBulletin) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		// 分页查询通知公告
		if (null == sysBulletin) {
			sysBulletin = new SysBulletin();
		}
		sysBulletin.setStatus("2");
		sysBulletinService.findSysBulletin(sysBulletin, pageCtr);
		List<SysBulletin> bulletinList = pageCtr.getRecords();
		for (Iterator iterator = bulletinList.iterator(); iterator.hasNext();) {
			SysBulletin bulletin = (SysBulletin) iterator.next();
			SysBulletinReadinfo sysBulletinReadinfo = new SysBulletinReadinfo();
			sysBulletinReadinfo.setBulletinid(bulletin.getBulletinid());
			sysBulletinReadinfo.setReaderid(getCurUser().getUserid());
			bulletin.setReadover(sysBulletinService.isReadover(sysBulletinReadinfo));
		}
		return "list4corp";
	}

	/**
	 * 显示公告及附件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		Long bulletinid = sysBulletin.getBulletinid();
		sysBulletin = sysBulletinService.load(bulletinid);
		sysBulletinAnnexs = sysBulletinService.findSysBulletinAnnexByBulletinId(bulletinid);
		if (logger.isDebugEnabled()) {
			logger.debug("isPreview : " + isPreview);
		}
		if (!isPreview) {
			SysUser sysUser = getCurUser();
			String userid = sysUser.getUserid();
			SysBulletinReadinfo sysBulletinReadinfo = new SysBulletinReadinfo();
			sysBulletinReadinfo.setBulletinid(bulletinid);
			sysBulletinReadinfo.setReaderid(userid);
			sysBulletinReadinfo.setReadtime(new Date());
			sysBulletinService.save(sysBulletinReadinfo);
			return "detail";
		}

		return "listattach";
	}

	public String edit() throws Exception {
		if (!isNew) {
			Long bulletinid = sysBulletin.getBulletinid();
			sysBulletin = sysBulletinService.load(bulletinid);
			sysBulletinAnnexs = sysBulletinService.findSysBulletinAnnexByBulletinId(bulletinid);
		}
		return "edit";
	}

	/**
	 * 下载
	 * 
	 * @return
	 */
	public String down() {
		sysBulletinAnnex = sysBulletinService
				.getSysBulletinAnnexById(sysBulletinAnnex.getAnnexid());
		filepath = sysBulletinAnnex.getPath();
		fileName = sysBulletinAnnex.getAnnexname();
		return "download";
	}

	public String save() throws Exception {
		SysUser sysUser = getCurUser();
		String userid = sysUser.getUserid();
		if (isNew) {
			sysBulletin.setWriterid(userid);
			sysBulletin.setWritetime(new Date());
			Long bulletinId = sysBulletinService.getNextSysBulletinSeqId();
			sysBulletin.setBulletinid(bulletinId);
			String status = sysBulletin.getStatus();
			if (null != status && "2".equals(status)) {
				sysBulletin.setPublisherid(userid);
				sysBulletin.setPublishtime(new Date());
			}

			sysBulletinService.save(sysBulletin, bulletinId);
			if (null != uploads && !uploads.isEmpty()) {
				for (int i = 0; i < uploads.size(); i++) {
					File file = uploads.get(i);
					String filename = fileNames.get(i);
					SysBulletinAnnex sysBulletinAnnex = new SysBulletinAnnex();
					sysBulletinAnnex.setAnnexname(filename);
					sysBulletinAnnex.setBulletinid(bulletinId);
					String suffix = CommUtils.getSuffix(filename);
					sysBulletinAnnex.setUploadtime(new Date());
					sysBulletinAnnex.setPath(filename);
					sysBulletinService.save(sysBulletinAnnex);
					Long annexid = sysBulletinAnnex.getAnnexid();
					String fileUrl = sysBulletinService.getSysBulletinAnnexUrl(file, annexid,
							suffix);
					sysBulletinAnnex.setPath(fileUrl);
					sysBulletinService.update(sysBulletinAnnex);
				}
			}
		} else {
			String status = sysBulletin.getStatus();
			if (null != status && "2".equals(status)) {
				sysBulletin.setPublisherid(userid);
				sysBulletin.setPublishtime(new Date());
			}
			if (null != uploads && !uploads.isEmpty()) {
				Long bulletinId = sysBulletin.getBulletinid();
				for (int i = 0; i < uploads.size(); i++) {
					String filename = fileNames.get(i);
					SysBulletinAnnex sysBulletinAnnex = new SysBulletinAnnex();
					sysBulletinAnnex.setAnnexname(filename);
					sysBulletinAnnex.setBulletinid(bulletinId);
					String suffix = CommUtils.getSuffix(filename);
					sysBulletinAnnex.setPath(filename);
					sysBulletinAnnex.setUploadtime(new Date());
					sysBulletinService.save(sysBulletinAnnex);
					Long annexid = sysBulletinAnnex.getAnnexid();
					String fileUrl = sysBulletinService.getSysBulletinAnnexUrl(uploads.get(i),
							annexid, suffix);
					sysBulletinAnnex.setPath(fileUrl);
					sysBulletinService.update(sysBulletinAnnex);
				}
			}
			sysBulletinService.update(sysBulletin);
		}
		msg = new Msg(Msg.SUCCESS, "操作成功",
				"alert('操作成功');frameElement.lhgDG.curWin.location.reload();");
		return MSG;
	}

	/**
	 * 删除公告
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		if (null != bulletinIds && bulletinIds.length > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("bulletinIds.length : " + bulletinIds.length);
			}
			for (int i = 0; i < bulletinIds.length; i++) {
				SysBulletin sysBulletin = sysBulletinService.load(bulletinIds[i]);
				sysBulletinService.delete(sysBulletin);
			}
		}
		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	public String deleteattach() throws Exception {

		sysBulletinAnnex = sysBulletinService
				.getSysBulletinAnnexById(sysBulletinAnnex.getAnnexid());
		sysBulletinService.delete(sysBulletinAnnex);
		msg = new Msg(Msg.SUCCESS, "删除成功");

		return MSG;
	}

	/**
	 * 发布公告(点击标题栏使用)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refer() throws Exception {
		String msgStr = "";
		if (null != bulletinIds && bulletinIds.length >= 0) {
			SysUser sysUser = getCurUser();
			for (int i = 0; i < bulletinIds.length; i++) {
				SysBulletin sysBulletin = sysBulletinService.load(bulletinIds[i]);
				sysBulletin.setStatus("2");
				sysBulletin.setPublisherid(sysUser.getUserid());
				sysBulletin.setPublishtime(new Date());
				sysBulletinService.updateStatus(sysBulletin);
			}
			msgStr = "1";
		} else {
			msgStr = "0";
		}
		msg = new Msg(Msg.SUCCESS, msgStr);
		return MSG;
	}

	public SysBulletin getSysBulletin() {
		return sysBulletin;
	}

	public void setSysBulletin(SysBulletin sysBulletin) {
		this.sysBulletin = sysBulletin;
	}

	public List<Object[]> getWriterList() {
		return writerList;
	}

	public List<Object[]> getPublisherList() {
		return publisherList;
	}

	public void setWriterList(List<Object[]> writerList) {
		this.writerList = writerList;
	}

	public void setPublisherList(List<Object[]> publisherList) {
		this.publisherList = publisherList;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("SYS_BULLETIN");
	}

	public List<File> getUpload() {
		return uploads;
	}

	public List<String> getUploadFileName() {
		return fileNames;
	}

	public List<String> getUploadContentType() {
		return uploadContentTypes;
	}

	public void setUpload(List<File> uploads) {
		this.uploads = uploads;
	}

	public void setUploadFileName(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setUploadContentType(List<String> uploadContentTypes) {
		this.uploadContentTypes = uploadContentTypes;
	}

	public Map<Long, Integer> getCountOfReadMap() {
		return countOfReadMap;
	}

	public void setCountOfReadMap(Map<Long, Integer> countOfReadMap) {
		this.countOfReadMap = countOfReadMap;
	}

	public Long[] getBulletinIds() {
		return bulletinIds;
	}

	public void setBulletinIds(Long[] bulletinIds) {
		this.bulletinIds = bulletinIds;
	}

	public List<SysBulletinAnnex> getSysBulletinAnnexs() {
		return sysBulletinAnnexs;
	}

	public void setSysBulletinAnnexs(List<SysBulletinAnnex> sysBulletinAnnexs) {
		this.sysBulletinAnnexs = sysBulletinAnnexs;
	}

	public Map<String, SysUser> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, SysUser> userMap) {
		this.userMap = userMap;
	}

	public boolean getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public SysBulletinAnnex getSysBulletinAnnex() {
		return sysBulletinAnnex;
	}

	public void setSysBulletinAnnex(SysBulletinAnnex sysBulletinAnnex) {
		this.sysBulletinAnnex = sysBulletinAnnex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
