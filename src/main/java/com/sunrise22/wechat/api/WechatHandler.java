package com.sunrise22.wechat.api;

import com.sunrise22.wechat.domain.WechatMsg;
import com.sunrise22.wechat.domain.WechatResponse;

/** 一个微信消息处理类必须选择实现下列接口 */
public interface WechatHandler {
	
	/** 检查消息可靠性 */
	boolean check(String signature, String timestamp, String nonce);
	
	/** 文字消息处理 */
	WechatResponse text(WechatMsg msg);
	
	/** 图像消息处理 */
	WechatResponse image(WechatMsg msg);
	/** 声音消息处理 */
	WechatResponse voice(WechatMsg msg);
	/** 视频消息处理 */
	WechatResponse video(WechatMsg msg);
	/** 定位信息消息处理 */
	WechatResponse location(WechatMsg msg);
	/** 链接消息处理 */
	WechatResponse link(WechatMsg msg);
	
	/** 订阅处理 */
	WechatResponse eventSubscribe(WechatMsg msg);
	/** scan消息处理 */
	WechatResponse eventScan(WechatMsg msg);
	/** 周边消息处理 */
	WechatResponse eventLocation(WechatMsg msg);
	/** 点击消息处理 */
	WechatResponse eventClick(WechatMsg msg);
	WechatResponse eventView(WechatMsg msg);
	/** 默认不识别或者新增的消息处理 */
	WechatResponse defaultMsg(WechatMsg msg);
	
	/** 针对进行处理 */
	WechatResponse handle(WechatMsg in);

}
