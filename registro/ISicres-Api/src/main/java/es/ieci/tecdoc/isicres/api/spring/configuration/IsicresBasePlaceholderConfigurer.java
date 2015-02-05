package es.ieci.tecdoc.isicres.api.spring.configuration;

import java.io.File;

import org.springframework.core.io.Resource;

import com.ieci.tecdoc.common.isicres.ConfigFileSubdirPathResolverIsicres;

public class IsicresBasePlaceholderConfigurer
		extends
		es.ieci.tecdoc.fwktd.core.spring.configuration.IeciBasePlaceholderConfigurer {
	

	@Override
	public void setLocations(String[] locations) {

		Resource[] ieciLocations = new Resource[locations.length];
		
		String subdir=ConfigFileSubdirPathResolverIsicres.getSubdir();
		for (int i = 0; i < locations.length; i++) {
			String location = (new StringBuilder()).append(subdir).append(File.separator).append(locations[i]).toString();
			ieciLocations[i] = configurationResourceLoader.loadResource(location, null);
		}

		super.setLocations(ieciLocations);
	}

}
