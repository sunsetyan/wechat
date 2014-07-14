package com.sunrise22.wechat.domain;

public class WechatVoice {
	
	private String mediaId;
	
	public WechatVoice() {}

	public WechatVoice(String mediaId) {
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
