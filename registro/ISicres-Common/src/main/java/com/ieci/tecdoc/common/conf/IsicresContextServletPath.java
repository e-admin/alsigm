package com.ieci.tecdoc.common.conf;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class IsicresContextServletPath {
	
	public static String SERVLET_CONTEXT_PATH=null;
	
	public static String getRealPathFromWebContextResource(String resourcePath){
		String result = (new StringBuilder()).append(SERVLET_CONTEXT_PATH).append(File.separator).append(resourcePath).toString();
		result = FilenameUtils.normalize(result);
		return result;
	}

}
