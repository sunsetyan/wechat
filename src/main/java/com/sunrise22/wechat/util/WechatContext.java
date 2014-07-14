/**
 * 
 */
package com.sunrise22.wechat.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Lang;

import com.sunrise22.wechat.api.WechatAPI;
import com.sunrise22.wechat.api.WechatHandler;
import com.sunrise22.wechat.domain.WechatMaster;
import com.sunrise22.wechat.impl.BasicWechatHandler;
import com.sunrise22.wechat.impl.WechatApiImpl;

/**
 * 处理weixin的容器
 */
public class WechatContext {

	/** default context */
	public static final String DEFAULT = "default";

	public WechatContext() {
	}

	protected Map<String, WechatMaster> masters = new HashMap<String, WechatMaster>();

	protected Map<String, WechatAPI> apis = new HashMap<String, WechatAPI>();

	protected Map<String, WechatHandler> handlers = new HashMap<String, WechatHandler>();

	public WechatAPI getAPI(String openId) {
		if (openId == null)
			openId = DEFAULT;
		return apis.get(openId);
	}

	public WechatMaster getMaster(String openId) {
		if (openId == null)
			openId = DEFAULT;
		return masters.get(openId);
	}

	public void setMasters(Map<String, WechatMaster> masters) {
		this.masters = masters;
	}

	public void setApis(Map<String, WechatAPI> apis) {
		this.apis = apis;
	}

	@SuppressWarnings("rawtypes")
	public void setPath(String path) {
		PropertiesProxy pp = new PropertiesProxy(path);
		Map<String, Object> map = new LinkedHashMap(pp.toMap());
		if (pp.get("openid") != null) {
			String appid = pp.get("openid");
			WechatMaster def = Lang.map2Object(map, WechatMaster.class);
			masters.put(appid, def);
			apis.put(appid, new WechatApiImpl(def));
			handlers.put(appid, new BasicWechatHandler(def.getToken()));
		}
		for (Entry<String, Object> en : map.entrySet()) {
			String key = en.getKey();
			if (key.endsWith(".openid")) {
				key = key.substring(0, key.indexOf('.'));
				Map<String, Object> tmp = filter(map, key + ".", null, null,
						null);
				String openid = tmp.get("openid").toString();
				WechatMaster one = Lang.map2Object(tmp, WechatMaster.class);
				masters.put(openid, one);
				apis.put(openid, new WechatApiImpl(one));
				handlers.put(openid, new BasicWechatHandler(one.getToken()));
			}
		}
	}

	/**
	 * map对象浅过滤,返回值是一个新的map
	 * 
	 * @param source
	 *            原始的map对象
	 * @param prefix
	 *            包含什么前缀,并移除前缀
	 * @param include
	 *            正则表达式 仅包含哪些key(如果有前缀要求,则已经移除了前缀)
	 * @param exclude
	 *            正则表达式 排除哪些key(如果有前缀要求,则已经移除了前缀)
	 * @param keyMap
	 *            映射map, 原始key--目标key (如果有前缀要求,则已经移除了前缀)
	 * @return 经过过滤的map,与原始map不是同一个对象
	 */
	private Map<String, Object> filter(Map<String, Object> source,
			String prefix, String include, String exclude,
			Map<String, String> keyMap) {
		LinkedHashMap<String, Object> dst = new LinkedHashMap<String, Object>();
		if (source == null || source.isEmpty())
			return dst;
		Pattern includePattern = include == null ? null : Pattern
				.compile(include);
		Pattern excludePattern = exclude == null ? null : Pattern
				.compile(exclude);

		for (Entry<String, Object> en : source.entrySet()) {
			String key = en.getKey();
			if (prefix != null) {
				if (key.startsWith(prefix))
					key = key.substring(prefix.length());
				else
					continue;
			}
			if (includePattern != null && !includePattern.matcher(key).find())
				continue;
			if (excludePattern != null && excludePattern.matcher(key).find()) 
				continue;
			if (keyMap != null && keyMap.containsKey(key))
				dst.put(keyMap.get(key), en.getValue());
			else
				dst.put(key, en.getValue());
		}
		return dst;
	}

	public WechatHandler getHandler(String openId) {
		return handlers.get(openId);
	}

}
