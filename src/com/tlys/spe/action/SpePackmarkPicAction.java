package com.tlys.spe.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpePackmark;
import com.tlys.spe.model.SpePackmarkPic;
import com.tlys.spe.service.SpePackmarkPicService;
import com.tlys.spe.service.SpePackmarkService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpePackmarkPicAction extends _BaseAction {
	protected final Log log = LogFactory.getLog(this.getClass());
	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	
	private String ids;
	
	private SpePackmarkPic spePackmarkPic;
	private PageCtr pageCtr;
	
	private List<SpePackmark> markList;
	
	private String markkind;
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	
	@Autowired
	SpePackmarkPicService spePackmarkPicService;
	
	@Autowired
	SpePackmarkService spePackmarkService;
	
	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/spe/spe-packmark-pic!list.action";

		if(null==pageCtr){
			pageCtr = new PageCtr();
			//当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			//在生成页码跳转html时，应带上此参数
			if(null != spePackmarkPic){
				String schObjKey = "spePackmarkPic_"+new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, spePackmarkPic);
			}
		}else{
			//当在前台点页码进入时，应同时传来schObjKey
			if(null!=pageCtr.getSchObjKey()){
				spePackmarkPic = (SpePackmarkPic)getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(4);
		
		spePackmarkPicService.findSpePackmarkPic(spePackmarkPic,pageCtr);
		
		
		return "list";
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception{
		
		//spePackmarkPic = spePackmarkPicService.getSpePackmarkPic(id);
		//spePackmarkPicService.delete(spePackmarkPic);
		System.out.println("SpePackmarkPicAction.delete->ids=="+ids);
		spePackmarkPicService.deleteByIds(ids);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}
	
	public String edit() throws Exception {
		if(null!=markkind){
			SpePackmark markSch = new SpePackmark();
			markSch.setKind(markkind);
			markList = spePackmarkService.find(markSch);
		}else{
			markList = spePackmarkService.findAll();
		}
		if(null != id){
			spePackmarkPic = spePackmarkPicService.getSpePackmarkPic(id);
			////在pic的编辑界面中，打开管理mark的界面，正好删除了本pic所在的mark，
			//而又连带删除了本pic时，关闭mark窗口时，会回来刷新pic编辑界面，就会出现null
			//这时，就应视为新建
			if(null==spePackmarkPic){
				id = null;
				spePackmarkPic =new SpePackmarkPic();
				isNew = true;
			}
			isNew = false;
		}else{
			spePackmarkPic =new SpePackmarkPic();
			isNew = true;
		}
		return "input";
	}


	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// 新增记录
			spePackmarkPicService.save(spePackmarkPic,upload,uploadFileName);
		} else {
			// 修改记录
			spePackmarkPicService.update(spePackmarkPic,upload,uploadFileName);
			id = spePackmarkPic.getMarkpicid();
		}
		
		
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}
	
	public String searchpage() throws Exception {
		return "searchpage";
	}
	

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_LAW");
	}
	
	//-------------------------------------------------

	
	public void setSpePackmarkPic(SpePackmarkPic spePackmarkPic) {
		this.spePackmarkPic = spePackmarkPic;
	}

	

	
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public PageCtr getPageCtr() {
		return pageCtr;
	}


	public void setPageCtr(PageCtr pageCtr) {
		this.pageCtr = pageCtr;
	}


	public String getId() {
		return id;
	}


	public SpePackmarkPic getSpePackmarkPic() {
		return spePackmarkPic;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setId(String id) {
		this.id = id;
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}

	public List<SpePackmark> getMarkList() {
		return markList;
	}

	public void setMarkList(List<SpePackmark> markList) {
		this.markList = markList;
	}

	public String getMarkkind() {
		return markkind;
	}

	public void setMarkkind(String markkind) {
		this.markkind = markkind;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
