package com.tlys.spe.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.spe.model.SpeCertishiptrain;
import com.tlys.spe.service.SpeCertishiptrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpeCertishiptrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414807L;

	private Long id;
	
	private SpeCertishiptrain speCertishiptrain;
	
	private List<SpeCertishiptrain> certTrainList;
	
	@Autowired
	SpeCertishiptrainService speCertishiptrainService;

	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	/*public String list() throws Exception {
		certTrainList = speCertishiptrainService.find(speCertishiptrain);
		return "list";
	}*/
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception{
		speCertishiptrain = speCertishiptrainService.getSpeCertishiptrain(id);
		speCertishiptrainService.delete(speCertishiptrain);
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
			speCertishiptrain = speCertishiptrainService.getSpeCertishiptrain(id);
			isNew = false;
		}else{
			if(null==speCertishiptrain){
				speCertishiptrain =new SpeCertishiptrain();
			}
			isNew = true;
		}
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
			speCertishiptrainService.save(speCertishiptrain);
		} else {
			// 修改记录
			speCertishiptrainService.update(speCertishiptrain);
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

	public SpeCertishiptrain getSpeCertishiptrain() {
		return speCertishiptrain;
	}

	public void setSpeCertishiptrain(SpeCertishiptrain speCertishiptrain) {
		this.speCertishiptrain = speCertishiptrain;
	}

	public List<SpeCertishiptrain> getCertTrainList() {
		return certTrainList;
	}

	public void setCertTrainList(List<SpeCertishiptrain> certTrainList) {
		this.certTrainList = certTrainList;
	}
	

}
