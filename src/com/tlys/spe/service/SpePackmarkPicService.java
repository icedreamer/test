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
 * @author 冯彦明
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
		
		//这里是要防止在pic的编辑界面中，打开管理mark的界面，正好删除了本pic所在的mark，而又连带删除了本pic时，就会出现null
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
		
		//保存完pic，再保存类项号信息
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
		
		//先将此标志图相关的类项号信息全部删除，再插入
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
	 * 分页查找，返回的list直接注入到pageCtr对象中
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
	 * 为SpePackmarkPic列表中的每个对象附加上SpePackmarkPicno List
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
	//将SpePackmarkPicno取出，做成字典markpicid:picnoList
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
	 * 保存附件到目录，将文件地址保存进数据库
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
		// 删除原来的附件
		if (null != attachUrl && !"".equals(attachUrl)) {
			String existFilePath = context.getRealPath(attachUrl);
			File existFile = new File(existFilePath);
			if (existFile.exists()) {
				existFile.delete();
			}
		}
		// 保存新上传的附件
		//上传文件的相对URL
		String destUrlPath = CommUtils.getString(ATTACH_URL, CERT_ATTACH_URL, idStr, "/");
		//上传文件的物理路径
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
		
		// 删除记录之前先删除附件,以及危险货类项号（如果有的话)
		deleteSpePackmarkPicAttach(spePackmarkPic);
		spePackmarkPicnoDao.deleteByMarkPicid(spePackmarkPic.getMarkpicid());
		
		// 删除记录
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
			// 删除记录之前先删除附件,以及危险货类项号（如果有的话)
			deleteSpePackmarkPicAttach(spePackmarkPic);
			spePackmarkPicnoDao.deleteByMarkPicid(spePackmarkPic.getMarkpicid());
			// 删除记录
			spePackmarkPicDao.delete(spePackmarkPic);
		}
	}
	
	

	/**
	 * 删除附件
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
			//删除时要连同文件所在的文件夹一起删除
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
	 * 将一个标志下的所有图形删除
	 * 注意，要调用本类下的delete方法，而不是用dao中的删除方法
	 * 这样可以将连带的图形附件和危货类项号一并删除
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
