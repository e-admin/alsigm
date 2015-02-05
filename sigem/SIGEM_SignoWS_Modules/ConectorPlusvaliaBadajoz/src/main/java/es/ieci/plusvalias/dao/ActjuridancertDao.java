package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.ActjuridancertDTO;

/**
 * Home object for domain model class ActjuridancertDTO.
 * 
 * @see es.ieci.plusvalias.domain.ActjuridancertDTO
 * @author Hibernate Tools
 */
public class ActjuridancertDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public ActjuridancertDTO findByCode(Integer code) throws Exception
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getting ActjuridancertDTO with code: " + code);
		}
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ActjuridancertDTO.class);
			criteria.add(Restrictions.eq("codigo", code));

			List list = getHibernateTemplate().findByCriteria(criteria);
			ActjuridancertDTO acto = null;
			
			if (list == null || list.isEmpty())
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, no instance found");
				}
				
				return null;
			}
			else
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, instances found: " + list.size());
				}
				
				acto = (ActjuridancertDTO)list.get(0);
			}
			
			return acto;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public ActjuridancertDTO findByCode(String code) throws Exception
	{
		return findByCode(Integer.parseInt(code));
	}
}
