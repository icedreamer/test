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
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
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
	 * ���渽����Ŀ¼�����ļ���ַ��������ݿ�
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
		// ɾ��ԭ���ĸ���
		if (null != attachUrl && !"".equals(attachUrl)) {
			String existFilePath = context.getRealPath(attachUrl);
			File existFile = new File(existFilePath);
			if (existFile.exists()) {
				existFile.delete();
			}
		}

		// �������ϴ��ĸ���
		// �ϴ��ļ������URL
		String destUrlPath = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, idStr, "/");
		// �ϴ��ļ�������·��
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
			// ɾ��֤��֮ǰ��ɾ��֤�յĸ���
			deleteSpeCertisupercargoAttach(speCertisupercargo);
		}
		// ɾ��֤�ռ�¼
		speCertisupercargoDao.delete(speCertisupercargo);
	}

	/**
	 * ɾ��֤�յĸ���
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
			// ɾ��ʱҪ��ͬ�ļ����ڵ��ļ���һ��ɾ��
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
	 * �����������֤��
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
			// ��·��ǰ��/ȥ��
			filePathList.add(attachUrl.substring(1));
		}
		String filePath = content.getRealPath(ATTACH_URL + CERT_ATTACH_URL);
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String zipFileUrl = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, "Ѻ��Ա֤.zip");
		File destZipFile = new File(content.getRealPath(zipFileUrl));

		// ѹ���ļ���
		compressionFile(filePathList, destZipFile, "֤��");
		return zipFileUrl;
	}

	/**
	 * ����ѹ����(�������)
	 * 
	 * @param filePathList
	 * @param destZipFile
	 * @param title
	 *            ��ѹ��������ʾ���ļ��е�����
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
