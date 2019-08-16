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
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.service.DicCarkindService;
import com.tlys.dic.service.DicCartypeService;

/**
 * @author 冯彦明
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCartypeAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicCartype dicCartype;
	private List<DicCartype> dicCartypes;
	private String id;

	private List<DicCarkind> dicCarkinds;

	private boolean isNew;

	@Autowired
	DicCartypeService dicCartypeService;

	@Autowired
	DicCarkindService dicCarkindService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicCartypeService.expExcel(dicCartypes, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicCartype) {
			if (id == null) {
				dicCartypes = dicCartypeService.findDICAll();
			} else {
				dicCartypes = dicCartypeService.findByCarkindid(id);
			}
		} else {
			dicCartypes = dicCartypeService.find(dicCartype);
		}

		return "list";
	}

	public String edit() throws Exception {
		dicCarkinds = dicCarkindService.findAll();
		if (null != id) {
			detail();
			isNew = false;
		} else {
			dicCartype = new DicCartype();
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.dicCartype = dicCartypeService.load(id);
		return "detail";
	}

	public String save() throws Exception {
		if (null == dicCartype) {
			throw new Exception("数据未接收到！");
		} else {

			dicCartypeService.save(dicCartype, isNew);


			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			dicCartypeService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！", buildScript());
		}
		return MSG;
	}

	// =================================
	/**
	 * @return the dicCartype
	 */
	public DicCartype getDicCartype() {
		return dicCartype;
	}

	/**
	 * @param dicCartype
	 *            the dicCartype to set
	 */
	public void setDicCartype(DicCartype dicCartype) {
		this.dicCartype = dicCartype;
	}

	/**
	 * @return the dicCartypes
	 */
	public List<DicCartype> getDicCartypes() {
		return dicCartypes;
	}

	/**
	 * @param dicCartypes
	 *            the dicCartypes to set
	 */
	public void setDicCartypes(List<DicCartype> dicCartypes) {
		this.dicCartypes = dicCartypes;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the dicCarkinds
	 */
	public List<DicCarkind> getDicCarkinds() {
		return dicCarkinds;
	}

	/**
	 * @param dicCarkinds
	 *            the dicCarkinds to set
	 */
	public void setDicCarkinds(List<DicCarkind> dicCarkinds) {
		this.dicCarkinds = dicCarkinds;
	}

	/**
	 * 生成用于msg页面的script 因为此页面放在lhgdialog生成的弹窗中，因此控制相对复杂 所以放在此处生成
	 * 
	 * @return
	 */
	private String buildScript() {
		String script = "frameElement.lhgDG.SetCancelBtn('关闭',function(){frameElement.lhgDG.curWin.location.reload();});";
		script += "frameElement.lhgDG.SetXbtn(function(){frameElement.lhgDG.curWin.location.reload();},false);";
		return script;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
