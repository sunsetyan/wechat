/**
 * 
 */
package com.sunrise22.galaxy.entry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 当加入新的配置表之后需要调用一个wget接口。
 * 
 * @author shengling
 */
public class GalaxyControlServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doService(req, res);
	}

	/**
	 * wget "http://localhost:8086/sunrise22-galaxy/init?at=init"
	 * 
	 * wget "http://localhost:8086/sunrise22-galaxy/control?target=app&action=init"
	 * @throws IOException 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doService(req, res);
	}

	private void doService(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setCharacterEncoding("UTF-8");
		synchronized (this.getClass()) {
			long start = System.currentTimeMillis();
			String logMessage = "";
			Map<String, String> params = extractParameters(req);

			String target = params.get("target");
			if (target == null) {
				target = "app";
			}

			try {
				if (target.equals("app")) {
					logMessage = controlApp(params);
				} else {
					logMessage = "未知的target参数";
				}
			} catch (Exception e) {
				log.error("服务器错误", e);
				logMessage = "发现异常: " + e.getClass() + " : " + e.getMessage()
						+ ", 请检查服务器日志";
			}

			PrintWriter writer = res.getWriter();
			writer.write(logMessage);
			writer.flush();

			long end = System.currentTimeMillis();
			log.info("req.getLocalName:" + req.getLocalName() + " usage:"
					+ (end - start) + "ms, params: " + params.toString() + "while: " + logMessage);
		}
	}

	private String controlApp(Map<String, String> params) {
		String action = params.get("action");
		if (action == null) {
			action = params.get("at");
		}

		String result = "";
		if (action.equals("init")) {
			log.info("服务器开始加载配置文件。");
			// SpringBeanFactory.init();
			// log.info("加载spring 配置文件成功。。");
			// result += "加载spring 配置文件成功 | ";

			// TODO 特定的配置文件 这里不做处理
			// NoticeInfoLoader loader = NoticeInfoLoader.getInstance();
			// loader.init();
			log.info("加载配置文件成功！");
			result += "加载配置文件成功！";
		} else {
			log.error("未知的命令: target=app, action=" + action);
			result += "未知的命令: target=app, action=" + action;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> extractParameters(ServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> en = (Enumeration<String>) request
				.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			// 如果同一个参数有多个值，将只取第一个
			String value = request.getParameter(name);
			params.put(name, value);
		}
		return params;
	}

	private static Log log = LogFactory.getLog(GalaxyControlServlet.class.getName());

	/**
	 * UUID
	 */
	private static final long serialVersionUID = -4380514992656295083L;

}
