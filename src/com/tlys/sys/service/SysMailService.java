package com.tlys.sys.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.dao.SysMailDao;
import com.tlys.sys.dao.SysMailattachDao;
import com.tlys.sys.dao.SysMailfolderDao;
import com.tlys.sys.model.SysMail;
import com.tlys.sys.model.SysMailattach;
import com.tlys.sys.model.SysMailfolder;
import com.tlys.sys.model.SysUser;

@Service
public class SysMailService extends _GenericService {
	private final static String SYS_MAIL_ATTACH_URL = "MAIL/";
	private Log log = LogFactory.getLog(SysMailService.class);
	@Autowired
	SysMailfolderDao sysMailfolderDao;

	@Autowired
	SysMailDao sysMailDao;
	@Autowired
	SysMailattachDao sysMailattachDao;

	public void findSysMailfolders(SysMailfolder sysMailfolder,
			PageCtr<SysMailfolder> pageCtr) {
		pageCtr.setTotalRecord(sysMailfolderDao
				.getSysMailfolderCount(sysMailfolder));
		sysMailfolderDao.findSysMailfolders(sysMailfolder, pageCtr);
	}

	public void findSysMails(SysMail sysMail, PageCtr<SysMail> pageCtr) {
		pageCtr.setTotalRecord(sysMailDao.getSysMailCount(sysMail));
		sysMailDao.findSysMails(sysMail, pageCtr);
	}

	/**
	 * 得到当前用户各邮件夹的邮件数量
	 * 
	 * @param sysUser
	 */
	public Map findUserMailCounts(SysUser sysUser) {

		// 先初始化进0值，否则如查此人某文件夹没邮件，则在map中出来为null,在页面中不显示
		Map mcMap = new HashMap() {
			{
				put("DRAFAT", "0");
				put("TRASH", "0");
				put("INBOX", "0");
				put("SENDBOX", "0");
				put("newmail", "0");
			}
		};
		List objArrList = sysMailDao.getMailCountGroup(sysUser);

		for (Iterator iter = objArrList.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();
			mcMap.put(objArr[0], objArr[1]);
		}

		int newCnt = sysMailDao.getNewMailCount(sysUser);
		mcMap.put("newmail", newCnt);

		return mcMap;
	}

	public SysMail getSysMailById(long mailid) {
		return sysMailDao.load(mailid);
	}

	public SysMailfolder getSysMailfolder(long id) {
		return sysMailfolderDao.load(id);
	}

	public SysMailattach getSysMailattach(long id) {
		return sysMailattachDao.load(id);
	}

	public void save(SysMail sysMail) {
		sysMailDao.save(sysMail);
	}

	public void saveOrUpdate(SysMail sysMail) {
		sysMailDao.saveOrUpdate(sysMail);
	}

	public void save(SysMailattach sysMailattach) {
		sysMailattachDao.save(sysMailattach);
	}

	public void save(SysMailfolder sysMailfolder) {
		sysMailfolderDao.save(sysMailfolder);
	}

	public void update(SysMail sysMail) {
		sysMailDao.update(sysMail);
	}

	public void update(SysMailfolder sysMailfolder) {
		sysMailfolderDao.update(sysMailfolder);
	}

	public void update(SysMailattach sysMailattach) {
		sysMailattachDao.update(sysMailattach);
	}

	/**
	 * 删除邮件实体(该方法一般不会用到，执行该方法将删除所有相关发送过的信息)
	 * 
	 * @param sysMail
	 */
	public void delete(SysMail sysMail) {
		if (null == sysMail) {
			return;
		}
		// 删除附件信息
		List<SysMailattach> attachList = sysMailattachDao.findByProperty(
				"mailid", sysMail.getMailid());
		for (int i = 0; i < attachList.size(); i++) {
			SysMailattach sysMailattach = attachList.get(i);
			delete(sysMailattach);
		}
		List<SysMailfolder> folderList = sysMailfolderDao.findByProperty(
				"sysMail.mailid", sysMail.getMailid());
		// 删除邮件文件夹里所有相关邮件
		if (null != folderList && !folderList.isEmpty()) {
			for (int i = 0; i < folderList.size(); i++) {
				delete(folderList.get(i));
			}
		}
		// 删除邮件
		sysMailDao.delete(sysMail);
	}

	/**
	 * 删除附件
	 * 
	 * @param sysMailattach
	 */
	public void delete(SysMailattach sysMailattach) {
		ServletContext context = ServletActionContext.getServletContext();

		if (null == sysMailattach) {
			return;
		}
		String filePath = context.getRealPath(sysMailattach.getPath());
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		sysMailattachDao.delete(sysMailattach);
	}

