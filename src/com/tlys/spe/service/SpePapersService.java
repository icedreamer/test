package com.tlys.spe.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.service.EquCarService;
import com.tlys.spe.dao.SpePapersDao;
import com.tlys.spe.dao.SpePaperstypeDao;
import com.tlys.spe.model.SpePapers;
import com.tlys.spe.model.SpePaperstype;

@Service
public class SpePapersService extends _GenericService {
	private Log logger = LogFactory.getLog(SpePapersService.class);
	@Autowired
	SpePapersDao spePapersDao;
	@Autowired
	SpePaperstypeDao spePaperstypeDao;
	@Autowired
	CacheService cacheService;
	@Autowired
	DicMap dicMap;

	@Autowired
	EquCarService equCarService;

	private final static String PAPERS_ATTACH_URL = "PAPER/";
	private final static String PAPERS_ATTACH_ZIP_URL = "PAPER/ZIP/";

	public SpePapers getSpePapers(Long id) {
		return spePapersDao.load(id);
	}

	public void save(SpePapers spePapers) {
		spePapersDao.save(spePapers);
	}

	public void saveOrUpdate(SpePapers spePapers) {
		spePapersDao.saveOrUpdate(spePapers);
	}

	public void save(SpePaperstype spePaperstype) {
		spePaperstypeDao.save(spePaperstype);
		this.setAlterFlag();

	}

	public void update(SpePapers spePapers) {
		spePapersDao.updateEntity(spePapers, spePapers.getId());
	}

	public void saveOrUpdate(SpePaperstype spePaperstype) {
		spePaperstypeDao.saveOrUpdate(spePaperstype);
		this.setAlterFlag();
	}

	public SpePaperstype getSpePaperstype(String ptypeid) {
		return spePaperstypeDao.load(ptypeid);
	}

	public String findSpePapers(SpePapers spePapers, PageCtr<SpePapers> pageCtr) {
		int totalRecord = getSpePapersCount(spePapers);
		pageCtr.setTotalRecord(totalRecord);
		spePapersDao.findSpePapers(spePapers, pageCtr);
		Map sptpMap = dicMap.getSpePaperstypeMap();
		String ptypename = null;
		if (null != spePapers && null != spePapers.getSpePaperstype()) {
			SpePaperstype sptp = (SpePaperstype) sptpMap.get(spePapers
					.getSpePaperstype().getPtypeid());
			if (null != sptp) {
				ptypename = sptp.getName();
			}
		}
		return ptypename == null ? "֤��" : ptypename;
	}

	public List<SpePapers> findSpePapers() {
		return spePapersDao.findAll();
	}

	/**
	 * ���渽����Ŀ¼���������ļ���ַ
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public String getFileUrl(SpePapers spePapers, Long spePapersId,
			File srcFile, String suffix) {
		ServletContext context = ServletActionContext.getServletContext();
		String attachUrl = spePapers.getPath();
		if (log.isDebugEnabled()) {
			log.debug("attachUrl : " + attachUrl);
		}
		// ɾ��ԭ���ĸ���
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
		// �������ϴ��ĸ���
		String destFilePath = CommUtils.getString(ATTACH_URL,
				PAPERS_ATTACH_URL, spePapersId, "/");
		String destPath = context.getRealPath(destFilePath);
		File file = new File(destPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String destFileName = CommUtils.getString(spePapersId, ".", suffix);
		String destFileUrl = destFilePath + destFileName;
		if (logger.isDebugEnabled()) {
			logger.debug("destFileName : " + destFileName);
			logger.debug("destFileUrl : " + destFileUrl);
		}
		File destFile = new File(destPath + "/" + destFileName);
		if (copyFile(srcFile, destFile)) {
			return destFileUrl;
		}
		return "";
	}

	public List<SpePaperstype> findSpePaperstype() {
		return spePaperstypeDao.findSpePaperstype();
	}

	public List<SpePaperstype> findSpePaperstypeByTpid(String tpid) {
		return spePaperstypeDao.findByTpid(tpid);
	}

	public List<SpePapers> findSpePapers(SpePapers spePapers) {
		return spePapersDao.findSpePapers(spePapers);
	}

	/**
	 * ɾ��֤������
	 * 
	 * @param spePaperstype
	 */
	public void delete(SpePaperstype spePaperstype) {
		String ptypeid = spePaperstype.getPtypeid();
		// ɾ��֤������֮ǰ�Ƚ������͵�֤�յ�������Ϊ00
		spePapersDao.updateSpePapersPtypeid(ptypeid);
		// ɾ��֤������
		spePaperstypeDao.delete(spePaperstype);

	}

