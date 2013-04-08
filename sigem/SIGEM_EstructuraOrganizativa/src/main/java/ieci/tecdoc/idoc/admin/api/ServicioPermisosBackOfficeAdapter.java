package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.user.UsuarioPermisosBackOffice;
import ieci.tecdoc.idoc.admin.internal.UsuariosPermisosBackOfficeImpl;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.CriterioBusqueda;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.PermisosBackOfficeException;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.ServicioPermisosBackOffice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ServicioPermisosBackOfficeAdapter implements ServicioPermisosBackOffice{

	
	/**
	 * Este método devuelve un array con todos los usuarios que respondan al criterio de búsqueda.
	 * @param criterio Contiene (al menos) los siguientes valores de búsqueda: usuario, nombre y apellidos.
	 * @param idEntidad Nombre de la entidad
	 * @return DatosUsuario[] Array con todos los usuarios que respondan al criterio de búsqueda
	 * @throws PermisosBackOfficeExceiption
	 */
	public DatosUsuario[] busquedaUsuarios(CriterioBusqueda criterio, String entidad)throws PermisosBackOfficeException{
		PermisosBackOfficeManager manager=new PermisosBackOfficeManager();
		ArrayList listaUsuariosPermisosBackOffice=null;
		listaUsuariosPermisosBackOffice = manager.searchUserBackOffice(criterio, entidad);
		return getDatosUsuariosServicio(listaUsuariosPermisosBackOffice);	
	}

	
	/**
	 * Este método devuelve los datos del usuario que corresponda al id pasado por parámetro
	 * @param idUsuario Identificador del usuario que queremos buscar
	 * @param idEntidad Nombre de la entidad
	 * @return DatosUsuario Usuario que corresponda al identificador
	 * @throws Exception 
	 */
	public DatosUsuario obtenerDatosUsuario(int idUsuario, String entidad) throws PermisosBackOfficeException {
		PermisosBackOfficeManager manager=new PermisosBackOfficeManager();
		return getDatosUsuarioServicio(manager.getUsuarioPermisosBackOffice(idUsuario, entidad));
	}

	
	/**
	 * Este método devuelve un array con todos los usuarios con permisos en cualquiera de 
	 * las aplicaciones de administración.
	 * @param entidad Nombre de la entidad 
	 * @return DatosUsuario[] Array con todos los usuarios con permisos en cualquiera de 
	 * las aplicaciones de administración.
	 * @throws PermisosBackOfficeException En caso de producirse alguna excepción.
	 */	
	public DatosUsuario[] obtenerUsuarios(String entidad) throws PermisosBackOfficeException {
		
		PermisosBackOfficeManager manager=new PermisosBackOfficeManager();
		DatosUsuario[] datosUsuario=null;
		UsuariosPermisosBackOfficeImpl usuariosPermisosBackOfficeImpl = manager.getUsuariosPermisosBackOffice(entidad);
		datosUsuario=getDatosUsuariosServicio(usuariosPermisosBackOfficeImpl.get_list());
		return datosUsuario;
		
	}
	
	private DatosUsuario getDatosUsuarioServicio(UsuariosPermisosBackOfficeImpl usuariosId){
		
		DatosUsuario datosUsuario=new DatosUsuario();
		datosUsuario.set_id(usuariosId.get(0).getIdUsuario());
		
		String[] idAplicaciones=new String[usuariosId.count()];
		for(int i=0;i<usuariosId.count();i++){
			idAplicaciones[i]=Integer.toString(usuariosId.get(i).getIdAplicacion());
		}
		datosUsuario.set_idAplicaciones(idAplicaciones);
		datosUsuario.set_nombre(usuariosId.get(0).getNombre());
		datosUsuario.set_nombreReal(usuariosId.get(0).getNombreReal());
		String apellidos=usuariosId.get(0).getPrimerApellido() + " " + usuariosId.get(0).getSegundoApellido();
		datosUsuario.set_apellidos(apellidos);
		
		return datosUsuario;
	}
	
	private DatosUsuario[] getDatosUsuariosServicio(ArrayList listaUsuariosPermisosBackOffice){
		HashMap map=new HashMap();
		
		for(int i=0;i<listaUsuariosPermisosBackOffice.size();i++){
			UsuarioPermisosBackOffice usuarioPermisosBackOffice=(UsuarioPermisosBackOffice)listaUsuariosPermisosBackOffice.get(i);
			Integer key=new Integer(usuarioPermisosBackOffice.getIdUsuario());
			if(map.containsKey(key)){
				((UsuariosPermisosBackOfficeImpl)map.get(key)).add(usuarioPermisosBackOffice);
			}else{
				UsuariosPermisosBackOfficeImpl usuarios=new UsuariosPermisosBackOfficeImpl();
				usuarios.add(usuarioPermisosBackOffice);
				map.put(key, usuarios);
			}
		}
		
		DatosUsuario[] datosUsuario=new DatosUsuario[map.size()];
		
		Iterator it = map.entrySet().iterator();
		int contador=0;
		while (it.hasNext()) {
			Entry actual = (Entry)it.next();
			UsuariosPermisosBackOfficeImpl usuarios = (UsuariosPermisosBackOfficeImpl)actual.getValue();
			datosUsuario[contador++]=getDatosUsuarioServicio(usuarios);
		}
		return datosUsuario;
		
		/*
		DatosUsuario[] listaDatosUsuario=new DatosUsuario[listaUsuariosPermisosBackOffice.size()];
		for(int i=0;i<listaUsuariosPermisosBackOffice.size();i++){
			UsuarioPermisosBackOffice usuarioPermisosBackOffice=(UsuarioPermisosBackOffice)listaUsuariosPermisosBackOffice.get(i);
			listaDatosUsuario[i]=getDatosUsuarioServicio(usuarioPermisosBackOffice);
		}		
		return listaDatosUsuario;
		*/
		
	}
	
	
}
