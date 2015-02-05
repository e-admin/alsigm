package ieci.tecdoc.sgm.core.log.web;

import java.io.File;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;


/**
 * Servlet para la inicializacin de log4j en las aplicaciones de sigem
 * Deberá estar definido como un servlet que se autoarranque y que sea el primero de ellos
 * para ello deberemos tener en el web.xml una entrada del tipo:
 * 
 * 
 * 
 * <servlet>
    <servlet-name>log4j-init</servlet-name>
    <servlet-class>ieci.tecdoc.sgm.core.log.web.Log4jInitServlet</servlet-class>
    <init-param>
      <param-name>subdir</param-name>
      <param-value>/subdirectorioDondeSeEncuentraElFichero</param-value>
    </init-param>
    
    <init-param>
      <param-name>fileName</param-name>
      <param-value>log4j.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  
  
  NOTA: el pararámetro del servlet  "subdir" deberá ser el nombre de la aplicacion ejemplo: /SIGEM_AplicacionWeb
  por lo que el archivo en el proyecto war deberá ir bajo la ruta
  /src/main/resources/SIGEM_AplicacionWeb

 *
 */

public class Log4jInitServlet extends HttpServlet {
	
	public final static String SUBDIR_PARAM_NAME="subdir";
	
	public final static String FILE_NAME_PARAM_NAME="fileName";
	
	
	protected String subdir;
	
	protected String fileName;
			
	private static final long serialVersionUID = 1L;
	

	public void init() throws ServletException {
		
		this.subdir=this.getInitParameter(SUBDIR_PARAM_NAME);
		
		this.fileName=this.getInitParameter(FILE_NAME_PARAM_NAME);
		
		configureLog4j();
		

}
	
	protected void configureLog4j() {
		
		
		String file = getLo4jFilePath();

		if (file != null) {

			if (new File(file).exists()) {
				
				String extension=FilenameUtils.getExtension(file);
				
				if ("properties".equalsIgnoreCase(extension)){
					PropertyConfigurator.configure(file);
				}
				

				if ("xml".equalsIgnoreCase(extension)) {
					DOMConfigurator.configure(file);
				}
				

				Logger.getLogger(Log4jInitServlet.class).info(
						"Log4j cargado [" + file + "]");
			} else {
				Logger.getLogger(Log4jInitServlet.class).info(
						"Log4j no encontrado [" + file + "]");
			}
		}

	}
	
	protected String getLo4jFilePath(){
		
		String result="";
		
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		result = pathResolver.resolveFullPath(fileName,subdir);
		
		return result;
		
	}

	public String getSubdir() {
		return subdir;
	}

	public void setSubdir(String subdir) {
		this.subdir = subdir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}