	public void delete(SpePapers spePapers) {
		if (null == spePapers) {
			return;
		}
		if (spePapers.getPath() != null && spePapers.getPath().length() > 0) {
			// ɾ��֤��֮ǰ��ɾ��֤�յĸ���
			deleteSpePapersAttach(spePapers);
		}
		// ɾ��֤�ռ�¼
		spePapersDao.delete(spePapers);
	}

	/**
	 * ɾ��֤�յĸ���
	 * 
	 * @param spePapers
	 */
	private void deleteSpePapersAttach(SpePapers spePapers) {
		if (null == spePapers) {
			return;
		}
		String attachUrl = spePapers.getPath();
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath(attachUrl);
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	public String getNextPtypeId() {
		String maxPtypeId = spePaperstypeDao.getMaxPtypeId();
		int nextId = (null != maxPtypeId && !"".equals(maxPtypeId) ? Integer
				.parseInt(maxPtypeId.trim()) + 1 : 1);
		String nextIdStr = String.valueOf(nextId);
		return (nextIdStr.length() == 1 ? "0" + nextIdStr : nextIdStr);
	}

	public int getSpePapersCount(final SpePapers spePapers) {
		/*
		 * String[] corpids = (null == spePapers ? null :
		 * spePapers.getCorpids()); String startTime = (null == spePapers ? null :
		 * spePapers.getStartTime()); String endTime = (null == spePapers ? null :
		 * spePapers.getEndTime()); String papersname = (null == spePapers ? "" :
		 * spePapers.getPapersname()); String key =
		 * CommUtils.getString("SPE_PAPERS_COUNT_", CommUtils
		 * .getString(corpids), startTime, endTime, papersname); Integer count =
		 * (Integer) cacheService.get(key); if (count == null) { count =
		 * spePapersDao.getSpePapersCount(spePapers); cacheService.put(key,
		 * count); }
		 */
		Integer count = spePapersDao.getSpePapersCount(spePapers);
		return count;
	}

	/**
	 * �����������֤��
	 * 
	 * @param spePapersList
	 * @return
	 */
	public String downall(List<SpePapers> spePapersList) {
		if (null == spePapersList) {
			return "";
		}
		ServletContext content = ServletActionContext.getServletContext();
		String[] files = new String[spePapersList.size()];
		for (int i = 0; i < spePapersList.size(); i++) {
			SpePapers spePapers = spePapersList.get(i);
			String attachUrl = spePapers.getPath();
			if (null == attachUrl || "".equals(attachUrl)) {
				continue;
			}
			// File file = new File(attachUrl);
			// if (!file.exists()) {
			// continue;
			// }
			files[i] = attachUrl.substring(1);
		}
		String filePath = content.getRealPath(ATTACH_URL + PAPERS_ATTACH_URL);
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String zipFileUrl = CommUtils.getString(ATTACH_URL, PAPERS_ATTACH_URL,
				"֤��.zip");
		File destZipFile = new File(content.getRealPath(zipFileUrl));
		// ѹ���ļ���
		compressionFile(files, destZipFile, "֤��");
		return zipFileUrl;
	}

	/**
	 * ����ѹ����(�������)
	 * 
	 * @param files
	 * @param destZipFile
	 * @param title
	 *            ��ѹ��������ʾ���ļ��е�����
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
				String filePath = files[i];
				if (null == filePath || "".equals(filePath)) {
					continue;
				}
				// if (!new File(filePath).exists()) {
				// continue;
				// }
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

	public static void main(String[] args) {
		FileOutputStream fos = null;
		ZipOutputStream outputStream = null;
		try {
			String filesPath = "F:/EicSpace/pro_tlys/WebContent/attach/PAPER/";
			File dir = new File(filesPath);
			if (dir.isDirectory()) {
				File[] f = dir.listFiles();
				ZipEntry zipEntry = new ZipEntry("֤��");
				fos = new FileOutputStream("f:/֤��.zip");
				outputStream = new ZipOutputStream(fos);
				for (int i = 0; i < f.length; i++) {
					if (f[i].isDirectory()) {
						File[] files = f[i].listFiles();
						for (int j = 0; j < files.length; j++) {
							zipEntry = new ZipEntry(files[j].getAbsolutePath());
							outputStream.putNextEntry(zipEntry);
							System.out.println(files[j].getName());
							if (!files[j].exists()) {
								continue;
							}
							FileInputStream in = new FileInputStream(files[j]);
							int b;
							while ((b = in.read()) != -1) {
								outputStream.write(b);
							}
							in.close();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				fos.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public Long getNextSpePapersSeqId() {
		return spePapersDao.getNextSpePapersSeqId();
	}

	/**
	 * ���ڵ�ǰ����(spePaperstype)�䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ
	 * Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ�� ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("spePaperstype");
	}

	public String findCarnos(EquCar equCar) throws Exception {
		List<EquCar> list = equCarService.find(equCar);
		String carnos = CommUtils.getRestrictionsIn(list, "carno");
		return carnos;
	}
}
