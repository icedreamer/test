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
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicTransporter;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicTransporterService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicTransporterAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicTransporter dicTransporter;
	private List<DicTransporter> dicTransporters;
	private String id;
	
	private String provids;
	


	private List<DicProvince> dicProvinces;
	
	private boolean isNew;
	

	@Autowired
	DicTransporterService dicTransporterService;
	
	@Autowired
	DicProvinceService dicProvinceService;
	
	public String list() throws Exception{
		if(null==dicTransporter){
			if(null==provids){
				dicTransporters = dicTransporterService.findAll();
			}else{
				dicTransporters = dicTransporterService.findByProvids(provids);
			}
		}else{
			dicTransporters = dicTransporterService.find(dicTransporter);
		}
		
		return "list";
	} 
	
	public String edit(){
		dicProvinces = dicProvinceService.findAll();
		if(null!=id){
			detail();
			isNew = false;
		}else{
			dicTransporter=new DicTransporter();
			dicTransporter.setIsactive("1");
			dicTransporter.setIsbelongsino("1");
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicTransporter = dicTransporterService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicTransporter){
    		throw new Exception("数据未接收到！");
		} else {
			//System.out.println("DicTransporterAction.save->dicTransporter=="+dicTransporter);
			dicTransporterService.save(dicTransporter,isNew);
			
			//以下用于msg页面显示的设置
			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
    	return MSG;
    } 
    
    public String delete() throws Exception{
    	if(null==id){
    		throw new Exception("不能确定要删除的对象！");
    	}else{
    		dicTransporterService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"删除对象成功！",buildScript());
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
		dicTransporterService.expExcel(dicTransporters,response);
		
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
    
    //=================================  
	/**
	 * @return the dicTransporter
	 */
	public DicTransporter getDicTransporter() {
		return dicTransporter;
	}

	/**
	 * @param dicTransporter the dicTransporter to set
	 */
	public void setDicTransporter(DicTransporter dicTransporter) {
		this.dicTransporter = dicTransporter;
	}

	/**
	 * @return the dicTransporters
	 */
	public List<DicTransporter> getDicTransporters() {
		return dicTransporters;
	}

	/**
	 * @param dicTransporters the dicTransporters to set
	 */
	public void setDicTransporters(List<DicTransporter> dicTransporters) {
		this.dicTransporters = dicTransporters;
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
		String script = "frameElement.lhgDG.SetCancelBtn('关闭',function(){frameElement.lhgDG.curWin.location.reload();});";
		script += "frameElement.lhgDG.SetXbtn(function(){frameElement.lhgDG.curWin.location.reload();},false);";
		return script;
	}
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

	public String getProvids() {
		return provids;
	}

	public void setProvids(String provids) {
		this.provids = provids;
	}

}
