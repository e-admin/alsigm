package es.ieci.tecdoc.fwktd.sir.ws.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import es.ieci.tecdoc.fwktd.sir.ws.utils.SortedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class IdentificadoresIntercambioTestHelper {
	private static Logger logger=LoggerFactory.getLogger(IdentificadoresIntercambioTestHelper.class);
	private static final String fileName="identificadoresIntercambioTest.properties";
	private static SortedProperties identificadores;
	static{
		load();
	}
	
	public static void save() {
		
		try{
			String filePath= new ClassPathResource(fileName).getFile().getAbsolutePath();
			
			identificadores.store(new FileOutputStream(filePath),null);
		}catch(IOException e){
			logger.error("Error en savePersistentHashMap",e);
		}	
	}
	
	@SuppressWarnings("unchecked")
	public static void load(){
		identificadores= new SortedProperties();
		try {
			identificadores.load(new ClassPathResource(fileName).getInputStream());
		} catch (IOException e) {
			logger.error("Error en load",e);
		}
	}
	
	public static void put(String key,String value){
		identificadores.put(key, value);
	}
	
	public static String get(String key){
		return (String)identificadores.get(key);
	}
}
