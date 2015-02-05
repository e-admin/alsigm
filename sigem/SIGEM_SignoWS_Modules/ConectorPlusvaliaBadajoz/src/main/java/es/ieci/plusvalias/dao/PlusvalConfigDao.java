package es.ieci.plusvalias.dao;

//Generated 28-jun-2011 11:25:36 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.PlusvalConfigDTO;

/**
 * Home object for domain model class PlusvalConfigDTO.
 * 
 * @see es.ieci.plusvalias.domain.PlusvalConfigDTO
 * @author Hibernate Tools
 */
public class PlusvalConfigDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public PlusvalConfigDTO findFirst() throws Exception
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("getting first PlusvalConfigDao");
		}
		
		try
		{
			List list = getHibernateTemplate().loadAll(PlusvalConfigDTO.class);
			PlusvalConfigDTO acto = null;
			
			if (list == null || list.isEmpty())
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, no instance found");
				}
				
				throw new Exception("Error PlusvalConfigDao");
			}
			else
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("get successful, PlusvalConfigDao instances found: " + list.size());
				}
				
				acto = (PlusvalConfigDTO)list.get(0);
			}
			
			return acto;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
}
