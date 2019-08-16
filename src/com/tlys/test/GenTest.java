/**
 * 
 */
package com.tlys.test;

import java.io.File;
import java.util.HashSet;

import com.tlys.comm.util.FileCommUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author fengym
 *
 */
public class GenTest {

	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
	}

	public static void test3(){
		HashSet areaidSet = new HashSet();
		String[] corpidArr = new String[]{"aaaa","bbbb","cccc","dddd"};
		for (int i = 0; i < corpidArr.length; i++) {
			//areaidSet.add(corpidArr[i]);
		}
		String areaids = areaidSet.toString().replace(" ", "").replace("[", "").replace("]", "");
		String aids = Arrays.toString(corpidArr);
		System.out.println("GenTest.test3->areaids=="+areaids);
		System.out.println("GenTest.test3->aids=="+aids);
		
    }

	public static void test1(){
		String[] aa = {"hello","world"};
		//System.out.println("GenTest.test1->aa.toString()=="+aa.toString());
	}

	
    public static void test2(){
    	String fpath = "d:\\_abc";
    	File f = new File(fpath);
    	FileCommUtil.delete(f);
    	System.out.println("GenTest.test2->fpath=="+fpath);
	}
    
    
}
