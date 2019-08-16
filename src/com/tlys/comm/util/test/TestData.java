package com.tlys.comm.util.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;

public class TestData {

	public static void main(String[] args) throws Exception {
		//System.out.print(DateCalendar.getIMonth("",-3,"yyyy-MM"));
		getRowSetDynaClass("TB_ZBC_EQU_CAR_CERTIFICATE","");
	}

	public static RowSetDynaClass getRowSetDynaClass(String tableName,
			String condition) {
		String sql = "";
		String zdzs="";
		String zdms="";
		RowSetDynaClass rowSetDynaClass = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = Conn.getConnection();
			sql = "select a.column_name 字段名, b.comments 中文说明  from user_tab_columns a, user_col_comments b where a.column_name = b.column_name and a.table_name = b.table_name and a.table_name = upper('"
					+ tableName + "') order by a.Column_Id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs != null) {
				rowSetDynaClass = new RowSetDynaClass(rs, false);
				DynaProperty[] names = rowSetDynaClass.getDynaProperties();
				List list = rowSetDynaClass.getRows();
				sql = "select ";
				for (int i = 0; i < list.size(); i++) {
					BasicDynaBean bean = (BasicDynaBean) list.get(i);
					for (int j = 0; j < 2; j++) {
						String zdm = names[j].getName().toString();
						String zdz = bean.get(zdm) != null ? bean.get(zdm)
								.toString() : "";
						if (j == 0) {
							sql += "t." + zdz + " as ";
							zdzs +="\"" + zdz.toLowerCase() + "\",";
						} else {
							int index = zdz.indexOf(".");
							if (index > 1)
								zdz = zdz.substring(0, index);
							sql += zdz;
							zdms +="\"" + zdz + "\",";
							// sql += "\"" + zdz + "\"";//过渡别名中特殊字符
						}
					}
				}
				System.out.println(zdzs);

				System.out.println(zdms);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowSetDynaClass;
	}

	public static void getExceptionInfo(){
		javax.swing.JOptionPane.showMessageDialog(null, "功能完善中...");
	}
}
