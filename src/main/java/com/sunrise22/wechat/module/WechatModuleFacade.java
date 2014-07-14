package com.sunrise22.wechat.module;

import org.nutz.ioc.loader.annotation.IocBean;

import com.sunrise22.wechat.mvc.WechatAbstractModule;
import com.sunrise22.wechat.util.WechatInfo;

@IocBean(fields="wechatHandler")
public class WechatModuleFacade extends WechatAbstractModule {
	
	public WechatModuleFacade() {
		WechatInfo.enableDevMode();
	}
	
}
