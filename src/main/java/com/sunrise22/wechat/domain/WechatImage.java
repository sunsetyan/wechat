package com.sunrise22.wechat.domain;

public class WechatImage {
	private String mediaId;
	
	public WechatImage() {}

	public WechatImage(String mediaId) {
		super();
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
