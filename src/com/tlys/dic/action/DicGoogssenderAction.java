/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicGoogssender;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicGoogssenderService;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicGoogssenderAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicGoogssender dicGoogssender;
	private List<DicGoogssender> dicGoogssenders;
	private String id;
	
	private List<DicProvince> dicProvinces;
	
	private boolean isNew;
	
	/**
	 * 以下三个属性标志视图增加页面的复选框值（input页面)
	 */
	private String corpsrc;
	private String depsrc;
	private String customersrc;
	
	private String curname;
	

	@Autowired
	DicGoogssenderService dicGoogssenderService;
	
	@Autowired
	DicProvinceService dicProvinceService;
	
	public String list(){
		if(null==dicGoogssender){
			dicGoogssenders = dicGoogssenderService.findAll();
		}else{
			dicGoogssenders = dicGoogssenderService.find(dicGoogssender);
		}
		
		return "list";
	} 
	
	/**
	 * 此方法打开的页面是各公司、单位的复选框列表
	 * 各公司、复选框的数据是从各自的action中拿到，包含到页面之中的 
	 * @return
	 */
	public String edit(){
		if(null!=id){
			detail();
			isNew = false;
		}else{
			dicGoogssender=new DicGoogssender();
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicGoogssender = dicGoogssenderService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicGoogssender){
    		throw new Exception("数据未接收到！");
		} else {
			//System.out.println("DicGoogssenderAction.save->dicGoogssender=="+dicGoogssender);
			//dicGoogssenderService.save(dicGoogssender,isNew);
			
			//以下用于msg页面显示的设置
			msg = new Msg(Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					buildScript(),
					null);
		}
    	return MSG;
    } 
    
    public String delete() throws Exception{
    	if(null==id){
    		throw new Exception("不能确定要删除的对象！");
    	}else{
    		dicGoogssenderService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"删除对象成功！",buildScript());
    	}
    	return MSG;
    }
    
    
    public String addorg() throws Exception{
    	 if(null==corpsrc && null==depsrc && null==customersrc){
        		msg = new Msg(Msg.SUCCESS,
    					"没有选中要加入的单位");
        	}else{
        		if(null!=corpsrc){
        			dicGoogssenderService.updateSendersFromSinocorp(corpsrc);
        		}
        		if(null!=depsrc){
        			dicGoogssenderService.updateSendersFromSinodepartment(depsrc);
        		}
        		if(null!=customersrc){
        			dicGoogssenderService.updateSendersFromCustomer(customersrc);
        		}
        		
        		msg = new Msg(Msg.SUCCESS,
    					"操作成功执行！",buildScript());
        	}
        	return MSG;
        }
    
    /**
   	 * 导出excel
   	 * @return
   	 */
   	public String expExcel() throws Exception{
   		list();//得到List
   		HttpServletResponse response = ServletActionContext.getResponse();
   		dicGoogssenderService.expExcel(dicGoogssenders,response);
   		
   		msg = new Msg(Msg.SUCCESS);
   		return MSG;
   	}
    
    //=================================  
	/**
	 * @return the dicGoogssender
	 */
	public DicGoogssender getDicGoogssender() {
		return dicGoogssender;
	}

	/**
	 * @param dicGoogssender the dicGoogssender to set
	 */
	public void setDicGoogssender(DicGoogssender dicGoogssender) {
		this.dicGoogssender = dicGoogssender;
	}

	/**
	 * @return the dicGoogssenders
	 */
	public List<DicGoogssender> getDicGoogssenders() {
		return dicGoogssenders;
	}

	/**
	 * @param dicGoogssenders the dicGoogssenders to set
	 */
	public void setDicGoogssenders(List<DicGoogssender> dicGoogssenders) {
		this.dicGoogssenders = dicGoogssenders;
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
	 * 生成用于msg页面的script
	 * 因为此页面放在lhgdialog生成的弹窗中，因此控制相对复杂
	 * 所以放在此处生成
	 * @return
	 */
	private String buildScript(){
		String script = "dg.curWin.location.reload();";
		return script;
	}

	/**
	 * @return the curname
	 */
	public String getCurname() {
		return curname;
	}

	/**
	 * @param curname the curname to set
	 */
	public void setCurname(String curname) {
		this.curname = curname;
	}

	/**
	 * @return the corpsrc
	 */
	public String getCorpsrc() {
		return corpsrc;
	}

	/**
	 * @param corpsrc the corpsrc to set
	 */
	public void setCorpsrc(String corpsrc) {
		this.corpsrc = corpsrc;
	}

	/**
	 * @return the depsrc
	 */
	public String getDepsrc() {
		return depsrc;
	}

	/**
	 * @param depsrc the depsrc to set
	 */
	public void setDepsrc(String depsrc) {
		this.depsrc = depsrc;
	}

	/**
	 * @return the customersrc
	 */
	public String getCustomersrc() {
		return customersrc;
	}

	/**
	 * @param customersrc the customersrc to set
	 */
	public void setCustomersrc(String customersrc) {
		this.customersrc = customersrc;
	}
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
