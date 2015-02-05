package ieci.tecdoc.sgm.catalogo.ws.client;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import junit.framework.TestCase;

public class CatalogoTramitesWSRemoteClientTest extends TestCase {

	ieci.tecdoc.sgm.core.services.dto.Entidad entidad = null;
	{
		entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador("00001");
		entidad.setNombre("Ayto00001");
	}
	
	public void testGetService() {
		fail("Not yet implemented");
	}

	public void testSetService() {
		fail("Not yet implemented");
	}

	public void testAddProcedure() {
		fail("Not yet implemented");
	}

	public void testGetProcedure() {
		fail("Not yet implemented");
	}

	public void testQuery() {
		fail("Not yet implemented");
	}

	public void testDeleteProcedure() {
		fail("Not yet implemented");
	}

	public void testUpdateProcedure() {
		fail("Not yet implemented");
	}

	public void testIsDocumentReferenced() {
		fail("Not yet implemented");
	}

	public void testAddProcedureDocument() {
		fail("Not yet implemented");
	}

	public void testDeleteProcedureDocument() {
		fail("Not yet implemented");
	}

	public void testGetDocument() {
		fail("Not yet implemented");
	}

	public void testAddDocument() {
		fail("Not yet implemented");
	}

	public void testDeleteDocument() {
		fail("Not yet implemented");
	}

	public void testUpdateDocument() {
		fail("Not yet implemented");
	}

	public void testGetDocuments() {
		fail("Not yet implemented");
	}

	public void testGetProcedureDocuments() {
		fail("Not yet implemented");
	}

	public void testGetProcedureDocument() {
		fail("Not yet implemented");
	}

	public void testUpdateProcedureDocument() {
		fail("Not yet implemented");
	}

	public void testGetProcedures() {
		try {
			ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites("SIGEM_ServicioCatalogoTramites.SIGEM.WEBSERVICE");
			oServicio.getProcedures(entidad);
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetDocumentfromCode() {
		fail("Not yet implemented");
	}

	public void testGetAddressee() {
		fail("Not yet implemented");
	}

	public void testAddAddressee() {
		fail("Not yet implemented");
	}

	public void testDeleteAddressee() {
		fail("Not yet implemented");
	}

	public void testUpdateAddressee() {
		fail("Not yet implemented");
	}

	public void testGetAddressees() {
		fail("Not yet implemented");
	}

	public void testGetHook() {
		fail("Not yet implemented");
	}

	public void testAddHook() {
		fail("Not yet implemented");
	}

	public void testDeleteHook() {
		fail("Not yet implemented");
	}

	public void testUpdateHook() {
		fail("Not yet implemented");
	}

	public void testGetHooks() {
		try {
			ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites("SIGEM_ServicioCatalogoTramites.SIGEM.WEBSERVICE");
			oServicio.getHooks(entidad);
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetHooksByType() {
		try {
			ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites("SIGEM_ServicioCatalogoTramites.SIGEM.WEBSERVICE");
			oServicio.getHooksByType(1, entidad);
		} catch (SigemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetHookType() {
		fail("Not yet implemented");
	}

	public void testAddHookType() {
		fail("Not yet implemented");
	}

	public void testDeleteHookType() {
		fail("Not yet implemented");
	}

	public void testUpdateHookType() {
		fail("Not yet implemented");
	}

	public void testGetHookTypes() {
		fail("Not yet implemented");
	}

	public void testGetAuthHooks() {
		fail("Not yet implemented");
	}

	public void testAddAuthHooks() {
		fail("Not yet implemented");
	}

	public void testDeleteAuthHooks() {
		fail("Not yet implemented");
	}

	public void testUpdateAuthHooks() {
		fail("Not yet implemented");
	}

	public void testGetAuthHook() {
		fail("Not yet implemented");
	}

}
