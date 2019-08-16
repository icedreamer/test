/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.service.DicSinodepartmentService;

/**
 * @author ������
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicSinodepartmentAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicSinodepartment dicSinodepartment;
	private List<DicSinodepartment> dicSinodepartments;
	private String id;
	
	private String parentid;
	
	private List<DicProvince> dicProvinces;
	
	private boolean isNew;
	
	private String viewname;
	
	private Map<String,List<DicSinodepartment>> acDepMap;
	

	@Autowired
	DicSinodepartmentService dicSinodepartmentService;
	
	public String list(){
		if(null==dicSinodepartment){
			dicSinodepartments = dicSinodepartmentService.findAll();
		}else{
			dicSinodepartments = dicSinodepartmentService.find(dicSinodepartment);
		}
		
		return "list";
	} 
	
	@Action(value="dicSinodepartmentView",results=
		{@Result(name = "listview", location="/dic/dic-salecorp-input.jsp"),
			@Result(name = "listview_googsreceiver", location="/dic/dic-googsreceiver-input.jsp"),
			@Result(name = "listview_googssender", location="/dic/dic-googssender-input.jsp")
		}) 
	public String list4view() {
		//����ͼΪsalecorpʱ����Ĭ��ֵlistview,�������������ͼ�ַ���
		String result = "listview";
		// ����Ҫ��viewnameת����actionname,��Ϊ����receiver,sender�������߲�һ��
		if ("googsreceiver".equals(viewname) || "googssender".equals(viewname)) {
			viewname = viewname.substring(5);
			result += "_"+viewname;
		}
		acDepMap= dicSinodepartmentService.find4view(viewname);
		return result;
	}
	
	public String edit(){
		if(null!=id){
			detail();
			isNew = false;
		}else{
			dicSinodepartment=new DicSinodepartment();
			dicSinodepartment.setParentid(parentid);
			dicSinodepartment.setIsactive("1");
			dicSinodepartment.setIsreceiver("0");
			dicSinodepartment.setIssender("0");
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicSinodepartment = dicSinodepartmentService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicSinodepartment){
    		throw new Exception("����δ���յ���");
		} else {
			dicSinodepartmentService.save(dicSinodepartment,isNew);
			
			//��������msgҳ����ʾ������

			msg = new Msg(
					Msg.SUCCESS,
					"���/�༭�ɹ���",
					false,
					"alert('���/�༭�ɹ���');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
    	return MSG;
    } 
    
    public String delete() throws Exception{
    	if(null==id){
    		throw new Exception("����ȷ��Ҫɾ���Ķ���");
    	}else{
    		dicSinodepartmentService.delete(id);
			msg = new Msg(
					Msg.SUCCESS,
					"ɾ������ɹ���",
					false,
					"alert('ɾ������ɹ���');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
    	}
    	return MSG;
    }
    
    //=================================  
	/**
	 * @return the dicSinodepartment
	 */
	public DicSinodepartment getDicSinodepartment() {
		return dicSinodepartment;
	}

	/**
	 * @param dicSinodepartment the dicSinodepartment to set
	 */
	public void setDicSinodepartment(DicSinodepartment dicSinodepartment) {
		this.dicSinodepartment = dicSinodepartment;
	}

	/**
	 * @return the dicSinodepartments
	 */
	public List<DicSinodepartment> getDicSinodepartments() {
		return dicSinodepartments;
	}

	/**
	 * @param dicSinodepartments the dicSinodepartments to set
	 */
	public void setDicSinodepartments(List<DicSinodepartment> dicSinodepartments) {
		this.dicSinodepartments = dicSinodepartments;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the dicProvinces
	 */
	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	/**
	 * @param dicProvinces the dicProvinces to set
	 */
	public void setDicProvinces(List<DicProvince> dicProvinces) {
		this.dicProvinces = dicProvinces;
	}

	/**
	 * @return the parentid
	 */
	public String getParentid() {
		return parentid;
	}

	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	/**
	 * ��������msgҳ���script
	 * ��Ϊ��ҳ�����lhgdialog���ɵĵ����У���˿�����Ը���
	 * ���Է��ڴ˴�����
	 * @return
	 */
	private String buildScript(){
		String script = "frameElement.lhgDG.SetCancelBtn('�ر�',function(){frameElement.lhgDG.curWin.location.reload();});";
		script += "frameElement.lhgDG.SetXbtn(function(){frameElement.lhgDG.curWin.location.reload();},false);";
		return script;
	}

	/**
	 * @return the viewname
	 */
	public String getViewname() {
		return viewname;
	}

	/**
	 * @param viewname the viewname to set
	 */
	public void setViewname(String viewname) {
		this.viewname = viewname;
	}

	/**
	 * @return the acDepMap
	 */
	public Map<String, List<DicSinodepartment>> getAcDepMap() {
		return acDepMap;
	}

	/**
	 * @param acDepMap the acDepMap to set
	 */
	public void setAcDepMap(Map<String, List<DicSinodepartment>> acDepMap) {
		this.acDepMap = acDepMap;
	}
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
