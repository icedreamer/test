package com.tlys.comm.util.test;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiWriter {

	public static void main(String[] args) throws IOException {

		String[] sheets = { "��ҵ�Ա���̨��", "��ѹ�޳��������ܱ�", "��ѹ�޳��������ܱ�",
				"��ѹ�޳�ѹ������ʹ�õǼ�֤", "��ҵ�Ա���֤��", "�Ա���������Ϣ��" };
		int y = sheets.length*2;
		int x=0;
		String text = "";
		for(int i=1;i<sheets.length;i++){
			text += sheets[i]+"\n";
			if(x<sheets[i].length())
				x=sheets[i].length();
		}		
		x = x/2;
		// ��������������
		HSSFWorkbook wb = new HSSFWorkbook();
		// �������������
		HSSFSheet sheet = wb.createSheet("�ҵĹ�����");
		// ������ͼ����
		HSSFPatriarch p = sheet.createDrawingPatriarch();
		// ������Ԫ�����,��ע���뵽4��,1��,B5��Ԫ��
		HSSFCell cell = sheet.createRow(4).createCell(1);
		// ���뵥Ԫ������
		cell.setCellValue(new HSSFRichTextString("��ע"));
		// ��ȡ��ע����
		// (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
		// col2, int row2)
		// ǰ�ĸ������������,���ĸ������Ǳ༭����ʾ��עʱ�Ĵ�С.
		HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0,
				(short) 2,6, (short) x, y));
		// ������ע��Ϣ
		comment.setString(new HSSFRichTextString(text));
		// �������,ѡ��B5��Ԫ��,��״̬��
		comment.setAuthor("toad");
		// ����ע��ӵ���Ԫ�������
		cell.setCellComment(comment);
		// ���������
		FileOutputStream out = new FileOutputStream("writerPostil.xls");

		wb.write(out);
		// �ر�������
		out.close();
	}

}
