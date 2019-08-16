package com.tlys.sys.service;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.sys.dao.SysBulletinAnnexDao;
import com.tlys.sys.dao.SysBulletinDao;
import com.tlys.sys.dao.SysBulletinReadinfoDao;
import com.tlys.sys.model.SysBulletin;
import com.tlys.sys.model.SysBulletinAnnex;
import com.tlys.sys.model.SysBulletinReadinfo;
import com.tlys.sys.model.SysUser;

/**
 * ֪ͨ�������
 * 
 * @author ccsong 2012-5-29 ����3:41:57
 */
@Service
public class SysBulletinService extends _GenericService {

	private final static String BULLETIN_ATTACH_HTML_URL = "BULLETIN/html/";
	private final static String BULLETIN_ATTACH_ANNEX_URL = "BULLETIN/annex/";

	@Autowired
	SysBulletinDao sysBulletinDao;
	@Autowired
	SysBulletinAnnexDao sysBulletinAnnexDao;
	@Autowired
	SysBulletinReadinfoDao sysBulletinReadinfoDao;

	public void findSysBulletin(SysBulletin sysBulletin, PageCtr<SysBulletin> pageCtr) {
		pageCtr.setTotalRecord(getSysBulletinCount(sysBulletin));
		sysBulletinDao.findSysBulletin(sysBulletin, pageCtr);
	}

	/**
	 * add by fengym ������������¼������ҳչʾ��
	 * 
	 * @return
	 */
	public List findSysBulletin4Index(SysUser curuser, int size) {
		String userid = curuser.getUserid();
		SysBulletin bt = new SysBulletin();
		bt.setStatus("2");
		PageCtr pageCtr = new PageCtr();
		pageCtr.setPageSize(size);
		findSysBulletin(bt, pageCtr);
		List btList = pageCtr.getRecords();

		// ���뵱ǰ�û��Ƿ��Ѷ��ı�־
		for (Iterator iter = btList.iterator(); iter.hasNext();) {
			SysBulletin itbt = (SysBulletin) iter.next();
			Long btid = itbt.getBulletinid();
			SysBulletinReadinfo btread = new SysBulletinReadinfo();
			btread.setBulletinid(btid);
			btread.setReaderid(userid);
			itbt.setReadover(sysBulletinReadinfoDao.ifReadover(btread));
		}

		return btList;
	}

	/**
	 * ����û�б����û������Ĺ���
	 * 
	 * @param userid
	 * @param status
	 * @return
	 */
	public List<SysBulletin> findSysBulletin(String userid, String status) {
		return sysBulletinDao.findSysBulletin(userid, status);
	}

	public List<Object[]> findWriter() {
		return sysBulletinDao.findWriter();
	}

	public List<Object[]> findPublisher() {
		return sysBulletinDao.findPublisher();
	}

	public SysBulletin load(Long id) {
		return sysBulletinDao.load(id);
	}

