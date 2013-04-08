package common.manager;

import javax.servlet.http.HttpServletRequest;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;

public abstract class ArchidocServiceBaseTest extends ArchidocBaseTest {

	private ServiceClient serviceClient;

	private AppUser user;

	public ServiceClient getServiceClient() {
		if(serviceClient == null){

			serviceClient = ServiceClient.create(user);
		}

		return serviceClient;
	}

	public AppUser getAppUser(){
		if(user == null){

		}
		return user;
	}


}
