package ieci.tecdoc.sgm.admsistema.proceso.importacion;

import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;
import ieci.tecdoc.sgm.admsistema.proceso.Proceso;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Importar implements Runnable {
	
	/**
	 * Logger de la clase
	 */
	private static final Logger logger = Logger.getLogger(Importar.class);
			
	/**
	 * Parámetros de exportación
	 */
	private Map parameters = null;

	/**
	 * Gestor de importación de entidades
	 */
	private IProcessManager importProcess = null;


	/**
	 * Constructor
	 * @param params Parámetros de exportación
	 */
	public Importar(Map params) {
		super();
		setParameters(params);
		
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("admon-entidades-context.xml"));
		importProcess = (IProcessManager) beanFactory.getBean("importProcess");
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void run() {

		try {
			
			if (importProcess != null) {
				importProcess.execute(parameters);
			} else {
				logger.error("No se ha especificado el gestor de importación de entidades");
			}
	
		} catch (Throwable t) {
			logger.error("Error en el proceso de importación", t);
		}
	}

	public static boolean importar(Map params) {
		
		Thread process = new Thread(new Importar(params));
		process.start();

	    return true;
	}

	public static List obtenerProcesosImportaciones() {
		String directorio = System.getProperties().getProperty("user.home") + File.separator;
		File d=new java.io.File(directorio+File.separator+Defs.EXPORTAR);
		Hashtable exportaciones=new Hashtable();
	    File f[]=d.listFiles();
	    List listado = new ArrayList();
	    
	    if (f != null) {
		    for(int i=0;i<f.length;i++) {
		    	boolean acabado=false;
		        if(!f[i].isDirectory())
		        	continue;
		        String contenido;
		        try {
		        	InputStream is=new FileInputStream(d.getAbsolutePath()+File.separator+f[i].getName()+File.separator+Defs.ESTATUS);
		        	byte[]b=new byte[is.available()];
		        	is.read(b);
		        	contenido=new String(b);
		        	exportaciones.put(f[i].getName(), contenido);
	
		        	if(contenido.indexOf("[FIN]")>0)
		        		acabado=true;
		        	else
		        		continue;
		        } catch (Exception e) {
		        	contenido="archivo no encontrado "+Defs.ESTATUS;
		        	acabado=true;
		        }
		        if (listado.size() == 0)
		        	listado.add(new Proceso("", ""));
		        listado.add(new Proceso(Utilidades.obtenerNombreDescriptivoProceso(f[i].getName(), Defs.EXPORTAR), f[i].getName()));
		    }
	    }
	    return listado;
	}

}
