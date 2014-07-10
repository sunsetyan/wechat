package com.sunrise22.galaxy.entry;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();// managed by context
		
		Set<HttpSession> sessions = (HashSet<HttpSession>)application.getAttribute("session");
		if (sessions == null) {
			sessions = new HashSet<HttpSession>();
			
			application.setAttribute("sessions", sessions);
		}
		sessions.add(session);
		log.debug("创建session 连接数:" + sessions.size());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		
		Set<HttpSession> sessions = (HashSet<HttpSession>)application.getAttribute("session");
		sessions.remove(session);
		log.debug("移除session-连接数:" + sessions.size());
	}
	
	private static final Log log = LogFactory.getLog(AppSessionListener.class);

}
