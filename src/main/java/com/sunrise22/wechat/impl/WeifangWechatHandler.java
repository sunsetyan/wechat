/**
 * 
 */
package com.sunrise22.wechat.impl;

import org.nutz.ioc.loader.annotation.IocBean;

import com.sunrise22.wechat.domain.WechatMsg;
import com.sunrise22.wechat.domain.WechatResponse;
import com.sunrise22.wechat.util.WechatInfo;

/**
 */
@IocBean(name="wechatHandler", args={"9b6b3a8ae"})
public class WeifangWechatHandler extends BasicWechatHandler {

	public WeifangWechatHandler(String token) {
		super(token);
	}
	
	public WechatResponse text(WechatMsg msg) {
		if ("god".equals(msg.getContent())) // 用户输入god,我就惊叹一下嘛!
			return WechatInfo.respText(null, "Oh my God!");
		else 
			return WechatInfo.respText(null, "Out of my way!"); // 否则,滚开!
	}
	
	

}
