package ieci.tecdoc.sgm.usuario;

import java.util.Iterator;
import java.util.List;

import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.usuario.datatypes.Usuario;
import ieci.tecdoc.sgm.usuario.datatypes.UsuarioImpl;
import ieci.tecdoc.sgm.usuario.exception.UsuarioExcepcion;

public class UsuarioManagerAdapter implements ServicioAutenticacionUsuarios {

	public DatosUsuario authenticateUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException {

		try {
			return getServiceUser(UsuarioManager.get(user.getUser(), user.getPassword(), entidad.getIdentificador()));
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
		
	}
	
	public DatosUsuario getUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException {
		try {
			return getServiceUser(UsuarioManager.get(user.getUser(), entidad.getIdentificador()));
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}		
	}
	
	public void deleteUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException {
		try {
			UsuarioManager.delete(user.getUser(), entidad.getIdentificador());
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}		
	}
	
	public DatosUsuario[] findUsers(CriterioBusquedaUsuarios criteria, Entidad entidad) throws AutenticacionUsuarioException{
		try {
			return getUsersArray(UsuarioManager.findUsers(getCriterioBusqueda(criteria), entidad.getIdentificador()));
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}				
	}
	
	public void updateUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException{
		try {
			UsuarioManager.updateUser(getImplUser(user), entidad.getIdentificador());
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}				
	}
	
	public void createUser(DatosUsuario user, Entidad entidad) throws AutenticacionUsuarioException{
		try {
			UsuarioManager.createUser(getImplUser(user), entidad.getIdentificador());
		} catch (UsuarioExcepcion e) {
			throw getUserAuthExcepcion(e);
		} catch (Throwable e){
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}						
	}
	
	private DatosUsuario getServiceUser(Usuario poUsuarioManager){
		DatosUsuario oUser = new DatosUsuario();
		oUser.setEmail(poUsuarioManager.getEmail());
		oUser.setId(poUsuarioManager.getId());
		oUser.setLastname(poUsuarioManager.getApellidos());
		oUser.setName(poUsuarioManager.getNombre());
		oUser.setPassword(poUsuarioManager.getPassword());
		oUser.setUser(poUsuarioManager.getUsuario());
		
		return oUser;
	}

	private Usuario getImplUser(DatosUsuario poUser){
		Usuario oUsuario = new UsuarioImpl();
		oUsuario.setApellidos(poUser.getLastname());
		oUsuario.setEmail(poUser.getEmail());
		oUsuario.setId(poUser.getId());
		oUsuario.setNombre(poUser.getName());
		oUsuario.setPassword(poUser.getPassword());
		oUsuario.setUsuario(poUser.getUser());
		
		return oUsuario;
	}
	
	private CriterioBusquedaUsuario getCriterioBusqueda(CriterioBusquedaUsuarios poCriteria){
		CriterioBusquedaUsuario oCriterioManager = new CriterioBusquedaUsuario();
		oCriterioManager.setUser(poCriteria.getUser());
		return oCriterioManager;
	}
	
	private DatosUsuario[] getUsersArray(List poList){
		if((poList == null) && (poList.size() == 0)){
			return null;
		}
		Iterator oIterator = poList.iterator();
		DatosUsuario[] aLista = new DatosUsuario[poList.size()];
		UsuarioImpl oUserManager = null;
		int eCounter = 0;
		while(oIterator.hasNext()){
			oUserManager = (UsuarioImpl)oIterator.next();
			aLista[eCounter++] = getServiceUser(oUserManager);
		}
		return aLista;
	}
	
	private AutenticacionUsuarioException getUserAuthExcepcion(UsuarioExcepcion e){
		return new AutenticacionUsuarioException(e.getErrorCode() + 1000000, e.getCause());
	}

}
