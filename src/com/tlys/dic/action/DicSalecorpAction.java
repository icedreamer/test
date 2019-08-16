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
import com.tlys.dic.model.DicSalecorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSalecorpService;
import com.tlys.dic.service.DicSinocorpService;

/**
 * @author 冯彦明
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicSalecorpAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicSalecorp dicSalecorp;
	private List<DicSalecorp> dicSalecorps;
	private String id;
	
	private String corpsrc;
	private String depsrc;
	
	private String curname;
	
	private List<DicSinocorp> dicSinocorps;
	

	@Autowired
	DicSalecorpService dicSalecorpService;
	
	@Autowired
	DicSinocorpService dicSinocorpService;
	
	
	public String list(){
		if(null==dicSalecorp){
			dicSalecorps = dicSalecorpService.findAll();
		}else{
			dicSalecorps = dicSalecorpService.find(dicSalecorp);
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
		}else{
			dicSalecorp=new DicSalecorp();
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicSalecorp = dicSalecorpService.load(id);
    	return "detail";
    } 
    
    public String delete() throws Exception{
    	
    	if(null==id){
    		throw new Exception("不能确定要删除的对象！");
    	}else{
    		dicSalecorpService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"删除对象成功！",buildScript());
    	}
    	return MSG;
    }
    
    public String addorg() throws Exception{
    	
    	if(null==corpsrc && null==depsrc){
    		msg = new Msg(Msg.SUCCESS,
					"没有选中要加入的单位");
    	}else{
    		if(null!=corpsrc){
    			dicSalecorpService.updateSalecorpsFromSinocorp(corpsrc);
    		}
    		if(null!=depsrc){
    			dicSalecorpService.updateSalecorpsFromSinodepartment(depsrc);
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
   		dicSalecorpService.expExcel(dicSalecorps,response);
   		
   		msg = new Msg(Msg.SUCCESS);
   		return MSG;
   	}
    
    //=================================  
	/**
	 * @return the dicSalecorp
	 */
	public DicSalecorp getDicSalecorp() {
		return dicSalecorp;
	}

	/**
	 * @param dicSalecorp the dicSalecorp to set
	 */
	public void setDicSalecorp(DicSalecorp dicSalecorp) {
		this.dicSalecorp = dicSalecorp;
	}

	/**
	 * @return the dicSalecorps
	 */
	public List<DicSalecorp> getDicSalecorps() {
		return dicSalecorps;
	}

	/**
	 * @param dicSalecorps the dicSalecorps to set
	 */
	public void setDicSalecorps(List<DicSalecorp> dicSalecorps) {
		this.dicSalecorps = dicSalecorps;
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
	 * 生成用于msg页面的script
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
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
