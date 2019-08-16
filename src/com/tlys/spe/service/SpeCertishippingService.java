package com.tlys.spe.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.dao.SpeCertishippingDao;
import com.tlys.spe.dao.SpeCertishiptrainDao;
import com.tlys.spe.model.SpeCertishipping;

/**
 * @author 冯彦明
 * 
 */

@Service
public class SpeCertishippingService extends _GenericService {
	@Autowired
	SpeCertishippingDao speCertishippingDao;

	@Autowired
	SpeCertishiptrainDao speCertishiptrainDao;

	@Autowired
	CacheService cacheService;

	@Autowired
	DicMap dicMap;

	private final static String CERT_ATTACH_URL = "CERTISHIPPING/";
	private final static String CERT_ATTACH_ZIP_URL = "CERTISHIPPING/ZIP/";

	public SpeCertishipping getSpeCertishipping(Long id) {
		return speCertishippingDao.load(id);
	}

	public void save(SpeCertishipping speCertishipping, File upload, String uploadFileName) {
		if (null == speCertishipping.getPhotoipath()) {
			speCertishipping.setPhotoipath("");
		}
		speCertishippingDao.save(speCertishipping);

		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(speCertishipping, upload, uploadFileName);
		}
	}

	public void update(SpeCertishipping speCertishipping, File upload, String uploadFileName) {
		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(speCertishipping, upload, uploadFileName);
		}
		speCertishippingDao.updateEntity(speCertishipping, speCertishipping.getRegid());
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void findSpeCertishipping(SpeCertishipping speCertishipping, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getSpeCertishippingCount(speCertishipping);
		pageCtr.setTotalRecord(totalRecord);
		speCertishippingDao.findSpeCertishipping(speCertishipping, pageCtr);

		dicMap.bdCorp(pageCtr.getRecords());
	}

	public List<SpeCertishipping> findAll() {
		return speCertishippingDao.findAll();
	}

	/**
	 * 保存附件到目录，将文件地址保存进数据库
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public void updateFileInfo(SpeCertishipping speCertishipping, File srcFile, String destFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		String idStr = String.valueOf(speCertishipping.getRegid());
		SpeCertishipping localSpeCertishipping = speCertishippingDao.load(speCertishipping.getRegid());
		String attachUrl = null;
		
		if (null != localSpeCertishipping) {
			attachUrl = localSpeCertishipping.getCertipath();
		}
		if (log.isDebugEnabled()) {
			log.debug("attachUrl : " + attachUrl);
		}
		// 删除原来的附件
		if (null != attachUrl && !"".equals(attachUrl)) {
			String existFilePath = context.getRealPath(attachUrl);
			File existFile = new File(existFilePath);
			if (existFile.exists()) {
				existFile.delete();
			}
		}
		// 保存新上传的附件
		// 上传文件的相对URL
		String destUrlPath = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, idStr, "/");
		// 上传文件的物理路径
		String destRealPath = context.getRealPath(destUrlPath);
		File file = new File(destRealPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String saveFileName = idStr + "." + CommUtils.getExt(destFileName);

		String destUrlFile = destUrlPath + saveFileName;
		String destRealFile = destRealPath + "/" + saveFileName;
		File destFile = new File(destRealFile);
		if (copyFile(srcFile, destFile)) {
			speCertishipping.setCertipath(destUrlFile);
			speCertishipping.setPhotoipath(destUrlFile);
			speCertishipping.setFilename(destFileName);
		}
	}

	public void delete(SpeCertishipping speCertishipping) throws Exception {
		if (null == speCertishipping) {
			return;
		}
		if (speCertishipping.getPhotoipath() != null
				&& speCertishipping.getPhotoipath().length() > 0) {
			// 删除合格证之前先删除合格证的附件
			deleteSpeCertishippingAttach(speCertishipping);
		}
		// 删除培训记录
		speCertishiptrainDao.delete(speCertishipping.getRegid());
		// 删除合格证记录
		speCertishippingDao.delete(speCertishipping);

	}

	/**
	 * 删除附件
	 * 
	 * @param speCertishipping
	 */
	private void deleteSpeCertishippingAttach(SpeCertishipping speCertishipping) throws Exception {
		if (null == speCertishipping || null == speCertishipping.getCertipath()) {
			return;
		}
		String attachUrl = speCertishipping.getCertipath();
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath(attachUrl);
		File file = new File(filePath);
		if (file.exists()) {
			// 删除时要连同文件所在的文件夹一起删除
			File pDir = file.getParentFile();
			if (pDir.exists() && pDir.isDirectory()) {
				FileCommUtil.delete(pDir);
			} else {
				FileCommUtil.delete(file);
			}
		}
	}

	public int getSpeCertishippingCount(final SpeCertishipping speCertishipping) {

		int count = speCertishippingDao.getSpeCertishippingCount(speCertishipping);
		return count;
	}

	/**
	 * 打包下载所有合格证
	 * 
	 * @param speCertishippingList
	 * @return
	 */
	public String downAll(List<SpeCertishipping> speCertishippingList) {
		if (null == speCertishippingList) {
			return "";
		}
		ServletContext content = ServletActionContext.getServletContext();
		List filePathList = new ArrayList();
		;
		for (int i = 0; i < speCertishippingList.size(); i++) {
			SpeCertishipping speCertishipping = speCertishippingList.get(i);
			String attachUrl = speCertishipping.getCertipath();
			if (null == attachUrl || "".equals(attachUrl)) {
				continue;
			}
			// 将路径前的/去掉
			filePathList.add(attachUrl.substring(1));
		}
		String filePath = content.getRealPath(ATTACH_URL + CERT_ATTACH_URL);
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String zipFileUrl = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, "合格证.zip");
		File destZipFile = new File(content.getRealPath(zipFileUrl));
		// 压缩文件包
		compressionFile(filePathList, destZipFile, "合格证");
		return zipFileUrl;
	}

	/**
	 * 创建压缩包(打包下载)
	 * 
	 * @param filePathList
	 * @param destZipFile
	 * @param title
	 *            在压缩包里显示的文件夹的名字
	 */
	private void compressionFile(List filePathList, File destZipFile, String title) {
		FileOutputStream fos = null;
		ZipOutputStream outputStream = null;
		ServletContext content = ServletActionContext.getServletContext();
		try {
			fos = new FileOutputStream(destZipFile);
			outputStream = new ZipOutputStream(fos);
			ZipEntry zipEntry = new ZipEntry(title);
			for (int i = 0; i < filePathList.size(); i++) {
				String filePath = (String) filePathList.get(i);
				zipEntry = new ZipEntry(filePath);
				outputStream.putNextEntry(zipEntry);
				File f = new File(content.getRealPath(filePath));
				if (!f.exists() || !f.isFile()) {
					continue;
				}
				FileInputStream in = new FileInputStream(f);
				int b;
				if (log.isDebugEnabled()) {
					log.debug(zipEntry.getName());
				}
				while ((b = in.read()) != -1) {
					outputStream.write(b);
				}
				in.close();
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("get compression file error.", e);
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
	}

}
