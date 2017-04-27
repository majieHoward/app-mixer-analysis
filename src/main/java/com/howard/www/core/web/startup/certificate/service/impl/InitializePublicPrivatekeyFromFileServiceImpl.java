package com.howard.www.core.web.startup.certificate.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.howard.www.core.web.startup.certificate.service.IInitializePublicPrivatekey;
import com.howard.www.core.web.util.FrameworkStringUtil;

/**
 * 
 * @author howard
 * 
 */

public class InitializePublicPrivatekeyFromFileServiceImpl implements
		IInitializePublicPrivatekey, InitializingBean, DisposableBean {
	protected final Logger log = LoggerFactory
			.getLogger(InitializePublicPrivatekeyFromFileServiceImpl.class);
	@Autowired
	private ApplicationContext cApplicationContext;
	private String folderOfHaveCertificate;
	private String postfixOfCertificate;
	private ConcurrentHashMap<String, String> fileNameOfItems = new ConcurrentHashMap<String, String>();

	public String getFolderOfHaveCertificate() {
		return folderOfHaveCertificate;
	}

	public void setFolderOfHaveCertificate(String folderOfHaveCertificate) {
		StringBuffer directoryOfCompiledRoot = new StringBuffer();
		directoryOfCompiledRoot
				.append(InitializePublicPrivatekeyFromFileServiceImpl.class
						.getClassLoader().getResource("").getPath())
				.append("/").append(folderOfHaveCertificate);
		this.folderOfHaveCertificate = directoryOfCompiledRoot.toString();
		directoryOfCompiledRoot.setLength(0);
	}

	public String getPostfixOfCertificate() {
		return postfixOfCertificate;
	}

	public void setPostfixOfCertificate(String postfixOfCertificate) {
		this.postfixOfCertificate = postfixOfCertificate;
	}

	private void obtainCertificateUnderTheFolder(String folderName,
			List<String> nameOfCertificateItems, String folderPath)
			throws Exception {
		File paramFolder = new File(folderName);
		if (paramFolder.isDirectory()) {
			File[] paramCertificateFile = paramFolder.listFiles();
			for (int i = 0; i < paramCertificateFile.length; i++) {
				String paramFolderPath = null;
				if (!paramCertificateFile[i].getName().endsWith(
						postfixOfCertificate)
						|| paramCertificateFile[i].isDirectory()) {
					paramFolderPath = paramCertificateFile[i].getName();
				} else {
					paramFolderPath = folderPath;
				}
				obtainCertificateUnderTheFolder(
						paramCertificateFile[i].getPath(),
						nameOfCertificateItems, paramFolderPath);
			}
		} else if (paramFolder.getName().endsWith(postfixOfCertificate)) {
			StringBuffer paramOfKey = new StringBuffer();
			paramOfKey.append(folderPath).append("/")
					.append(paramFolder.getName());
			fileNameOfItems.put(paramOfKey.toString(), paramFolder.getName());
			nameOfCertificateItems.add(paramOfKey.toString());
			paramOfKey.setLength(0);
		}
	}

	private String obtainCertificateFilePath(String nameOfCertificateItem)
			throws Exception {
		StringBuffer paramOfCertificateFilePath = new StringBuffer();
		paramOfCertificateFilePath.append(folderOfHaveCertificate).append("/")
				.append(nameOfCertificateItem);
		return paramOfCertificateFilePath.toString();
	}

	public void readCertificateFile() throws Exception {
		if (!"".equals(FrameworkStringUtil.asString(this.postfixOfCertificate))
				&& !"".equals(FrameworkStringUtil
						.asString(this.folderOfHaveCertificate))) {
			log.info(
					"from {} folder obtain all certificate files with {} suffix",
					this.folderOfHaveCertificate, this.postfixOfCertificate);
			List<String> nameOfCertificateItems = new ArrayList<String>();
			obtainCertificateUnderTheFolder(this.folderOfHaveCertificate,
					nameOfCertificateItems, "/");
			if (nameOfCertificateItems.size() > 0) {
				int taskSize = 5;
				ExecutorService pool = Executors.newFixedThreadPool(taskSize);
				CountDownLatch latch = new CountDownLatch(
						nameOfCertificateItems.size());
				for (String nameOfCertificateItem : nameOfCertificateItems) {
					log.info("file name is {}", nameOfCertificateItem);
					StructurePublicPrivateItemFromFileServiceImpl structurePublicPrivateItem = (StructurePublicPrivateItemFromFileServiceImpl) cApplicationContext
							.getBean("structurePublicPrivateItemFromFile");
					structurePublicPrivateItem.initPublicPrivateItem(latch,
							obtainCertificateFilePath(nameOfCertificateItem),
							fileNameOfItems.get(nameOfCertificateItem),
							this.postfixOfCertificate);
					pool.submit(structurePublicPrivateItem);
				}
				latch.await();
				pool.shutdown();
			} else {
				log.debug(
						"Did not obtain any certificate file with {} suffix from {} folder",
						this.postfixOfCertificate, this.folderOfHaveCertificate);
			}
		}

	}

	public void destroy() throws Exception {

	}

	public void afterPropertiesSet() throws Exception {
		readCertificateFile();
	}
}
