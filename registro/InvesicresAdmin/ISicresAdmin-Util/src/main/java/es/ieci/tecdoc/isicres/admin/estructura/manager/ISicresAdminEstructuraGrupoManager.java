package es.ieci.tecdoc.isicres.admin.estructura.manager;

import es.ieci.tecdoc.isicres.admin.estructura.dao.Group;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Groups;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;

public class ISicresAdminEstructuraGrupoManager {

	public Groups getGrupos(String entidad) throws Exception{
		Groups groups= new Groups();
		groups.loadLite(entidad);
	    return groups;
	}

	public Group getGrupo(int idGrupo, String entidad) throws Exception{
		Group group= ISicresAdminObjectFactory.createGroup();
		group.load(idGrupo, entidad);
	    return group;
	}
}
