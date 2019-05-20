package com.sinosoft.newstandard.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 把自定义的配置文件放入Environment对象中,以达到用@ConfigurationProperties注解可以自动绑定的目的.
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CustomConfigurationPropertiesApplicationListener implements ApplicationListener<ApplicationEvent>, Ordered {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 在自动绑定数据时spring会获取此名称对应的资源,并用这些资源进行属性映射.
     */
    private static final String ATTACHED_PROPERTY_SOURCE_NAME = "configurationProperties";

    /**
     * 自定义yml文件名称的通配符规则,只有符合此规则的yml文件才会被添加到Environment对象中.
     */
    private static final String CUSTOM_YAML_PATTERN = "/**/custom-*.yml";

    @Override
    @SuppressWarnings("NullableProblems")
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            ConfigurableEnvironment environment = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();
            try {
                ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
                //通过通配符规则获取所有自定义的yml文件
                Resource[] resources = resourcePatternResolver.getResources(CUSTOM_YAML_PATTERN);
                //把yml对应的文件转换成PropertiesPropertySource对象
                List<PropertiesPropertySource> propertySourceList = Arrays.stream(resources).map(resource -> {
                    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
                    yaml.setResources(resource);
                    return new PropertiesPropertySource(resource.getFilename(), yaml.getObject());
                }).collect(Collectors.toList());
                //重新装配Environment对象
                reConfigEnvironment(environment, propertySourceList);
            } catch (IOException e) {
                logger.error("Environment对象重新装配时,未找到yml文件", e);
            }
        }
    }

    /**
     * 重新装配Environment对象.
     *
     * @param environment        Environment对象
     * @param propertySourceList 自定义yml文件所对应的PropertiesPropertySourced对象
     */
    private void reConfigEnvironment(ConfigurableEnvironment environment, List<PropertiesPropertySource> propertySourceList) {
        MutablePropertySources sources = environment.getPropertySources();
        //把所有PropertiesPropertySourced对象添加到Environment对象中
        for (PropertiesPropertySource propertiesPropertySource : propertySourceList) {
            sources.addFirst(propertiesPropertySource);
        }
        //获取旧的ConfigurationPropertySourcesPropertySource对象
        PropertySource<?> configurationPropertySourcesPropertySource_Original = sources.get(ATTACHED_PROPERTY_SOURCE_NAME);
        //删除旧的ConfigurationPropertySourcesPropertySource对象
        sources.remove(ATTACHED_PROPERTY_SOURCE_NAME);
        try {
            //获取ConfigurationPropertySourcesPropertySource类型的构造方法
            Constructor<? extends PropertySource> constructor = configurationPropertySourcesPropertySource_Original.getClass().getDeclaredConstructor(String.class, Iterable.class);
            constructor.setAccessible(true);
            //重新获取ConfigurationPropertySourcesPropertySource对象
            PropertySource configurationPropertySourcesPropertySource_New = constructor.newInstance(ATTACHED_PROPERTY_SOURCE_NAME, ConfigurationPropertySources.from(sources));
            //把ConfigurationPropertySourcesPropertySource对象重新放入Environment对象
            sources.addFirst(configurationPropertySourcesPropertySource_New);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            logger.error("Environment对象重新装配时,反射获取ConfigurationPropertySourcesPropertySource实例异常", e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 22;
    }

}
