package com.sunrise22.wechat.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import com.sunrise22.wechat.api.WechatAPI;
import com.sunrise22.wechat.domain.WechatMaster;
import com.sunrise22.wechat.domain.WechatMenu;
import com.sunrise22.wechat.domain.WechatResponse;
import com.sunrise22.wechat.util.WechatInfo;

public class WechatApiImpl implements WechatAPI {
	private static final Log log = LogFactory.getLog(WechatApiImpl.class);

	protected String base = "https://api.weixin.qq.com/cgi-bin";

	protected WechatMaster master;

	public WechatApiImpl(WechatMaster master) {
		super();
		this.master = master;
	}

	@Override
	public void send(WechatResponse msg) {
		if (msg.getFromUserName() == null)
			msg.setFromUserName(master.getOpenid());
		String str = WechatInfo.asJson(msg);
		if (WechatInfo.DEV_MODE)
			log.debug("api out msg ===>" + str);
		call("/massage/custom/send", METHOD.POST, str);
	}

	@Override
	public String tmpQr(int expire_seconds, String scene_id) {
		NutMap map = new NutMap().setv("expire_seconds", expire_seconds)
				.setv("action_name", "QR_SCENE")
				.setv("scene", new NutMap().setv("scene_id", scene_id));
		return call("", METHOD.POST, Json.toJson(map)).get("ticket").toString();
	}

	@Override
	public String godQr(int scene_id) {
		NutMap map = new NutMap().setv("action_name", "QR_LIMIT_SCENE").setv(
				"scene", new NutMap().setv("scene_id", scene_id));
		return call("", METHOD.POST, Json.toJson(map)).get("ticket").toString();
	}

	@Override
	public String qrUrl(String ticket) {
		return base + "/showqrcode?ticket=" + ticket;
	}

	@Override
	public String getAccessToken() {
		String token = master.getAccess_token();
		if (token == null || master.getAccess_token_expires() < System.currentTimeMillis()) {
			synchronized (master) {
				if (token == null || master.getAccess_token_expires() < System.currentTimeMillis()) {
					reflushAccessToken();
					token = master.getAccess_token();
				}
			}
		}
		return token;
	}

	@Override
	public void createMenu(WechatMenu menu) {
		call("/menu/create", METHOD.POST, Json.toJson(menu));
	}

	@Override
	public WechatMenu fetchMenu() {
		Map<String, Object> map = call("/menu/get", METHOD.GET, null);
		return Lang.map2Object((Map<String, Object>) map.get("menu"),
				WechatMenu.class);
	}

	@Override
	public void clearMenu() {
		call("/menu/clear", METHOD.GET, null);
	}

	/** 获取之前需要更新token */
	public void reflushAccessToken() {
		String url = String.format(
				"%s/token?grant_type=client_credential&appid=%s&secret=%s",
				base, master.getAppid(), master.getAppsecret());
		Response res = Http.get(url);
		if (!res.isOK()) {
			throw new IllegalArgumentException("reflushAccessToken FAIL, openId=" + master.getOpenid());
		}
		String str = res.getContent();
		Map<String, Object> map = (Map<String, Object>)Json.fromJson(str);
		master.setAccess_token(map.get("access_token").toString());
		master.setAccess_token_expires(System.currentTimeMillis() 	+ (((Number) map.get("expires_in")).intValue() - 60) * 1000);
	}
	
	/** call a service */
	protected Map<String, Object> call(String URL, METHOD method, String body) {
		String token = getAccessToken();
		if (URL.contains("?")) {
			URL = base + URL + "&access_token=" + token;
		} else {
			URL = base + URL + "?access_token=" + token;
		}
		Request req = Request.create(URL, method);
		if (body != null)
			req.setData(body);
		Response res = Sender.create(req).send();// 5 s
		if (!res.isOK()) {
			throw new IllegalArgumentException("res code = " + res.getStatus());
		}
		Map<String, Object> map = (Map<String, Object>)Json.fromJson(res.getReader());
		if (map != null && map.containsKey("errcode") && ((Number)map.get("errcode")).intValue() != 0) 
			throw new IllegalArgumentException(map.toString());
		return map;
	}

}
