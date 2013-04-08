package ieci.tecdoc.sgm.autenticacion.ws.client;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ServicioAutenticacionUsuariosRemoteClientTest extends TestCase {

	public void testAuthenticateUser() {
		fail("Not yet implemented");
	}

	public void testCreateUser() {
		try {
			ieci.tecdoc.sgm.core.services.dto.Entidad oEntidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
			oEntidad.setIdentificador("00001");
			ServicioAutenticacionUsuarios oServicio = LocalizadorServicios.getServicioAutenticacionUsuarios("SIGEM_ServicioAutenticacion.SIGEM.WEBSERVICE");
			DatosUsuario oDatos = new DatosUsuario();
			oDatos.setEmail("00001@00001.com");
			oDatos.setId("user00001");
			oDatos.setLastname("user00001");
			oDatos.setName("user00001");
			oDatos.setPassword("user00001");
			oDatos.setUser("user00001");
			oServicio.createUser(oDatos, oEntidad);
			
			oEntidad.setIdentificador("00002");
			oDatos = new DatosUsuario();
			oDatos.setEmail("00002@00002.com");
			oDatos.setId("user00002");
			oDatos.setLastname("user00002");
			oDatos.setName("user00002");
			oDatos.setPassword("user00002");
			oDatos.setUser("user00002");
			oServicio.createUser(oDatos, oEntidad);
			//prueba
		} catch (SigemException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (Exception e){
			e.printStackTrace();
			Assert.fail();			
		}
		Assert.assertTrue(true);
	}

	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	public void testFindUsers() {
		fail("Not yet implemented");
	}

	public void testGetUser() {
		fail("Not yet implemented");
	}

	public void testUpdateUser() {
		fail("Not yet implemented");
	}

}
