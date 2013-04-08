package ieci.tecdoc.sgm.consolidacion.config;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class ConfigLoader {

	private ConcurrentHashMap<String,ConsolidacionConfig> configuracionEntidades=new ConcurrentHashMap<String,ConsolidacionConfig>();
	private static ConfigLoader instance;
	public static ConfigLoader getInstance(){
		if(instance==null){
			instance=new ConfigLoader();
		}
		return instance;
	}
	
	public ConsolidacionConfig getConfig(String idEntidad){
		ConsolidacionConfig config=null;
		if(StringUtils.isBlank(idEntidad)) return null;
		config=configuracionEntidades.get(idEntidad);
		if(config==null){
			config=new ConsolidacionConfig();
			configuracionEntidades.put(idEntidad, config);
		}
		config.loadConfiguration(idEntidad);
		return config;
	}
	

}
