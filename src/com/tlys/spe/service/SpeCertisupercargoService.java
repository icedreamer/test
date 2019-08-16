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
import com.tlys.spe.dao.SpeCertisupercargoDao;
import com.tlys.spe.model.SpeCertishipping;
import com.tlys.spe.model.SpeCertisupercargo;

@Service
public class SpeCertisupercargoService extends _GenericService {
	@Autowired
	SpeCertisupercargoDao speCertisupercargoDao;

	@Autowired
	CacheService cacheService;

	@Autowired
	DicMap dicMap;

	private final static String CERT_ATTACH_URL = "CERTISUPERCARGO/";
	private final static String CERT_ATTACH_ZIP_URL = "CERTISUPERCARGO/ZIP/";

	public SpeCertisupercargo getSpeCertisupercargo(Long id) {
		return speCertisupercargoDao.load(id);
	}

	public void save(SpeCertisupercargo speCertisupercargo, File upload, String uploadFileName) {
		if (null == speCertisupercargo.getPhotoipath()) {
			speCertisupercargo.setPhotoipath("");
		}
		speCertisupercargoDao.save(speCertisupercargo);

		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(speCertisupercargo, upload, uploadFileName);
		}
	}

	public void update(SpeCertisupercargo speCertisupercargo, File upload, String uploadFileName) {
		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(speCertisupercargo, upload, uploadFileName);
		}
		speCertisupercargoDao.updateEntity(speCertisupercargo, speCertisupercargo.getRegid());
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertisupercargo
	 * @param pageCtr
	 */
	public void findSpeCertisupercargo(SpeCertisupercargo speCertisupercargo, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getSpeCertisupercargoCount(speCertisupercargo);
		pageCtr.setTotalRecord(totalRecord);
		speCertisupercargoDao.findSpeCertisupercargo(speCertisupercargo, pageCtr);

		dicMap.bdCorp(pageCtr.getRecords());
	}

	public List<SpeCertisupercargo> findAll() {
		return speCertisupercargoDao.findAll();
	}

	/**
	 * 保存附件到目录，将文件地址保存进数据库
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public void updateFileInfo(SpeCertisupercargo speCertisupercargo, File srcFile,
			String destFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		SpeCertisupercargo localSpeCertisupercargo = speCertisupercargoDao.load(speCertisupercargo
				.getRegid());
		String attachUrl = null;
		if (null != localSpeCertisupercargo) {
			attachUrl = localSpeCertisupercargo.getCertipath();
		}
		String idStr = String.valueOf(speCertisupercargo.getRegid());
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
			speCertisupercargo.setCertipath(destUrlFile);
			speCertisupercargo.setPhotoipath(destUrlFile);
			speCertisupercargo.setFilename(destFileName);
		}
	}

	public void delete(SpeCertisupercargo speCertisupercargo) throws Exception {
		if (null == speCertisupercargo) {
			return;
		}
		if (speCertisupercargo.getPhotoipath() != null
				&& speCertisupercargo.getPhotoipath().length() > 0) {
			// 删除证照之前先删除证照的附件
			deleteSpeCertisupercargoAttach(speCertisupercargo);
		}
		// 删除证照记录
		speCertisupercargoDao.delete(speCertisupercargo);
	}

	/**
	 * 删除证照的附件
	 * 
	 * @param speCertisupercargo
	 */
	private void deleteSpeCertisupercargoAttach(SpeCertisupercargo speCertisupercargo)
			throws Exception {
		if (null == speCertisupercargo || null == speCertisupercargo.getCertipath()) {
			return;
		}
		String attachUrl = speCertisupercargo.getCertipath();
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

	public int getSpeCertisupercargoCount(final SpeCertisupercargo speCertisupercargo) {

		int count = speCertisupercargoDao.getSpeCertisupercargoCount(speCertisupercargo);
		return count;
	}

	/**
	 * 打包下载所有证照
	 * 
	 * @param speCertisupercargoList
	 * @return
	 */
	public String downAll(List<SpeCertisupercargo> speCertisupercargoList) {
		if (null == speCertisupercargoList) {
			return "";
		}
		ServletContext content = ServletActionContext.getServletContext();
		List filePathList = new ArrayList();
		for (int i = 0; i < speCertisupercargoList.size(); i++) {
			SpeCertisupercargo speCertisupercargo = speCertisupercargoList.get(i);
			String attachUrl = speCertisupercargo.getCertipath();
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
		String zipFileUrl = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, "押运员证.zip");
		File destZipFile = new File(content.getRealPath(zipFileUrl));

		// 压缩文件包
		compressionFile(filePathList, destZipFile, "证照");
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
