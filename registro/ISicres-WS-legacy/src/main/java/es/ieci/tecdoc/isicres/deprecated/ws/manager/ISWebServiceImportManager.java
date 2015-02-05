package es.ieci.tecdoc.isicres.deprecated.ws.manager;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;

import java.util.List;

public interface ISWebServiceImportManager {

	public RegisterInfo importFolder(UserInfo user, List<PersonInfo> inter,
			Folder folder, Entidad entidad);
}
