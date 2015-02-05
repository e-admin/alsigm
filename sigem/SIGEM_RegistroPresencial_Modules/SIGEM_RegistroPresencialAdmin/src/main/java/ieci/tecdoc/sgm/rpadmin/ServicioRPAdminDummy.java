package ieci.tecdoc.sgm.rpadmin;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.Campos;
import ieci.tecdoc.sgm.core.services.rpadmin.Contador;
import ieci.tecdoc.sgm.core.services.rpadmin.Contadores;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribucion;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribuciones;
import ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Filtros;
import ieci.tecdoc.sgm.core.services.rpadmin.InformeBean;
import ieci.tecdoc.sgm.core.services.rpadmin.InformesBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Libro;
import ieci.tecdoc.sgm.core.services.rpadmin.LibroBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Libros;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficina;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficinas;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizacion;
import ieci.tecdoc.sgm.core.services.rpadmin.OrganizacionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizaciones;
import ieci.tecdoc.sgm.core.services.rpadmin.PermisoSicres;
import ieci.tecdoc.sgm.core.services.rpadmin.PermisosSicres;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;
import ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Transporte;
import ieci.tecdoc.sgm.core.services.rpadmin.Transportes;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistrador;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistradorBean;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuariosRegistradores;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioAgregadoImpl;

import java.util.Date;

public class ServicioRPAdminDummy implements ServicioRPAdmin {

	private int i = 1;
	private int z = 1;
	private int y = 1;
	private int p = 1;

	public UsuariosRegistradores obtenerUsuarios(Entidad entidad)
			throws RPAdminException {
		UsuariosRegistradores usuarios = new UsuariosRegistradores();

		UsuarioRegistrador usuario = new UsuarioRegistrador();
		usuario.setId(1);
		usuario.setNombre("Usuario1");
		usuario.setNombreCompleto("UsuarioCompleto1");
		usuario.setOficinaRegistro("Oficina1");
		usuario.setPerfil("Operador de registro");
		usuarios.add(usuario);

		UsuarioRegistrador usuario2 = new UsuarioRegistrador();
		usuario2.setId(2);
		usuario2.setNombre("Usuario2");
		usuario2.setNombreCompleto("UsuarioCompleto2");
		usuario2.setOficinaRegistro("Oficina2");
		usuario2.setPerfil("Superusuario");
		usuarios.add(usuario2);

		return usuarios;
	}

	public OptionsBean obtenerUsuarios(int id, int tipo, Entidad entidad)
	throws RPAdminException {
		OptionsBean options = new OptionsBean();
		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Usuario Oficina 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Usuario Oficina 2");
		options.add(option2);

		return options;
	}

	public UsuariosRegistradores obtenerUsuariosAsociacion(int deptId, Entidad entidad)	throws RPAdminException{
		UsuariosRegistradores users = new UsuariosRegistradores();
		SicresUsuarioAgregadoImpl usuario = new SicresUsuarioAgregadoImpl();
		usuario.setId(1);
		usuario.setNombre("Usuario1");
		usuario.setNombreCompleto("UsuarioCompleto1");
		usuario.setAgregado(false);
		users.add(usuario);

		SicresUsuarioAgregadoImpl usuario2 = new SicresUsuarioAgregadoImpl();
		usuario2.setId(2);
		usuario2.setNombre("Usuario2");
		usuario2.setNombreCompleto("UsuarioCompleto2");
		usuario2.setAgregado(true);
		users.add(usuario2);

		return users;
	}

	public UsuariosRegistradores obtenerUsuariosOficina(int idOficina, boolean usuarios,
			boolean agregados, Entidad entidad)	throws RPAdminException {

		UsuariosRegistradores users = new UsuariosRegistradores();

		SicresUsuarioAgregadoImpl usuario = new SicresUsuarioAgregadoImpl();
		usuario.setId(1);
		usuario.setNombre("Usuario1");
		usuario.setNombreCompleto("UsuarioCompleto1");
		usuario.setAgregado(false);
		users.add(usuario);

		SicresUsuarioAgregadoImpl usuario2 = new SicresUsuarioAgregadoImpl();
		usuario2.setId(2);
		usuario2.setNombre("Usuario2");
		usuario2.setNombreCompleto("UsuarioCompleto2");
		usuario2.setAgregado(true);
		users.add(usuario2);

		return users;
	}