	public void delete(SysMailfolder sysMailfolder) {
		sysMailfolderDao.delete(sysMailfolder);
	}

	public long getNextSysMailSeqId() {
		return sysMailDao.getNextSysMailSeqId();
	}

	public String getFileUrl(SysMailattach sysMailattach, Long sysMailId,
			File srcFile, String suffix) {
		ServletContext context = ServletActionContext.getServletContext();
		String attachUrl = sysMailattach.getPath();
		if (log.isDebugEnabled()) {
			log.debug("attachUrl : " + attachUrl);
		}
		// 删除原来的附件
		if (null != attachUrl && !"".equals(attachUrl)) {
			String existFilePath = context.getRealPath(attachUrl);
			if (log.isDebugEnabled()) {
				log.debug("existFilePath : " + existFilePath);
			}
			File existFile = new File(existFilePath);
			if (log.isDebugEnabled()) {
				log.debug("existFile.exists() : " + existFile.exists());
			}
			if (existFile.exists()) {
				existFile.delete();
			}
		}
		// 保存新上传的附件
		String destFilePath = CommUtils.getString(ATTACH_URL,
				SYS_MAIL_ATTACH_URL, sysMailId, "/");
		String destPath = context.getRealPath(destFilePath);
		File file = new File(destPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String destFileName = CommUtils.getString(sysMailId, ".", suffix);
		String destFileUrl = destFilePath + destFileName;
		if (log.isDebugEnabled()) {
			log.debug("destFileName : " + destFileName);
			log.debug("destFileUrl : " + destFileUrl);
		}
		File destFile = new File(destPath + "/" + destFileName);
		if (copyFile(srcFile, destFile)) {
			return destFileUrl;
		}
		return "";
	}

	public List<SysMailattach> findSysMailattach(long mailid) {
		return sysMailattachDao.findByProperty("mailid", mailid);
	}

	public List<SysMailattach> findSysMailattach(List<Long> mailids) {
		return sysMailattachDao.findSysMailattach(mailids);
	}

	public long getSysMailattachSize(long mailid) {
		long size = 0;
		List<SysMailattach> list = sysMailattachDao.findByProperty("mailid",
				mailid);
		ServletContext context = ServletActionContext.getServletContext();
		if (null != list && !list.isEmpty()) {
			for (SysMailattach sysMailattach : list) {
				String path = context.getRealPath(sysMailattach.getPath());
				File file = new File(path);
				if (file.exists()) {
					size += file.length();
				}
			}
		}
		return size;
	}

	public List<Object[]> findUsers(String userid) {
		return sysMailfolderDao.findUsers(userid);
	}

	/**
	 * 转发邮件 同时将附件一并转发
	 * 
	 * @param mailid
	 */
	public void copySysMailAttachs(long oldMailId, long newMailId) {
		List<SysMailattach> attachList = findSysMailattach(oldMailId);
		log.debug("attachList : " + attachList);
		ServletContext context = ServletActionContext.getServletContext();
		if (attachList != null && !attachList.isEmpty()) {
			for (SysMailattach sysMailattach : attachList) {
				SysMailattach mailattach = new SysMailattach();
				CommUtils.copyProperties(sysMailattach, mailattach);
				String attachUrl = sysMailattach.getPath();
				if (log.isDebugEnabled()) {
					log.debug("attachUrl : " + attachUrl);
				}
				// 删除原来的附件
				if (null != attachUrl && !"".equals(attachUrl)) {
					String existFilePath = context.getRealPath(attachUrl);
					File srcFile = new File(existFilePath);

					String destFilePath = CommUtils.getString(ATTACH_URL,
							SYS_MAIL_ATTACH_URL, newMailId, "/");
					String destPath = context.getRealPath(destFilePath);
					log.debug("destFilePath : " + destFilePath);
					log.debug("destPath : " + destPath);
					File file = new File(destPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					String fileName = srcFile.getName();
					String suffix = CommUtils.getSuffix(fileName);
					if (log.isDebugEnabled()) {
						log.debug("fileName : " + fileName);
						log.debug("suffix : " + suffix);
					}
					String newFileName = CommUtils.getString(newMailId, ".",
							suffix);
					File destFile = new File(CommUtils.getString(destPath,"/",
							newFileName));
					log.debug("newFileName : " + newFileName);
					log.debug("destFile : " + destFile);
					copyFile(srcFile, destFile);
					mailattach.setPath(destFilePath + newFileName);
					mailattach.setMailid(newMailId);

				} else {
					continue;
				}

				save(mailattach);

			}
		}

	}
}
