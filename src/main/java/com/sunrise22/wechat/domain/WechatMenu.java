package com.sunrise22.wechat.domain;

import java.util.List;

import org.nutz.json.JsonField;

/** wechat 的菜单项配置拓扑结构很简单，所以易于解释 */
public class WechatMenu {
	
	private String name;
	private String type;
	private String key;
	private String url;
	@JsonField("sub_button")
	private List<WechatMenu> subButtons;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<WechatMenu> getSubButtons() {
		return subButtons;
	}
	public void setSubButtons(List<WechatMenu> subButtons) {
		this.subButtons = subButtons;
	}

}
