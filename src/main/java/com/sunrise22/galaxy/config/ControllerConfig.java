package com.sunrise22.galaxy.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/** 配置文件相关 */
public final class ControllerConfig {

	public static void load() {
		if (config != null) {
			config.clear();
		}
		CompositeConfiguration conf = new CompositeConfiguration();
		conf.addConfiguration(new SystemConfiguration(), true);
		conf.addConfiguration(loadConfig(getRootFile()), true);
		
		loadIncludeFiles(conf);
		loadOuterFiles(conf);
		loadSunrise22Data(conf);
		config = conf;
	}

	public static void init() {
		load();
	}

	public static void refresh() {
		load();
	}

	/** 根据key获得配置选项 */
	public static String get(String key) {
		return config.getString(key);
	}

	/** 获得整个配置文件 */
	public static String getRootFile() {
		return "sunrise22.galaxy.conf";
	}

	// 配置文件对应的配置
	private static Configuration config;

	private ControllerConfig() {
	}
	
	private static void loadSunrise22Data(CompositeConfiguration conf) {
		dataFilePath = conf.getString("data.dir");
		if (dataFilePath != null && dataFilePath.trim().length() != 0) {
			System.out.println("资料文件路径为 :  " + dataFilePath);
		}
		 //PropertyConfigurator.configure(dataFilePath + "/log4j.properties");
		 log.info("资料文件路径为 :  " + dataFilePath);
	}

	/** 加载配置文件 */
	private static void loadIncludeFiles(CompositeConfiguration compConf) {
		String includeFiles = compConf.getString("include.conf.files");
		if (includeFiles != null && includeFiles.trim().length() != 0) {
			String[] files = includeFiles.split(" ");
			for (String f : files) {
				Configuration conf = loadConfig(f);
				if (conf != null) {
					compConf.addConfiguration(conf);
					log.info("配置文件 " + f + " 加载完成");
				}
			}
		}
	}

	private static Configuration loadConfig(String fileOnClassPath) {
		Configuration conf = null;
		try {
			PropertiesConfiguration proConf = new PropertiesConfiguration();
			InputStream in = ControllerConfig.class.getResource(
					"/" + fileOnClassPath).openStream();
			proConf.load(new InputStreamReader(in, "utf8"));
			conf = proConf;
		} catch (Exception e) {
			log.fatal("读取配置文件" + fileOnClassPath + "失败，请检查该文件是否在classpath上。", e);
			throw new IllegalStateException(fileOnClassPath + "读取失败！");
		}
		return conf;
	}
	
	private static void loadOuterFiles(CompositeConfiguration compConf) {
		String outerFiles = compConf.getString("data.conf.files");
		if (outerFiles != null && outerFiles.trim().length() != 0) {
			String[] files = outerFiles.split(" ");
			for (String f : files) {
				Configuration conf = loadOuterConfig(f);
				if (conf != null) {
					compConf.addConfiguration(conf);
					log.info("配置文件 " + f + " 加载完成");
				}
			}
		}
	}

	private static String dataFilePath;

	public static String getDataFilePath() {
		return dataFilePath;
	}

	private static void loadConfigData(CompositeConfiguration compConf) {
		dataFilePath = compConf.getString("data.dir");
		if (dataFilePath != null && dataFilePath.trim().length() != 0) {
			log.info("消息的资料文件路径为 :  " + dataFilePath);
		}
	}

	private static Configuration loadOuterConfig(String file) {
		Configuration conf = null;
		try {
			PropertiesConfiguration proConf = new PropertiesConfiguration();
			FileReader reader = new FileReader(file);
			proConf.load(reader);
			reader.close();
			conf = proConf;
		} catch (FileNotFoundException e) {
			log.fatal("找不到外部配置文件" + file, e);
			e.printStackTrace();
		} catch (ConfigurationException e) {
			log.fatal("加载外部配置文件" + file + "时发生错误", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal("加载外部配置文件" + file + "时发生错误", e);
			e.printStackTrace();
		}
		return conf;
	}

	private static Log log = LogFactory.getLog(ControllerConfig.class);

}
