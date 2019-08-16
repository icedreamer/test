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
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicCustomerService;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author ������
 *
 */


@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCustomerAction extends _BaseAction{
	
	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());
	

	private DicCustomer dicCustomer;
	private List<DicCustomer> dicCustomers;
	private String id;
	
	private String provids;
	
	private List<DicProvince> dicProvinces;
	
	private boolean isNew;
	
	private String viewname;
	

	@Autowired
	DicCustomerService dicCustomerService;
	
	@Autowired
	DicProvinceService dicProvinceService;
	
	public String list() throws Exception{
		if(null==dicCustomer){
			if(null==provids){
				dicCustomers = dicCustomerService.findAll();
			}else{
				dicCustomers = dicCustomerService.findByProvids(provids);
			}
		}else{
			dicCustomers = dicCustomerService.find(dicCustomer);
		}
		
		return "list";
	} 
	
	/**
	 * �б�רΪ����ͼ�༭���ṩ
	 * @return
	 */
	@Action(value="dicCustomerView",results=
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
		
		dicCustomers = dicCustomerService.find4view(viewname);
		return result;
	}
	
	public String edit(){
		dicProvinces = dicProvinceService.findAll();
		if(null!=id){
			detail();
			isNew = false;
		}else{
			dicCustomer=new DicCustomer();
			isNew = true;
		}
		return "input";
	} 
    
    public String detail(){
    	this.dicCustomer = dicCustomerService.load(id);
    	return "detail";
    } 

    public String save() throws Exception{
    	if(null==dicCustomer){
    		throw new Exception("����δ���յ���");
		} else {
			//System.out.println("DicCustomerAction.save->dicCustomer=="+dicCustomer);
			dicCustomerService.save(dicCustomer,isNew);
			

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
    		dicCustomerService.delete(id);
    		msg = new Msg(Msg.SUCCESS,
					"ɾ������ɹ���",buildScript());
    	}
    	return MSG;
    }
    
    /**
	 * ����excel
	 * @return
	 */
	public String expExcel() throws Exception{
		list();//�õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicCustomerService.expExcel(dicCustomers,response);
		
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
    
    //=================================  
	/**
	 * @return the dicCustomer
	 */
	public DicCustomer getDicCustomer() {
		return dicCustomer;
	}

	/**
	 * @param dicCustomer the dicCustomer to set
	 */
	public void setDicCustomer(DicCustomer dicCustomer) {
		this.dicCustomer = dicCustomer;
	}

	/**
	 * @return the dicCustomers
	 */
	public List<DicCustomer> getDicCustomers() {
		return dicCustomers;
	}

	/**
	 * @param dicCustomers the dicCustomers to set
	 */
	public void setDicCustomers(List<DicCustomer> dicCustomers) {
		this.dicCustomers = dicCustomers;
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
