package ieci.tecdoc.sgm.nt.updater;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdaterConfig {
	private static final Logger logger = Logger.getLogger(UpdaterConfig.class);

	private static String lapso = null;
	
	public static String UPDATER_LAPSO="UPDATER_LAPSO";
	
	static{
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("updater-config-beans.xml");
			Map propiedades=(Map) context.getBean("NOTIFICACIONES.UPDATER_CONFIG");
			
			Iterator it=propiedades.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry map=(Map.Entry)it.next();
				if(map.getKey().equals(UPDATER_LAPSO))
					lapso=(String)map.getValue();
			}
		} catch (Exception e) {
			logger.error("Error inicializando configuración de puertos", e);
		}
	}
	
	public static String getLapso(){
		return lapso; 
	}
}

