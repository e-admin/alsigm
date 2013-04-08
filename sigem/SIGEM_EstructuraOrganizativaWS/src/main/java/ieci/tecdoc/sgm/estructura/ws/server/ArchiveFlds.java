package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ArchiveFlds  extends RetornoServicio{
	
	private ArchiveFld[] archiveFldsList;

	public ArchiveFld[] getArchiveFldsList() {
		return archiveFldsList;
	}

	public void setArchiveFldsList(ArchiveFld[] archiveFldsList) {
		this.archiveFldsList = archiveFldsList;
	}
}
