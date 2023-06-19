package org.olympos.athena.datasource.registrar.mapperScanner;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * MapperBeanNameGenerator
 *
 * @Describe:
 *   {MapperBean} 名字生成器
 * @Author Chasen Hao
 * @Date 2020-12-29 15:34
 */
class MapperBeanNameGenerator implements BeanNameGenerator {

    private String prefix;

    /**
     * MapperBean 的名字生成器
     * @param prefix
     */
    public MapperBeanNameGenerator(String prefix) {
        Assert.notNull( prefix, "Prefix must not be null");
        this.prefix = prefix;
    }

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return prefix + ClassUtils.getShortName( definition.getBeanClassName());
    }
}
