package com.tlys.comm.util;

import java.io.IOException;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

public class ClasspathScanningAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	protected Log logger = LogFactory.getLog(this.getClass());
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
	private final TypeFilter entityFilter = new AnnotationTypeFilter(Entity.class);
	private String resourcePattern = DEFAULT_RESOURCE_PATTERN;
	private String basePackages;

	private boolean isEntity(MetadataReader metadataReader) throws IOException {
		if (entityFilter.match(metadataReader, this.metadataReaderFactory)) {
			return true;
		}
		return false;
	}

	protected void postProcessAnnotationConfiguration(AnnotationConfiguration config) throws HibernateException {
		try {
			long start = System.currentTimeMillis();
			String basePackageResourcePath = ClassUtils.convertClassNameToResourcePath(basePackages);
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + basePackageResourcePath + "/"
					+ this.resourcePattern;
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
				if (isEntity(metadataReader)) {
					String classFileFullPath = resource.getURL().getPath();
					int startIndex = classFileFullPath.indexOf("classes/");
					final String classFilePath = classFileFullPath.substring(startIndex + "classes/".length(),
							classFileFullPath.length() - ClassUtils.CLASS_FILE_SUFFIX.length());
					Class entityClass = null;
					try {
						String className = ClassUtils.convertResourcePathToClassName(classFilePath);
						entityClass = ClassUtils.forName(className);
					} catch (ClassNotFoundException e) {
						throw new HibernateException("Entity class not found during classpath scanning", e);
					}
					config.addAnnotatedClass(entityClass);
				}
			}
			long end = System.currentTimeMillis();
			logger.info("search entity time: " + (end - start) + "ms");
		} catch (Exception e) {
			logger.error("I/O failure during classpath scanning ", e);
		}

	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}
}
