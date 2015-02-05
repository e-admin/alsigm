package es.ieci.tecdoc.fwktd.core.spring.configuration;

public interface ConfigFilePathResolver {

	/**
	 * @param fileName
	 * @return
	 */
	public abstract String resolveFullPath(String fileName);
	
	public abstract String resolveFullPath(String fileName,String subDir);

}