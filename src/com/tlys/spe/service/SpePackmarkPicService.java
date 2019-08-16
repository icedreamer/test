package com.tlys.spe.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.dao.SpePackmarkPicDao;
import com.tlys.spe.dao.SpePackmarkPicnoDao;
import com.tlys.spe.model.SpePackmarkPic;
import com.tlys.spe.model.SpePackmarkPicno;

/**
 * @author ������
 * 
 */

@Service
public class SpePackmarkPicService extends _GenericService {
	@Autowired
	SpePackmarkPicDao spePackmarkPicDao;
	
	@Autowired
	SpePackmarkPicnoDao spePackmarkPicnoDao;
	
	@Autowired
	DicMap dicMap;
	
	private final static String CERT_ATTACH_URL = "PACKMARKPIC/";
	private final static String CERT_ATTACH_ZIP_URL = "PACKMARKPIC/ZIP/";

	public SpePackmarkPic getSpePackmarkPic(String id) {
		SpePackmarkPic markPic = spePackmarkPicDao.load(id);
		
		//������Ҫ��ֹ��pic�ı༭�����У��򿪹���mark�Ľ��棬����ɾ���˱�pic���ڵ�mark����������ɾ���˱�picʱ���ͻ����null
		if (null != markPic) {
			Map<String, List<SpePackmarkPicno>> picnoMap = getPicnoMap();
			List<SpePackmarkPicno> picnoList = picnoMap.get(markPic
					.getMarkpicid());
			markPic.setPicnoList(picnoList);
		}
		return markPic;
	}

	public void save(SpePackmarkPic spePackmarkPic,File upload,String uploadFileName) throws Exception{
		if(null==spePackmarkPic.getPath()){
			spePackmarkPic.setPath("");
		}
		if(null==spePackmarkPic.getMarkpicid() || "".equals(spePackmarkPic.getMarkpicid())){
			spePackmarkPic.setMarkpicid(CommUtils.getSeqStr(spePackmarkPicDao.getSeq(), 6));
		}
		
		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(spePackmarkPic, upload, uploadFileName);
		}
		spePackmarkPicDao.save(spePackmarkPic);
		
