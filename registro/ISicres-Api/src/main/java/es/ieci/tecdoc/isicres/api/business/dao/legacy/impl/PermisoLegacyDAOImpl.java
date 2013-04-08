package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.Type;

import com.ieci.tecdoc.common.invesdoc.Iuserobjperm;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;

import es.ieci.tecdoc.isicres.api.business.dao.PermisoDAO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class PermisoLegacyDAOImpl extends IsicresBaseHibernateDAOImpl implements PermisoDAO {
	
		
	/**
	 * @param libro
	 * @param usuario
	 * @return
	 * @throws HibernateException
	 */
	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, UsuarioVO usuario) {
		
		PermisosLibroVO result = null;
		
		String idLibro=libro.getId();
		String idUsuario=usuario.getId();
		
		try{
		
			//obtenemos los permisos basicos a nivel de usario y libro
			result=this.getBasicPermisosUsuarioLibro(idLibro, idUsuario);
			
			// un usuario se considerea administrador del libro si es adminitrador o bien es creador del libro
			//miramos si el usuario es administrador de libro
			boolean isAdministrador=isAdministrador(idUsuario,idLibro);
			result.setAdministrador(isAdministrador);
		}catch (Exception ex){
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * un usuario se considerea administrador del libro si es adminitrador o bien es creador del libro
	 * @param idUsuario
	 * @param idLibro
	 * @return
	 * @throws HibernateException 
	 */
	protected boolean isAdministrador(String idUsuario,String idLibro) throws HibernateException{
		boolean result=false;
		
		boolean isAdministrador=isAdministradorLibro(idUsuario,idLibro);
		
		result=isAdministrador;
				
		//miramos si es creador de libro
		if (!isAdministrador){
			boolean isCreadorLibro =isCreadorLibro(idUsuario,idLibro);
			result=isCreadorLibro;
		}
		
		return result;
		
	}
	
	
	
	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, OficinaVO oficina) {
		PermisosLibroVO result = null;
		
		String idDepartamento=oficina.getIdDepartamento();
		String idLibro= libro.getId();
		try {
			result=getBasicPermisosDepartamentoLibro(idLibro,idDepartamento);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	
	protected  PermisosLibroVO getBasicPermisosUsuarioLibro(String  idLibro, String idUsuario) throws HibernateException {
		
		PermisosLibroVO result= null;
			
		//permisos a nivel de usuario
		Integer idTipoDestinatario = new Integer(IDocKeys.IUSEROBJPERM_USER_TYPE);

		result=getBasicPermisosLibro(idLibro,idTipoDestinatario,idUsuario);

		return result;
		
	
	}
	
	/**
	 * @param idLibro
	 * @param idDepartamento
	 * @return
	 * @throws HibernateException
	 */
	public  PermisosLibroVO getBasicPermisosDepartamentoLibro(String idLibro, String idDepartamento) throws HibernateException {

		PermisosLibroVO result= null;

		//permisos a nivel de departamento
		Integer idTipoDestinatario = new Integer(IDocKeys.IUSEROBJPERM_DEPT_TYPE);
		
		
		getBasicPermisosLibro(idLibro,idTipoDestinatario,idDepartamento);
		
		return result;
	}
	
	
	
	/**
	 * Un usuario es administrador del libro si es administrador o bien si es el creador del libro
	 * @param idUsuario
	 * @param idLibro
	 * @return
	 * @throws HibernateException
	 */
	protected boolean isAdministradorLibro(String idUsuario, String idLibro) throws HibernateException{
		boolean result=false;
		
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HibernateKeys.HIBERNATE_ScrBookadmin);
		query.append(" scr WHERE scr.iduser=? AND scr.idbook=?");
		
		List list = getSession().find(query.toString(), new Object[] { idUsuario, idLibro }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
		
		if (list != null && !list.isEmpty()){
			result=true;
		}
		
		return result;
		
	}
	
	/**
	 * Metodo que comprueba si el usuario es el creador del libro
	 * @param idUsuario
	 * @param idLibro
	 * @return
	 * @throws HibernateException
	 */
	protected boolean isCreadorLibro(String idUsuario, String idLibro) throws HibernateException{
		boolean result=false;
		
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HibernateKeys.HIBERNATE_Idocarchhdr);
		query.append(" idoc WHERE idoc.archid=? and idoc.crtrid=?");
		List creador= getSession().find(query.toString(), new Object[] { idLibro, idUsuario}, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
		
		if (creador!=null && !creador.isEmpty()){
			result=true;
		}
		
		return result;
	}
	
	
	
	
	/**
	 * Obtiene los permisos b�sicos del libro para el tipo de destinatario espeficicado y para el destinatario
	 * @param idLibro
	 * @param tipoDestinatarioPermisos puede ser uno de : 
	 * 	        IDocKeys.IUSEROBJPERM_DEPT_TYPE = 2;
     			IDocKeys.IUSEROBJPERM_USER_TYPE = 1;
				IDocKeys.IUSEROBJPERM_GROUP_TYPE = 3; //TODO este valor no sabemos si se debe usar
	 * @param idDestinatario 
	 * @return
	 * @throws HibernateException
	 */
	protected PermisosLibroVO getBasicPermisosLibro(String idLibro, Integer tipoDestinatarioPermisos, String idDestinatario) throws HibernateException{
		PermisosLibroVO result=new PermisosLibroVO();
		result.setIdLibro(idLibro);
		
		StringBuffer query = new StringBuffer();
		Integer DstType = new Integer(IDocKeys.IUSEROBJPERM_DEPT_TYPE);
		List list = null;

		query.append("FROM ");
		query.append(HibernateKeys.HIBERNATE_Iuserobjperm);
		query.append(" iuser WHERE iuser.dstid=? AND iuser.objid=? AND iuser.dsttype=?");

		list = this.getSession().find(query.toString(), new Object[] { idDestinatario, idLibro,	idDestinatario }, 
				new Type[] { Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });
		
		//de todos los permisos nos quedamos solo con los que competen a este caso
		if (list != null && !list.isEmpty()) {
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Iuserobjperm iuser = (Iuserobjperm) iterator.next();
					int Aperm = iuser.getAperm();
				
					switch (Aperm) {
					
					case IDocKeys.IUSEROBJPERM_QUERY_PERM:
						 result.setConsulta(true);
						
						break;
						
					case IDocKeys.IUSEROBJPERM_CREATE_PERM:
						result.setCreacion(true);
						
						break;
						
					case IDocKeys.IUSEROBJPERM_MODIFY_PERM:
						result.setModificacion(true);
						
						break;
	
					default:
						break;
					}
					
			}
		}
		
		return result;
	}





	
	
	
	

}
