package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MockConfigurationResourceLoaderImpl implements
		ConfigurationResourceLoader {
	private static final Logger logger = LoggerFactory
			.getLogger(MockConfigurationResourceLoaderImpl.class);

	public Resource loadResource(String resourceName, Map params) {
		ClassPathResource result = new ClassPathResource(
				"es/ieci/tecdoc/fwktd/core/spring/configuration/"
						+ resourceName);
		return result;
	}

	public String pathResolver(String resourceName, Map params) {
		String result = null;

		Resource resource = this.loadResource(resourceName, null);
		try {
			result = resource.getURL().getPath();
		} catch (IOException e) {
			String message = e.getLocalizedMessage();
			logger.warn(message, e);
		}

		return result;
	}

}
