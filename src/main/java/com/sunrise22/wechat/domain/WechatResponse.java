package com.sunrise22.wechat.domain;

import java.util.List;

public class WechatResponse {
	
	@Override
	public String toString() {
		return "WechatResponse [fromUserName=" + fromUserName + ", toUserName="
				+ toUserName + ", msgType=" + msgType + ", content=" + content
				+ ", createTime=" + createTime + ", image=" + image
				+ ", voice=" + voice + ", video=" + video + ", music=" + music
				+ ", articles=" + articles + "]";
	}

	private String fromUserName;
	private String toUserName;
	private String msgType;
	private String content;
	private long createTime;
	private WechatImage image;
	private WechatVoice voice;
	private WechatVideo video;
	private WechatMusic music;
	private List<WechatArticle> articles;
	
	public WechatResponse() {
		createTime = System.currentTimeMillis();
	}
	
	public WechatResponse(String msgType) {
		this();
		this.msgType = msgType;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WechatImage getImage() {
		return image;
	}

	public void setImage(WechatImage image) {
		this.image = image;
	}

	public WechatVoice getVoice() {
		return voice;
	}

	public void setVoice(WechatVoice voice) {
		this.voice = voice;
	}

	public WechatVideo getVideo() {
		return video;
	}

	public void setVideo(WechatVideo video) {
		this.video = video;
	}

	public WechatMusic getMusic() {
		return music;
	}

	public void setMusic(WechatMusic music) {
		this.music = music;
	}

	public List<WechatArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WechatArticle> articles) {
		this.articles = articles;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
