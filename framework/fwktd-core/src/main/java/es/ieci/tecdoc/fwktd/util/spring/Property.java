package es.ieci.tecdoc.fwktd.util.spring;

import java.util.Properties;

/**
 * Clase de utilidad para almacenar el valor de una propiedad a obtener de un Properties.
 * Un ejemplo de uso sería un caso en el que se necesitara establecer a un atributo de una clase
 * de terceros un valor obtenido de una propiedad de un fichero de propiedades,
 * haciendo esto mediante la definición de beans de spring, no siendo posible establecer la propiedad
 * mediante PropertyPlaceholderConfigurer de la forma: ${propiedad}.
 * Ej:<br/>
 * <pre>&lt;bean id="fwktd_time_configurationResourceLoaderImpl" name="fwktd_time_configurationResourceLoader"
 * 	  class="es.ieci.tecdoc.fwktd.core.spring.configuration.IeciSystemConfigurationResourceLoaderImpl"/&gt;
 * &lt;bean id="fwktd_time_configurationFactoryBeanImpl" name="fwktd_time_configurationFactoryBean"
 *       class="es.ieci.tecdoc.fwktd.core.config.business.MultiEntityPropertiesFactory"&gt;
 * 	&lt;property name="configurationResourceLoader" ref="fwktd_time_configurationResourceLoader"/&gt;
 * 	&lt;property name="fileName" value="fwktd-time/fwktd-time.properties"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="fwktd_time_configurationFactoryImpl" name="fwktd_time_configurationFactory"
 *       factory-bean="fwktd_time_configurationFactoryBean"
 *       factory-method="getProperties" scope="prototype"&gt;
 * &lt;/bean&gt;
 * <b>&lt;bean id="fwktd_time_jndiBaseName" class="es.ieci.tecdoc.fwktd.util.spring.Property" lazy-init="true" scope="prototype"&gt;
 * 	&lt;constructor-arg ref="fwktd_time_configurationFactory"/&gt;
 * 	&lt;constructor-arg value="fwktd-time.jdbc.datasource"/&gt;
 * &lt;/bean&gt;</b>
 * &lt;bean id="fwktd_time_jndiBaseNamenFactoryImpl" name="fwktd_time_jndiBaseNameFactory"
 *       factory-bean="fwktd_time_jndiBaseName"
 *       factory-method="getProperty" lazy-init="true" scope="prototype"&gt;
 * &lt;/bean&gt;
 * &lt;bean id="fwktd_time_dataSourceImpl" name="fwktd_time_dataSource"
 * 	class="es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityJndiDatasource" lazy-init="true" scope="prototype"&gt;
 * 	&lt;property name="jndiBaseName"  ref="fwktd_time_jndiBaseNameFactory" /&gt;
 * &lt;/bean&gt;</pre>
 * @author Iecisa
 */
public class Property {

	String property;

	public Property(Properties properties, String propertyName){
		property = (String) properties.get(propertyName);
	}

	public String getProperty(){
		return property;
	}

	public void setProperty(String property){
		this.property = property;
	}
}
