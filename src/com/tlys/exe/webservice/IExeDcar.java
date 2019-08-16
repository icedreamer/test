package com.tlys.exe.webservice;

public interface IExeDcar {

	/**
	 * 修改实际载重量
	 * 
	 * @param car_no
	 * @param cdy_org_stn
	 * @param dest_stn
	 * @param cdy_date
	 * @param wb_nbr
	 * @param car_actual_wgt
	 * @param shipper_name
	 * @param con_name
	 * @param cdy_code
	 * @param login_name
	 * @param repeat_num
	 * @return
	 */
	String updateTransActualWgt(String car_no, String cdy_org_stn, String dest_stn, String cdy_date, String wb_nbr,
			Double car_actual_wgt, String shipper_name, String con_name, String cdy_code, int repeat_num);

	/**
	 * 运行动态数据接口
	 */
	String getExeDcarStat(String car_no, String login_name);

	/**
	 * 车辆轨迹数据接口
	 * 
	 * @return
	 */
	String getExeGcarEvta(String car_no, String s_date, String e_date, String login_name);
}
