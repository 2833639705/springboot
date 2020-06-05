package com.woniuxy.springboot01.configuration;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.woniuxy.springboot01.realm.CustomRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    // 1.realm
    @Bean
    public CustomRealm realm() {
        CustomRealm realm = new CustomRealm();
        // 开启缓存
        realm.setCachingEnabled(true);
        // 设置认证的缓存
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName("abc");
        // 设置授权的缓存
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName("cba");
        return realm;
    }

    // 2.配置securityManager 安全管理器
    @Bean
    public SecurityManager securityManager(CustomRealm realm) { // 参数名字要与方法名相同--通过方法名注入
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm
        manager.setRealm(realm);
        // 调方法:获取缓存管理器
        manager.setCacheManager(ehCacheManager()); // 只会创建一个
        return manager;
    }

    // 3.配置过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        // 登陆页面
        factoryBean.setLoginUrl("/login.html");
        // 没权限页面
        factoryBean.setUnauthorizedUrl("/error.html");
        // 设置过滤器链
        Map<String, String> map = new LinkedHashMap<>(); // 有序map
        map.put("/login.html", "anon");
        map.put("/user.html", "anon");
        map.put("/user/login", "anon"); // 给后端请求放行
        map.put("/logout.html", "logout");
        map.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(map);
        return factoryBean;
    }

    // 4.配置shiro对thymeleaf模板引擎支持的对象
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    // 5.配置注解相关的(shiro)
    // 5.1配置生命周期管理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    // 5.2配置
    @Bean
    @DependsOn("lifecycleBeanPostProcessor") // 依赖于生命周期管理器
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    // 5.3
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    // 6.缓存
    // 6.1
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager manager = new EhCacheManager();
        // 加载配置文件
        manager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return manager;
    }
    // 6.2 交给安全管理器
    // 6.3 配置缓存授权和管理分开放
}
