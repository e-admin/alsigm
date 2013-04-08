import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.Libros;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficinas;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OrganizacionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.PermisosSicres;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistradorBean;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuariosRegistradores;
import ieci.tecdoc.sgm.rpadmin.ServicioRPAdminAdapter;
import ieci.tecdoc.sgm.rpadmin.ServicioRPAdminDummy;
import junit.framework.TestCase;

/*$Id*/

public class RegistroPresencialAdminTest extends TestCase {

	public static ServicioRPAdminAdapter oServicio = new ServicioRPAdminAdapter();

	public static Entidad entidad = new Entidad();

	static {
		entidad.setIdentificador("00001");
	}

	public void testListarUsuarios() {
		try {
			UsuariosRegistradores users = oServicio.obtenerUsuarios(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testListarOficinasCombo() {
		try {
			OptionsBean users = oServicio.obtenerOficinasCombo(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testListarPerfilesCombo() {
		try {
			OptionsBean users = oServicio.obtenerPerfilesCombo(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testCrearUsuario() {
		try {
			UsuarioRegistradorBean user = new UsuarioRegistradorBean();
			user.setNombre("pruebaAnexaReg");
			user.setAccesoOperaciones(true);
			user.setAdaptacionRegistros(true);
			user.setCiudad("Ciudad 1");
			user.setCodigoPostal("28003");
			user.setIdPerfil(2);
			user.setIntroduccionFecha(true);
			user.setNombreIdentificacion("Surname");
			user.setPrimerApellido("Apellido 1");
			
			int userId = oServicio.crearUsuario(user, entidad);
			
			assertEquals(17, userId);
		} catch (RPAdminException e) {
			fail();
		}
	}
	
	public void testEditarUsuario() {
		try {
			UsuarioRegistradorBean user = new UsuarioRegistradorBean();
			user.setId(17);
			user.setNombre("pruebaAnexaReg");
			user.setAccesoOperaciones(false);
			user.setAdaptacionRegistros(true);
			user.setCodigoPostal("28003");
			user.setIdPerfil(2);
			user.setIntroduccionFecha(true);
			user.setNombreIdentificacion("Surname");
			user.setPrimerApellido("Apellido 1");
			
			oServicio.editarUsuario(user, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}
	
	public void testObtenerUsuario() {
		try {
			UsuarioRegistradorBean user = new UsuarioRegistradorBean();
			user = oServicio.obtenerUsuario(17, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}
	
	public void testEliminarUsuario() {
		try {
			UsuarioRegistradorBean user = new UsuarioRegistradorBean();
			oServicio.eliminarUsuario(13, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}	

	public void testListarOficinasListado() {
		try {
			Oficinas oficinas = oServicio.obtenerOficinas(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testListarEntidadesRegistralesListado() {
		try {
			OptionsBean er = oServicio.obtenerEntidadesRegistralesCombo(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testListarTiposOficinaListado() {
		try {
			OptionsBean tipos = oServicio.obtenerTipoOficinasCombo(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testObtenerOficina() {
		try {
			OficinaBean oficina = new OficinaBean();
			oficina = oServicio.obtenerOficina(9, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}	
	
	public void testObtenerDepartamentosNoOficina() {
		try {
			OptionsBean options = oServicio.obtenerDepartamentosCombo(true, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}
	
	public void testCrearOficina() {
		try {
			OficinaBean oficina = oServicio.obtenerOficina(9, entidad);
			oficina.setCiudad("Beneixama");
			oficina.setDireccion("C/ Mi casa nº 25");
			oficina.setRepresentante("YoMismo");
			oficina.setId(8);
			oficina.setCodigo("005");
			oficina.setNombre("PRUEBA");
			int id = oServicio.crearOficina(oficina, entidad);
			assertEquals(10, id);
		} catch (RPAdminException e) {
			fail();
		}
	}		
	
	public void testEditarOficina() {
		try {
			OficinaBean oficina = oServicio.obtenerOficina(8, entidad);
			oficina.setCiudad("Cuenca");
			oficina.setDireccion("C/ Su casa nº 25");
			oficina.setRepresentante("Otro");
			oficina.setNombre("PRUEBAMODIFICADA");
			oServicio.editarOficina(oficina, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}	
	
	public void testEliminarOficina() {
		try {
			oServicio.eliminarOficina(8, entidad);
		} catch (RPAdminException e) {
			fail();
		}
	}	

	public void testObtenerLibros() {
		try {
			Libros libros = oServicio.obtenerLibros(2,entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testObtenerPermisosOficinasLibro() {
		try {
			PermisosSicres permisos = oServicio.obtenerPermisosOficinasLibro(19, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testOficinasDesasociadasALibro() {
		try {
			oServicio.obtenerOficinasDesasociadasALibro(19, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testObtenerPermisosUsuariosOficinasLibro() {
		try {
			PermisosSicres permisos = oServicio.obtenerPermisosUsuariosOficina(19, 3, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testAsociarOficinaALibro() {
		try {
			oServicio.asociarOficinaALibro(19, new int[]{3, 5}, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testDesAsociarOficinaALibro() {
		try {
			oServicio.desasociarOficinaALibro(19, 3, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testModificarPermisos() {
		try {
			PermisosSicres permisos = new ServicioRPAdminDummy().obtenerPermisosOficinasLibro(0,null);
			oServicio.modificarPermisos(permisos, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testObtenerOrgPadre() {
		try {
			oServicio.obtenerOrganizacionesPadre(entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testObtenerOrgHijos() {
		try {
			oServicio.obtenerHijosOrganizacion(2915, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testObtenerOrganizacion() {
		try {
			oServicio.obtenerOrganizacion(2915, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testCrearOrganizacion() {
		try {
			OrganizacionBean permisos = new ServicioRPAdminDummy().obtenerOrganizacion(1, entidad);
			oServicio.crearOrganizacion(false, permisos, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEditarOrganizacion() {
		try {
			OrganizacionBean permisos = new ServicioRPAdminDummy().obtenerOrganizacion(1, entidad);
			permisos.setNombre("Otro nombre");
			permisos.setCodigoPostal("29003");
			permisos.setId(4897);
			oServicio.editarOrganizacion(permisos, entidad);
		} catch (RPAdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void testEliminarOrganizacion() {
		try {
			oServicio.eliminarOrganizacion(4897, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}	
	
	public void testObtenerDistribuciones() {
		try {
			oServicio.obtenerDistribuciones(4888, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}
	
	public void testEliminarDistribucion() {
		try {
			oServicio.eliminarDistribucion(19, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}
	
	public void testCrearDistribuciones() {
		try {
			oServicio.crearDistribuciones(4888, 1, new int[]{5,10,15}, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}

	public void testCerrar() {
		try {
			oServicio.modificarEstadoLibro(14, "jose", 1, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}
	
	public void testOficinasAsociadasAUsuario() {
		try {
			oServicio.obtenerOficinasAsociadasAUsuario(15, entidad);
			;
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}

	public void testOficinasDesasociadasAUsuario() {
		try {
			oServicio.obtenerOficinasDesasociadasAUsuario(15, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}

	public void testAsociarOficinaAUsuario() {
		try {
			oServicio.asociarOficinasAUsuario(15, 10, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}
	
	public void testDesAsociarOficinaAUsuario() {
		try {
			oServicio.desasociarOficinaAUsuario(15, 5, entidad);
		} catch (RPAdminException e) {
			e.printStackTrace();
		}
	}
	
}
