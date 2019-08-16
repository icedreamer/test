package com.tlys.sys.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.sys.model.SysMail;
import com.tlys.sys.model.SysMailattach;
import com.tlys.sys.model.SysMailfolder;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMailService;
import com.tlys.sys.service.SysUserService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysMailAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4048478170365776792L;

	private List<SysMail> sysMails;

	private SysMail sysMail;
	private SysMailfolder sysMailfolder;
	private List<SysUser> sysUserList;
	private String userids;
	// private File upload;
	// private String uploadFileName;
	// private String uploadContentType;
	private List<File> uploads;
	private List<String> fileNames;
	private List<String> uploadContentTypes;
	private String sendType;
	private List<SysUser> ccUsers;
	private List<SysUser> toUsers;
	private List<Object[]> fromUsers;
	private List<SysMailattach> sysMaiattachList;
	private Map<Long, List<SysMailattach>> sysMailattachsByMailIdMap;
	private SysMailattach sysMailattach;
	private String filepath;
	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreacorpMap;
	private DicSinocorp dicSinocorp;
	private Map<String, SysUser> sysUserMap;
	private String superUserId;

	private Boolean isTrans;

	private String fileName;
	// 存放当前用户各邮件箱邮件数
	Map mailMap;
	@Autowired
	SysMailService sysMailService;

	@Autowired
	SysUserService sysUserService;
	@Autowired
	DicMap dicMap;

	public String left() throws Exception {

		mailMap = sysMailService.findUserMailCounts(getCurUser());

		return "left";
	}

	public String list() throws Exception {
		String pageUrl = "/sys/sys-mail!list.action";
		if (null == sysMailfolder) {
			sysMailfolder = new SysMailfolder();
		}
		sysMailfolder.setSysUser(getCurUser());
		if (null == pageCtr) {
			pageCtr = new PageCtr<SysMailfolder>();
			if (null != sysMailfolder) {
				String schObjKey = "sysMailfolder_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysMailfolder);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysMailfolder = (SysMailfolder) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		// 分页查询
		sysMailService.findSysMailfolders(sysMailfolder, pageCtr);
		List<SysMailfolder> list = pageCtr.getRecords();
		List<Long> mailids = null;
		List<SysMailattach> sysMailattachList = null;
		if (null != list && !list.isEmpty()) {
			mailids = new ArrayList<Long>();
			for (int i = 0; i < list.size(); i++) {
				SysMailfolder sysMailfolder = list.get(i);
				SysMail sysMail = sysMailfolder.getSysMail();
				if (null != sysMail) {
					mailids.add(sysMail.getMailid());
				}
			}
			if (!mailids.isEmpty()) {
				sysMailattachList = sysMailService.findSysMailattach(mailids);
			}
		}
		if (null != sysMailattachList && !sysMailattachList.isEmpty()) {
			sysMailattachsByMailIdMap = new HashMap<Long, List<SysMailattach>>();
			for (SysMailattach sysMailattach : sysMailattachList) {
				long mailid = sysMailattach.getMailid();
				List<SysMailattach> attachList = sysMailattachsByMailIdMap
						.get(mailid);
				if (null == attachList) {
					attachList = new ArrayList<SysMailattach>();
				}
				attachList.add(sysMailattach);
				sysMailattachsByMailIdMap.put(mailid, attachList);
			}
		}

		sysUserMap = dicMap.getUserMap();

		fromUsers = sysMailService.findUsers(getCurUser().getUserid());

		superUserId = CommUtils.getSuperUser();
		return "list";
	}

	/**
	 * 邮件转发方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transmit() throws Exception {
		sysMail = sysMailService.getSysMailById(sysMail.getMailid());
		sysMaiattachList = sysMailService
				.findSysMailattach(sysMail.getMailid());
		dicSinocorpMap = dicMap.getCorpAllMap();
		dicAreacorpMap = dicMap.getAreaMap();
		return "transmit";
	}

	public String edit() throws Exception {

		if (!isNew) {
			sysMailfolder = sysMailService.getSysMailfolder(sysMailfolder
					.getId());
			// sysMail = sysMailfolder.getSysMail();
			sysMail = sysMailService.getSysMailById(sysMail.getMailid());
			if (logger.isDebugEnabled()) {
				logger.debug("sysMail.body : " + sysMail.getBody());
				logger.debug("sysMail.getMailid : " + sysMail.getMailid());
				logger.debug("sysMail.title : " + sysMail.getSubject());
				logger.debug("sysMail.mailsize : " + sysMail.getMailsize());
			}
			List<String> userids = new ArrayList<String>();
			String toid = sysMail.getToid() == null ? "" : sysMail.getToid();
			List<String> toIdList = new ArrayList<String>();
			if (!"".equals(toid)) {
				String[] toids = toid.split(",");
				toIdList = Arrays.asList(toids);
			}
			if (!toIdList.isEmpty()) {
				userids.addAll(toIdList);
			}
			String ccid = sysMail.getCcid() == null ? "" : sysMail.getCcid();
			List<String> ccIdList = new ArrayList<String>();
			if (!"".equals(ccid)) {
				String[] ccids = ccid.split(",");
				ccIdList = Arrays.asList(ccids);
			}
			if (!ccIdList.isEmpty()) {
				userids.addAll(ccIdList);
			}
			List<SysUser> userList = null;
			if (!userids.isEmpty()) {
				userList = sysUserService.findSysUser(userids);
			}
			toUsers = new ArrayList<SysUser>();
			ccUsers = new ArrayList<SysUser>();
			if (null != userList && !userList.isEmpty()) {
				for (SysUser sysUser : userList) {
					if (toIdList.contains(sysUser.getUserid())) {
						toUsers.add(sysUser);
					} else if (ccIdList.contains(sysUser.getUserid())) {
						ccUsers.add(sysUser);
					}
				}
			}
			String folder = sysMailfolder.getFolder();
			String readflag = sysMailfolder.getReadflag();
			if ("INBOX".equalsIgnoreCase(folder) && "0".equals(readflag)) {
				sysMailfolder = sysMailService.getSysMailfolder(sysMailfolder
						.getId());
				if (null != sysMailfolder) {
					sysMailfolder.setReadflag("1");
					sysMailfolder.setReadtime(new Date());
					sysMailService.update(sysMailfolder);
				}
			}
			sysMaiattachList = sysMailService.findSysMailattach(sysMail
					.getMailid());
		}
		dicSinocorpMap = dicMap.getCorpAllMap();
		dicAreacorpMap = dicMap.getAreaMap();

		return "edit";
	}

	public String loadUsers() throws Exception {
		List<String> userIdList = null;
		if (null != userids && !"".equals(userids)) {
			userIdList = Arrays.asList(userids.split(","));
		}
		String corpid = "";
		if (null != dicSinocorp) {
			corpid = dicSinocorp.getCorpid();
		}
		sysUserList = sysUserService.findSysUserByNoContain(userIdList, corpid);
		return "users";
	}

	/**
	 * 保存邮件为草稿，保存入草稿箱
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		Long sysMailId = 0l;
		if (isNew) {
			sysMailId = sysMailService.getNextSysMailSeqId();
			sysMail.setMailid(sysMailId);
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
			SysMailfolder sysMailfolder = new SysMailfolder();
			sysMailfolder.setFolder("DRAFAT");
			sysMailfolder.setReadflag("1");
			sysMailfolder.setSysMail(sysMail);
			sysMailfolder.setSysUser(getCurUser());
			sysMailService.save(sysMailfolder);
		} else {
			sysMailId = sysMail.getMailid();
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
		}
		String script = "";
		if (isNew) {
			script += "alert('添加成功！');window.parent.location.href='/sys/sys-mail-frame.jsp?surl=DRAFAT';";
		} else {
			script += "alert('编辑成功！');";
			// script +=
			// "frameElement.lhgDG.removeBtn('save');frameElement.lhgDG.removeBtn('send');";
			script += "frameElement.lhgDG.curWin.location.reload();";
			// script +=
			// "window.location.href='/sys/sys-mail-frame.jsp?surl=DRAFAT';";
		}
		msg = new Msg(Msg.SUCCESS, "添加/编辑成功", script);
		return MSG;
	}

	private int renderSysMail(long sysMailId) throws Exception {
		long mailSize = 0;
		long attachSize = sysMailService.getSysMailattachSize(sysMailId);
		if (null != uploads && uploads.size() > 0) {
			long size = 0;
			for (int i = 0; i < uploads.size(); i++) {
				size += uploads.get(i).length();
			}
			mailSize = attachSize + size + sysMail.getBody().getBytes().length;
		} else {
			mailSize = attachSize + sysMail.getBody().getBytes().length;
		}
		if (mailSize > 20 * 1024 * 1024) {
			return 0;
		}
		// 保存邮件信息
		sysMail.setSysUser(getCurUser());
		if (logger.isDebugEnabled()) {
			logger.debug("sysMail.body : " + sysMail.getBody());
			logger.debug("body.size : " + sysMail.getBody().getBytes().length);
		}
		sysMail.setMailsize(mailSize);
		sysMail.setMailtime(new Date());
		sysMailService.saveOrUpdate(sysMail);
		// 保存附件
		if (null != uploads && !uploads.isEmpty()) {
			logger.debug("uploads.size : " + uploads.size());
			for (int i = 0; i < uploads.size(); i++) {
				File file = uploads.get(i);
				String filename = fileNames.get(i);
				SysMailattach sysMailattach = new SysMailattach();
				sysMailattach.setUploadtime(new Date());
				sysMailattach.setFilename(filename);
				sysMailattach.setFormattype(uploadContentTypes.get(i));
				sysMailattach.setMailid(sysMailId);
				String suffix = CommUtils.getSuffix(filename);
				sysMailattach.setPath(filename);
				sysMailService.save(sysMailattach);
				String fileUrl = sysMailService.getFileUrl(sysMailattach,
						sysMailattach.getId(), file, suffix);
				sysMailattach.setPath(fileUrl);
				sysMailService.update(sysMailattach);
			}
		}
		return 1;
	}

	/**
	 * 发送邮件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String send() throws Exception {
		Long sysMailId = 0l;
		long oldMailid = sysMail.getMailid();
		if (isNew) {
			sysMailId = sysMailService.getNextSysMailSeqId();
			sysMail.setMailid(sysMailId);
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
			// 保存文件夹
			String toid = sysMail.getToid();
			if (null != toid && !"".equals(toid)) {
				String[] toids = toid.split(",");
				for (int i = 0; i < toids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(toids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			String ccid = sysMail.getCcid();
			if (null != ccid && !"".equals(ccid)) {
				String[] ccids = ccid.split(",");
				for (int i = 0; i < ccids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(ccids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			sysMailfolder = new SysMailfolder();
			sysMailfolder.setSysMail(sysMail);
			sysMailfolder.setFolder("SENDBOX");
			sysMailfolder.setSysUser(getCurUser());
			sysMailfolder.setReadflag("1");
			sysMailService.save(sysMailfolder);
			// 转发功能使用
//			if (isTrans) {
//				sysMailService.copySysMailAttachs(oldMailid, sysMailId);
//			}
		} else {
			sysMailId = sysMail.getMailid();
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
			// 保存文件夹
			String toid = sysMail.getToid();
			if (null != toid && !"".equals(toid)) {
				String[] toids = toid.split(",");
				for (int i = 0; i < toids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(toids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			String ccid = sysMail.getCcid();
			if (null != ccid && !"".equals(ccid)) {
				String[] ccids = ccid.split(",");
				for (int i = 0; i < ccids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(ccids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			// 如果用户保存了草稿箱，则将邮件状态标记为已经发送的邮件，进入发件箱
			sysMailfolder = sysMailService.getSysMailfolder(sysMailfolder
					.getId());
			if (null != sysMailfolder) {
				sysMailfolder.setFolder("SENDBOX");
				sysMailService.update(sysMailfolder);
			}
		}
		String script = "alert('发送成功！');";
		if (isNew) {
			script += "frameElement.lhgDG.curWin.parent.location.href='/sys/sys-mail-frame.jsp?surl=SENDBOX';";
		} else {
			// script +=
			// "frameElement.lhgDG.removeBtn('save');frameElement.lhgDG.removeBtn('send');";
			script += "frameElement.lhgDG.curWin.parent.location.href='/sys/sys-mail-frame.jsp?surl=SENDBOX';";
		}

		msg = new Msg(Msg.SUCCESS, "发送成功", script);
		return MSG;
	}
	/**邮件转发时调用发送
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendTransmit() throws Exception{
		Long sysMailId = 0l;
		long oldMailid = sysMail.getMailid();
		if (isNew) {
			sysMailId = sysMailService.getNextSysMailSeqId();
			sysMail.setMailid(sysMailId);
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
			// 保存文件夹
			String toid = sysMail.getToid();
			if (null != toid && !"".equals(toid)) {
				String[] toids = toid.split(",");
				for (int i = 0; i < toids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(toids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			String ccid = sysMail.getCcid();
			if (null != ccid && !"".equals(ccid)) {
				String[] ccids = ccid.split(",");
				for (int i = 0; i < ccids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(ccids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			sysMailfolder = new SysMailfolder();
			sysMailfolder.setSysMail(sysMail);
			sysMailfolder.setFolder("SENDBOX");
			sysMailfolder.setSysUser(getCurUser());
			sysMailfolder.setReadflag("1");
			sysMailService.save(sysMailfolder);
			// 转发功能使用
			if (isTrans) {
				sysMailService.copySysMailAttachs(oldMailid, sysMailId);
			}
		} else {
			sysMailId = sysMail.getMailid();
			int uploadStatus = renderSysMail(sysMailId);
			if (uploadStatus == 0) {
				msg = new Msg(Msg.FAILURE, "上传的附件要小于20MB，请重新上传！");
				return MSG;
			}
			// 保存文件夹
			String toid = sysMail.getToid();
			if (null != toid && !"".equals(toid)) {
				String[] toids = toid.split(",");
				for (int i = 0; i < toids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(toids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			String ccid = sysMail.getCcid();
			if (null != ccid && !"".equals(ccid)) {
				String[] ccids = ccid.split(",");
				for (int i = 0; i < ccids.length; i++) {
					SysMailfolder sysMailfolder = new SysMailfolder();
					sysMailfolder.setFolder("INBOX");
					SysUser sysUser = sysUserService.load(ccids[i]);
					sysMailfolder.setSysUser(sysUser);
					sysMailfolder.setReadflag("0");
					sysMailfolder.setReadtime(null);
					sysMailfolder.setSysMail(sysMail);
					sysMailService.save(sysMailfolder);
				}
			}
			// 如果用户保存了草稿箱，则将邮件状态标记为已经发送的邮件，进入发件箱
			sysMailfolder = sysMailService.getSysMailfolder(sysMailfolder
					.getId());
			if (null != sysMailfolder) {
				sysMailfolder.setFolder("SENDBOX");
				sysMailService.update(sysMailfolder);
			}
		}
		String script = "alert('发送成功！');";
		if (isNew && !isTrans) {
			script += "window.parent.location.href='/sys/sys-mail-frame.jsp?surl=SENDBOX';";
		} else {
			// script +=
			// "frameElement.lhgDG.removeBtn('save');frameElement.lhgDG.removeBtn('send');";
			script += "frameElement.lhgDG.curWin.parent.location.href='/sys/sys-mail-frame.jsp?surl=SENDBOX';";
		}

		msg = new Msg(Msg.SUCCESS, "发送成功", script);
		return MSG;
	}

	public String reply() throws Exception {
		sysMailfolder = sysMailService.getSysMailfolder(sysMailfolder.getId());
		return "reply";
	}

	/**
	 * 删除邮件(如果是收件箱，则将状态转为废件箱；如果是草稿、废件箱、发件箱，则直接删除记录)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String ids = sysMailfolder.getIds();
		String folder = sysMailfolder.getFolder();
		if (null != ids && !"".equals(ids)) {
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				SysMailfolder sysMailfolder = sysMailService
						.getSysMailfolder(Long.valueOf(idArray[i].trim()));
				if (null != folder && "INBOX".equalsIgnoreCase(folder)) {
					sysMailfolder.setFolder("TRASH");
					sysMailService.update(sysMailfolder);
				} else {
					if ("DRAFAT".equals(folder)) {
						SysMail sysMail = sysMailfolder.getSysMail();
						long mailid = sysMail.getMailid();
						List<Long> mailids = new ArrayList<Long>();
						mailids.add(mailid);
						List<SysMailattach> attachList = sysMailService
								.findSysMailattach(mailids);
						if (null != attachList && !attachList.isEmpty()) {
							for (int j = 0; j < attachList.size(); j++) {
								SysMailattach sysMailattach = attachList.get(j);
								sysMailService.delete(sysMailattach);
							}
						}
					}

					sysMailService.delete(sysMailfolder);
				}
			}
		}
		String script = "alert('删除成功');window.parent.location.href='/sys/sys-mail-frame.jsp?surl="
				+ folder + "';";
		msg = new Msg(Msg.SUCCESS, "删除成功", script);
		return MSG;
	}

	public String down() {
		sysMailattach = sysMailService.getSysMailattach(sysMailattach.getId());
		filepath = sysMailattach.getPath();
		fileName = sysMailattach.getFilename();
		return "download";
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("SYS_MAIL");
	}

	public SysMail getSysMail() {
		return sysMail;
	}

	public void setSysMail(SysMail sysMail) {
		this.sysMail = sysMail;
	}

	public SysMailfolder getSysMailfolder() {
		return sysMailfolder;
	}

	public void setSysMailfolder(SysMailfolder sysMailfolder) {
		this.sysMailfolder = sysMailfolder;
	}

	public List<SysMail> getSysMails() {
		return sysMails;
	}

	public void setSysMails(List<SysMail> sysMails) {
		this.sysMails = sysMails;
	}

	public List<SysUser> getSysUserList() {
		return sysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public List<File> getUpload() {
		return uploads;
	}

	public void setUpload(List<File> uploadList) {
		this.uploads = uploadList;
	}

	public List<String> getUploadFileName() {
		return fileNames;
	}

	public void setUploadFileName(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public List<String> getUploadContentType() {
		return uploadContentTypes;
	}

	public void setUploadContentType(List<String> uploadContentTypeList) {
		this.uploadContentTypes = uploadContentTypeList;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public List<SysUser> getCcUsers() {
		return ccUsers;
	}

	public void setCcUsers(List<SysUser> ccUsers) {
		this.ccUsers = ccUsers;
	}

	public List<SysUser> getToUsers() {
		return toUsers;
	}

	public void setToUsers(List<SysUser> toUsers) {
		this.toUsers = toUsers;
	}

	public List<SysMailattach> getSysMaiattachList() {
		return sysMaiattachList;
	}

	public void setSysMaiattachList(List<SysMailattach> sysMaiattachList) {
		this.sysMaiattachList = sysMaiattachList;
	}

	public List<Object[]> getFromUsers() {
		return fromUsers;
	}

	public void setFromUsers(List<Object[]> fromUsers) {
		this.fromUsers = fromUsers;
	}

	public Map<Long, List<SysMailattach>> getSysMailattachsByMailIdMap() {
		return sysMailattachsByMailIdMap;
	}

	public void setSysMailattachsByMailIdMap(
			Map<Long, List<SysMailattach>> sysMailattachsByMailIdMap) {
		this.sysMailattachsByMailIdMap = sysMailattachsByMailIdMap;
	}

	public SysMailattach getSysMailattach() {
		return sysMailattach;
	}

	public void setSysMailattach(SysMailattach sysMailattach) {
		this.sysMailattach = sysMailattach;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	public Map getMailMap() {
		return mailMap;
	}

	public void setMailMap(Map mailMap) {
		this.mailMap = mailMap;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, SysUser> getSysUserMap() {
		return sysUserMap;
	}

	public void setSysUserMap(Map<String, SysUser> sysUserMap) {
		this.sysUserMap = sysUserMap;
	}

	public String getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(String superUserId) {
		this.superUserId = superUserId;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public Boolean getIsTrans() {
		return isTrans;
	}

	public void setIsTrans(Boolean isTrans) {
		this.isTrans = isTrans;
	}

}
