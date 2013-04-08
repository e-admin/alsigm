package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.DerechoDTO;

/**
 * Home object for domain model class DerechoDTO.
 * 
 * @see es.ieci.plusvalias.domain.DerechoDTO
 * @author Hibernate Tools
 */
public class DerechoDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public List findByYear(int year)
	{
		logger.debug("getting Derechos with year: " + year);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(DerechoDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", new Integer(year)));
			criteria.addOrder(Order.asc("texto"));
			List list = getHibernateTemplate().findByCriteria(criteria);

			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
			}
			
			return list;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw re;
		}
	}

	public Integer findByYearNum(Integer year, Integer numero) throws Exception
	{
		logger.debug("getting Derechos with year: " + year);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(DerechoDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year));
			criteria.add(Restrictions.eq("numero", numero));
			criteria.addOrder(Order.asc("texto"));
			List list = getHibernateTemplate().findByCriteria(criteria);

			DerechoDTO derecho = null;
			
			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
				throw new Exception("Error DerechoDao");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				derecho = (DerechoDTO) list.get(0);
			}
			
			return derecho.getDias();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw re;
		}
	}
	
	public DerechoDTO findByCodAncert(Integer year, Integer codancert, String tipoancert) throws Exception
	{
		logger.debug("getting Derechos with year: " + year);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(DerechoDTO.class);
			criteria.add(Restrictions.eq("anyoliquidacion", year));
			criteria.add(Restrictions.eq("codancert", codancert));
			criteria.add(Restrictions.eq("tipoancert", tipoancert));
			criteria.addOrder(Order.asc("texto"));
			
			List list = getHibernateTemplate().findByCriteria(criteria);

			DerechoDTO derecho = null;
			
			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
				throw new Exception("Error DerechoDao");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				derecho = (DerechoDTO)list.get(0);
			}
			
			return derecho;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw re;
		}
	}
}
