package org.olympos.athena.datasource.registrar.mapperScanner;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * MapperScannerConfiguratorRegistrar
 *
 * @Describe:
 *   将每个数据源的 {MapperScannerConfigurator} 注册到 {spring context} 当中
 * @Author Chasen Hao
 * @Date 2023-02-09 14:47
 */
public class MapperScannerConfiguratorRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    /**
     * 注册bean定义
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        StringBuilder name = new StringBuilder();
        StringBuilder basePackage = new StringBuilder();
        int i=1;

        for (;; i++) {
            name.append( environment.getProperty( "datasource.dblist.db" + i + ".name", "db"));
            basePackage.append( environment.getProperty( "datasource.dblist.db" + i + ".mappersPath", ""));

            if ( name.isEmpty() || basePackage.isEmpty()) break;
            registerMapperScannerBean( registry, i, basePackage.toString());
            name.delete( 0, name.length());
            basePackage.delete( 0, basePackage.length());
        }
    }

    /**
     * registerMapperScanner 的注册
     * @param registry
     * @param i
     * @param basePackage
     */
    private void registerMapperScannerBean(BeanDefinitionRegistry registry, int i, String basePackage) {

        String name = environment.getProperty( "datasource.dblist.db" + i + ".name", "db");

        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition( MapperScannerConfigurer.class);
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();

        mutablePropertyValues.addPropertyValue( new PropertyValue( "basePackage", basePackage));
        mutablePropertyValues.addPropertyValue( new PropertyValue( "sqlSessionTemplateBeanName", name + "SqlSessionTemplate"));
        mutablePropertyValues.addPropertyValue( new PropertyValue( "nameGenerator", new MapperBeanNameGenerator( name)));

        rootBeanDefinition.setPropertyValues( mutablePropertyValues);
        registry.registerBeanDefinition( name + "MapperScannerConfigurer", rootBeanDefinition);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
