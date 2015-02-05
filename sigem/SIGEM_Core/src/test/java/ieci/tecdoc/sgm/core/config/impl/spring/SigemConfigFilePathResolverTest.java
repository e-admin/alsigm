package ieci.tecdoc.sgm.core.config.impl.spring;

import junit.framework.TestCase;

public class SigemConfigFilePathResolverTest extends TestCase {

	protected SigemConfigFilePathResolver resolver;
	
	
	public void testConfigFilePathResolverResolveFullPath(){
		String configSubdir="SIGEM_Core";
		this.resolver=SigemConfigFilePathResolver.getInstance(); 
		String fileName="SIGEM_spring.properties";
		String path =resolver.resolveFullPath(fileName,configSubdir);
		assertNotNull(path);
	}

}
