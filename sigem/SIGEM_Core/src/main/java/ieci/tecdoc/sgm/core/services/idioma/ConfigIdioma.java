package ieci.tecdoc.sgm.core.services.idioma;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigIdioma {
	private static final Logger logger = Logger.getLogger(ConfigIdioma.class);
	public final static String FILE_NAME_PARAM_NAME="Idiomas.properties";
	public final static String FILE_NAME_PRESENTACION_PARAM_NAME="IdiomasPresentacion.properties";
	public final static String SUBDIR_PARAM_NAME="SIGEM_Core";
	
	private static Properties idiomasProperties =null;
	private static Properties idiomasPresentacionProperties=null;
	private String mostrarComboIdioma;
	private String mostrarIdiomaPresentacion;

	static{
			idiomasProperties =ConfigIdioma.getConfigFileIdiomas();
			idiomasPresentacionProperties=ConfigIdioma.getConfigFileIdiomasPresentacion();	
	}
	
	public String getMostrarIdiomaPresentacion() {
		return mostrarIdiomaPresentacion;
	}
	public void setMostrarIdiomaPresentacion(String mostrarIdiomaPresentacion) {
		this.mostrarIdiomaPresentacion = mostrarIdiomaPresentacion;
	}

	public String getMostrarComboIdioma() {
		return mostrarComboIdioma;
	}
	public void setMostrarComboIdioma(String mostrarComboIdioma) {
		this.mostrarComboIdioma = mostrarComboIdioma;
	}
	public Properties getIdiomasProperties() {
		return idiomasProperties;
	}
	public void setIdiomasProperties(Properties idiomasProperties) {
		ConfigIdioma.idiomasProperties = idiomasProperties;
	}
	public Properties getIdiomasPresentacionProperties() {
		return idiomasPresentacionProperties;
	}
	public void setIdiomasPresentacionProperties(
			Properties idiomasPresentacionProperties) {
		ConfigIdioma.idiomasPresentacionProperties = idiomasPresentacionProperties;
	}
	public static Properties getConfigFileIdiomasPresentacion(){
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		Properties result = pathResolver.loadProperties(FILE_NAME_PRESENTACION_PARAM_NAME,SUBDIR_PARAM_NAME);
		
		return result;
	}
	public static Properties getConfigFileIdiomas(){
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		Properties result = pathResolver.loadProperties(FILE_NAME_PARAM_NAME,SUBDIR_PARAM_NAME);

		return result;
	}
}
