package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.SituacionDTO;

/**
 * Home object for domain model class SituacionDTO.
 * 
 * @see es.ieci.plusvalias.domain.SituacionDTO
 * @author Hibernate Tools
 */
public class SituacionDao extends HibernateDaoSupport
{
	public static final String EN_TRAMITE = "EN TRAMITE";
	public static final String SIN_INCREMENTO_VALOR = "SIN INCREMENTO DE VALOR";
	public static final String EXENTAS = "EXENTAS";
	public static final String PRESCRITAS = "PRESCRITAS";
	public static final String COBRADA = "COBRADA";
	
	protected final Log logger = LogFactory.getLog(getClass());

	public List findByYear(int year)
	{
		logger.debug("getting SituacionDTO with year: " + year);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SituacionDTO.class);
			criteria.add(Restrictions.eq("anyoejercicio", new Integer(year)));
			criteria.addOrder(Order.asc("numero"));
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
	
	public String findByYearNum(int year, int num)
	{
		logger.debug("getting SituacionDTO with year: " + year + " and num: " + num);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SituacionDTO.class);
			criteria.add(Restrictions.eq("anyoejercicio", new Integer(year)));
			criteria.add(Restrictions.eq("numero", new Integer(num)));
			
			SituacionDTO situacion = null;

			List list = getHibernateTemplate().findByCriteria(criteria);

			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				
				situacion = (SituacionDTO)list.get(0);
			}
			
			return situacion.getTexto().trim();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public String findByYearTexto(int year, String texto)
	{
		logger.debug("getting SituacionDTO with year: " + year + " and texto: " + texto);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SituacionDTO.class);
			criteria.add(Restrictions.le("anyoejercicio", new Integer(year)));
			criteria.add(Restrictions.ilike("texto", "%" + texto + "%"));
			criteria.addOrder(Order.desc("anyoejercicio"));
			
			SituacionDTO situacion = null;

			List list = getHibernateTemplate().findByCriteria(criteria);

			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instances found: " + list.size());
				
				situacion = (SituacionDTO)list.get(0);
			}
			
			return situacion.getTexto().trim();
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}
}
