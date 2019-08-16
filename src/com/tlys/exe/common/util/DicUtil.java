package com.tlys.exe.common.util;

/**
 * �ֵ䷭�뷽��
 * 
 * @author �״���
 * 
 */
public class DicUtil {
	public static String getZbc_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "������";
		else if (code.equals("1"))
			return "�Ա���";
		else
			return "";
	}

	/**
	 * �ֵ䷭�룬�ؿ�ѡ��
	 * 
	 * @param code
	 * @return
	 */
	public static String getLe_code(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "��";
		else if (code.equals("1"))
			return "��";
		else
			return "";
	}

	/**
	 * ȡ��������
	 * 
	 * @param code
	 * @return
	 */
	public static String getTrain_dir(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "����";
		else if (code.equals("1"))
			return "����";
		else
			return "";
	}

	/**
	 * ȡ�ò�Ʒ���0��ͳ����1����ͳ��;3����Ʒ��;4��ú;5��ԭ��;6������;9������
	 * 
	 * @param code
	 * @return
	 */
	public static String getGoods_type(String code) {
		code = code == null ? "" : code;
		if (code.equals("1"))
			return "ͳ��";
		else if (code.equals("0"))
			return "��ͳ��";
		else if (code.equals("3"))
			return "��Ʒ��";
		else if (code.equals("4"))
			return "ú";
		else if (code.equals("5"))
			return "ԭ��";
		else if (code.equals("6"))
			return "����";
		else if (code.equals("9"))
			return "����";
		else
			return "";
	}

	/**
	 * ȡ�ò�Ʒ���ñ�ʾ0-���á�1-���ޡ����á�2-������
	 * ���Ϊ1���ж�corpid��car_owner_id��car_user_id��ֵ��=car_owner_id��Ϊ���ޣ�=car_user_idΪ���ã������������һ������Ϊ����
	 * 
	 * @param code
	 * @return
	 */
	public static String getCar_rent_flag(String code, String corpid, String car_owner_id, String car_user_id) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "����";
		else if (code.equals("1")) {
			if (corpid.equals(car_owner_id))
				return "����";
			else if (corpid.equals(car_user_id))
				return "����";
			else
				return "����";
		} else if (code.equals("2"))
			return "������";
		else
			return "";
	}

	/**
	 * ȡ��·����
	 * 
	 * @param code
	 * @return
	 */

	public static String getAdm(String code) {
		code = code == null ? "" : code;
		if (code.equals("B"))
			return "��";
		else if (code.equals("T"))
			return "��";
		else if (code.equals("P"))
			return "��";
		else if (code.equals("V"))
			return "̫";
		else if (code.equals("C"))
			return "��";
		else if (code.equals("F"))
			return "֣";
		else if (code.equals("N"))
			return "��";
		else if (code.equals("Y"))
			return "��";
		else if (code.equals("K"))
			return "��";
		else if (code.equals("H"))
			return "��";
		else if (code.equals("G"))
			return "��";
		else if (code.equals("Q"))
			return "��";
		else if (code.equals("Z"))
			return "��";
		else if (code.equals("W"))
			return "��";
		else if (code.equals("M"))
			return "��";
		else if (code.equals("J"))
			return "��";
		else if (code.equals("R"))
			return "��";
		else if (code.equals("O"))
			return "��";
		else if (code.equals(""))
			return "";
		else
			return "����";

	}

	/**
	 * ȡ����Ϣ��Դ��ʾ R-�˹���¼��T-������Ϣ��E-��ҵAEI��L-ʯ��LIS
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_type(String code) {
		code = code == null ? "" : code;
		code = code.trim();
		if (code.equals("R"))
			return "�˹���¼";
		else if (code.equals("T"))
			return "������Ϣ";
		else if (code.equals("E"))
			return "";
		else if (code.equals("L"))
			return "ʯ��LIS";
		else
			return "";
	}

	/**
	 * ������ҵվ����ʶ,0����1��
	 * 
	 * @param code
	 * @return
	 */
	public static String getIn_out_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "��";
		else if (code.equals("1"))
			return "��";
		else
			return "";
	}

	/**
	 * AEI������־��0-����1-��
	 * 
	 * @param code
	 * @return
	 */
	public static String getAEI_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "��";
		else if (code.equals("1"))
			return "��";
		else
			return "";
	}

	/**
	 * ���ٱ�־,0-������1-����
	 * 
	 * @param code
	 * @return
	 */
	public static String getSpeed_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "����";
		else if (code.equals("1"))
			return "����";
		else
			return "";
	}

	/**
	 * ���ұ��,��H��-������'Y��-Զ����
	 * 
	 * @param code
	 * @return
	 */
	public static String getFact_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("H"))
			return "������";
		else if (code.equals("Y"))
			return "Զ����";
		else
			return "";
	}

	/**
	 * ������־,T-��������Q-��ҵ�Ա�����X-δʶ�������
	 * 
	 * @param code
	 * @return
	 */
	public static String getCar_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("T"))
			return "��";
		else if (code.equals("Q"))
			return "��";
		else if (code.equals("X"))
			return "δʶ�����";
		else
			return "";
	}

	/**
	 * AEI��ȡ����Ϣ��Դ��ʶ��0�豸ɨ��,1�˹���¼
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "�豸ɨ��";
		else if (code.equals("1"))
			return "�˹���¼";
		else
			return "";
	}

	/**
	 * ȡ�õ�����Ϣ״̬
	 * 
	 * @param code
	 * @return
	 */
	public static String getAllocationStatus(String code) {
		code = code == null ? "" : code;
		if (code.equals("1"))
			return "�ύ";
		else if (code.equals("0"))
			return "¼��";
		else if (code.equals("2"))
			return "����ʧЧ";
		else
			return "δ֪";
	}

	/**
	 * ȡ�õ�����־
	 * 
	 * @param code
	 * @return
	 */
	public static String getA_d_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("A"))
			return "��";
		else if (code.equals("D"))
			return "��";
		else
			return "";
	}

	/**
	 * ƥ������
	 * 
	 * @param code
	 * @return
	 */
	public static String getMsg_id(String code) {
		code = code == null ? "" : code;
		if (code.equals("P"))
			return "��Ʊ";
		else if (code.equals("Q"))
			return "ȷ��";
		else
			return "";
	}

	/**
	 * Ԥ��ԭ��
	 * 
	 * @param code
	 * @return
	 */
	public static String getPbl_reason(String code) {
		code = code == null ? "" : code;
		if (code.equals("PM"))
			return "Ʒ��";
		else if (code.equals("CZ"))
			return "����վ";
		else if (code.equals("SP"))
			return "װ������";
		else if (code.equals("DP"))
			return "ж������";
		else if (code.equals("SN"))
			return "������";
		else
			return "";
	}

	/**
	 * ƥ������
	 * 
	 * @param code
	 * @return
	 */
	public static String getOper_flag(String code) {
		code = code == null ? "" : code;
		if (code.equals("0"))
			return "δ����";
		else if (code.equals("1"))
			return "�ѽ��";
		else if (code.equals("2"))
			return "������";
		else
			return "";
	}
}