		//������pic���ٱ����������Ϣ
		List noList = spePackmarkPic.getPicnoList();
		if (null != noList) {
			for (Iterator iter = noList.iterator(); iter.hasNext();) {
				SpePackmarkPicno picno = (SpePackmarkPicno) iter.next();
				picno.setMarkpicid(spePackmarkPic.getMarkpicid());
				spePackmarkPicnoDao.save(picno);
			}
		}
	}


	public void update(SpePackmarkPic spePackmarkPic,File upload,String uploadFileName) {
		if (null != upload && null != uploadFileName && !"".equals(uploadFileName)) {
			updateFileInfo(spePackmarkPic, upload, uploadFileName);
		}
		spePackmarkPicDao.updateEntity(spePackmarkPic, spePackmarkPic.getMarkpicid());
		
		//�Ƚ��˱�־ͼ��ص��������Ϣȫ��ɾ�����ٲ���
		spePackmarkPicnoDao.deleteByMarkPicid(spePackmarkPic.getMarkpicid());
		
		List noList = spePackmarkPic.getPicnoList();
		if (null != noList) {
			for (Iterator iter = noList.iterator(); iter.hasNext();) {
				SpePackmarkPicno picno = (SpePackmarkPicno) iter.next();
				picno.setMarkpicid(spePackmarkPic.getMarkpicid());
				spePackmarkPicnoDao.save(picno);
			}
		}
		
	}



	/**
	 * ��ҳ���ң����ص�listֱ��ע�뵽pageCtr������
	 * @param spePackmarkPic
	 * @param pageCtr
	 */
	public void findSpePackmarkPic(SpePackmarkPic spePackmarkPic, PageCtr pageCtr) throws Exception{
		int totalRecord = getSpePackmarkPicCount(spePackmarkPic);
		pageCtr.setTotalRecord(totalRecord);
		spePackmarkPicDao.findSpePackmarkPic(spePackmarkPic, pageCtr);
		dicMap.bdPackmark(pageCtr.getRecords());
		if(null != spePackmarkPic.getSpePackmark() && 
				null!=spePackmarkPic.getSpePackmark().getKind() &&
				spePackmarkPic.getSpePackmark().getKind().equals("02")){
			bdPicnoList(pageCtr.getRecords());
		}
	}
	
	/**
	 * ΪSpePackmarkPic�б��е�ÿ�����󸽼���SpePackmarkPicno List
	 */
	private void bdPicnoList(List picList){
		Map<String,List<SpePackmarkPicno>> picnoMap = getPicnoMap();
		
		for (Iterator iter = picList.iterator(); iter.hasNext();) {
			SpePackmarkPic pic = (SpePackmarkPic) iter.next();
			List<SpePackmarkPicno> curPicnoList = picnoMap.get(pic.getMarkpicid());
			if(null != curPicnoList){
				pic.setPicnoList(curPicnoList);
			}
		}
	}
	//��SpePackmarkPicnoȡ���������ֵ�markpicid:picnoList
	private Map getPicnoMap(){
		List<SpePackmarkPicno> picnoList = spePackmarkPicnoDao.findAll();
		Map<String,List<SpePackmarkPicno>> picnoMap = new HashMap();
		for (Iterator it = picnoList.iterator(); it.hasNext();) {
			SpePackmarkPicno picno = (SpePackmarkPicno) it.next();
			String picid = picno.getMarkpicid();
			List<SpePackmarkPicno> picnoListNew= picnoMap.get(picid);
			if(null==picnoListNew){
				picnoListNew = new ArrayList();
				picnoMap.put(picid, picnoListNew);
			}
			picnoListNew.add(picno);
		}
		return picnoMap;
	}

	public List<SpePackmarkPic> findAll() {
		return spePackmarkPicDao.findAll();
	}
	
	public List<SpePackmarkPic> find(SpePackmarkPic spePackmarkPic) {
		return spePackmarkPicDao.find(spePackmarkPic);
	}

	/**
	 * ���渽����Ŀ¼�����ļ���ַ��������ݿ�
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public void updateFileInfo(SpePackmarkPic spePackmarkPic, File srcFile, String destFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		String idStr = String.valueOf(spePackmarkPic.getMarkpicid());
		String attachUrl = spePackmarkPic.getPath();
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
		//�ϴ��ļ������URL
		String destUrlPath = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, idStr, "/");
		//�ϴ��ļ�������·��
		String destRealPath = context.getRealPath(destUrlPath);
		File file = new File(destRealPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String saveFileName = idStr +"."+CommUtils.getExt(destFileName);
		
		String destUrlFile = destUrlPath + saveFileName;
		String destRealFile = destRealPath + "/" + saveFileName;
		File destFile = new File(destRealFile);
		
		if (copyFile(srcFile, destFile)) {
			spePackmarkPic.setPath(destUrlFile);
			spePackmarkPic.setFilename(destFileName);
		}
	}

	

	public void delete(SpePackmarkPic spePackmarkPic) throws Exception{
		if (null == spePackmarkPic) {
			return;
		}
		
		// ɾ����¼֮ǰ��ɾ������,�Լ�Σ�ջ�����ţ�����еĻ�)
		deleteSpePackmarkPicAttach(spePackmarkPic);
		spePackmarkPicnoDao.deleteByMarkPicid(spePackmarkPic.getMarkpicid());
		
		// ɾ����¼
		spePackmarkPicDao.delete(spePackmarkPic);
	}
	
	public void deleteByIds(String ids) throws Exception{
		List<SpePackmarkPic> pp = null;
		if (null == ids) {
			return;
		}else{
			pp = spePackmarkPicDao.findAll(" where markpicid in ("+ids+")");
		}
		for (Iterator iter = pp.iterator(); iter.hasNext();) {
			SpePackmarkPic spePackmarkPic = (SpePackmarkPic) iter.next();
			// ɾ����¼֮ǰ��ɾ������,�Լ�Σ�ջ�����ţ�����еĻ�)
			deleteSpePackmarkPicAttach(spePackmarkPic);
			spePackmarkPicnoDao.deleteByMarkPicid(spePackmarkPic.getMarkpicid());
			// ɾ����¼
			spePackmarkPicDao.delete(spePackmarkPic);
		}
	}
	
	

	/**
	 * ɾ������
	 * 
	 * @param spePackmarkPic
	 */
	private void deleteSpePackmarkPicAttach(SpePackmarkPic spePackmarkPic) throws Exception{
		if (null == spePackmarkPic) {
			return;
		}
		String attachUrl = spePackmarkPic.getPath();
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath(attachUrl);
		File file = new File(filePath);
		if (file.exists()) {
			//ɾ��ʱҪ��ͬ�ļ����ڵ��ļ���һ��ɾ��
			File pDir = file.getParentFile();
			if(pDir.exists() && pDir.isDirectory()){
				FileCommUtil.delete(pDir);
			}else{
				FileCommUtil.delete(file);
			}
		}
	}



	public int getSpePackmarkPicCount(final SpePackmarkPic spePackmarkPic) {
		int count = spePackmarkPicDao.getSpePackmarkPicCount(spePackmarkPic);
		return count;
	}

	/**
	 * ��һ����־�µ�����ͼ��ɾ��
	 * ע�⣬Ҫ���ñ����µ�delete��������������dao�е�ɾ������
	 * �������Խ�������ͼ�θ�����Σ�������һ��ɾ��
	 * @param markid
	 * @throws Exception
	 */
	public void deleteByMarkid(String markid) throws Exception{
		SpePackmarkPic picSch = new SpePackmarkPic();
		picSch.setMarkid(markid);
		List<SpePackmarkPic> picList = find(picSch);
		for (SpePackmarkPic spePackmarkPic : picList) {
			delete(spePackmarkPic);
		}
	}
}
