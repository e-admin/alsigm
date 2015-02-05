package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.IntedemoraDTO;

/**
 * Home object for domain model class IntedemoraDTO.
 * 
 * @see es.ieci.plusvalias.domain.IntedemoraDTO
 * @author Hibernate Tools
 */
public class IntedemoraDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public Double findByYear(Integer year)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getting IntedemoraDTO with year: " + year);
		}
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(IntedemoraDTO.class);
			criteria.add(Restrictions.eq("anyoejercicio", year));
			List list = getHibernateTemplate().findByCriteria(criteria);
			IntedemoraDTO inteDemoraDto = null;
			
			if (list == null || list.isEmpty())
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, no instance found");
				}
			}
			else
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, instances found: " + list.size());
				}
				
				inteDemoraDto = (IntedemoraDTO)list.get(0);
			}
			
			return inteDemoraDto.getPorcentaje();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw re;
		}
	}
}