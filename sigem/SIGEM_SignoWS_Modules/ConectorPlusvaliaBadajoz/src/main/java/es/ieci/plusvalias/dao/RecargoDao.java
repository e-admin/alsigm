package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.RecargoDTO;

/**
 * Home object for domain model class RecargoDTO.
 * 
 * @see es.ieci.plusvalias.domain.RecargoDTO
 * @author Hibernate Tools
 */
public class RecargoDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public RecargoDTO findByYear(Integer year, Integer numdias) throws Exception
	{
		logger.debug("getting RecargoDTO with year: " + year);
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(RecargoDTO.class);
			criteria.add(Restrictions.eq("anyoejercicio", year));
			criteria.add(Restrictions.ge("maxdias", numdias));
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);

			RecargoDTO recargo = null;
			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
				throw new Exception("Error RecargoDao");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				recargo = (RecargoDTO) list.get(0);
			}
			return recargo;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}

}
