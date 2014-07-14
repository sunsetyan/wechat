package com.sunrise22.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Xmls;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.View;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.RawView;
import org.nutz.mvc.view.ViewWrapper;

import com.sunrise22.wechat.api.WechatHandler;
import com.sunrise22.wechat.domain.WechatArticle;
import com.sunrise22.wechat.domain.WechatEventType;
import com.sunrise22.wechat.domain.WechatImage;
import com.sunrise22.wechat.domain.WechatMsg;
import com.sunrise22.wechat.domain.WechatMsgType;
import com.sunrise22.wechat.domain.WechatMusic;
import com.sunrise22.wechat.domain.WechatResponse;
import com.sunrise22.wechat.domain.WechatVideo;
import com.sunrise22.wechat.domain.WechatVoice;
import com.sunrise22.wechat.mvc.WechatView;

/** 处理微信传输的数据 */
public class WechatInfo {

	public static boolean DEV_MODE = false;

	/** 将微信消息转换为对应的wechat消息类型对应的数据结构 */
	public static WechatMsg convert(InputStream in) {
		Map<String, Object> params = Xmls.asMap(Xmls.xml(in)
				.getDocumentElement());
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, Object> en : params.entrySet()) {
			map.put(Strings.lowerFirst(en.getKey()), en.getValue());
		}
		if (DEV_MODE) {
			log.debug("Income converted to>> \n" + Json.toJson(params));
		}
		return Lang.map2Object(map, WechatMsg.class);
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
		switch (WechatMsgType.valueOf(in.getMsgType())) {
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
		case event:// 事件推送要多一层解析
			result = handleEvent(in, handler);
			break;
		default:
			log.info("New MsyType=" + in.getMsgType()
					+ " ? fallback to defaultMsg");
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
		res.setFromUserName(res.getToUserName());
		res.setToUserName(res.getFromUserName());
		res.setCreateTime(System.currentTimeMillis() / 1000);
		return res;
	}

