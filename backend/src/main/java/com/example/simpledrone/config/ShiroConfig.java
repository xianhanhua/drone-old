package com.example.simpledrone.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.simpledrone.security.TokenFilter;
import com.example.simpledrone.security.UserRealm;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm userRealm(@Value("${app.security.username}") String username,
                           @Value("${app.security.password}") String password) {
        return new UserRealm(username, password);
    }

    @Bean
    public SecurityManager securityManager(Realm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         @Value("${app.security.api-token}") String token) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("token", new TokenFilter(token));
        bean.setFilters(filters);

        Map<String, String> chain = new LinkedHashMap<String, String>();
        chain.put("/api/auth/**", "anon");
        chain.put("/api/**", "token");
        bean.setFilterChainDefinitionMap(chain);
        return bean;
    }
}
