/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicProvinceAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicProvince dicProvince;
	private List<DicProvince> dicProvinces;
	private String id;
	
	private String parentid;
	
	private boolean isNew;
	

	@Autowired
	DicProvinceService dicProvinceService;
	
	public String list(){
		if(null==dicProvince){
			dicProvinces = dicProvinceService.findAll();
		}else{
			dicProvinces = dicProvinceService.find(dicProvince);
		}
		
		return "left";
	} 
	
	public String listpop(){
		list();
		return "listpop";
	} 
	
	public String edit(){
		if(null!=id){
			detail();
			isNew = false;
		}else{
			dicProvince=new DicProvince();
			//dicProvince.setParentid(parentid);
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicProvince = dicProvinceService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicProvince){
    		throw new Exception("数据未接收到！");
		} else {
			dicProvinceService.save(dicProvince,isNew);
			
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
    		dicProvinceService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"删除对象成功！",buildScript());
    	}
    	return MSG;
    }
    
    //=================================  
	/**
	 * @return the dicProvince
	 */
	public DicProvince getDicProvince() {
		return dicProvince;
	}

	/**
	 * @param dicProvince the dicProvince to set
	 */
	public void setDicProvince(DicProvince dicProvince) {
		this.dicProvince = dicProvince;
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
	 * 生成用于msg页面的script
	 * 因为此页面放在lhgdialog生成的弹窗中，因此控制相对复杂
	 * 所以放在此处生成
	 * @return
	 */
	private String buildScript(){
		String script = "frameElement.lhgDG.SetCancelBtn('关闭',function(){frameElement.lhgDG.curWin.location.reload();});";
		script += "frameElement.lhgDG.SetXbtn(function(){frameElement.lhgDG.curWin.location.reload();},false);";
		return script;
	}

}
