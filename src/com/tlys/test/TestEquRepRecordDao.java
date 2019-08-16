package com.tlys.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.spe.dao.SpePapersDao;
import com.tlys.spe.dao.SpePaperstypeDao;
import com.tlys.spe.model.SpePapers;
import com.tlys.spe.model.SpePaperstype;

public class TestEquRepRecordDao extends BaseTest {
	// @Autowired
	// EquRepRecordDao equRepRecordDao;
	//
	// @Autowired
	// EquCarDao equCarDao;
	// @Autowired
	// EquRepCarsDao equRepCarsDao;
	@Autowired
	SpePaperstypeDao spePaperstypeDao;
	@Autowired
	SpePapersDao spePapersDao;

	// @Test()
	// @Rollback(value = true)
	// @Check(constraints = "testfindEquRepRecord")
	// public void testfindEquRepRecord() {
	// List<EquRepRecord> equRepRecords = equRepRecordDao.findEquRepRecord();
	// }
	//
	// @Test()
	// @Rollback(value = true)
	// @Check(constraints = "findEquRepCars")
	// public void findEquRepCars() {
	// EquRepCars equRepCars = new EquRepCars();
	// EquRepCarsId equRepCarsId = new EquRepCarsId();
	// equRepCarsId.setRepkind("MR");
	// equRepCars.setId(equRepCarsId);
	// PageCtr<EquRepCars> pageCtr = new PageCtr<EquRepCars>();
	// equRepCarsDao.findEquRepCars(equRepCars, pageCtr);
	// List<EquRepCars> equRepCarses = pageCtr.getRecords();
	// if (null != equRepCarses && !equRepCarses.isEmpty()) {
	// for (EquRepCars equRepCars2 : equRepCarses) {
	// EquRepCarsId id = equRepCars2.getId();
	// logger.debug("carno : " + id.getCarno());
	// }
	// }
	// }
	//
	// public static void main(String[] args) {
	// StringBuffer buffer = new StringBuffer();
	// String filePath = "D:\\WorkDocument\\铁路自备车\\许可证相片\\许可证相片";
	// File file = new File(filePath);
	// if (file.isDirectory()) {
	// File[] files = file.listFiles();
	// for (int i = 0; i < files.length; i++) {
	// System.out.println(files[i].getName());
	// File f = files[i];
	// int index = f.getName().lastIndexOf(".");
	// String carno = f.getName().substring(0, index);
	// String sql =
	// "insert into TB_ZBC_SPE_PAPERS (id,corpid,corpname,carno ,ptypeid,papername,formattype,path,remarks,createdtime,filename) "
	// + "values(seq_tb_zbc_spe_papers.NEXTVAL,'11700000','南化公司','"
	// + carno
	// + "','04','"
	// + carno
	// + "','pjpeg','/attach/PAPER/')";
	// buffer.append(sql);
	// }
	// }
	// }

	@Test()
	@Rollback(value = false)
	@Check(constraints = "save")
	public void save() throws Exception {
		FileOutputStream fos = null;
		String filePath = "D:/WorkDocument/铁路自备车/许可证相片/a";
		File file = new File(filePath);
		SpePaperstype spePaperstype = spePaperstypeDao.load("04");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				Long spePapersId = spePapersDao.getNextSpePapersSeqId();
				System.out.println(files[i].getName());
				File f = files[i];
				String destPath = "F:/EicSpace/pro_tlys/WebContent/PAPER/" + spePapersId + "/";
				File destFile = new File(destPath);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				String fileName = f.getName();
				logger.debug("f.path : " + f.getAbsolutePath());
				logger.debug("f.exist : " + f.exists());
				if (CommUtils.getSuffix(fileName).equals("db")) {
					continue;
				}
				// Image image = ImageIO.read(f);
				// logger.debug("image : " + image);
				// if (image == null) {
				// continue;
				// }
				// int width = image.getWidth(null);
				// int height = image.getHeight(null);
				//
				// int newWidth = 1000;
				// if (width < newWidth) {
				// newWidth = width;
				// }
				//
				// int newHeight = height * newWidth / width;

				File destF = new File(destPath + spePapersId + ".jpg");
				FileCommUtil.copyFile(f, destF);
				// fos = new FileOutputStream(destF);
				// BufferedImage bufferedImage = new BufferedImage(newWidth,
				// newHeight, BufferedImage.TYPE_INT_RGB);
				// Graphics g = bufferedImage.getGraphics();
				// g.drawImage(image.getScaledInstance(newWidth, newHeight,
				// image.SCALE_SMOOTH), 0, 0, null);
				// g.dispose();
				// ImageIO.write(bufferedImage, "JPG", fos);
				// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
				// encoder.encode(bufferedImage);
				// fos.close();

				int index = fileName.lastIndexOf(".");
				String carno = fileName.substring(0, index);
				SpePapers spePapers = new SpePapers();
				spePapers.setCarno(carno);
				spePapers.setCorpid("11700000");
				spePapers.setCorpname("南化公司");
				spePapers.setCreatedtime(new Date());
				spePapers.setFilename(fileName);
				spePapers.setFormattype("pjpeg");
				spePapers.setPapersname(carno);
				spePapers.setId(spePapersId);
				spePapers.setSpePaperstype(spePaperstype);
				spePapers.setPath("/attach/PAPER/" + spePapersId + "/" + spePapersId + ".jpg");
				spePapersDao.save(spePapers);
			}
		}
		logger.debug("save success.");
	}

	@Test()
	@Rollback(value = false)
	@Check(constraints = "save")
	public void save2() throws Exception {
		String filePath = "D:\\WorkDocument\\铁路自备车\\安全技术合格证.南化\\安全技术合格证\\a\\";
		File file = new File(filePath);
		SpePaperstype spePaperstype = spePaperstypeDao.load("02");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				Long spePapersId = spePapersDao.getNextSpePapersSeqId();
				System.out.println(files[i].getName());
				File f = files[i];
				String destPath = "F:\\EicSpace\\pro_tlys\\WebContent\\PAPER\\" + spePapersId + "\\";
				File destFile = new File(destPath);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				String fileName = f.getName();
				File destF = new File(destPath + spePapersId + ".jpg");
				FileCommUtil.copyFile(f, destF);
				int index = fileName.lastIndexOf(".");
				String carno = fileName.substring(0, index);
				SpePapers spePapers = new SpePapers();
				spePapers.setCarno(carno);
				spePapers.setCorpid("11700000");
				spePapers.setCorpname("南化公司");
				spePapers.setCreatedtime(new Date());
				spePapers.setFilename(fileName);
				spePapers.setFormattype("pjpeg");
				spePapers.setPapersname(carno);
				spePapers.setId(spePapersId);
				spePapers.setSpePaperstype(spePaperstype);
				spePapers.setPath("/attach/PAPER/" + spePapersId + "/" + spePapersId + ".jpg");
				spePapersDao.save(spePapers);
			}
		}
		logger.debug("save success.");
	}

	public static void main(String[] args) {
		// String path = "F:/EicSpace/pro_tlys/WebContent/IMGP1823.JPG";
		String path = "D:/WorkDocument/铁路自备车/安全技术合格证.南化/安全技术合格证/全部正面/0631649.tif";
		File file = new File(path);
		try {
			BufferedImage image = ImageIO.read(file);
			System.out.println(file.length());
			// Image image = ImageIO.read(file);
			System.out.println(image);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
