package com.sunrise22.galaxy.usage;

import java.io.File;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@At("/f")
@AdaptBy(type=UploadAdaptor.class)
@IocBean
@InjectName
public class HiAction {
	private static Log log = LogFactory.getLog(HiAction.class.getName());
	
	@At
	public String upload(@Param("file") File tempFile) {
		log.info("Get a file --> "+tempFile + " size=" +tempFile.length());
		return "upload 1 file = " + tempFile.getAbsolutePath() + " size=" +tempFile.length();
	}
}
