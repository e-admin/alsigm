package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.DeduccionDTO;

/**
 * Home object for domain model class DeduccionDTO.
 * 
 * @see es.ieci.plusvalias.domain.DeduccionDTO
 * @author Hibernate Tools
 */
public class DeduccionDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public Integer findByAnyos(Integer anyos) throws Exception
	{
		logger.debug("getting DeduccionDTO with anyos: " + anyos);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(DeduccionDTO.class);
			criteria.add(Restrictions.ge("anyos", anyos));
			criteria.addOrder(Order.asc("anyos"));

			List list = getHibernateTemplate().findByCriteria(criteria);

			DeduccionDTO deduccionDto = null;
			
			if (list == null || list.isEmpty())
			{
				return findByAnyosDesc(anyos);
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				deduccionDto = (DeduccionDTO) list.get(0);
			}
			
			return deduccionDto.getPorcentaje();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Integer findByAnyosDesc(Integer anyos) throws Exception
	{
		logger.debug("getting DeduccionDTO with anyos: " + anyos);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(DeduccionDTO.class);
			criteria.add(Restrictions.le("anyos", anyos));
			criteria.addOrder(Order.desc("anyos"));

			List list = getHibernateTemplate().findByCriteria(criteria);

			DeduccionDTO deduccionDto = null;
			
			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
				throw new Exception("Error DeduccionDao");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				deduccionDto = (DeduccionDTO) list.get(0);
			}
			
			return deduccionDto.getPorcentaje();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
}
