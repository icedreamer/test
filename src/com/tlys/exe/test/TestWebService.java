package com.tlys.exe.test;

import java.net.MalformedURLException;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class TestWebService {

	public static void main(String[] args) throws MalformedURLException, Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		org.apache.cxf.endpoint.Client client = dcf.createClient("http://10.5.5.1:7001/services/ExeDcarService?wsdl");
		// sayHello 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
		Object[] objects2 = client.invoke("updateTransActualWgt", new Object[] { "0693951", "Q01340", "Q01340",
				"2012-04-01", "S085334", 50.0, "北京铁路局", "石家庄车站", "0000000060005499", 1 });
		// // 输出调用结果
		System.out.println(objects2[0].toString());
		// Object[] objects = client.invoke("getExeGcarEvta", new Object[] {
		// "0693951", "2012-05-01", "20120-06-04",
		// "HGXSDDZCX" });
		// // 输出调用结果
		// System.out.println(objects[0].toString());
	}

}
