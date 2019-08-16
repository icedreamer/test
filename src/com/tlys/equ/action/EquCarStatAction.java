/**
 * 
 */
package com.tlys.equ.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.service.EquCarService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquCarStatAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private EquCar equCar = new EquCar();
	private Map statMap;
	private List carList;
	
	private Map goodsIdMap;
	private Map corpIdMap;
	
	private String goodsIds;
	private String corpIds;
	
	@Autowired
	EquCarService equCarService;

	/**
	 * 原设计是用户在前台提交goodsIds和corpIds，然后在后台过滤出相应的Map
	 * 现改为用户在前台的选择不进行提交，只是改变前台显示的行或列
	 * 因此，此类中goodsIds和corpIds永远为null
	 * @return
	 */
	public String carStatis() {
		Map goodsMap = equCarService.findGoodsMap();
		
		if(null == goodsIds){
			goodsIdMap = goodsMap;
		}else{
			goodsIdMap = equCarService.filterGoodsMap(goodsMap,goodsIds);
		}
		if(null == corpIds){
			corpIdMap = equCarService.findCorpidMap();
		}else{
			corpIdMap = equCarService.buildCidMap(corpIds);
		}

		statMap = equCarService.buidStatMap(goodsMap,corpIdMap);
		return "list";
	}

	
	




	public void prepare() throws Exception {
		initOpraMap("EQU_CAR");
	}
	
	/**
	 * @return the equCar
	 */
	public EquCar getEquCar() {
		return equCar;
	}

	/**
	 * @param equCar
	 *            the equCar to set
	 */
	public void setEquCar(EquCar equCar) {
		this.equCar = equCar;
	}

	
	/**
	 * @return the carList
	 */
	public List getCarList() {
		return carList;
	}

	/**
	 * @param carList
	 *            the carList to set
	 */
	public void setCarList(List carList) {
		this.carList = carList;
	}




	public Map getStatMap() {
		return statMap;
	}




	public void setStatMap(Map statMap) {
		this.statMap = statMap;
	}







	public Map getGoodsIdMap() {
		return goodsIdMap;
	}







	public void setGoodsIdMap(Map goodsIdMap) {
		this.goodsIdMap = goodsIdMap;
	}







	public Map getCorpIdMap() {
		return corpIdMap;
	}







	public void setCorpIdMap(Map corpIdMap) {
		this.corpIdMap = corpIdMap;
	}







	public String getGoodsIds() {
		return goodsIds;
	}







	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}







	public String getCorpIds() {
		return corpIds;
	}







	public void setCorpIds(String corpIds) {
		this.corpIds = corpIds;
	}




}
