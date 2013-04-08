package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.PorcentajeDTO;

/**
 * Home object for domain model class PorcentajeDTO.
 * @see es.ieci.plusvalias.domain.PorcentajeDTO
 * @author Hibernate Tools
 */
public class PorcentajeDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public List findByYear(int year) {
		logger.debug("getting PorcentajeDTO with year: " + year);
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(PorcentajeDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", new Integer(year))); 
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);

			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
			}else{
				logger.debug("get successful, instances found: " + list.size());
			}
			return list;
		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Double findByYears(Integer year, Integer numany) throws Exception {
		logger.debug("getting PorcentajeDTO with year: " + year);
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(PorcentajeDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year));
			criteria.add(Restrictions.ge("maxanyo", numany));
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);
			PorcentajeDTO porcentajeDto = null;
			
			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
				throw new Exception("Error PorcentajeDao");
			}else{
				logger.debug("get successful, instances found: " + list.size());
				porcentajeDto = (PorcentajeDTO)list.get(0);
			}
			return porcentajeDto.getPorcentaje();

		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
}
