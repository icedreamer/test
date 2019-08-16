package com.tlys.spe.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.spe.model.SpePackmark;
import com.tlys.spe.service.SpePackmarkService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpePackmarkAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private String id;
	private SpePackmark spePackmark;
	private List<SpePackmark> markList;
	
	private String kind;

	
	@Autowired
	SpePackmarkService spePackmarkService;
	
	
	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		if(null==spePackmark){
			markList = spePackmarkService.findAll();
		}else{
			markList = spePackmarkService.find(spePackmark);	
		}
		return "list";
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception{
		spePackmark = spePackmarkService.getSpePackmark(id);
		spePackmarkService.delete(spePackmark);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}
	
	public String edit() throws Exception {
		if(null != id){
			spePackmark = spePackmarkService.getSpePackmark(id);
			isNew = false;
		}else{
			spePackmark =new SpePackmark();
			spePackmark.setKind(kind);
			isNew = true;
		}
		SpePackmark markSch = new SpePackmark();
		markSch.setKind(kind);
		markList = spePackmarkService.find(markSch);
		
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
			spePackmark.setCreatedtime(new Date());
			spePackmarkService.save(spePackmark);
		} else {
			// 修改记录
			spePackmark.setMarkid(id);
			spePackmarkService.update(spePackmark);
		}
		
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		kind = spePackmark.getKind();
		return edit();
	}
	
	
	//-------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SpePackmark getSpePackmark() {
		return spePackmark;
	}

	public void setSpePackmark(SpePackmark spePackmark) {
		this.spePackmark = spePackmark;
	}

	public List<SpePackmark> getMarkList() {
		return markList;
	}

	public void setMarkList(List<SpePackmark> markList) {
		this.markList = markList;
	}

	public PageCtr getPageCtr() {
		return pageCtr;
	}

	public void setPageCtr(PageCtr pageCtr) {
		this.pageCtr = pageCtr;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	
	

	
}