	public UsuarioRegistradorBean obtenerUsuario(int id, Entidad entidad) throws RPAdminException {
		UsuarioRegistradorBean usuario = new UsuarioRegistradorBean();
		usuario.setAccesoOperaciones(true);
		usuario.setAdaptacionRegistros(false);
		usuario.setAltaPersonas(true);
		usuario.setArchivoRegistros(false);
		usuario.setCambioDestinoDistribuidos(false);
		usuario.setCambioDestinoRechazados(true);
		usuario.setCiudad("Ciudad " + id);
		usuario.setCodigoPostal("28003");
		usuario.setDireccion("Direccion" + id);
		usuario.setEmail("usuariodummy" + id + "@dummy.es");
		usuario.setFax("22222222");
		usuario.setId(id);
		usuario.setIdPerfil(1);
		usuario.setIntroduccionFecha(true);
		usuario.setModificacionCampos(true);
		usuario.setModificacionFecha(false);
		usuario.setModificaPersonas(true);
		usuario.setNombreIdentificacion("Nombre " + id);
		usuario.setPrimerApellido("1erApellido " + id);
		usuario.setProvincia("Provincia " + id);
		usuario.setRechazoRegistros(false);
		usuario.setSegundoApellido("2oApellido " + id);
		usuario.setTelefono("11111111");
		return usuario;
	}

