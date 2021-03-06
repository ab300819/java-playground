package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 65. 注解驱动的依赖注入处理过程
 *
 * @author mason
 */
@Slf4j
class AnnotationDependencyInjectionResolutionDemoTest {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动应用上下文
        applicationContext.refresh();
    }

    @Test
    public void resolveDependenceTest() {
        AnnotationDependencyInjectionResolutionDemo bean = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
        log.info(bean.getUser().toString());
        log.info(bean.getInjectUser().toString());
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }
}