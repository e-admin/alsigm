package ieci.tecdoc.sgm.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.admin.AdministracionException;
import ieci.tecdoc.sgm.admin.beans.Aplicaciones;
import ieci.tecdoc.sgm.admin.beans.PerfilImpl;
import ieci.tecdoc.sgm.admin.beans.Perfiles;
import ieci.tecdoc.sgm.admin.database.PerfilDatos;
import ieci.tecdoc.sgm.admin.interfaces.Perfil;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
/**
 * $Id: AdministracionPerfilManager.java,v 1.1.2.6 2008/05/08 07:02:09 jnogales Exp $
 */

public class AdministracionPerfilManager {

	private static final Logger logger = Logger.getLogger(AdministracionPerfilManager.class);

	public AdministracionPerfilManager(){
	}
	
	public void nuevoPerfil(PerfilImpl poPerfil) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Creando Perfil...");
		}
		if(poPerfil == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos = new PerfilDatos(poPerfil);
		try {
			oDatos.insert();
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil creado.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error creando nuevo Perfil.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error creando nuevo Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
	}
	
	public void eliminarPerfil(PerfilImpl poPerfil) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Eliminando Perfil...");
		}		
		if(poPerfil== null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}			
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos=new PerfilDatos(poPerfil);
		try {
			oDatos.delete();
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil eliminado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error eliminando Perfil.", e);
			}
		}
		catch(Exception e) {
			logger.error("Error eliminando Perfil.", e);
		}
	}
	
	public void eliminarPerfil(String idEntidad, String idUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Eliminando Perfil...");
		}		
		if(idEntidad== null || idUsuario==null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}			
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos=new PerfilDatos();
		try {
			oDatos.delete(idUsuario, idEntidad);
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil eliminado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error eliminando Perfil.", e);
			}
		}
		catch(Exception e) {
			logger.error("Error eliminando Perfil.", e);
		}
	}
	
	public void eliminarPerfil(String idUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Eliminando Perfil de usuario...");
		}		
		if(idUsuario==null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}			
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos=new PerfilDatos();
		try {
			oDatos.delete(idUsuario);
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil eliminado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error eliminando Perfil de usuario.", e);
			}
		}
		catch(Exception e) {
			logger.error("Error eliminando Perfil de usuario.", e);
		}
	}

	public void actualizarPerfil(String[] idAplicaciones, String idUsuario, String idEntidad) throws AdministracionException {
		if(logger.isDebugEnabled()) {
			logger.debug("Actualizando Perfil...");
		}				
		if(idAplicaciones == null || idUsuario == null || idEntidad == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos = new PerfilDatos();
		try {
			oDatos.update(idAplicaciones, idUsuario, idEntidad);
			if(logger.isDebugEnabled()) {
				logger.debug("Perfil actualizado.");
			}
		}
		catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error actualizando Perfil.", e);
			}
			throw e;
		}
		catch(Exception e) {
			logger.error("Error actualizando Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
	}

	
	public Perfiles obtenerPerfiles(String idEntidad, String idUsuario) throws AdministracionException {
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Perfil...");
		}				
		if(idEntidad == null || idUsuario == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		PerfilDatos oDatos = new PerfilDatos();
		Perfiles perfiles = null;
		try{
			perfiles = oDatos.load(idEntidad, idUsuario);
			if(logger.isDebugEnabled()){
				logger.debug("Perfil obtenido.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error obteniendo Perfil.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error obteniendo Perfil.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return perfiles;
	}
	
	public Perfiles obtenerPerfiles(String idUsuario) throws AdministracionException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo perfíles de usuario...");
		}				
		if(idUsuario == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		Perfiles perfiles = null;
		PerfilDatos oDatos = new PerfilDatos();
		try{
			perfiles = oDatos.load(idUsuario);
		}catch(Exception e){
			logger.error("Error obteniendo Perfíles.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return perfiles;	
			
	}
	
	public Aplicaciones obtenerAplicaciones(String idUsuario, String idEntidad) throws AdministracionException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo aplicaciones de usuario...");
		}				
		if(idUsuario == null || idEntidad == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		Aplicaciones apps = new Aplicaciones();
		PerfilDatos oDatos = new PerfilDatos();
		try{
			Perfiles perfiles = oDatos.load(idEntidad, idUsuario);
			for (int i = 0; i< perfiles.count(); i++){
				if (perfiles.get(i) != null)
					apps.add(AdministracionAplicacionManager.obtenerAplicacion(perfiles.get(i).getIdAplicacion()));
			}
		}catch(Exception e){
			logger.error("Error obteniendo Perfíles.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return apps;	
			
	}
	
	public Entidad[] getEntidades(String idUsuario) throws AdministracionException {
		Entidad[] entidades = null;
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Entidades de usuario...");
		}				
		if(idUsuario == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}

		try{
			ServicioEntidades service = LocalizadorServicios.getServicioEntidades();
			Perfiles perfiles = obtenerPerfiles(idUsuario);
			List entidadesDif = new ArrayList();
			for(int j=0; j<perfiles.count(); j++)
				if (!entidadesDif.contains(perfiles.get(j).getIdEntidad()))
					entidadesDif.add(perfiles.get(j).getIdEntidad());
			entidades = new Entidad[entidadesDif.size()];
			for (int i = 0; i< entidadesDif.size();i++){
				entidades[i] = service.obtenerEntidad((String)entidadesDif.get(i));
			}
		}catch(Exception e){
			
		}
		return entidades;
	}

	

	
}
