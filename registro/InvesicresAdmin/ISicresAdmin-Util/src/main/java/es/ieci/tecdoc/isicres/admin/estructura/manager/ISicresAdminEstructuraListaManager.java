package es.ieci.tecdoc.isicres.admin.estructura.manager;

import es.ieci.tecdoc.isicres.admin.estructura.dao.VolumeLists;


public class ISicresAdminEstructuraListaManager {

	public VolumeLists getListas(String entidad) throws Exception{
		VolumeLists listas= new VolumeLists();
		listas.load(entidad);
	    return listas;
	}
}
