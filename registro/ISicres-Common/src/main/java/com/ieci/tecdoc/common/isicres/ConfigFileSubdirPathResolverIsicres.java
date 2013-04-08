package com.ieci.tecdoc.common.isicres;

public class ConfigFileSubdirPathResolverIsicres {

	public static String getSubdir(){
		
		return ConfigFilePathResolverIsicres.getInstance().getConfigSubdir();
	}
}
