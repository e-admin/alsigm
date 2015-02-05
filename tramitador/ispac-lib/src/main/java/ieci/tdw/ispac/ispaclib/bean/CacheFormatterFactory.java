package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheFormatterFactory extends BeanFormatterFactory {

	private static CacheFormatterFactory _instance;
	private Map mtablemap;

	private CacheFormatterFactory() throws ISPACException {
		super();
		mtablemap = Collections.synchronizedMap(new HashMap());
	}

	public static synchronized CacheFormatterFactory getInstance() throws ISPACException {
		
		if (_instance == null) {
			_instance = new CacheFormatterFactory();
		}

		return _instance;
	}

	public BeanFormatter getFormatter(String resourcePath) throws ISPACException {

		BeanFormatter formatter = (BeanFormatter) mtablemap.get(resourcePath);

		if (formatter == null) {
			formatter = getBeanFormatterResPath(resourcePath);
			mtablemap.put(resourcePath, formatter);
		}

		return formatter;
	}

	public BeanFormatter getFormatter(EntityApp entityApp) throws ISPACException {

		// Eliminar caché de formateadores
//		String key = getBeanFormatterKey(entityApp);
//		BeanFormatter formatter = (BeanFormatter) mtablemap.get(key);
//		if (formatter == null) {

		BeanFormatter formatter = null;
		
		// Obtener el formateado a partir del XML
		if (StringUtils.isNotBlank(entityApp.getFormatterXML())) {
			formatter = getBeanFormatter(entityApp.getFormatterXML());
		} else if (StringUtils.isNotBlank(entityApp.getFormatter())) {
			formatter = getFormatter(entityApp.getPath() + entityApp.getFormatter());
		}
			
//			mtablemap.put(key, formatter);
//		}

		return formatter;
	}

//	private String getBeanFormatterKey(EntityApp entity) {
//		
//		String key = String.valueOf(entity.getAppId());
//
//		// Comprobar si el entorno es de multientidad
//		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
//		if (info != null){
//			String organizationId = info.getOrganizationId();
//			if (StringUtils.isNotBlank(organizationId)) {
//				key = organizationId + "_" + key;
//			}
//		}
//
//		return key;
//	}
}
