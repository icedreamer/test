package com.tlys.spe.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.tlys.spe.dao.SpeLawDao;
import com.tlys.spe.model.SpeLaw;

@Service
public class SpeLawService extends _GenericService<SpeLaw> {

	@Autowired
	SpeLawDao speLawDao;

	@Autowired
	CacheService cacheService;
	@Autowired
	DicMap dicMap;
	// 附件路径
	private final static String CERT_ATTACH_URL = "LAW/";
	// 打包路径
	private final static String CERT_ATTACH_ZIP_URL = "LAW/ZIP/";

	public SpeLaw getSpeLaw(Long id) {
		return speLawDao.load(id);
	}

	public void save(SpeLaw speLaw, File upload, String uploadFileName) {
		speLawDao.save(speLaw);
		if (null != upload && null != uploadFileName
				&& !"".equals(uploadFileName)) {
			updateFileInfo(speLaw, upload, uploadFileName);
		}
	}

	public void update(SpeLaw speLaw, File upload, String uploadFileName) {
		if (null != upload && null != uploadFileName
				&& !"".equals(uploadFileName)) {
			updateFileInfo(speLaw, upload, uploadFileName);
		}
		speLawDao.updateEntity(speLaw, speLaw.getId());
	}

	public void update(SpeLaw speLaw) {
		speLawDao.updateEntity(speLaw, speLaw.getId());
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speLaw
	 * @param pageCtr
	 * @throws Exception
	 */
	public void findSpeLaw(SpeLaw speLaw, PageCtr pageCtr) throws Exception {
		int totalRecord = getSpeLawCount(speLaw);
		pageCtr.setTotalRecord(totalRecord);
		speLawDao.findSpeLaw(speLaw, pageCtr);
		dicMap.bdSpeLaw(pageCtr.getRecords());
	}

	public List<SpeLaw> findSpeLaw() {
		return speLawDao.findAll();
	}

	/**
	 * 保存附件到目录，将文件地址保存进数据库
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public void updateFileInfo(SpeLaw speLaw, File srcFile, String destFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		String attachUrl = speLaw.getPath();
		// 删除原来的附件及SWF文件
		if (null != attachUrl && !"".equals(attachUrl)) {
			String existFilePath = context.getRealPath(attachUrl);
			File existFile = new File(existFilePath);
			if (existFile.exists()) {
				existFile.delete();
			}
			String swfUrl = attachUrl.substring(0,
					attachUrl.lastIndexOf("/") + 1)
					+ speLaw.getId() + ".swf";
			String swfFilePath = context.getRealPath(swfUrl);
			File swfFile = new File(swfFilePath);
			if (swfFile.exists()) {
				swfFile.delete();
			}
		}
		// 保存新上传的附件
		String destFilePath = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL,
				speLaw.getId(), "/");
		String destPath = context.getRealPath(destFilePath);
		File file = new File(destPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 保存文件名
		String saveFileName = speLaw.getId() + "."
				+ CommUtils.getExt(destFileName);

		// 文件路径
		String oddFileUrl = destFilePath + saveFileName;
		File destFile = new File(destPath + "/" + saveFileName);
		if (copyFile(srcFile, destFile)) {
			// 文件保存成功时添加文件路径和文件名
			speLaw.setPath(oddFileUrl);
			speLaw.setFilename(destFileName);
			// 生成swf文件
			String newFile = destPath + "/" + speLaw.getId() + ".swf";
			String oldFile = destPath + "/" + saveFileName;
			boolean bl = toSwf(oldFile, newFile);
			if (!bl)
				javax.swing.JOptionPane.showMessageDialog(null, newFile
						+ "文件保存不成功");
		}
	}

	public void delete(SpeLaw speLaw) {
		if (null == speLaw) {
			return;
		}
		if (speLaw.getPath() != null && speLaw.getPath().length() > 0) {
			// 删除证照之前先删除证照的附件
			deleteSpeLawAttach(speLaw);
		}
		// 删除证照记录
		speLawDao.delete(speLaw);
	}

	/**
	 * 删除证照的附件
	 * 
	 * @param speLaw
	 */
	private void deleteSpeLawAttach(SpeLaw speLaw) {
		if (null == speLaw) {
			return;
		}
		String attachUrl = speLaw.getPath();
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

	public int getSpeLawCount(final SpeLaw speLaw) {
		int count = speLawDao.getSpeLawCount(speLaw);
		return count;
	}

	/**
	 * 打包下载所有证照
	 * 
	 * @param speLawList
	 * @return
	 */
	public String downall(List<SpeLaw> speLawList) {
		if (null == speLawList) {
			return "";
		}
		ServletContext content = ServletActionContext.getServletContext();
		String[] files = new String[speLawList.size()];
		for (int i = 0; i < speLawList.size(); i++) {
			SpeLaw speLaw = speLawList.get(i);
			String attachUrl = speLaw.getPath();
			if (null == attachUrl || "".equals(attachUrl)) {
				continue;
			}
			files[i] = attachUrl.substring(1);
		}

		String zipFileUrl = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL,
				"法律法规.zip");
		File destZipFile = new File(content.getRealPath(zipFileUrl));
		// 压缩文件包
		compressionFile(files, destZipFile, "法律法规");
		return zipFileUrl;
	}

	/**
	 * 创建压缩包(打包下载)
	 * 
	 * @param files
	 * @param destZipFile
	 * @param title
	 *            在压缩包里显示的文件夹的名字
	 */
	private void compressionFile(String[] files, File destZipFile, String title) {
		FileOutputStream fos = null;
		ZipOutputStream outputStream = null;
		ServletContext content = ServletActionContext.getServletContext();
		try {
			fos = new FileOutputStream(destZipFile);
			outputStream = new ZipOutputStream(fos);
			ZipEntry zipEntry = new ZipEntry(title);
			for (int i = 0; i < files.length; i++) {
				zipEntry = new ZipEntry(files[i]);
				outputStream.putNextEntry(zipEntry);
				File f = new File(content.getRealPath(files[i]));
				if (!f.exists()) {
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
