package com.tlys.sys.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.sys.model.SysContact;
import com.tlys.sys.model.SysContactService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/sys")
public class SysContactAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460751129127897507L;

	private List<SysContact> contactList;
	private List<SysContact> SinocorpList;
	private SysContact sysContact;
	private String filepath;
	private String filename;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String corpid;
	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreaMap;

	@Autowired
	DicMap dicMap;
	@Autowired
	SysContactService sysContactService;

	public String contact() throws Exception {
		Map<String, DicAreacorp> areacorpMap = dicMap.getAreaMap();
		Set<String> areacorpSet = areacorpMap.keySet();
		contactList = new ArrayList<SysContact>();
		for (String areaid : areacorpSet) {
			DicAreacorp dicAreacorp = areacorpMap.get(areaid);
			SysContact sysContact = new SysContact();
			sysContact.setAreaid(areaid);
			sysContact.setAreaname(dicAreacorp.getShortname());
			sysContact.setId(areaid);
			sysContact.setText(dicAreacorp.getShortname());
			sysContact.setState("closed");
			contactList.add(sysContact);
		}
		corps();
		return "contact";
	}

	public void corps() throws Exception {
		Map<String, DicSinocorp> dicSinocorpMap = dicMap.getCorpMap();
		Set<String> corpIdSet = dicSinocorpMap.keySet();
		if (null != corpIdSet && !corpIdSet.isEmpty()) {
			SinocorpList = new ArrayList<SysContact>();
			for (String corpid : corpIdSet) {
				DicSinocorp dicSinocorp = dicSinocorpMap.get(corpid);
				if(dicSinocorp != null && dicSinocorp.getShortname().indexOf("自营") != -1){
					continue;
				}
				SysContact sysContact = new SysContact();
				sysContact.setId(corpid);
				sysContact.setAreaname(dicSinocorp.getShortname());
				sysContact.setText(dicSinocorp.getShortname());
				SinocorpList.add(sysContact);
			}
		}
	}

	public String loadtab() throws Exception {
		dicSinocorpMap = dicMap.getCorpAllMap();
		dicAreaMap = dicMap.getAreaMap();
		return "loadtab";
	}
	
	public String down() throws Exception {
		
		filepath = "/download/contact/"+corpid+".xls";
		//filename = corpid;
		
		return "download";
	}

	public String upload() throws Exception {
		String suffix = CommUtils.getSuffix(uploadFileName);
		sysContactService.upload(corpid, upload, suffix);

		msg = new Msg(Msg.SUCCESS, "上传成功！");
		return MSG;
	}

	public List<SysContact> getContactList() {
		return contactList;
	}

	public void setContactList(List<SysContact> contactList) {
		this.contactList = contactList;
	}

	public SysContact getSysContact() {
		return sysContact;
	}

	public void setSysContact(SysContact sysContact) {
		this.sysContact = sysContact;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public Map<String, DicAreacorp> getDicAreaMap() {
		return dicAreaMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public void setDicAreaMap(Map<String, DicAreacorp> dicAreaMap) {
		this.dicAreaMap = dicAreaMap;
	}

	public String getFilepath() {
		return filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<SysContact> getSinocorpList() {
		return SinocorpList;
	}

	public void setSinocorpList(List<SysContact> sinocorpList) {
		SinocorpList = sinocorpList;
	}

}
