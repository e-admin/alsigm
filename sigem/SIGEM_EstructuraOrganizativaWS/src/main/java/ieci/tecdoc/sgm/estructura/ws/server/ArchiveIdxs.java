
package ieci.tecdoc.sgm.estructura.ws.server;

/**
 * Bean de índices. 
 */

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ArchiveIdxs  extends RetornoServicio{

	private ArchiveIdx[] archiveIndxsList;

	public ArchiveIdx[] getArchiveIndxsList() {
		return archiveIndxsList;
	}

	public void setArchiveIndxsList(ArchiveIdx[] archiveIndxsList) {
		this.archiveIndxsList = archiveIndxsList;
	}
	
}

