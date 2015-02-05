package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.util.Map;

import org.springframework.core.io.Resource;

public interface ConfigurationResourceLoader {
	public Resource loadResource(String resourceName, Map params);
	public String pathResolver(String resourceName, Map params);

}
