package com.tlys.exe.common.util;

/**
 * 字典翻译方法
 * 
 * @author 孔垂云
 * 
 */
public class DicUtil {
	public static String getZbc_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "部属车";
		else if (code.equals("1"))
			return "自备车";
		else
			return "";
	}

	/**
	 * 字典翻译，重空选择
	 * 
	 * @param code
	 * @return
	 */
	public static String getLe_code(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "空";
		else if (code.equals("1"))
			return "重";
		else
			return "";
	}

	/**
	 * 取得上下行
	 * 
	 * @param code
	 * @return
	 */
	public static String getTrain_dir(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "上行";
		else if (code.equals("1"))
			return "下行";
		else
			return "";
	}

	/**
	 * 取得产品类别0—统销；1—非统销;3—成品油;4—煤;5—原油;6—沥青;9—其它
	 * 
	 * @param code
	 * @return
	 */
	public static String getGoods_type(String code) {
		code = code == null ? "" : code;
		if (code.equals("1"))
			return "统销";
		else if (code.equals("0"))
			return "非统销";
		else if (code.equals("3"))
			return "成品油";
		else if (code.equals("4"))
			return "煤";
		else if (code.equals("5"))
			return "原油";
		else if (code.equals("6"))
			return "沥青";
		else if (code.equals("9"))
			return "其它";
		else
			return "";
	}

	/**
	 * 取得产品租用标示0-自用、1-租赁、租用、2-部属车
	 * 如果为1，判断corpid和car_owner_id、car_user_id的值，=car_owner_id，为租赁，=car_user_id为租用，如果两个都不一样，则为租用
	 * 
	 * @param code
	 * @return
	 */
	public static String getCar_rent_flag(String code, String corpid, String car_owner_id, String car_user_id) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "自用";
		else if (code.equals("1")) {
			if (corpid.equals(car_owner_id))
				return "租赁";
			else if (corpid.equals(car_user_id))
				return "租用";
			else
				return "租用";
		} else if (code.equals("2"))
			return "部属车";
		else
			return "";
	}

	/**
	 * 取得路局名
	 * 
	 * @param code
	 * @return
	 */

	public static String getAdm(String code) {
		code = code == null ? "" : code;
		if (code.equals("B"))
			return "哈";
		else if (code.equals("T"))
			return "沈";
		else if (code.equals("P"))
			return "京";
		else if (code.equals("V"))
			return "太";
		else if (code.equals("C"))
			return "呼";
		else if (code.equals("F"))
			return "郑";
		else if (code.equals("N"))
			return "武";
		else if (code.equals("Y"))
			return "西";
		else if (code.equals("K"))
			return "济";
		else if (code.equals("H"))
			return "上";
		else if (code.equals("G"))
			return "南";
		else if (code.equals("Q"))
			return "广";
		else if (code.equals("Z"))
			return "宁";
		else if (code.equals("W"))
			return "成";
		else if (code.equals("M"))
			return "昆";
		else if (code.equals("J"))
			return "兰";
		else if (code.equals("R"))
			return "乌";
		else if (code.equals("O"))
			return "青";
		else if (code.equals(""))
			return "";
		else
			return "其他";

	}

	/**
	 * 取得信息来源标示 R-人工补录、T-货运信息、E-企业AEI、L-石化LIS
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_type(String code) {
		code = code == null ? "" : code;
		code = code.trim();
		if (code.equals("R"))
			return "人工补录";
		else if (code.equals("T"))
			return "货运信息";
		else if (code.equals("E"))
			return "";
		else if (code.equals("L"))
			return "石化LIS";
		else
			return "";
	}

	/**
	 * 进出企业站区标识,0进，1出
	 * 
	 * @param code
	 * @return
	 */
	public static String getIn_out_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "进";
		else if (code.equals("1"))
			return "出";
		else
			return "";
	}

	/**
	 * AEI主备标志，0-主，1-备
	 * 
	 * @param code
	 * @return
	 */
	public static String getAEI_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "主";
		else if (code.equals("1"))
			return "备";
		else
			return "";
	}

	/**
	 * 低速标志,0-正常，1-低速
	 * 
	 * @param code
	 * @return
	 */
	public static String getSpeed_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "正常";
		else if (code.equals("1"))
			return "低速";
		else
			return "";
	}

	/**
	 * 厂家编号,‘H’-哈科所'Y’-远望谷
	 * 
	 * @param code
	 * @return
	 */
	public static String getFact_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("H"))
			return "哈科所";
		else if (code.equals("Y"))
			return "远望谷";
		else
			return "";
	}

	/**
	 * 车辆标志,T-部属车，Q-企业自备车，X-未识别货车，
	 * 
	 * @param code
	 * @return
	 */
	public static String getCar_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("T"))
			return "部";
		else if (code.equals("Q"))
			return "企";
		else if (code.equals("X"))
			return "未识别货车";
		else
			return "";
	}

	/**
	 * AEI中取得信息来源标识，0设备扫描,1人工补录
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "设备扫描";
		else if (code.equals("1"))
			return "人工补录";
		else
			return "";
	}

	/**
	 * 取得调配信息状态
	 * 
	 * @param code
	 * @return
	 */
	public static String getAllocationStatus(String code) {
		code = code == null ? "" : code;
		if (code.equals("1"))
			return "提交";
		else if (code.equals("0"))
			return "录入";
		else if (code.equals("2"))
			return "过期失效";
		else
			return "未知";
	}

	/**
	 * 取得到发标志
	 * 
	 * @param code
	 * @return
	 */
	public static String getA_d_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("A"))
			return "到";
		else if (code.equals("D"))
			return "发";
		else
			return "";
	}

	/**
	 * 匹配类型
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_id(String code) {
		code = code == null ? "" : code;
		if (code.equals("P"))
			return "货票";
		else if (code.equals("Q"))
			return "确报";
		else
			return "";
	}

	/**
	 * 预警原因
	 * 
	 * @param code
	 * @return
	 */
	public static String getPbl_reason(String code) {
		code = code == null ? "" : code;
		if (code.equals("PM"))
			return "品名";
		else if (code.equals("CZ"))
			return "发到站";
		else if (code.equals("SP"))
			return "装运资质";
		else if (code.equals("DP"))
			return "卸载资质";
		else if (code.equals("SN"))
			return "发货人";
		else
			return "";
	}

	/**
	 * 匹配类型
	 * 
	 * @param code
	 * @return
	 */
	public static String getOper_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "未处理";
		else if (code.equals("1"))
			return "已解决";
		else if (code.equals("2"))
			return "不处理";
		else
			return "";
	}
}
