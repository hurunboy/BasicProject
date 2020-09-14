package com.pdb.admin.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author sgl
 * @Date 2018-06-11 17:23
 */
@Configuration
public class ShiroConfig {

  /**
   * 凭证匹配器
   */
  @Bean
  public BCCredentialsMatcher bcCredentialsMatcher() {
    BCCredentialsMatcher bcCredentialsMatcher = new BCCredentialsMatcher();
    return bcCredentialsMatcher;
  }

  /**
   * 自定义realm
   */
  @Bean
  public UserRealm userRealm() {
    UserRealm userRealm = new UserRealm();
    userRealm.setCredentialsMatcher(bcCredentialsMatcher());
    return userRealm;
  }

  /**
   * 安全管理器 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
   */
  @Bean
  public DefaultWebSecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(userRealm());
    return securityManager;
  }


  /**
   * 设置过滤规则
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setLoginUrl("/login.htm");
    shiroFilterFactoryBean.setSuccessUrl("/");
    shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

    //注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
    //所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了。
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    filterChainDefinitionMap.put("/static/**", "anon");
    filterChainDefinitionMap.put("/login.htm", "anon");
    filterChainDefinitionMap.put("/ok.htm", "anon");
    filterChainDefinitionMap.put("/test1", "anon");
    filterChainDefinitionMap.put("/captcha.jpg", "anon");
    filterChainDefinitionMap.put("/favicon.ico", "anon");
    filterChainDefinitionMap.put("/**", "authc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

    return shiroFilterFactoryBean;
  }
}
