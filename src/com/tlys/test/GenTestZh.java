/**
 * 
 */
package com.tlys.test;

import java.util.Scanner;

/**
 * @author fengym
 *
 */
public class GenTestZh {

	public static void main(String[] args) {

		//�������������⣬�û���ѡ��ִ����һ����Ŀ,Ŀǰֻ�޶��û���������Ŀ��ѡ��

		System.out.println("��ѡ��ִ�е���Ŀ��1��2)�󣬰��س�ִ�У�");

		Scanner reader=new Scanner(System.in);
		while(!reader.hasNextInt() && (reader.nextInt()>0 || reader.nextInt()<3)){
			System.out.println("��������ȷ����ѡ��ִ�е���Ŀ�������س�ִ�У�");
			reader=new Scanner(System.in);
		}

		int selectedNo = reader.nextInt();
		switch (selectedNo) {
			case 1:
				test1();
				break;
			case 2:
				test1();
				break;
			default:
				System.out.println("û�������Ŀ��");
		}

	}



	/**
	 * �� 1+2+3+....+K>200ʱ����k��С��ֵ��20
	 */
	public static void test1(){
			int k = 0;
			for (int i = 0; i < 200; i = i + k) {
				k++;
			}
			System.out.println(k);
	}

	

	/**
	 * �� 2 + 4 + 6 + .... k < 200ʱ����k����ֵ
	 */
    public static void test2(){
    		System.out.println("-------�� Ŀ���� 2 + 4 + 6 + .... k < 200ʱ����k����ֵ--------");
			int k = 2;
			for (int i = 0; i < 200; i = i + k + 2) {
				k = k + 2;
				System.out.println(i + " + " + k);
			}
			System.out.println(k);
	}
}
