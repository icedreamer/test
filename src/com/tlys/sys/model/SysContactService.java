package com.tlys.sys.model;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;

/**
 * 
 * @author ccsong
 * 
 */
@Service
public class SysContactService extends _GenericService {

	// SysContactDao sysContactDao;
	private String CONTACT_FILE_PATH = "/download/contact/";

	// /**
	// * ��Ҫ����״�ṹչʾͨѶ¼����ʱ�����÷�ҳ��ѯ
	// *
	// * @param sysContact
	// * @return
	// */
	// public List<SysContact> findSysContact(SysContact sysContact) {
	// return sysContactDao.findSysContact(sysContact);
	// }
	//
	// /**
	// * ��ѯ����˾
	// *
	// * @return
	// * @throws Exception
	// */
	// public List<Object[]> findAreas() throws Exception {
	// return sysContactDao.findAreas();
	// }
	//
	// public SysContact getSysContact(Long contactid) {
	// return sysContactDao.load(contactid);
	// }
	//
	// public void save(SysContact sysContact) {
	// sysContactDao.save(sysContact);
	// }
	//
	// public void update(SysContact sysContact) {
	// sysContactDao.update(sysContact);
	// }
	//
	// public void delete(SysContact sysContact) {
	// sysContactDao.delete(sysContact);
	// }
	/**
	 * �����ϴ���ϵ��ʽ ���ݲ�ͬ����ҵ����
	 * 
	 * @param corpid
	 *            ��ҵ����
	 * @param srcFile
	 *            �����ϴ����ļ�
	 * @param suffix
	 *            �ļ���׺
	 */
	public void upload(String corpid, File srcFile, String suffix) {
		ServletContext context = ServletActionContext.getServletContext();
		String destFilePath = context.getRealPath(CONTACT_FILE_PATH);
		// �������ϴ��ĸ���
		File file = new File(destFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = srcFile.getName();
		String destFileName = CommUtils.getString(corpid, ".", suffix);
		String destFileUrl = CommUtils.getString(destFilePath, "/", destFileName);
		File destFile = new File(destFileUrl);
		copyFile(srcFile, destFile);
		if (log.isDebugEnabled()) {
			log.debug("copy file success...");
		}
	}
}
