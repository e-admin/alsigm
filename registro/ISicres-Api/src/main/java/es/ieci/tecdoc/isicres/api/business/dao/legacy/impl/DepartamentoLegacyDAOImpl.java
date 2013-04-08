package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.HibernateKeys;

import es.ieci.tecdoc.isicres.api.business.dao.DepartamentoDAO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class DepartamentoLegacyDAOImpl extends IsicresBaseHibernateDAOImpl  implements DepartamentoDAO {

	
	public DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario) {

		DepartamentoUsuarioVO result=null;
		
		String idUsuario=usuario.getId();
		ScrOfic scrOfic =null;
		try {
			
			//obtenemos el departamento del usuario
			scrOfic = getDepartamentoUsuario(idUsuario);
			
			if (scrOfic!=null){
				result=new DepartamentoUsuarioVO();
				String idDepartamento=Integer.toString(scrOfic.getDeptid());
				result.setId(idDepartamento);
			}
						
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
							
		
		return result;
	}
	
	public DepartamentoOficinaVO getDepartamentoOficina(OficinaVO oficina) {
		DepartamentoOficinaVO result=null;
		
		String idOficina=oficina.getId();
				
		try {
			//obtenemos el departamento de la oficina
			ScrOfic scrOfic = getDepartamentoOficina(idOficina);
			if (scrOfic!=null){
				result=new DepartamentoOficinaVO();
				String idDepartamento=Integer.toString(scrOfic.getDeptid());
				result.setId(idDepartamento);
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
				
		return result;
	}
	
	
	protected ScrOfic getDepartamentoOficina(String idOficina) throws HibernateException{
		
		ScrOfic result=null;
				
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HibernateKeys.HIBERNATE_ScrOfic);
		query.append(" scr WHERE scr.id=?");
		List list = getSession().find(query.toString(), new Object[] { idOficina },new Type[] { Hibernate.INTEGER });
		
		if (list != null && !list.isEmpty()) {
			result = (ScrOfic) list.get(0);
		}
		
		return result;
		
		
	}
	
	protected ScrOfic getDepartamentoUsuario(String idUsuario) throws HibernateException{
		
		ScrOfic result=null;
		
		//query para sacar el departamento del usuario
		StringBuffer queryIuseruserhdr = new StringBuffer();
		queryIuseruserhdr.append("deptid in (select deptid from iuseruserhdr where id = ");
		queryIuseruserhdr.append(idUsuario);
		queryIuseruserhdr.append(")");
						
		Criteria criteria = getSession().createCriteria(ScrOfic.class);
		criteria.add(Expression.sql(queryIuseruserhdr.toString()));
		List oficinasDepartamentoList = criteria.list();
		
		if (oficinasDepartamentoList!=null && !oficinasDepartamentoList.isEmpty()){
			result= (ScrOfic) oficinasDepartamentoList.get(0);
			
		}
		return result;
		
	}

	

}
