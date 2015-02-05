package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.TipoimpositivoDTO;

/**
 * Home object for domain model class TipoimpositivoDTO.
 * @see es.ieci.plusvalias.domain.TipoimpositivoDTO
 * @author Hibernate Tools
 */
public class TipoimpositivoDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public List findByYear(int year) {
		logger.debug("getting TipoimpositivoDTO with year: " + year);
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoimpositivoDTO.class);
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
		logger.debug("getting TipoimpositivoDTO with year: " + year);
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoimpositivoDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year));
			criteria.add(Restrictions.ge("maxanyo", numany));
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);
			
			TipoimpositivoDTO tipoDto = null;
			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
				throw new Exception("Error TipoImpositivoDao");
			}else{
				logger.debug("get successful, instances found: " + list.size());
				tipoDto = (TipoimpositivoDTO)list.get(0);
			}
			return tipoDto.getTipo();

		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
}
