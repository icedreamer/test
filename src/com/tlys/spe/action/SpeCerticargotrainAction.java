package com.tlys.spe.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.spe.model.SpeCerticargotrain;
import com.tlys.spe.service.SpeCerticargotrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeCerticargotrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private Long id;
	
	private SpeCerticargotrain speCerticargotrain;
	
	private List<SpeCerticargotrain> certTrainList;
	
	@Autowired
	SpeCerticargotrainService speCerticargotrainService;

	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	/*public String list() throws Exception {
		certTrainList = speCerticargotrainService.find(speCerticargotrain);
		return "list";
	}*/
	
	/**
	 * 删除押运员证
	 * 
	 * @return
	 */
	public String delete() throws Exception{
		speCerticargotrain = speCerticargotrainService.getSpeCerticargotrain(id);
		speCerticargotrainService.delete(speCerticargotrain);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}
	
	/**
	 * 新增或编辑
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		if(null != id){
			speCerticargotrain = speCerticargotrainService.getSpeCerticargotrain(id);
			isNew = false;
		}else{
			if(null==speCerticargotrain){
				speCerticargotrain =new SpeCerticargotrain();
			}
			//System.out.println("SpeCerticargotrainAction.edit->regid=="+regid);
			//speCerticargotrain.setRegid(regid);
			isNew = true;
		}
		//rectDate(speCerticargotrain);
		return "input";
	}


	/**
	 * 保存培训记录信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// 新增记录
			speCerticargotrainService.save(speCerticargotrain);
		} else {
			// 修改记录
			speCerticargotrainService.update(speCerticargotrain);
		}
		
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}
	
	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_MANAGE");
	}
	
	
	//-------------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpeCerticargotrain getSpeCerticargotrain() {
		return speCerticargotrain;
	}

	public void setSpeCerticargotrain(SpeCerticargotrain speCerticargotrain) {
		this.speCerticargotrain = speCerticargotrain;
	}

	public List<SpeCerticargotrain> getCertTrainList() {
		return certTrainList;
	}

	public void setCertTrainList(List<SpeCerticargotrain> certTrainList) {
		this.certTrainList = certTrainList;
	}
	

}
