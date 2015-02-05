package ieci.tecdoc.sgm.admin;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad;
import ieci.tecdoc.sgm.core.services.administracion.AdministracionException;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.admin.beans.AccionesMultientidad;
import ieci.tecdoc.sgm.admin.beans.Aplicaciones;
import ieci.tecdoc.sgm.admin.beans.PerfilImpl;
import ieci.tecdoc.sgm.admin.beans.Perfiles;
import ieci.tecdoc.sgm.admin.beans.UsuarioImpl;
import ieci.tecdoc.sgm.admin.beans.Usuarios;


public class AdministracionManagerAdapter implements ServicioAdministracion {


	public Entidad[] getEntidades(String usuario) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		Entidad[] entidad = null;
		try{
			entidad = roleManager.getEntidades(usuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return entidad;
	}

	public void altaUsuario(Usuario usuario) throws AdministracionException {
		UsuarioImpl poUsuario = getUsuario(usuario);
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		try{
			userManager.altaUsuario(poUsuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
	}

	public void actualizaUsuario(Usuario usuario) throws AdministracionException {
		UsuarioImpl poUsuario = getUsuario(usuario);
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		try{
			userManager.actualizarUsuario(poUsuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}	
	}

	public Usuario obtenerUsuario(String usuario) throws AdministracionException {
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		Usuario poUsuario = null;
		try{
			poUsuario = getServiceUsuario(userManager.obtenerUsuario(usuario));
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return poUsuario;
	}

	public boolean autenticaUsuario(String usuario, String password) throws AdministracionException {
		boolean autentica = false;
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		try{
			autentica = userManager.autenticaUsuario(usuario, password);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return autentica;
	}
	
	public void actualizaPasswordUsuario(Usuario usuario, String password) throws AdministracionException {
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		UsuarioImpl poUsuario= getUsuario(usuario);
		try{
			userManager.actualizaPassword(poUsuario, password);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			throw getAdministracionExcepcion(e);
		}

	}

	public void bajaUsuario(Usuario usuario) throws AdministracionException {
		UsuarioImpl poUsuario = getUsuario(usuario);
		AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
		try{
			userManager.bajaUsuario(poUsuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
	}

	public void altaPerfil(Perfil perfil) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		PerfilImpl poPerfil = getPerfil(perfil);
		try{
			roleManager.nuevoPerfil(poPerfil);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
	}

	public void bajaPerfil(Perfil perfil) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		PerfilImpl poPerfil = getPerfil(perfil);
		try{
			roleManager.eliminarPerfil(poPerfil);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
	}

	public void bajaPerfil(String idEntidad, String idUsuario) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		try{
			roleManager.eliminarPerfil(idEntidad, idUsuario);
		}catch (ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}		

	}
	
	public void actualizaPerfiles(String[] idAplicaciones, String idUsuario, String idEntidad) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		try{
			roleManager.actualizarPerfil(idAplicaciones, idUsuario, idEntidad);
		}catch (ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}		
	}

	public void bajaPerfil(String idUsuario) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		try{
			roleManager.eliminarPerfil(idUsuario);
		}catch (ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}		

	}
	
	public Perfil[] getPerfiles(String usuario) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		Perfiles perfiles = null;
		try{
			perfiles = roleManager.obtenerPerfiles(usuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return getServicePerfiles(perfiles);
	}

	public Perfil[] getPerfiles(String usuario, String entidad) throws AdministracionException {
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		Perfiles perfiles = null;
		try{
			perfiles = roleManager.obtenerPerfiles(entidad,usuario);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return getServicePerfiles(perfiles);
	}

	public Aplicacion getAplicacion(String idAplicacion) throws AdministracionException {
		Aplicacion aplicacion= null;
		try{
			aplicacion = getServiceAplicacion(AdministracionAplicacionManager.obtenerAplicacion(idAplicacion));
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return aplicacion;
	}

	public Aplicacion[] obtenerAplicaciones(String idUsuario, String idEntidad) throws AdministracionException {
		Aplicacion[] aplicacion= null;
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		try{
			aplicacion = getServiceAplicaciones(roleManager.obtenerAplicaciones(idUsuario, idEntidad));
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return aplicacion;
	}

	public Aplicacion[] getAplicaciones(String usuario, String entidad) throws AdministracionException {
		Aplicacion[] aplicacion= null;
		AdministracionPerfilManager roleManager = new AdministracionPerfilManager();
		try{
			aplicacion = getServiceAplicaciones(roleManager.obtenerAplicaciones(usuario, entidad));
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return aplicacion;
	}

	public Usuario[] getUsuariosEntidad(String idEntidad) throws AdministracionException {
		Usuario[] usuario = null;
		try{
			AdministracionUsuarioManager userManager = new AdministracionUsuarioManager();
			Usuarios usuarios = userManager.obtenerUsuarios(idEntidad);
			usuario = getServiceUsuarios(usuarios);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return usuario;
	}

	public Aplicacion[] getAplicaciones() throws AdministracionException {
		Aplicacion[] aplicacion = null;
		try{
			Aplicaciones aplicaciones = AdministracionAplicacionManager.obtenerAplicaciones();
			aplicacion = getServiceAplicaciones(aplicaciones);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return aplicacion;	
	}

	public AccionMultientidad[] getAccionesMultientidad() throws AdministracionException {
		AccionMultientidad[] accionMultientidad = null;
		try{
			AccionesMultientidad aplicaciones = AdministracionAccionMultientidadManager.obtenerAccionesMultientidad();
			accionMultientidad = getServiceAccionesMultientidad(aplicaciones);
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return accionMultientidad;	
	}

	public AccionMultientidad getAccionMultientidad(String idAccion) throws AdministracionException {
		AccionMultientidad accionMultientidad = null;
		try{
			accionMultientidad = getServiceAccionMultientidad(AdministracionAccionMultientidadManager.obtenerAccionMultientidad(idAccion));
		}catch(ieci.tecdoc.sgm.admin.AdministracionException e){
			getAdministracionExcepcion(e);
		}
		return accionMultientidad;
	}
	
	private PerfilImpl getPerfil(ieci.tecdoc.sgm.core.services.administracion.Perfil oPerfil){
		if (oPerfil == null)
			return null;

		PerfilImpl poPerfil = new PerfilImpl();
		poPerfil.setIdAplicacion(oPerfil.getIdAplicacion());
		poPerfil.setIdEntidad(oPerfil.getIdEntidad());
		poPerfil.setIdUsuario(oPerfil.getIdUsuario());

		return poPerfil;
	}

	private UsuarioImpl getUsuario(ieci.tecdoc.sgm.core.services.administracion.Usuario oUsuario){
		if (oUsuario == null)
			return null;

		UsuarioImpl poUsuario= new UsuarioImpl();
		poUsuario.setUsuario(oUsuario.getUsuario());
		poUsuario.setPassword(oUsuario.getPassword());
		poUsuario.setNombre(oUsuario.getNombre());
		poUsuario.setApellidos(oUsuario.getApellidos());
		poUsuario.setFechaAlta(oUsuario.getFechaAlta());

		return poUsuario;
	}
	private ieci.tecdoc.sgm.core.services.administracion.Aplicacion getServiceAplicacion(ieci.tecdoc.sgm.admin.interfaces.Aplicacion poAplicacion){
		if (poAplicacion == null)
			return null;

		ieci.tecdoc.sgm.core.services.administracion.Aplicacion oAplicacion = new ieci.tecdoc.sgm.core.services.administracion.Aplicacion();
		oAplicacion.setIdentificador(poAplicacion.getIdentificador());
		oAplicacion.setDefinicion(poAplicacion.getDefinicion());
		oAplicacion.setContextoApp(poAplicacion.getContextoApp());
		oAplicacion.setServidorApp(poAplicacion.getServidor());
		oAplicacion.setPuertoApp(poAplicacion.getPuertoApp());
		oAplicacion.setProtocolo(poAplicacion.getProtocolo());
		return oAplicacion;
	}

	private ieci.tecdoc.sgm.core.services.administracion.Aplicacion[] getServiceAplicaciones(Aplicaciones poAplicaciones){
		if (poAplicaciones == null)
			return new ieci.tecdoc.sgm.core.services.administracion.Aplicacion[0];

		ieci.tecdoc.sgm.core.services.administracion.Aplicacion[] oAplicacion = new ieci.tecdoc.sgm.core.services.administracion.Aplicacion[poAplicaciones.count()];
		for (int i=0;i<poAplicaciones.count();i++){
			oAplicacion[i] = new ieci.tecdoc.sgm.core.services.administracion.Aplicacion();
			oAplicacion[i].setIdentificador(poAplicaciones.get(i).getIdentificador());
			oAplicacion[i].setDefinicion(poAplicaciones.get(i).getDefinicion());
			oAplicacion[i].setContextoApp(poAplicaciones.get(i).getContextoApp());
			oAplicacion[i].setServidorApp(poAplicaciones.get(i).getServidor());
			oAplicacion[i].setPuertoApp(poAplicaciones.get(i).getPuertoApp());
			oAplicacion[i].setProtocolo(poAplicaciones.get(i).getProtocolo());
		}
		return oAplicacion;
	}
	
	private ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad getServiceAccionMultientidad(ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad poAccionMultientidad){
		if (poAccionMultientidad == null)
			return null;

		ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad oAccionMultientidad = new ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad();
		oAccionMultientidad.setIdentificador(poAccionMultientidad.getIdentificador());
		oAccionMultientidad.setNombre(poAccionMultientidad.getNombre());
		oAccionMultientidad.setClaseConfiguradora(poAccionMultientidad.getClaseConfiguradora());
		oAccionMultientidad.setClaseEjecutora(poAccionMultientidad.getClaseEjecutora());
		oAccionMultientidad.setInfoAdicional(poAccionMultientidad.getInfoAdicional());
		return oAccionMultientidad;
	}

	private ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad[] getServiceAccionesMultientidad(AccionesMultientidad poAccionesMultientidad){
		if (poAccionesMultientidad == null)
			return new ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad[0];

		ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad[] oAccionMultientidad = new ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad[poAccionesMultientidad.count()];
		for (int i=0;i<poAccionesMultientidad.count();i++){
			oAccionMultientidad[i] = getServiceAccionMultientidad(poAccionesMultientidad.get(i)); 
		}
		return oAccionMultientidad;
	}


	private ieci.tecdoc.sgm.core.services.administracion.Perfil[] getServicePerfiles(Perfiles poPerfiles){
		if (poPerfiles == null)
			return new ieci.tecdoc.sgm.core.services.administracion.Perfil[0];

		ieci.tecdoc.sgm.core.services.administracion.Perfil[] oPerfil = new ieci.tecdoc.sgm.core.services.administracion.Perfil[poPerfiles.count()];
		for (int i=0;i<poPerfiles.count();i++){
			oPerfil[i] = new ieci.tecdoc.sgm.core.services.administracion.Perfil();
			oPerfil[i].setIdAplicacion(poPerfiles.get(i).getIdAplicacion());
			oPerfil[i].setIdEntidad(poPerfiles.get(i).getIdEntidad());
			oPerfil[i].setIdUsuario(poPerfiles.get(i).getIdUsuario());
		}
		return oPerfil;
	}

	private ieci.tecdoc.sgm.core.services.administracion.Usuario[] getServiceUsuarios(Usuarios poUsuarios){
		if (poUsuarios == null)
			return new ieci.tecdoc.sgm.core.services.administracion.Usuario[0];

		ieci.tecdoc.sgm.core.services.administracion.Usuario[] oUsuario= new ieci.tecdoc.sgm.core.services.administracion.Usuario[poUsuarios.count()];
		for (int i=0;i<poUsuarios.count();i++){
			oUsuario[i]=new ieci.tecdoc.sgm.core.services.administracion.Usuario();
			oUsuario[i].setUsuario(poUsuarios.get(i).getUsuario());
			oUsuario[i].setNombre(poUsuarios.get(i).getNombre());
			oUsuario[i].setApellidos(poUsuarios.get(i).getApellidos());
			oUsuario[i].setPassword(poUsuarios.get(i).getPassword());
			oUsuario[i].setFechaAlta(poUsuarios.get(i).getFechaAlta());
		}
		return oUsuario;
	}

	private ieci.tecdoc.sgm.core.services.administracion.Usuario getServiceUsuario(ieci.tecdoc.sgm.admin.interfaces.Usuario poUsuario){
		if (poUsuario == null)
			return null;

		ieci.tecdoc.sgm.core.services.administracion.Usuario oUsuario = new ieci.tecdoc.sgm.core.services.administracion.Usuario();
		oUsuario.setApellidos(poUsuario.getApellidos());
		oUsuario.setFechaAlta(poUsuario.getFechaAlta());
		oUsuario.setNombre(poUsuario.getNombre());
		oUsuario.setPassword(poUsuario.getPassword());
		oUsuario.setUsuario(poUsuario.getUsuario());

		return oUsuario;
	}

	private AdministracionException getAdministracionExcepcion(ieci.tecdoc.sgm.admin.AdministracionException poException){
		if(poException == null){
			return new AdministracionException(AdministracionException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_ADMINISTRATION_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new AdministracionException(Long.valueOf(cCodigo.toString()).longValue(), poException);

	}

}
