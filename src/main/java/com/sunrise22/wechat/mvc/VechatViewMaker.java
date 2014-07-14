package com.sunrise22.wechat.mvc;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class VechatViewMaker implements ViewMaker {

	public View make(Ioc ioc, String type, String value) {
		if (!"wx".equals(type))
			return null;
		return WechatView.me;
	}

}
