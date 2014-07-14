/**
 * 
 */
package com.sunrise22.wechat.api;

import java.io.File;

import org.nutz.lang.Each;
import org.nutz.resource.NutResource;

import com.sunrise22.wechat.domain.WechatMenu;
import com.sunrise22.wechat.domain.WechatResponse;

/**
 * 封装weixin的API
 */
public interface WechatAPI {

	/** 发送消息 */
	void send(WechatResponse msg);

	// ---------------------------------------------------

	String tmpQr(int expire_seconds, String scene_id);

	String godQr(int scene_id);

	String qrUrl(String ticket);

	String getAccessToken();

	// ---------------------------------------------------

	/** 创建群组 */
	// WechatGroup createGroup();
	//
	// List<WechatGroup> listGroup();
	//
	// int userGroup(String openid);
	//
	// void renameGroup(WechatGroup group);
	//
	// void moveUser2Group(String openId, String groupId);

	// ---------------------------------------------------

	// WechatUser fetchUser(String openId, String lang);
	//
	// void listWatcher(Each<String> each);

	// ---------------------------------------------------

	void createMenu(WechatMenu menu);

	WechatMenu fetchMenu();

	void clearMenu();

	// ---------------------------------------------------

	// String mediaUpload(String type, File f);
	//
	// NutResource mediaGet(String mediaId);

}
