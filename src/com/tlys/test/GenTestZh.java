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

		//主程序引导标题，用户可选择执行哪一道题目,目前只限定用户在两道题目中选择

		System.out.println("请选择执行的题目（1，2)后，按回车执行：");

		Scanner reader=new Scanner(System.in);
		while(!reader.hasNextInt() && (reader.nextInt()>0 || reader.nextInt()<3)){
			System.out.println("请输入正确数字选择执行的题目，并按回车执行：");
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
				System.out.println("没有这个题目！");
		}

	}



	/**
	 * 当 1+2+3+....+K>200时，求k最小的值。20
	 */
	public static void test1(){
			int k = 0;
			for (int i = 0; i < 200; i = i + k) {
				k++;
			}
			System.out.println(k);
	}

	

	/**
	 * 当 2 + 4 + 6 + .... k < 200时，求k最大的值
	 */
    public static void test2(){
    		System.out.println("-------题 目：当 2 + 4 + 6 + .... k < 200时，求k最大的值--------");
			int k = 2;
			for (int i = 0; i < 200; i = i + k + 2) {
				k = k + 2;
				System.out.println(i + " + " + k);
			}
			System.out.println(k);
	}
}
