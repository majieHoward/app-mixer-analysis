package com.howard.www.core.bean.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 
 * @author howard
 * 
 */
public class AnnotationGeneratorBeanName extends AnnotationBeanNameGenerator {
	protected final Logger log = LoggerFactory
			.getLogger(AnnotationGeneratorBeanName.class);

	public String generateBeanName(BeanDefinition beanDefinition,
			BeanDefinitionRegistry beanDefinitionRegistry) {
		log.debug("annotation initialization bean is \"{}\"",
				beanDefinition.getBeanClassName());
		return super.generateBeanName(beanDefinition, beanDefinitionRegistry);
	}

}