	public void save(SysBulletin sysBulletin, Long bulletinId) {
		FileWriter fileWriter = null;
		try {
			String content = sysBulletin.getContent();

			if (log.isDebugEnabled()) {
				log.debug("content : " + content);
			}
			ServletContext context = ServletActionContext.getServletContext();
			String destPath = CommUtils.getString(ATTACH_URL, BULLETIN_ATTACH_HTML_URL, bulletinId,
					"/");
			String realpath = context.getRealPath(destPath);
			if (log.isDebugEnabled()) {
				log.debug("destPath : " + destPath);
			}
			File file = new File(realpath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = bulletinId + ".html";
			File destFile = new File(CommUtils.getString(realpath, "/", fileName));
			fileWriter = new FileWriter(destFile);
			fileWriter.write(content);
			sysBulletin.setBulletinid(bulletinId);
			sysBulletin.setPath(destPath + fileName);
			sysBulletinDao.save(sysBulletin);
		} catch (Exception e) {
			log.error("save sysBulletin error.", e);
		} finally {
			if (null != fileWriter) {
				try {
					fileWriter.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public void save(SysBulletinReadinfo sysBulletinReadinfo) {
		sysBulletinReadinfoDao.save(sysBulletinReadinfo);
	}

	/**
	 * �޸Ĺ���״̬�����ڷ�������
	 * 
	 * @param sysBulletin
	 */
	public void updateStatus(SysBulletin sysBulletin) {
		sysBulletinDao.update(sysBulletin);
	}

	/**
	 * �޸Ĺ��棬�����������ݵ���Ϣ
	 * 
	 * @param sysBulletin
	 */
	public void update(SysBulletin sysBulletin) {
		FileWriter fileWriter = null;
		try {
			String content = sysBulletin.getContent();
			Long bulletinId = sysBulletin.getBulletinid();
			if (log.isDebugEnabled()) {
				log.debug("content : " + content);
			}
			ServletContext context = ServletActionContext.getServletContext();
			String destPath = CommUtils.getString(ATTACH_URL, BULLETIN_ATTACH_HTML_URL, bulletinId,
					"/");
			String realpath = context.getRealPath(destPath);
			if (log.isDebugEnabled()) {
				log.debug("destPath : " + destPath);
			}
			File file = new File(realpath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = bulletinId + ".html";
			File destFile = new File(CommUtils.getString(realpath, "/", fileName));
			fileWriter = new FileWriter(destFile);
			fileWriter.write(content);
			sysBulletinDao.updateEntity(sysBulletin, sysBulletin.getBulletinid());
		} catch (Exception e) {
			log.error("update sysBulletin error.", e);
		} finally {
			if (null != fileWriter) {
				try {
					fileWriter.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * ɾ�����棬ͬʱɾ�������ļ��͸����ļ���ͬʱɾ��������Ϣ��¼��ͬʱɾ���Ķ���Ϣ
	 * 
	 * @param sysBulletin
	 */
	public void delete(SysBulletin sysBulletin) {
		Long bulletinId = sysBulletin.getBulletinid();
		List<SysBulletinAnnex> sysBulletinAnnexs = findSysBulletinAnnexByBulletinId(bulletinId);
		if (null != sysBulletinAnnexs && !sysBulletinAnnexs.isEmpty()) {
			for (int i = 0; i < sysBulletinAnnexs.size(); i++) {
				SysBulletinAnnex sysBulletinAnnex = sysBulletinAnnexs.get(i);
				delete(sysBulletinAnnex);
			}
		}
		sysBulletinDao.delete(sysBulletin);
	}

	/**
	 * ɾ��������¼
	 * 
	 * @param sysBulletinAnnex
	 */
	public void delete(SysBulletinAnnex sysBulletinAnnex) {
		if (null == sysBulletinAnnex) {
			return;
		}
		ServletContext context = ServletActionContext.getServletContext();
		String fileUrl = sysBulletinAnnex.getPath();
		String realpath = context.getRealPath(fileUrl);
		File file = new File(realpath);
		if (file.exists()) {
			FileCommUtil.delete(file);
		}
		sysBulletinAnnexDao.delete(sysBulletinAnnex);
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public Long getNextSysBulletinSeqId() {
		return sysBulletinDao.getNextSysBulletinSeqId();
	}

	public int getSysBulletinCount(final SysBulletin sysBulletin) {
		return sysBulletinDao.getSysBulletinCount(sysBulletin);
	}

	public void save(SysBulletinAnnex sysBulletinAnnex) {
		sysBulletinAnnexDao.save(sysBulletinAnnex);
	}

	/**
	 * ���ɸ���
	 * 
	 * @param srcFile
	 * @param bulletinId
	 * @param suffix
	 * @return
	 */
	public String getSysBulletinAnnexUrl(File srcFile, Long bulletinId, String suffix) {
		ServletContext context = ServletActionContext.getServletContext();
		// �������ϴ��ĸ���
		String destFilePath = CommUtils.getString(ATTACH_URL, BULLETIN_ATTACH_ANNEX_URL,
				bulletinId, "/");
		String destPath = context.getRealPath(destFilePath);
		File file = new File(destPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String destFileName = CommUtils.getString(bulletinId, ".", suffix);
		String destFileUrl = destFilePath + destFileName;
		File destFile = new File(destPath + "/" + destFileName);
		if (copyFile(srcFile, destFile)) {
			return destFileUrl;
		}
		return "";
	}

	/**
	 * ����ȡ�Ķ�����
	 * 
	 * @param bulletinIdList
	 * @return
	 */
	public List<Object[]> getSysBulletinReadCount(final List<Long> bulletinIdList) {
		return sysBulletinReadinfoDao.getSysBulletinReadCount(bulletinIdList);
	}

	/**
	 * ���ݹ���Id��ȡ��������и���
	 * 
	 * @param bulletinid
	 * @return
	 */
	public List<SysBulletinAnnex> findSysBulletinAnnexByBulletinId(Long bulletinid) {
		return sysBulletinAnnexDao.findSysBulletinAnnexByBulletinId(bulletinid);
	}

	/**
	 * �û��Ƿ�Ըù����¼�Ѷ�
	 * 
	 * @param userid
	 * @param bulletinid
	 * @return
	 */
	public SysBulletinReadinfo getSysBulletinReadinfo(final String userid, final Long bulletinid) {
		return sysBulletinReadinfoDao.getSysBulletinReadinfo(userid, bulletinid);
	}

	public SysBulletinAnnex getSysBulletinAnnexById(Long annexid) {
		return sysBulletinAnnexDao.load(annexid);
	}

	public boolean isReadover(SysBulletinReadinfo sysBulletinReadinfo) {
		return sysBulletinReadinfoDao.ifReadover(sysBulletinReadinfo);
	}

	public void update(SysBulletinAnnex sysBulletinAnnex) {
		sysBulletinAnnexDao.update(sysBulletinAnnex);
	}
}
