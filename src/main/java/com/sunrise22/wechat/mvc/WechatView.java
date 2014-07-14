package com.sunrise22.wechat.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;

import com.sunrise22.wechat.domain.WechatResponse;
import com.sunrise22.wechat.util.WechatInfo;

/** render the msg to the response */
public class WechatView implements View {
	
	public static final View me = new WechatView();
	
	public void render(HttpServletRequest req, HttpServletResponse res, Object obj) throws IOException {
		if (obj == null) 
			return;
		WechatInfo.asXml(res.getWriter(), (WechatResponse) obj);
	}

}
