package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.ReduccionDTO;

/**
 * Home object for domain model class ReduccionDTO.
 * @see es.ieci.plusvalias.domain.ReduccionDTO
 * @author Hibernate Tools
 */
public class ReduccionDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public Double findByYear(Integer year) throws Exception {
		logger.debug("getting ReduccionDTO with year: " + year);
		try{
			/**
			 * select PORREDUCCION from REDUCCION 
			 * where REDUCCION.ANYLIQUIDACION = MAEPLUSVAL. ANYLIQUIDACION
			 */
			
			DetachedCriteria criteria = DetachedCriteria.forClass(ReduccionDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year));

			List list = getHibernateTemplate().findByCriteria(criteria);
			ReduccionDTO reduccion = null;
			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
				throw new Exception("Error ReduccionDao");
			}else{
				logger.debug("get successful, instances found: " + list.size());
				reduccion = (ReduccionDTO) list.get(0);
			}
			return reduccion.getReduccion();
		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
}
