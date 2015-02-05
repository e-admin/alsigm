package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.Groups;

public class EstructuraOrganizativaGrupoManager {

	public Groups getGrupos(String entidad) throws Exception{
		Groups groups= new Groups();
		groups.loadLite(entidad);
	    return groups;
	}

	public Group getGrupo(int idGrupo, String entidad) throws Exception{
		Group group= ObjFactory.createGroup();
		group.load(idGrupo, entidad);
	    return group;
	}
}
