package org.olympos.cloud.client.configuration;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * ConfigurationFactory
 *
 * @Describe:
 * for 业务模块
 *
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
public class ConfigurationFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource)
            throws IOException {

        // 指定配置路径中的文件名
        String filename = resource.getResource().getFilename() == null ?
                "" : resource.getResource().getFilename();

        // 资源为空，则会抛出异常
        resource.getInputStream();

        // 配置文件为 properties 文件，调用原逻辑
        if ( resource == null
                || filename.endsWith(".properties"))
            return super.createPropertySource( name, resource);

        // yml文件 或 yaml文件，则使用yaml属性源加载器加载并返回标准属性源
        else if ( filename.endsWith(".yml")
                || filename.endsWith(".yaml"))
            return new YamlPropertySourceLoader()
                    .load( resource.getResource().getFilename(), resource.getResource()).get(0);

        // 非以上情况，则直接抛出异常，提示格式错误
        else
            throw new IOException("file format error! only support: properties, yml, yaml!");
    }
}
