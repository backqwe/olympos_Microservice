package org.olympos.athena.datasource;

import org.olympos.athena.datasource.registrar.mapperScanner.MapperScannerConfiguratorRegistrar;
import org.olympos.athena.datasource.registrar.sqlSession.SqlSessionTemplateRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ConfigRegistrarBooter
 *
 * @Describe:
 *   启动 {MapperScannerConfigurator} 注册器与 {SqlSessionTemplate} 注册器,
 *   以把 {MapperScannerConfigurator} 与 {SqlSessionTemplate} 注册到 {spring context} 当中
 * @Author Chasen Hao
 * @Date 2023-04-11 01:13
 */
@Import({MapperScannerConfiguratorRegistrar.class, SqlSessionTemplateRegistrar.class})
@Configuration
public class ConfigRegistrarStarter {
}
