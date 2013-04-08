package es.ieci.tecdoc.isicres.deprecated.ws.service.impl;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.deprecated.ws.manager.ISWebServiceImportManager;

public class ISWebServiceImportSoapImpl implements ServicioRegistroWebService {

	protected ISWebServiceImportManager isWebServiceImportManager;

	
	public RegisterInfo importFolder(UserInfo user, List<PersonInfo> inter,
			Folder folder, Entidad entidad) {
		Assert.notNull(user, "No se puede importar un registro sin indicar el usuario");
		Assert.notNull(folder,"No se puede importar un registro si no se indica el parametro Folder");
		Assert.notNull(folder.getFields(), "No se puede importar un registro sin fecha de registro");
		Assert.notNull(folder.getFields().getFields(), "No se puede importar un registro sin fecha de registro");
		Assert.notEmpty(folder.getFields().getFields().getItem(), "No se puede importar un registro sin fecha de registro");
		return getIsWebServiceImportManager().importFolder(user, inter, folder, entidad);
	
	}


	public ISWebServiceImportManager getIsWebServiceImportManager() {
		return isWebServiceImportManager;
	}


	public void setIsWebServiceImportManager(
			ISWebServiceImportManager isWebServiceImportManager) {
		this.isWebServiceImportManager = isWebServiceImportManager;
	}

	
}
