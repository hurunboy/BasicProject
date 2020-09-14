package com.pdb.admin.config.shiro;

import com.pdb.admin.config.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateModelException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Title:      FreemarkerConfig
 *
 * @version V1.0 Description: freemarker配置shiro
 * @date 2018年7月26日
 */
@Configuration
public class FreemarkerConfig {

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
  }
}
