package com.tlys.test;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class TestSpePapersService extends BaseTest {

	public static void main(String[] args) {
		try {
			// String filePath = "d:/05.jpg";
			String filePath = "d:/0631649.jpg";
			File file = new File(filePath);
			Image image = ImageIO.read(file);
			System.out.println(image);
			System.out.println(image.getWidth(null) + " x " + image.getHeight(null));
			System.out.println(System.getProperty("java.home"));
			System.out.println(file.length());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
