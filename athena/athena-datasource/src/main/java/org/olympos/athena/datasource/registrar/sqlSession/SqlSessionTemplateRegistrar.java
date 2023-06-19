package org.olympos.athena.datasource.registrar.sqlSession;

import jakarta.annotation.PreDestroy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * SqlSessionTemplateRegistrar
 *
 * @Describe:
 *   将每个数据源的 {SqlSessionTemplate} 注册到 {spring context} 当中
 * @Author Chasen Hao
 * @Date 2023-02-10 15:12
 */
public class SqlSessionTemplateRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger( SqlSessionTemplateRegistrar.class);

    private Environment environment;

    /**
     * 注册bean定义
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        PoolProperties p;
        DataSource ds;
        StringBuilder name = new StringBuilder();
        StringBuilder basePackage = new StringBuilder();

        int i=1;

        // 循环注册 DataSource
        // 循环注册 SqlSessionTemplate
        for (;; i++) {
            // 配置读入/配置非空检测
            name.append( environment.getProperty("datasource.dblist.db" + i + ".name", "db"));
            basePackage.append( environment.getProperty( "datasource.dblist.db" + i + ".mappersPath", ""));
            if ( name.isEmpty() || basePackage.isEmpty()) break;

            // 属性池
            p = DBSupporter.buildPoolProperties( this.getDBConfig( i));
            p.setLogAbandoned( true);
            p.setDefaultAutoCommit( true);
            // 数据源
            ds = new DataSource( p) {
                @Override
                @PreDestroy
                public void close() {
                    super.close( true);
                }
            };

            // 注册 SqlSessionTemplate
            registry.registerBeanDefinition( name + "SqlSessionTemplate", this.generateSqlSessionTemplate( p, i, ds));

            // 重置配置变量
            name.delete( 0, name.length());
            basePackage.delete( 0, basePackage.length());
        }
    }

    /**
     * 生成 SqlSessionTemplate
     * @param p
     * @param i
     * @param ds
     * @return
     */
    private RootBeanDefinition generateSqlSessionTemplate(PoolProperties p, int i, DataSource ds) {

        SqlSessionFactory sqlSessionFactory = null;
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource( ds);

        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch ( Exception e) {
            logger.error( e.getMessage(), e);
        }

        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition( SqlSessionTemplate.class);

        ConstructorArgumentValues cav = new ConstructorArgumentValues( );

        assert sqlSessionFactory != null;
        cav.addGenericArgumentValue( sqlSessionFactory);

        rootBeanDefinition.setConstructorArgumentValues( cav);
        rootBeanDefinition.setScope( "prototype");
        if ( i==1)
            rootBeanDefinition.setPrimary( true);
        return rootBeanDefinition;
    }

    /**
     * 获取数据库配置
     * @param i
     * @return
     */
    private DBBaseConfig getDBConfig(int i) {

        DBBaseConfig dbConf = new DBBaseConfig();
        dbConf.setHost( environment.getProperty( "datasource.dblist.db" + i + ".host", ""));
        dbConf.setDb( environment.getProperty( "datasource.dblist.db" + i + ".db", ""));
        dbConf.setUsername( environment.getProperty( "datasource.dblist.db" + i + ".username", ""));
        dbConf.setPassword( environment.getProperty( "datasource.dblist.db" + i + ".password", ""));
        dbConf.setMappersPath( environment.getProperty( "datasource.dblist.db" + i + ".mappersPath", ""));

        String port = environment.getProperty( "datasource.dblist.db" + i + ".port", "");
        if ( StringUtils.hasLength( port))
            dbConf.setPort( Integer.parseInt( port));

        String maxActive = environment.getProperty( "datasource.dblist.db" + i + ".maxActive", "");
        if ( StringUtils.hasLength( maxActive))
            dbConf.setMaxActive( Integer.parseInt( maxActive));

        String minIdle = environment.getProperty( "datasource.dblist.db" + i + ".minIdle", "");
        if ( StringUtils.hasLength( minIdle))
            dbConf.setMinIdle( Integer.parseInt( minIdle));

        String maxIdle = environment.getProperty( "datasource.dblist.db" + i + ".maxIdle", "");
        if ( StringUtils.hasLength( maxIdle))
            dbConf.setMaxIdle( Integer.parseInt( maxIdle));

        String validationInterval = environment.getProperty( "datasource.dblist.db" + i + ".validationInterval", "");
        if ( StringUtils.hasLength( validationInterval))
            dbConf.setValidationInterval( Integer.parseInt( validationInterval));

        String validationQueryTimeout = environment.getProperty( "datasource.dblist.db" + i + ".validationQueryTimeout", "");
        if ( StringUtils.hasLength( validationQueryTimeout))
            dbConf.setValidationQueryTimeout( Integer.parseInt( validationQueryTimeout));

        String timeBetweenEvictionRunsMillis = environment.getProperty( "datasource.dblist.db" + i + ".timeBetweenEvictionRunsMillis", "");
        if ( StringUtils.hasLength( timeBetweenEvictionRunsMillis))
            dbConf.setTimeBetweenEvictionRunsMillis( Integer.parseInt( timeBetweenEvictionRunsMillis));

        String initialSize = environment.getProperty( "datasource.dblist.db" + i + ".initialSize", "");
        if ( StringUtils.hasLength( initialSize))
            dbConf.setInitialSize( Integer.parseInt( initialSize));

        String maxWait = environment.getProperty( "datasource.dblist.db" + i + ".maxWait", "");
        if ( StringUtils.hasLength( maxWait))
            dbConf.setMaxWait( Integer.parseInt( maxWait));

        String removeAbandonedTimeout = environment.getProperty( "datasource.dblist.db" + i + ".removeAbandonedTimeout", "");
        if ( StringUtils.hasLength( removeAbandonedTimeout))
            dbConf.setRemoveAbandonedTimeout( Integer.parseInt( removeAbandonedTimeout));

        String minEvictableIdleTimeMillis = environment.getProperty( "datasource.dblist.db" + i + ".minEvictableIdleTimeMillis", "");
        if ( StringUtils.hasLength( minEvictableIdleTimeMillis))
            dbConf.setMinEvictableIdleTimeMillis( Integer.parseInt( minEvictableIdleTimeMillis));

        return dbConf;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
