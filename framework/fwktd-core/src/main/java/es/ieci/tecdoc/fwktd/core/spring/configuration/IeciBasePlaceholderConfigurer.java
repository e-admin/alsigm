package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.Resource;

/**
 *
 * PropertyPlaceHolder para spring que permitirá cargar los valores segun el
 * <code>ConfigurationResourceLoader</code> que tenga seteado
 *
 */
public class IeciBasePlaceholderConfigurer extends
		PlatformPropertyPlaceholderConfigurer {

	protected ConfigurationResourceLoader configurationResourceLoader;

	public void setLocations(String[] locations) {

		Resource[] ieciLocations = new Resource[locations.length];

		for (int i = 0; i < locations.length; i++) {
			ieciLocations[i] = configurationResourceLoader.loadResource(
					locations[i], null);
		}

		super.setLocations(ieciLocations);
	}

	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
	}

	public ConfigurationResourceLoader getConfigurationResourceLoader() {
		return configurationResourceLoader;
	}

	public void setConfigurationResourceLoader(
			ConfigurationResourceLoader configurationResourceLoader) {
		this.configurationResourceLoader = configurationResourceLoader;
	}

}
