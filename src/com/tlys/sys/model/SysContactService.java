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
	// * 因要用树状结构展示通讯录，暂时不采用分页查询
	// *
	// * @param sysContact
	// * @return
	// */
	// public List<SysContact> findSysContact(SysContact sysContact) {
	// return sysContactDao.findSysContact(sysContact);
	// }
	//
	// /**
	// * 查询区域公司
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
	 * 重新上传联系方式 根据不同的企业代码
	 * 
	 * @param corpid
	 *            企业代码
	 * @param srcFile
	 *            正在上传的文件
	 * @param suffix
	 *            文件后缀
	 */
	public void upload(String corpid, File srcFile, String suffix) {
		ServletContext context = ServletActionContext.getServletContext();
		String destFilePath = context.getRealPath(CONTACT_FILE_PATH);
		// 保存新上传的附件
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
