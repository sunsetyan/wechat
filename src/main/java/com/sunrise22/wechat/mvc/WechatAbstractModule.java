package com.sunrise22.wechat.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;

import com.sunrise22.wechat.api.WechatHandler;
import com.sunrise22.wechat.util.WechatInfo;

public abstract class WechatAbstractModule {
	protected WechatHandler wechatHandler;
	
	@At({"/weixin", "/weixin/?"})
	@Fail("http:200")
	public View msgIn(String key, HttpServletRequest req) throws IOException {
		log.debug("a wechat message call : " + System.currentTimeMillis());
		return WechatInfo.handle(getWechatHandler(key), req);
	}

	public WechatHandler getWechatHandler(String key) {
		return wechatHandler;
	}
	
	private static final Log log = LogFactory.getLog(WechatAbstractModule.class);

}
