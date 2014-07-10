package com.sunrise22.galaxy.entry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sunrise22.galaxy.config.ControllerConfig;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("to close galaxy context...");
		log.info("galaxy context closed");
		log.info("----------------------------------------------------------------");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			log.info("----------------------------------------------------------------");
			log.info("to initialize galaxy context");
			
			ControllerConfig.init();
			
			log.info("配置文件加载成功");
			
			log.info("galaxy context Initilized");
		} catch (Exception e) {
			log.error("启动服务时发生错误", e);
		}
	}
	
	private static Log log = LogFactory.getLog(ContextListener.class);

}
