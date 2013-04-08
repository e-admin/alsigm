package com.ieci.tecdoc.utils;

import junit.framework.TestCase;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;


public class ConfigFilePathResolverIsicresTest extends TestCase {
	
	protected ConfigFilePathResolverIsicres configFilePathResolverIsicres;
	protected void setUp() throws Exception {
		super.setUp();
		configFilePathResolverIsicres= ConfigFilePathResolverIsicres.getInstance();
		
		
	}
	
	public void testResolveFullPath(){
		
		String fileName="pruebaFileLoader1.test";
		String fullpath=configFilePathResolverIsicres.resolveFullPath(fileName);
		assertNotNull(fullpath);
		
		Resource fileResource =new FileSystemResource(fullpath);
		assertTrue(fileResource.exists());
		
		
		
		
		
		
		
		
	}

	
	
	
	

	

}
