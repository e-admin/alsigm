package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.BoniexenDTO;

/**
 * Home object for domain model class BoniexenDTO.
 * @see es.ieci.plusvalias.domain.BoniexenDTO
 * @author Hibernate Tools
 */
public class BoniexenDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public List findByYear(int year) {
		logger.debug("getting BoniexenDTO with year: " + year);
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(BoniexenDTO.class);
			criteria.add(Restrictions.eq("anyoejercicio", new Integer(year))); 
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
}
