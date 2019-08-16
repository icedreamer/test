/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.service.SysMenuService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicSinocorpAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicSinocorp dicSinocorp;
	private List<DicSinocorp> dicSinocorps;
	private String id;
	
	private String curUserid;
	
	private String provids;
	
	private String corptype;
	
	private List<SysNavimenu> sysNavimenus;
	
	
	
	

	private List<DicProvince> dicProvinces;
	private List<DicSinodepartment> departments;
	private List<DicAreacorp> dicAreacorps;
	
	/**
	 * 当前sinocorp为视图处理页面作列表时用到
	 * 用以标志此列表是为哪个视图来用的
	 * 详见list4view
	 */
	private String viewname;
	
	private boolean isNew;
	

	@Autowired
	DicSinocorpService dicSinocorpService;
	
	@Autowired
	DicProvinceService dicProvinceService;
	
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	SysMenuService sysMenuService;
	
	public String list() throws Exception{
		if(null==dicSinocorp){
			dicSinocorp = new DicSinocorp();
		}
		dicSinocorp.setProvids(provids);
		/*if(null==provids){
				dicSinocorps = dicSinocorpService.findAll();
			}else{
				dicSinocorps = dicSinocorpService.findByProvids(provids);
			}*/
			
		
		dicSinocorps = dicSinocorpService.find(dicSinocorp);
		
		corptype = "1";
		return "list";
	} 
	
	public String listHead() throws Exception{
		
		if(null==dicSinocorp){
			dicSinocorp = new DicSinocorp();
		}
		dicSinocorp.setProvids(provids);
		
		
		/*if(null==dicSinocorp){
			if(null==provids){
				dicSinocorps = dicSinocorpService.findAllHead();
			}else{
				dicSinocorps = dicSinocorpService.findByProvidsHead(provids);
			}*/
			
	
		dicSinocorps = dicSinocorpService.findHead(dicSinocorp);
	
		corptype = "0";
		return "list";
	} 
	
	/**
	 * 导出excel
	 * @return
	 */
	public String expExcel() throws Exception{
		if(null!=corptype && "0".equals(corptype)){
			listHead();
		}else{
			list();//得到List:dicSinocorps
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		dicSinocorpService.expExcel(dicSinocorps,response);
		
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	
	/**
	 * 为生成视图的列表
	 * @return
	 */
	@Action(value="dicSinocorpView",results=
		{@Result(name = "listview", location="/dic/dic-salecorp-input.jsp"),
			@Result(name = "listview_googsreceiver", location="/dic/dic-googsreceiver-input.jsp"),
			@Result(name = "listview_googssender", location="/dic/dic-googssender-input.jsp"),
			@Result(name = "listview_usercorpauth", location="/dic/dic-sinocorp-listpop.jsp")
		}) 
	public String list4view() {
		// 当视图为salecorp时，用默认值listview,其他的则加上视图字符串
		String result = "listview";
		// 这里要将viewname转换成actionname,因为对于receiver,sender，这两者不一样
		if ("googsreceiver".equals(viewname) || "googssender".equals(viewname)) {
			viewname = viewname.substring(5);
			result += "_" + viewname;
		}
		
		if("usercorpauth".equals(viewname)){
			result += "_"+ viewname;
			//System.out.println("DicSinocorpAction.list4view->curUserid=="+curUserid);
			dicSinocorps = dicSinocorpService.find4usercorpauth(curUserid);
			sysNavimenus = sysMenuService.findSysNavimenuByPmenuId(0);
		}else{
			dicSinocorps = dicSinocorpService.find4view(viewname);
		}
		return result;
	}

	public String edit() throws Exception{
		dicProvinces = dicProvinceService.findAll();
		dicAreacorps = dicAreacorpService.findAll();
		if(null!=id){
			detail();
			isNew = false;
		}else{
			//dicSinocorp=null;
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicSinocorp = dicSinocorpService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicSinocorp){
    		throw new Exception("数据未接收到！");
		} else {
			//System.out.println("DicSinocorpAction.save->dicSinocorp.getAddress()=="+dicSinocorp.getAddress());
			dicSinocorpService.save(dicSinocorp,isNew);
			
			//以下用于msg页面显示的设置
			
			msg = new Msg(Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[]{"curTitle",""});
		}
    	return MSG;
    } 
    
    public String delete() throws Exception{
    	if(null==id){
    		throw new Exception("不能确定要删除的对象！");
    	}else{
    		dicSinocorpService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"删除对象成功！");
    	}
    	return MSG;
    }
    
    /**
     *得到区域公司下属单位以及下属部门列表
     */
    public String suborgs(){
    	departments = dicSinocorpService.findDepartments(id);
    	return "suborgs";
    } 
    
   
    
   
    
    
    //=================================  
	/**
	 * @return the dicSinocorp
	 */
	public DicSinocorp getDicSinocorp() {
		return dicSinocorp;
	}

	/**
	 * @param dicSinocorp the dicSinocorp to set
	 */
	public void setDicSinocorp(DicSinocorp dicSinocorp) {
		this.dicSinocorp = dicSinocorp;
	}

	/**
	 * @return the dicSinocorps
	 */
	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	/**
	 * @param dicSinocorps the dicSinocorps to set
	 */
	public void setDicSinocorps(List<DicSinocorp> dicSinocorps) {
		this.dicSinocorps = dicSinocorps;
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
	 * @return the departments
	 */
	public List<DicSinodepartment> getDepartments() {
		return departments;
	}

	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(List<DicSinodepartment> departments) {
		this.departments = departments;
	}

	/**
	 * @return the dicAreacorps
	 */
	public List<DicAreacorp> getDicAreacorps() {
		return dicAreacorps;
	}

	/**
	 * @param dicAreacorps the dicAreacorps to set
	 */
	public void setDicAreacorps(List<DicAreacorp> dicAreacorps) {
		this.dicAreacorps = dicAreacorps;
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
	
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

	public String getCurUserid() {
		return curUserid;
	}

	public void setCurUserid(String curUserid) {
		this.curUserid = curUserid;
	}

	public String getProvids() {
		return provids;
	}

	public void setProvids(String provids) {
		this.provids = provids;
	}

	public String getCorptype() {
		return corptype;
	}

	public void setCorptype(String corptype) {
		this.corptype = corptype;
	}

	public List<SysNavimenu> getSysNavimenus() {
		return sysNavimenus;
	}

	public void setSysNavimenus(List<SysNavimenu> sysNavimenus) {
		this.sysNavimenus = sysNavimenus;
	}

}
