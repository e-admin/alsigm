package ieci.tecdoc.sgm.admsistema.proceso.configuracion;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;


public class Editar {
	
	private static final Logger logger = Logger.getLogger(Editar.class);
			
	public static String editar(String[] parametros, byte[] fichero, String tipoServidor) {
		try {
			InputStream is=new ByteArrayInputStream(fichero);
		    Hashtable hash=new Hashtable();
		    boolean addEntidad = false;
		    
		    String[] basesDeDatos = null;
		    String[] aliasBasesDeDatos = null;
		    int[][] posicionArray = null;
		    
		    if (Defs.SERVIDOR_TOMCAT_7.equals(tipoServidor)) {
		    	basesDeDatos = EditarServer7Xml.basesDeDatos;
			    aliasBasesDeDatos = EditarServer7Xml.aliasBasesDeDatos;
			    posicionArray = EditarServer7Xml.posicionArray;
		    } else if (Defs.SERVIDOR_TOMCAT_6.equals(tipoServidor)) {
		    	basesDeDatos = EditarServer6Xml.basesDeDatos;
			    aliasBasesDeDatos = EditarServer6Xml.aliasBasesDeDatos;
			    posicionArray = EditarServer6Xml.posicionArray;
		    } else if (Defs.SERVIDOR_TOMCAT_5.equals(tipoServidor)) {
		    	basesDeDatos = EditarServerXml.basesDeDatos;
			    aliasBasesDeDatos = EditarServerXml.aliasBasesDeDatos;
			    posicionArray = EditarServerXml.posicionArray;
		    } else if (Defs.SERVIDOR_JBOSS.equals(tipoServidor)) { 
		    	basesDeDatos = EditarDSXml.basesDeDatos;
			    aliasBasesDeDatos = EditarDSXml.aliasBasesDeDatos;
			    posicionArray = EditarDSXml.posicionArray; 
		    }
		    
		    for(int i = 0; i < basesDeDatos.length; i++) {
		    	if (parametros[6] != null && !"".equals(parametros[6])) {
		    		String aliadbd = aliasBasesDeDatos[i];
		    		DataConfigTomcat cdt = new DataConfigTomcat();
		    		if (Defs.PLUGIN_BASE_DATOS_POSTGRES.equals(parametros[6]) ||
		    			Defs.PLUGIN_BASE_DATOS_SQLSERVER.equals(parametros[6])) {
				    	String bd=basesDeDatos[i];
				    	cdt.setUsername(parametros[3]);
				    	cdt.setPassword(parametros[4]);
				    	if (Defs.PLUGIN_BASE_DATOS_POSTGRES.equals(parametros[6])) { 
				    		cdt.setDriverClassName(Defs.DRIVER_POSTGRES);
				    		cdt.setUrl("jdbc:postgresql://" + parametros[1] + ":" + parametros[2] + "/" + bd);
				    	} else {
				    		cdt.setDriverClassName(Defs.DRIVER_SQLSERVER);
				    		cdt.setUrl("jdbc:sqlserver://" + parametros[1] + ":" + parametros[2] + ";DatabaseName=" + bd);
				    	}
				    	hash.put(aliadbd, cdt);
				    	addEntidad = true;
		    		} else {
		    			String bd=parametros[5];
				    	cdt.setUsername(parametros[posicionArray[i][0]]);
				    	cdt.setPassword(parametros[posicionArray[i][1]]);
				    	if (Defs.PLUGIN_BASE_DATOS_ORACLE.equals(parametros[6])) {
				    		cdt.setDriverClassName(Defs.DRIVER_ORACLE);
				    		cdt.setUrl("jdbc:oracle:thin:@" + parametros[1] + ":" + parametros[2] + ":" + bd);
				    	} else if (Defs.PLUGIN_BASE_DATOS_DB2.equals(parametros[6])) {
				    		cdt.setDriverClassName(Defs.DRIVER_DB2);
				    		cdt.setUrl("jdbc:db2://" + parametros[1] + ":" + parametros[2] + "/" + bd);
				    	}
		    		}
		    		cdt.setName(aliadbd);
		    		hash.put(aliadbd, cdt);
		    	}
		    }
		    
		    String contenido = null;
		    if (Defs.SERVIDOR_TOMCAT_7.equals(tipoServidor)) {
		    	EditarServer7Xml esx = new EditarServer7Xml(is);
		    	esx.añadirResource(hash, parametros[0], addEntidad);
		    	esx.añadirResourceLink(parametros[0]);
		    	contenido = esx.escribir();
		    } else if (Defs.SERVIDOR_TOMCAT_6.equals(tipoServidor)) {
		    	EditarServer6Xml esx = new EditarServer6Xml(is);
		    	esx.añadirResource(hash, parametros[0], addEntidad);
		    	esx.añadirResourceLink(parametros[0]);
		    	contenido = esx.escribir();
			} else if (Defs.SERVIDOR_TOMCAT_5.equals(tipoServidor)) {
		    	EditarServerXml esx = new EditarServerXml(is);
		    	esx.añadirResource(hash, parametros[0], addEntidad);
		    	esx.añadirResourceLink(parametros[0]);
		    	contenido = esx.escribir();
		    } else if (Defs.SERVIDOR_JBOSS.equals(tipoServidor)) { 
		    	EditarDSXml edsx = new EditarDSXml(is);
		    	edsx.añadirResource(hash, parametros[0], addEntidad);
		    	contenido = edsx.escribir();
		    }
		    
		    return contenido;
		} catch(Exception e) {
			logger.error("Se ha producido un error al generar el fichero de configuración del servidor.", e.getCause());
			return null;
		}
	}
}
