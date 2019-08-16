package com.tlys.test;

import java.io.File;
import java.util.List;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.FileCommUtil;
import com.tlys.spe.dao.SpeCertishippingDao;
import com.tlys.spe.model.SpeCertishipping;

public class TestSpeCertishipService extends BaseTest {
	@Autowired
	SpeCertishippingDao speCertishippingDao;

	@Test()
	@Rollback(value = false)
	@Check(constraints = "save")
	public void save() {
		try {
			String filePath = "D:/WorkDocument/铁路自备车/危险货物运输培训合格证（化工销售有限公司2012)/危险货物运输培训合格证/茂名";
			File file = new File(filePath);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					long speCertishippingId = speCertishippingDao.getNextSpeCertishippingSeqId();
					File f = files[i];
					String fileName = f.getName();
					if (CommUtils.getSuffix(fileName).equals("db")) {
						continue;
					}
					int index = fileName.lastIndexOf(".");
					String name = fileName.substring(0, index);
					// System.out.println("name : " + name);
					// SpeCertishipping speCertishipping = new
					// SpeCertishipping();
					// speCertishipping.setCorpid("10850000");
					// // speCertishipping.setCorpname("巴陵石化");
					// speCertishipping.setCreatedtime(new Date());
					// speCertishipping.setExpiredate(null);
					// speCertishipping.setFilename(fileName);
					// speCertishipping.setName(name);
					// speCertishippingDao.save(speCertishipping);
					// long speCertishippingId = speCertishipping.getRegid();
					// speCertishipping.setCertipath("/attach/CERTISHIPPING/" +
					// speCertishippingId
					// + "/" + speCertishippingId + ".jpg");
					// speCertishippingDao.update(speCertishipping);
					String path = CommUtils.getString("/attach/CERTISHIPPING/", speCertishippingId,
							"/", speCertishippingId, ".jpg");
					String sql = "insert into TB_ZBC_SPE_CERTISHIPPING (regid,name,corpid,createdtime,filename,certipath) values ("
							+ speCertishippingId
							+ ",'"
							+ name
							+ "','31750000',sysdate,'"
							+ fileName + "','" + path + "');";
					System.out.println(sql);
					String destPath = "F:/WorkSpace/pro_tlys/WebContent/CERTISHIPPING/"
							+ speCertishippingId + "/";
					File destFile = new File(destPath);
					if (!destFile.exists()) {
						destFile.mkdirs();
					}
					// logger.debug("f.path : " + f.getAbsolutePath());
					// logger.debug("f.exist : " + f.exists());

					File destF = new File(destPath + speCertishippingId + ".jpg");
					FileCommUtil.copyFile(f, destF);
				}
			}
			logger.debug("save success.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test()
	@Rollback(value = false)
	@Check(constraints = "save")
	public void updateCertishipImg() {
		List<SpeCertishipping> shippingList = speCertishippingDao.findAll();
		if (shippingList != null && !shippingList.isEmpty()) {
			for (SpeCertishipping speCertishipping : shippingList) {
				renameFile(speCertishipping);
			}
		}
	}

	private void renameFile(SpeCertishipping speCertishipping) {
		if (!speCertishipping.getIssuer().equals("郑州铁路局货运处")) {
			return;
		}
		long regid = speCertishipping.getRegid();
		String filepath = "D:/WorkDocuments/铁路运输/郑州局培训证/郑州局培训证/培训合格证";
		File file = new File(filepath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				String filename = f.getName();
				int index = filename.lastIndexOf(".");
				String name = filename.substring(0, index);
				if (speCertishipping.getName().equals(name)) {
					// File destFile = new
					// File("D:/WorkDocuments/铁路运输/郑州局培训证/郑州局培训证/培训合格证-重命名/"
					// + regid + "/" + regid + ".jpg");
					String destPath = "D:/WorkDocuments/铁路运输/郑州局培训证/郑州局培训证/培训合格证-重命名/" + regid;
					File newFile = new File(destPath);
					if (!newFile.exists()) {
						newFile.mkdirs();
					}
					logger.debug("ID：" + speCertishipping.getRegid() + " name : " + name);
					try {
						FileCommUtil.copyFile(f, new File(destPath + "/" + regid + ".jpg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
