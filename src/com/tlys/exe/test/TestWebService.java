package com.tlys.exe.test;

import java.net.MalformedURLException;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class TestWebService {

	public static void main(String[] args) throws MalformedURLException, Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		org.apache.cxf.endpoint.Client client = dcf.createClient("http://10.5.5.1:7001/services/ExeDcarService?wsdl");
		// sayHello Ϊ�ӿ��ж���ķ������� ����Ϊ���ݵĲ��� ����һ��Object����
		Object[] objects2 = client.invoke("updateTransActualWgt", new Object[] { "0693951", "Q01340", "Q01340",
				"2012-04-01", "S085334", 50.0, "������·��", "ʯ��ׯ��վ", "0000000060005499", 1 });
		// // ������ý��
		System.out.println(objects2[0].toString());
		// Object[] objects = client.invoke("getExeGcarEvta", new Object[] {
		// "0693951", "2012-05-01", "20120-06-04",
		// "HGXSDDZCX" });
		// // ������ý��
		// System.out.println(objects[0].toString());
	}

}
