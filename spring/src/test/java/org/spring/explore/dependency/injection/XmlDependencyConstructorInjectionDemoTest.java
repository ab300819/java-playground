package org.spring.explore.dependency.injection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 56. 通过配置文件构造器注入
 *
 * @author mason
 */
@Slf4j
class XmlDependencyConstructorInjectionDemoTest {

    private DefaultListableBeanFactory listableBeanFactory;

    @BeforeEach
    void setUp() {
        listableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(listableBeanFactory);

        String configPath = "classpath:dependency-constructor-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(configPath);
    }

    @Test
    void getUserHolderTest() {
        UserHolder userHolder = listableBeanFactory.getBean(UserHolder.class);
        log.info(userHolder.toString());
    }
}