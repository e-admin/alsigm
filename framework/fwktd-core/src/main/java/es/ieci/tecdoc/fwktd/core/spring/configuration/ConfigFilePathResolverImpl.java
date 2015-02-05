package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Esta clase necesitará una archivo de spring del tipo: <beans>
 * 
 * <bean name="configurationResourceLoader" id="configurationResourceLoader"
 * class="es.ieci.tecdoc.fwktd.core.spring.configuration.IeciSystemConfigurationResourceLoaderImpl"
 * >
 * 
 * </bean>
 * 
 * <!-- Bean de configuracion --> <bean id="configurationMapBean"
 * class="org.springframework.beans.factory.config.MapFactoryBean"> <property
 * name="sourceMap"> <map> <entry key="CONFIG_SUBDIR" value=""/> </map>
 * </property> </bean>
 * 
 * La parte del bean de configuracion puede ser opcional segun el constructor
 * que usemos ya que podremos cargar el subdirectorio por parametro o bien por
 * fichero de spring
 * 
 */
public class ConfigFilePathResolverImpl implements ConfigFilePathResolver {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfigFilePathResolverImpl.class);

	protected static String SPRING_CONFIG_BEAN_DEFAULT = "configurationResourceLoader";

	protected static String CONFIGURATION_BEAN_DEFAULT = "configurationMapBean";

	protected static String CONFIG_SUBDIR_KEY_DEFAULT = "CONFIG_SUBDIR";

	/**
	 * nombre del fichero de spring a cargar
	 */
	protected String springBeansFileName;

	/**
	 * nombre del bean que contiene el <code>ConfigurationResourceLoader</code>
	 */
	protected String springConfigBean;

	/**
	 * nombre del bean de spring que contiene una map con la parametros de
	 * configuracion
	 */
	protected String configurationBean;

	/**
	 * key del map que se ha obtenido del <code>configurationBean</code> que se
	 * utilizara para realizar la busqueda del fichero
	 */
	protected String configSubDirKey;

	/**
	 * valor del subdirectorio, en esta implementación se obtiene mediante
	 * spring
	 */
	protected String configSubdir;

	/**
	 * contexto de spring
	 */
	protected ApplicationContext context;

	public ConfigFilePathResolverImpl(String springBeansFileName) {
		this.springBeansFileName = springBeansFileName;
		this.springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		this.configSubDirKey = CONFIG_SUBDIR_KEY_DEFAULT;
		this.configurationBean = CONFIGURATION_BEAN_DEFAULT;

		initContext();
		this.configSubdir = getConfigSubdirFromConfigurationBean();

	}
	
	public ConfigFilePathResolverImpl(ApplicationContext context) {
		this.springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		this.configSubDirKey = CONFIG_SUBDIR_KEY_DEFAULT;
		this.configurationBean = CONFIGURATION_BEAN_DEFAULT;

		this.context=context;
		this.configSubdir = getConfigSubdirFromConfigurationBean();

	}

	/**
	 * Constructor que obtiene el subdirectorio a concatenar al fichero que se
	 * busca a través del parámetro
	 * 
	 * @param springBeansFileName
	 * @param springConfigBean
	 * @param configSubdir
	 */
	public ConfigFilePathResolverImpl(String springBeansFileName,
			String springConfigBean, String configSubdir) {
		this.springBeansFileName = springBeansFileName;
		this.springConfigBean = springConfigBean;
		this.configSubdir = configSubdir;

		initContext();
	}
	/**
	 * Constructor que obtiene el subdirectorio a concatenar al fichero que se
	 * busca a través del parámetro
	 * 
	 * @param context
	 * @param springConfigBean
	 * @param configSubdir
	 */
	public ConfigFilePathResolverImpl(ApplicationContext context,String springConfigBean, String configSubdir) {
		this.context=context;
		this.springConfigBean = springConfigBean;
		this.configSubdir = configSubdir;
		
	}

	/**
	 * Constructor que obtiene el subdirectorio a concatenar a traves del
	 * fichero de spring
	 * 
	 * @param springBeansFileName
	 * @param springConfigBean
	 * @param configSubDirKey
	 * @param configurationBean
	 */
	public ConfigFilePathResolverImpl(String springBeansFileName,
			String springConfigBean, String configSubDirKey,
			String configurationBean) {
		this.springBeansFileName = springBeansFileName;
		this.springConfigBean = springConfigBean;
		this.configSubDirKey = configSubDirKey;
		this.configurationBean = configurationBean;

		initContext();
		this.configSubdir = getConfigSubdirFromConfigurationBean();

	}
	
	/**
	 * Constructor que obtiene el subdirectorio a concatenar a traves del
	 * fichero de spring
	 * 
	 * @param context
	 * @param springConfigBean
	 * @param configSubDirKey
	 * @param configurationBean
	 */
	public ConfigFilePathResolverImpl(ApplicationContext context,
			String springConfigBean, String configSubDirKey,
			String configurationBean) {
		this.context=context;
		this.springConfigBean = springConfigBean;
		this.configSubDirKey = configSubDirKey;
		this.configurationBean = configurationBean;
		this.configSubdir = getConfigSubdirFromConfigurationBean();

	}

	protected void initContext() {
		this.context = new ClassPathXmlApplicationContext(springBeansFileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver
	 * #resolveFullPath(java.lang.String)
	 */
	public String resolveFullPath(String fileName) {
		String result = null;

		ConfigurationResourceLoader cfgResourceLoader = (ConfigurationResourceLoader) context
				.getBean(springConfigBean);

		// recreamos el path relativo

		String subdir = getConfigSubdir();
		String relativePath = subdir + File.separator + fileName;

		// resolvemos el path
		result = cfgResourceLoader.pathResolver(relativePath, null);

		return result;
	}

	public String resolveFullPath(String fileName, String subDir) {

		String newFileName = subDir + File.separator + fileName;
		String result = this.resolveFullPath(newFileName);

		return result;
	}

	/**
     * método para obtener el subdirectorio, en este caso a través del bean de
	 * configuración
	 * 
	 * @return
	 */
	public String getConfigSubdirFromConfigurationBean() {
		String result = "";
		Map mapConfiguration = (Map) context.getBean(configurationBean);
		result = (String) mapConfiguration.get(configSubDirKey);

		return result;
	}

	public String getSpringBeansFileName() {
		return springBeansFileName;
	}

	public void setSpringBeansFileName(String springBeansFileName) {
		this.springBeansFileName = springBeansFileName;
	}

	public String getSpringConfigBean() {
		return springConfigBean;
	}

	public void setSpringConfigBean(String springConfigBean) {
		this.springConfigBean = springConfigBean;
	}

	public String getConfigSubDirKey() {
		return configSubDirKey;
	}

	public void setConfigSubDirKey(String configSubDirKey) {
		this.configSubDirKey = configSubDirKey;
	}

	public String getConfigurationBean() {
		return configurationBean;
	}

	public void setConfigurationBean(String configurationBean) {
		this.configurationBean = configurationBean;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public void setConfigSubdir(String configSubdir) {
		this.configSubdir = configSubdir;
	}

	public String getConfigSubdir() {
		return configSubdir;
	}

}
