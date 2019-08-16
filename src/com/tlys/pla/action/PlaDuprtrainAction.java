package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaDuprtrain;
import com.tlys.pla.service.PlaDuprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDuprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDuprtrain plaDuprtrain;
	private List<PlaDuprtrain> plaDuprtrains;
	private String acceptcarno;
	@Autowired
	PlaDuprtrainService plaDuprtrainService;

	/**
	 * 列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String _list() throws Exception {
		if (null == plaDuprtrain) {
			if (null == acceptcarno) {
				// 批准要车分号为空查询全部
				plaDuprtrains = plaDuprtrainService.findAll();
			} else {
				// 根据批准要车分号查询相关
				plaDuprtrains = plaDuprtrainService.find(acceptcarno);
			}
		} else {
			// 根据plaDuprtrain对象填充条件查询相关
			plaDuprtrains = plaDuprtrainService.find(plaDuprtrain);
		}
		return "list";
	}*/

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-duprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			if (null != plaDuprtrain) {
				String schObjKey = "plaDuprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDuprtrain);
			}
		} else {
			if (null != pageCtr.getSchObjKey()) {
				plaDuprtrain = (PlaDuprtrain) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(100);//不分页
		plaDuprtrainService.find(plaDuprtrain, pageCtr);
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDuprtrain = plaDuprtrainService.load(id);
		plaDuprtrainService.delete(plaDuprtrain);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDuprtrain = plaDuprtrainService.load(id);
			isNew = false;
		} else {
			plaDuprtrain = new PlaDuprtrain();
			isNew = true;
		}
		return "input";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// 新增记录
			plaDuprtrainService.save(plaDuprtrain);
		} else {
			// 修改记录
			plaDuprtrainService.update(plaDuprtrain);
		}

		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaDuprtrain getPlaDuprtrain() {
		return plaDuprtrain;
	}

	public void setPlaDuprtrain(PlaDuprtrain plaDuprtrain) {
		this.plaDuprtrain = plaDuprtrain;
	}

	public String getAcceptcarno() {
		return acceptcarno;
	}

	public void setAcceptcarno(String acceptcarno) {
		this.acceptcarno = acceptcarno;
	}

	public List<PlaDuprtrain> getPlaDuprtrains() {
		return plaDuprtrains;
	}
}
