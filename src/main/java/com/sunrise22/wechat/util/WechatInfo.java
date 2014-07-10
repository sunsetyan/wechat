package com.sunrise22.wechat.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sunrise22.wechat.api.WechatHandler;
import com.sunrise22.wechat.domain.WechatMsg;
import com.sunrise22.wechat.domain.WechatMsgType;
import com.sunrise22.wechat.domain.WechatResponse;

/** 处理微信传输的数据 */
public class WechatInfo {

	public static boolean DEV_MODE = false;

	/** 将微信消息转换为对应的wechat消息类型对应的数据结构 */
	public static WechatMsg convert(InputStream in) {
		Map<String, Object> params = Xmls.asMap(Xmls.xml(in)
				.getDocumentElement());
		Map<String, Object> tmp = new HashMap<String, Object>();
		for (Entry<String, Object> en : params.entrySet()) {
			tmp.put(Strings.lowerFirst(en.getKey()), en.getValue());
		}
		if (DEV_MODE) {
			log.debug("Income converted to>> \n" + Json.toJson(map));
		}
		return Lang.map2Object(tmp, WechatMsg.class);
	}

	/** 校验sigature */
	public static boolean check(String token, String signature,
			String timestamp, String nonce) {
		// 防范长密文攻击
		if (signature == null || signature.length() > 128 || timestamp == null
				|| timestamp.length() > 128 || nonce == null
				|| nonce.length() > 128) {
			log.warn("bad check : signature=" + signature + ",timestamp="
					+ timestamp + ",nonce=" + nonce);
			return false;
		}
		List<String> curr = new ArrayList<String>();
		curr.add(token);
		curr.add(timestamp);
		curr.add(nonce);
		Collections.sort(curr);
		String key = Lang.concat("", curr).toString();
		return Lang.sha1(key).equalsIgnoreCase(signature);
	}

	/** 打开dev模式 */
	public static void enableDevMode() {
		DEV_MODE = true;
		log.warn("sunrise22-wechat has enabled DEV mode");
	}
	
	/** 根据不同的消息类型，调用不同的WechatHandler方法 */
	public static WechatResponse handle(WechatMsg in, WechatHandler handler) {
		WechatResponse result = null;
		switch(WechatMsgType.valueOf(in.getMsgType())) {
		case text:
			result = handler.text(in);
			break;
		case image:
			result = handler.image(in);
			break;
		case voice:
			result = handler.voice(in);
			break;
		case video:
			result = handler.video(in);
			break;
		case location:
			result = handler.location(in);
			break;
		case link:
			result = handler.link(in);
			break;
		case event://事件推送要多一层解析
			result = handleEvent(in, handler);
			break;
		default:
			log.info("New MsyType=" + in.getMsgType() + " ? fallback to defaultMsg");
			result = handler.defaultMsg(in);
			break;
		}
		return result;
	}
	
	/** 指定一个Handler之后就任意组合出新的处理模块 额外处理推送消息 */
	public static WechatResponse handleEvent(WechatMsg in, WechatHandler handler) {
		WechatResponse result = null;
		return result;
	}
	
	/** 根据输入信息，修正发送信息的发送和接收者 */
	public static WechatResponse fix(WechatMsg req, WechatResponse res) {
		
	}

	/** 工具类 */
	private WechatInfo() {
	}

	private static final Log log = LogFactory.getLog(WechatMsg.class);

}
