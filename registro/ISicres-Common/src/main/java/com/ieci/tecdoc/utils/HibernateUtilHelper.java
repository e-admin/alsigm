package com.ieci.tecdoc.utils;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

public class HibernateUtilHelper {

	public String findHibernateConfigFile() {

		String result = null;

		result = findHibernateConfigFile(ConfigFilePathResolverIsicres.HIBERNATE_CFG_FILENAME_DEFAULT);

		return result;

	}

	public String findHibernateConfigFile(String fileHibernateConfigName) {
		String result = null;

		ConfigFilePathResolverIsicres configFilePathResolverIsicres = ConfigFilePathResolverIsicres.getInstance();
		result = configFilePathResolverIsicres
				.resolveFullPath(fileHibernateConfigName);

		return result;

	}

}
