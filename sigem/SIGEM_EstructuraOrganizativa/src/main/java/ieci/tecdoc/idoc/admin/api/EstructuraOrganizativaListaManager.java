package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.volume.VolumeLists;

public class EstructuraOrganizativaListaManager {

	public VolumeLists getListas(String entidad) throws Exception{
		VolumeLists listas= new VolumeLists();
		listas.load(entidad);
	    return listas;
	}
}
