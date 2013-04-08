package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.CataDTO;

/**
 * Home object for domain model class CataDTO.
 * @see es.ieci.plusvalias.domain.CataDTO
 * @author Hibernate Tools
 */
public class CataDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public CataDTO findByYear(Integer year) throws Exception {
		logger.debug("getting CataDTO with year: " + year);
		try{
			/** select  NOMTABLA, COEFICIENTE, OPERADOR  
			* 	from  MAECATA  
			*	where  MAECATA. NOMTABLA = MAEPLUSVAL.ANYLIQUIDACION
			*/
			
			DetachedCriteria criteria = DetachedCriteria.forClass(CataDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year)); 
			
			List list = getHibernateTemplate().findByCriteria(criteria);
			CataDTO cata = null;
			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
				throw new Exception("Error CataDao");
			}else{
				logger.debug("get successful, instances found: " + list.size());
				cata = (CataDTO) list.get(0);
			}
			return cata;
		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
}
