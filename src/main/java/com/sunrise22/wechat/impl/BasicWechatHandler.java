package com.sunrise22.wechat.impl;

import com.sunrise22.wechat.api.WechatHandler;
import com.sunrise22.wechat.domain.WechatMsg;
import com.sunrise22.wechat.domain.WechatResponse;
import com.sunrise22.wechat.util.WechatInfo;

public class BasicWechatHandler implements WechatHandler {
	protected String token;

	public BasicWechatHandler(String token) {
		this.token = token;
	}

	@Override
	public boolean check(String signature, String timestamp, String nonce) {
		return WechatInfo.check(token, signature, timestamp, nonce);
	}

	@Override
	public WechatResponse text(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse image(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse voice(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse video(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse location(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse link(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse eventSubscribe(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse eventScan(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse eventLocation(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse eventClick(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse eventView(WechatMsg msg) {
		return defaultMsg(msg);
	}

	@Override
	public WechatResponse defaultMsg(WechatMsg msg) {
		return WechatInfo.respText(null, "haha - > " + msg.getCreateTime());
	}

	@Override
	public WechatResponse handle(WechatMsg in) {
		return WechatInfo.handle(in, this);
	}

}