	public int crearUsuario(UsuarioRegistradorBean usuario, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return 1;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public void editarUsuario(UsuarioRegistradorBean usuario, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public void eliminarUsuario(int id, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public OptionsBean obtenerOficinasCombo(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("001 - Oficina Registro");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("002 - Oficina Registro 2");
		options.add(option2);

		return options;
	}

	public OptionsBean obtenerPerfilesCombo(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Operador de registro");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Administrador de libro de registro");
		options.add(option2);

		OptionBean option3 = new OptionBean();
		option3.setCodigo("3");
		option3.setDescripcion("Superusuario");
		options.add(option3);

		return options;
	}

	public Oficinas obtenerOficinas(Entidad entidad) throws RPAdminException {
		Oficinas oficinas = new Oficinas();

		Oficina oficina = new Oficina();
		oficina.setId(1);
		oficina.setNombre("Oficina1");
		oficina.setAbreviatura("Abreviatura1");
		oficina.setCodigo("001");
		oficina.setEntidadRegistral("Entidad Registral 1");
		oficinas.add(oficina);

		Oficina oficina2 = new Oficina();
		oficina2.setId(2);
		oficina2.setNombre("Usuario2");
		oficina2.setAbreviatura("Abreviatura2");
		oficina2.setCodigo("002");
		oficina2.setEntidadRegistral("Entidad Registral 2");
		oficinas.add(oficina2);

		return oficinas;
	}

	public OficinaBean obtenerOficina(int id, Entidad entidad) throws RPAdminException {
		OficinaBean oficina = new OficinaBean();
		oficina.setAbreviatura("Abriviatura " + id);
		oficina.setCiudad("Ciudad " + id);
		oficina.setCodigo("001");
		oficina.setCodigoPostal("28003");
		oficina.setDireccion("Direccion " + 1);
		oficina.setUri("oficina" + id + "dummy@dummy.es");
		oficina.setFax("22222222");
		oficina.setFecha(new Date());
		oficina.setId(id);
		oficina.setIdEntidadRegistral(2);
		oficina.setIdTipoOficina(2);
		oficina.setNombre("Nombre " + id);
		oficina.setProvincia("Provinvia " + id);
		oficina.setRepresentante("Representante " + id);
		oficina.setSello("Sello " + id);
		oficina.setTelefono("11111111");
		return oficina;
	}

	public int crearOficina(OficinaBean oficina, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return 1;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public void editarOficina(OficinaBean oficina, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public void eliminarOficina(int id, Entidad entidad) throws RPAdminException {
		if(Math.random()*100>10)
			return;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public OptionsBean obtenerDepartamentosCombo(boolean oficinas, Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Departamento 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Departamento 2");
		options.add(option2);

		return options;
	}

	public OptionsBean obtenerDepartamentosRaiz(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Departamento 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Departamento 2");
		options.add(option2);

		return options;
	}

	public OptionsBean obtenerGruposRaiz(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Grupo 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Grupo 2");
		options.add(option2);

		return options;
	}

	public OptionsBean obtenerGruposHijos(int idGrupo, Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo(String.valueOf(y));
		option.setDescripcion("Grupo 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo(String.valueOf(p+1));
		option2.setDescripcion("Grupo 2");
		options.add(option2);
		y++;
		p++;
		return options;
	}

	public OptionsBean obtenerDepartamentosHijos(int idDepartamento, Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo(String.valueOf(i));
		option.setDescripcion("Departamento 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo(String.valueOf(z+1));
		option2.setDescripcion("Departamento 2");
		options.add(option2);
		i++;
		z++;
		return options;
	}
	public OptionsBean obtenerEntidadesRegistralesCombo(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Entidad registral 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Entidad registral 2");
		options.add(option2);

		return options;
	}

	public OptionsBean obtenerTipoOficinasCombo(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();

		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Tipo Oficina 1");
		options.add(option);

		OptionBean option2 = new OptionBean();
		option2.setCodigo("2");
		option2.setDescripcion("Tipo Oficina 2");
		options.add(option2);

		return options;
	}

	public Libros obtenerLibros(int tipoLibro, Entidad entidad) throws RPAdminException {
		Libros libros = new Libros();

		Libro libro1 = new Libro();
		libro1.setId(1);
		libro1.setNombre(tipoLibro + "Libro 1");
		libro1.setTipo(tipoLibro);
		libros.add(libro1);

		Libro libro2 = new Libro();
		libro1.setId(2);
		libro1.setNombre(tipoLibro + "Libro 2");
		libro1.setTipo(tipoLibro);
		libros.add(libro2);

		return libros;
	}

	public PermisosSicres obtenerPermisosOficinasLibro(int bookId, Entidad entidad) throws RPAdminException {
		PermisosSicres permisos = new PermisosSicres();

		PermisoSicres permiso1 = new PermisoSicres();
		permiso1.setId(3);
		permiso1.setConsultar(true);
		permiso1.setCrear(true);
		permiso1.setModificar(false);
		permiso1.setNombre("Oficina 1");
		permiso1.setTipo(PermisoSicres.TIPO_OFICINA);
		permiso1.setIdBook(19);
		permisos.add(permiso1);

		PermisoSicres permiso2 = new PermisoSicres();
		permiso2.setId(5);
		permiso2.setConsultar(true);
		permiso2.setCrear(false);
		permiso2.setModificar(true);
		permiso2.setNombre("Oficina 2");
		permiso2.setTipo(PermisoSicres.TIPO_OFICINA);
		permiso2.setIdBook(19);
		permisos.add(permiso2);

		return permisos;
	}

	public void asociarOficinaALibro(int bookId, int[] destId, Entidad entidad) throws RPAdminException {
		return;
	}

	public void desasociarOficinaALibro(int bookId, int destId, Entidad entidad) throws RPAdminException {
		return;
	}

	public void modificarPermisos(PermisosSicres permisos, Entidad entidad) throws RPAdminException {
		return;
	}

	public PermisosSicres obtenerPermisosUsuariosOficinas(int bookId, int idsOfic[], Entidad entidad) throws RPAdminException {
		PermisosSicres permisos = new PermisosSicres();

		PermisoSicres permiso1 = new PermisoSicres();
		permiso1.setId(1);
		permiso1.setConsultar(true);
		permiso1.setCrear(true);
		permiso1.setModificar(false);
		permiso1.setNombre("Usuario 1");
		permiso1.setTipo(PermisoSicres.TIPO_USUARIO);
		permiso1.setIdBook(19);
		permisos.add(permiso1);

		PermisoSicres permiso2 = new PermisoSicres();
		permiso2.setId(2);
		permiso2.setConsultar(true);
		permiso2.setCrear(false);
		permiso2.setModificar(true);
		permiso2.setNombre("Usuario 2");
		permiso2.setTipo(PermisoSicres.TIPO_USUARIO);
		permiso2.setIdBook(19);
		permisos.add(permiso2);

		return permisos;
	}



	public PermisosSicres obtenerPermisosUsuariosLdapOficinas(int bookId,
			int[] idsOfic, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public int crearOrganizacion(boolean esPrimerNivel, OrganizacionBean organizacion, Entidad entidad) throws RPAdminException {
		return 1;
	}

	public void editarOrganizacion(OrganizacionBean organizacion, Entidad entidad) throws RPAdminException {
		return;
	}

	public void eliminarOrganizacion(int orgId, Entidad entidad) throws RPAdminException {
		return;
	}

	public Organizaciones obtenerHijosOrganizacion(int orgId, Entidad entidad) throws RPAdminException {
		Organizaciones lista = new Organizaciones();

		Organizacion bean1 = new Organizacion();
		bean1.setId(1);
		bean1.setIdPadre(-1);
		bean1.setAbreviatura("ABR 1");
		bean1.setFechaAlta(new Date());
		bean1.setNombre("Organizacion 1");
		bean1.setUid("001");
		bean1.setTipo(Math.abs(orgId));
		lista.add(bean1);

		Organizacion bean2 = new Organizacion();
		bean2.setId(2);
		bean2.setIdPadre(-1);
		bean2.setAbreviatura("ABR 2");
		bean2.setFechaAlta(new Date());
		bean2.setNombre("Organizacion 2");
		bean2.setUid("002");
		bean2.setTipo(Math.abs(orgId));
		lista.add(bean2);

		return lista;
	}

	public OrganizacionBean obtenerOrganizacion(int orgId, Entidad entidad) throws RPAdminException {
		OrganizacionBean bean = new OrganizacionBean();
		bean.setAbreviatura("ABR 1");
		bean.setCif("12345678S");
		bean.setCiudad("Ciudad");
		bean.setCodigoPostal("28003");
		bean.setDireccion("Direccion");
		bean.setFax("Fax");
		bean.setFechaAlta(new Date());
		bean.setId(1);
		bean.setIdPadre(-1);
		bean.setNombre("Nombre");
		bean.setProvincia("Provincia");
		bean.setTelefono("Telefono");
		bean.setTipo(1);
		bean.setUid("301");
		bean.setUri("http://www.mozicas.com");
		return bean;
	}

	public Organizaciones obtenerOrganizacionesPadre(Entidad entidad) throws RPAdminException {
		Organizaciones lista = new Organizaciones();

		Organizacion bean1 = new Organizacion();
		bean1.setId(-1);
		bean1.setIdPadre(0);
		bean1.setAbreviatura("ABR -1");
		bean1.setFechaAlta(new Date());
		bean1.setNombre("Tipo Unidad 1");
		bean1.setUid("101");
		bean1.setTipo(1);
		lista.add(bean1);

		Organizacion bean2 = new Organizacion();
		bean2.setId(-2);
		bean2.setIdPadre(0);
		bean2.setAbreviatura("ABR -2");
		bean2.setFechaAlta(new Date());
		bean2.setNombre("TipoUnidad 2");
		bean2.setUid("201");
		bean2.setTipo(2);
		lista.add(bean2);

		return lista;
	}

	public void crearDistribuciones(int idOrg, int idTipo, int[] ids, Entidad entidad) throws RPAdminException {
		return;
	}

	public void eliminarDistribucion(int id, Entidad entidad) throws RPAdminException {
		return;
	}

	public Distribuciones obtenerDistribuciones(int orgId, Entidad entidad) throws RPAdminException {
		Distribuciones lista = new Distribuciones();

		Distribucion bean1 = new Distribucion();
		bean1.setId(1);
		bean1.setIdDestino(1);
		bean1.setIdOrganizacion(1);
		bean1.setTipoDestino(1);
		bean1.setNombre("Usuario 1");
		lista.add(bean1);

		Distribucion bean2 = new Distribucion();
		bean2.setId(2);
		bean2.setIdDestino(2);
		bean2.setIdOrganizacion(1);
		bean2.setTipoDestino(1);
		bean2.setNombre("Usuario 2");
		lista.add(bean2);

		return lista;
	}

	public OptionsBean obtenerListas(Entidad entidad) throws RPAdminException {
		OptionsBean options = new OptionsBean();
		OptionBean option = new OptionBean();
		option.setCodigo("1");
		option.setDescripcion("Lista de Libros en Uso");
		options.add(option);
		OptionBean option1 = new OptionBean();
		option1.setCodigo("2");
		option1.setDescripcion("Lista de Libros Backup");
		options.add(option1);
		return options;
	}

	public Oficinas obtenerOficinasDesasociadasALibro(int bookId, Entidad entidad) throws RPAdminException {
		return null;
	}

	public void asociarListaALibro(int idBook, int idLista, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public LibroBean obtenerLibroBean(int idBook, Entidad entidad) throws RPAdminException {
		LibroBean libro = new LibroBean();
		libro.setId(1);
		libro.setIdLista(1);
		libro.setNombre("Registro de entrada");
		libro.setTipo(1);
		return libro;
	}

	public void crearLibro(Libro libro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
	}

	public void editarLibro(Libro libro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void modificarEstadoLibro(int idBook, int idEstado, String libro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void eliminarLibro(int idBook, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Campos obtenerCampos(int tipoFiltro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public OptionsBean obtenerOperadores(int tipoFiltro, int tipoCampo, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public int crearLibro(LibroBean libro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void editarContadorCentral(int anyo, int tipo, int contador, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void editarContadoresOficinas(int anyo, int tipo, Contador contadores, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void editarLibro(LibroBean libroBean, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void modificarEstadoLibro(int idBook, String usuario, int idEstado, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public int obtenerContadorCentral(int anyo, int tipo, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Contadores obtenerContadoresOficinas(int anyo, int tipo, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void configurarFiltro(int tipoFiltro, int tipoLibro, int idLibro, int idUserOfic, Filtros filtros, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void editarContadoresOficinas(int tipo, Contadores contadores, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Campos obtenerCampos(int tipoFiltro, int tipoLibro, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public Filtros obtenerFiltros(int tipoFiltro, int tipoLibro, int idLibro, int idUserOfic, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public OptionsBean obtenerOperadores(String tipoCampo, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void asociarOficinasAUsuario(int idUser, int idOfic, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void desasociarOficinaAUsuario(int idUser, int idOfic, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Oficinas obtenerOficinasAsociadasAUsuario(int idUser, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuario(int idUser, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}


	public void asociarOficinaPreferenteAUsuario(int userId, int idOficPref,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Integer obtenerIdOficinaPreferenteUsuario(int userId, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public Oficina obtenerOficinaAsociadaADeptoUsuario(int arg0, Entidad arg1)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void asociarUsuarioAOficinas(String[] idsUser, int idOfic,
			String entidad) throws RPAdminException {
		// TODO Auto-generated method stub
	}

	public void actualizarNumeracionOficinaAsociadaALibro(int idBook,
			int idOfic, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public int crearTransporte(Transporte arg0, Entidad arg1)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void editarTransporte(Transporte arg0, Entidad arg1)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void eliminarTransporte(int arg0, Entidad arg1)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Transporte obtenerTransporte(int arg0, Entidad arg1)
			throws RPAdminException {
		return null;
	}

	public Transportes obtenerTransportes(Entidad arg0) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public int crearTipoAsunto(TipoAsuntoBean arg0, Entidad arg1)
			throws RPAdminException {
		if(Math.random()*100>10)
			return 1;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public void editarTipoAsunto(TipoAsuntoBean arg0, Entidad arg1)
			throws RPAdminException {
		return;
	}

	public void eliminarTipoAsunto(int arg0, Entidad arg1)
			throws RPAdminException {
		return;
	}

	public TipoAsuntoBean obtenerTipoAsunto(int arg0, Entidad arg1)
			throws RPAdminException {

		TipoAsuntoBean tipoAsunto = new TipoAsuntoBean();
		tipoAsunto.setAllOfics(1);
		tipoAsunto.setCode("Codigo:" + arg0);
		tipoAsunto.setCreationDate(new Date());

		// TODO Auto-generated method stub
		return tipoAsunto;
	}

	public TiposAsuntoBean obtenerTiposAsunto(Entidad arg0)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void asociarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void desasociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void editarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Oficinas obtenerOficinasParaLista(Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public int crearUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void importarDepartamentos(int deptId, boolean isSelected,
			String idUnidad, Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void importarGruposLdap(String nodeDn, int maxChildrenLdap,
			int treeType, boolean isSelected, String idUnidad, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public UsuariosRegistradores obtenerUsuariosLdap(Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public UsuarioRegistradorBean obtenerUsuarioLdap(int ldapguid,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void eliminarUsuarioLdap(int id, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void editarUsuarioLdap(UsuarioRegistradorBean usuario,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public Oficinas obtenerOficinasUsuarioLdap(String ldapguid, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuarioLdap(String ldapguid,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public UsuariosRegistradores obtenerUsuariosOficinaLdap(int deptId,
			boolean usuarios, boolean agregados, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public UsuariosRegistradores obtenerUsuariosLdapAsociacion(int deptId,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public PermisosSicres obtenerPermisosUsuarios(int bookId, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public PermisosSicres obtenerPermisosUsuariosLdap(int bookId,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public OrganizacionBean obtenerOrganizacionByCode(String code,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}


	// INFORMES
	public InformesBean obtenerInformes(Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public InformeBean obtenerInforme(int id, Entidad entidad, OptionsBean perfiles)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public OptionsBean obtenerTiposInformesCombo(Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void editarInforme(InformeBean informe, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public int crearInforme(InformeBean informeBean, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		if(Math.random()*100>10)
			return 1;
		else
			throw new RPAdminException("Excepcion aleatoria");
	}

	public InformeBean descargarInforme(int id, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void eliminarInforme(int id, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void eliminarOficinaLDAP(int id, Entidad entidad)
			throws RPAdminException {
		// TODO Auto-generated method stub

	}

	public void desasociarOficinaAUsuarioLDAP(int idUser, int idOfic,
			Entidad entidad) throws RPAdminException {
		// TODO Auto-generated method stub

	}
}