	/**
	 * 创建一条文本响应
	 */
	public static WechatResponse respText(String to, String content) {
		WechatResponse res = new WechatResponse("text");
		res.setContent(content);
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/**
	 * 创建一条图片响应
	 */
	public static WechatResponse respImage(String to, String mediaId) {
		WechatResponse res = new WechatResponse("image");
		res.setImage(new WechatImage(mediaId));
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/**
	 * 创建一个语音响应
	 */
	public static WechatResponse respVoice(String to, String mediaId) {
		WechatResponse res = new WechatResponse("voice");
		res.setVoice(new WechatVoice(mediaId));
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/**
	 * 创建一个视频响应
	 */
	public static WechatResponse respVideo(String to, String mediaId,
			String title, String description) {
		WechatResponse res = new WechatResponse("video");
		res.setVideo(new WechatVideo(mediaId, title, description));
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/**
	 * 创建一个音乐响应
	 */
	public static WechatResponse respMusic(String to, String title,
			String description, String musicURL, String hQMusicUrl,
			String thumbMediaId) {
		WechatResponse res = new WechatResponse("music");
		res.setMusic(new WechatMusic(title, description, musicURL, hQMusicUrl,
				thumbMediaId));
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/** 图文消息 */
	public static WechatResponse respNews(String to, WechatArticle... articles) {
		return respNews(to, Arrays.asList(articles));
	}

	/** 图文消息 */
	public static WechatResponse respNews(String to,
			List<WechatArticle> articles) {
		WechatResponse res = new WechatResponse("news");
		res.setArticles(articles);
		if (to != null)
			res.setToUserName(to);
		return res;
	}

	/** cdata tag */
	public static String cdata(String str) {
		if (Strings.isBlank(str))
			return "";
		return "<![CDATA[" + str.replaceAll("]]", "_") + "]]>";
	}

	public static String tag(String key, String val) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(key).append(">");
		sb.append(val).append("");
		sb.append("</").append("key").append(">\n");
		return sb.toString();
	}

	/** 转换为微信系统识别的XML */
	public static String asXml(WechatResponse msg) {
		log.info("msg to xml str : " + msg.toString());
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>\n");
		sb.append(tag("ToUserName", cdata(msg.getToUserName())));
		sb.append(tag("FromUserName", cdata(msg.getFromUserName())));
		sb.append(tag("CreateTime", "" + msg.getCreateTime()));
		sb.append(tag("MsgType", cdata(msg.getMsgType())));
		switch (WechatMsgType.valueOf(msg.getMsgType())) {
		case text:
			sb.append(tag("Content", cdata(msg.getContent())));
			break;
		case image:
			sb.append(tag("Image",
					tag("MediaId", msg.getImage().getMediaId())));
			break;
		case voice:
			sb.append(tag("Voice",
					tag("MediaId", msg.getVideo().getMediaId())));
			break;
		case video:
			sb.append("<Video>\n");
			sb.append(tag("MediaId", cdata(msg.getVideo().getMediaId())));
			if (msg.getVideo().getTitle() != null)
				sb.append(tag("Title", cdata(msg.getVideo().getTitle())));
			if (msg.getVideo().getDescription() != null)
				sb.append(tag("Description", cdata(msg.getVideo()
						.getDescription())));
			sb.append("</Video>\n");
			break;
		case music:
			sb.append("<Music>\n");
			WechatMusic music = msg.getMusic();
			if (music.getTitle() != null)
				sb.append(tag("Title", cdata(music.getTitle())));
			if (music.getDescription() != null)
				sb.append(tag("Description", cdata(music.getDescription())));
			sb.append("</Music>\n");
			break;
		case news:
			sb.append(tag("ArticleCount", "" + msg.getArticles().size()));
			sb.append("<Articles>\n");
			for (WechatArticle article : msg.getArticles()) {
				sb.append("<item>\n");
				if (article.getTitle() != null)
					sb.append(tag("Title", cdata(article.getTitle())));
				if (article.getDescription() != null)
					sb.append(tag("Description",
							cdata(article.getDescription())));
				if (article.getPicUrl() != null)
					sb.append(tag("PicUrl", cdata(article.getPicUrl())));
				if (article.getUrl() != null)
					sb.append(tag("Url", cdata(article.getUrl())));
				sb.append("</item>\n");
			}
			sb.append("</Articles>\n");
			break;
		default:
			break;
		}
		sb.append("</xml>");
		String str = sb.toString();
		log.info("outcome: " + str);
		return str;
	}

	/**
	 * 转换为微信系统识别的XML 更好的是把tag更加抽象为xml结构
	 * 
	 * @throws IOException
	 */
	public static void asXml(Writer writer, WechatResponse msg)
			throws IOException {
		log.info("msg to xml : " + msg.toString());
		Writer out = writer;
		if (DEV_MODE) {
			writer = new StringWriter();
		}
		writer.write("<xml>\n");
		writer.write(tag("ToUserName", cdata(msg.getToUserName())));
		writer.write(tag("FromUserName", cdata(msg.getFromUserName())));
		writer.write(tag("CreateTime", "" + msg.getCreateTime()));
		writer.write(tag("MsgType", cdata(msg.getMsgType())));
		switch (WechatMsgType.valueOf(msg.getMsgType())) {
		case text:
			writer.write(tag("Content", cdata(msg.getContent())));
			break;
		case image:
			writer.write(tag("Image",
					tag("MediaId", msg.getImage().getMediaId())));
			break;
		case voice:
			writer.write(tag("Voice",
					tag("MediaId", msg.getVideo().getMediaId())));
			break;
		case video:
			writer.write("<Video>\n");
			writer.write(tag("MediaId", cdata(msg.getVideo().getMediaId())));
			if (msg.getVideo().getTitle() != null)
				writer.write(tag("Title", cdata(msg.getVideo().getTitle())));
			if (msg.getVideo().getDescription() != null)
				writer.write(tag("Description", cdata(msg.getVideo()
						.getDescription())));
			writer.write("</Video>\n");
			break;
		case music:
			writer.write("<Music>\n");
			WechatMusic music = msg.getMusic();
			if (music.getTitle() != null)
				writer.write(tag("Title", cdata(music.getTitle())));
			if (music.getDescription() != null)
				writer.write(tag("Description", cdata(music.getDescription())));
			writer.write("</Music>\n");
			break;
		case news:
			writer.write(tag("ArticleCount", "" + msg.getArticles().size()));
			writer.write("<Articles>\n");
			for (WechatArticle article : msg.getArticles()) {
				writer.write("<item>\n");
				if (article.getTitle() != null)
					writer.write(tag("Title", cdata(article.getTitle())));
				if (article.getDescription() != null)
					writer.write(tag("Description",
							cdata(article.getDescription())));
				if (article.getPicUrl() != null)
					writer.write(tag("PicUrl", cdata(article.getPicUrl())));
				if (article.getUrl() != null)
					writer.write(tag("Url", cdata(article.getUrl())));
				writer.write("</item>\n");
			}
			writer.write("</Articles>\n");
			break;
		default:
			break;
		}
		writer.write("</xml>");
		String str = writer.toString();
		log.info("outcome: " + str);
		out.write(str);
	}
	
	public static String asJson(WechatResponse msg) {
		StringWriter sw = new StringWriter();
		asJson(sw, msg);
		return sw.toString();
	}
	
	/**
	 * 将一个WxOutMsg转为主动信息所需要的Json文本
	 */
	public static void asJson(Writer writer, WechatResponse msg) {
		NutMap map = new NutMap();
		map.put("touser", msg.getToUserName());
		map.put("msgtype", msg.getMsgType());
		switch (WechatMsgType.valueOf(msg.getMsgType())) {
		case text:
			map.put("text", new NutMap().setv("content", msg.getContent()));
			break;
		case image :
			map.put("image", new NutMap().setv("media_id", msg.getImage().getMediaId()));
			break;
		case voice:
			map.put("voice", new NutMap().setv("media_id", msg.getVoice().getMediaId()));
			break;
		case video:
			NutMap _video = new NutMap();
			_video.setv("media_id", msg.getVideo().getMediaId());
			if (msg.getVideo().getTitle() != null)
				_video.put("title", (msg.getVideo().getTitle()));
			if (msg.getVideo().getDescription() != null)
				_video.put("description", (msg.getVideo().getDescription()));
			map.put("video", _video);
			break;
		case music :
			NutMap _music = new NutMap();
			WechatMusic music = msg.getMusic();
			if (music.getTitle() != null)
				_music.put("title", (music.getTitle()));
			if (music.getDescription() != null)
				_music.put("description", (music.getDescription()));
			if (music.getMusicUrl() != null)
				_music.put("musicurl", (music.getMusicUrl()));
			if (music.gethQMusicUrl() != null)
				_music.put("hqmusicurl", (music.gethQMusicUrl()));
			_music.put("thumb_media_id", (music.getThumbMediaId()));
			break;
		case news:
			NutMap _news = new NutMap();
			List<NutMap> list = new ArrayList<NutMap>();
			for (WechatArticle article : msg.getArticles()) {
				NutMap item = new NutMap();
				if (article.getTitle() != null)
					item.put("title", (article.getTitle()));
				if (article.getDescription() != null)
					item.put("description", (article.getDescription()));
				if (article.getPicUrl() != null)
					item.put("picurl", (article.getPicUrl()));
				if (article.getUrl() != null)
					item.put("url", (article.getUrl()));
				list.add(item);
			}
			_news.put("articles", list);
			map.put("news", _news);
			break;
		default:
			break;
		}
		Json.toJson(writer, map);
	}
	
	/**
	 * 用一个wxHandler处理对应的用户请求
	 * @throws IOException 
	 */
	public static View handle(WechatHandler wechatHandler, HttpServletRequest req) throws IOException {
		if (wechatHandler == null) {
			log.info("wechat handler is NULL");
			return HttpStatusView.HTTP_502;
		}
		if (!wechatHandler.check(req.getParameter("signature"), req.getParameter("timestamp"), req.getParameter("nonce"))) {
			log.info("token is invalid");
			return HttpStatusView.HTTP_502;
		}
		if ("GET".equalsIgnoreCase(req.getMethod())) {
			log.info("GET ? return echostr = " + req.getParameter("echostr"));
			return new ViewWrapper(new RawView(null), req.getParameter("echostr"));
		}
		WechatMsg wechatMsg = WechatInfo.convert(req.getInputStream());
		WechatResponse response = wechatHandler.handle(wechatMsg);
		if (response != null)
			WechatInfo.fix(wechatMsg, response);
		return new ViewWrapper(WechatView.me, response);
	}

	/** 工具类 */
	private WechatInfo() {
	}

	private static final Log log = LogFactory.getLog(WechatMsg.class);
	
	public static void main(String[] args) throws IOException {
		for (Object obj : WechatMsgType.values()) {
			System.out.printf("WechatResponse %s(WxInMsg msg);\n", obj);
		}
		for (Object obj : WechatEventType.values()) {
			System.out.printf("WechatResponse event%s(WxInMsg msg);\n", Strings.upperFirst(obj.toString().toLowerCase()));
		}
		StringWriter sw = new StringWriter();
		asXml(sw, respText(null, "Hi"));
		System.out.println(sw.toString());
	}
	
}
