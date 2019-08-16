package com.tlys.spe.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.spe.model.SpePackmarkPicno;
import com.tlys.spe.service.SpePackmarkPicnoService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpePackmarkPicnoAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private Long id;
	private SpePackmarkPicno spePackmarkPicno;
    private List<SpePackmarkPicno> picnoList;
	
	@Autowired
	SpePackmarkPicnoService spePackmarkPicnoService;
	
	/**
	 * �б�
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		picnoList = spePackmarkPicnoService.findSpePackmarkPicno(spePackmarkPicno);
		return "list";
	}
	
	/**
	 * ɾ��
	 * 
	 * @return
	 */
	public String delete() throws Exception{
		
		spePackmarkPicno = spePackmarkPicnoService.getSpePackmarkPicno(id);
		spePackmarkPicnoService.delete(spePackmarkPicno);
		msg = new Msg(Msg.SUCCESS, "ɾ���ɹ�");
		return MSG;
	}
	
	public String edit() throws Exception {
		if(null != id){
			spePackmarkPicno = spePackmarkPicnoService.getSpePackmarkPicno(id);
			isNew = false;
		}else{
			spePackmarkPicno =new SpePackmarkPicno();
			isNew = true;
		}
		return "input";
	}

	/**
	 * ����
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// ������¼
			
			spePackmarkPicnoService.save(spePackmarkPicno);
		} else {
			// �޸ļ�¼
			spePackmarkPicno.setId(id);
			spePackmarkPicnoService.update(spePackmarkPicno);
		}
		
		msg = new Msg(Msg.SUCCESS, "�����ɹ�!");
		return edit();
	}
	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_MANAGE");
	}

	
	
	//-------------------------------------------------

	
	public void setSpePackmarkPicno(SpePackmarkPicno spePackmarkPicno) {
		this.spePackmarkPicno = spePackmarkPicno;
	}

	
	public Long getId() {
		return id;
	}


	public SpePackmarkPicno getSpePackmarkPicno() {
		return spePackmarkPicno;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SpePackmarkPicno> getPicnoList() {
		return picnoList;
	}

	public void setPicnoList(List<SpePackmarkPicno> picnoList) {
		this.picnoList = picnoList;
	}
}
