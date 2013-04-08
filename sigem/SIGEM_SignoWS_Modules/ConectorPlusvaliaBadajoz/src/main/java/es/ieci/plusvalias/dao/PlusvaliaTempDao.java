package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.PlusvaliaTempDTO;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;

/**
 * Home object for domain model class PlusvaliaDTO.
 * 
 * @see es.ieci.plusvalias.domain.PlusvaliaDTO
 * @author Hibernate Tools
 */
public class PlusvaliaTempDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public void delete(PlusvaliaTempDTO plusvalia)
	{
		logger.debug("deleting Plusvalia");
		
		try
		{
			getHibernateTemplate().delete(plusvalia);
			
			logger.debug("delete successful");
		}
		catch (RuntimeException re)
		{
			logger.error("delete failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public Long save(PlusvaliaTempDTO plusvalia)
	{
		logger.debug("saving Plusvalia");
		
		if (plusvalia.getRefcatastral().length() > 18)
		{
			plusvalia.setRefcatastral(plusvalia.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			Long id = (Long)getHibernateTemplate().save(plusvalia);
			
			logger.debug("save successful");
			
			return id;
		}
		catch (RuntimeException re)
		{
			logger.error("save failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public void persist(PlusvaliaTempDTO transientInstance)
	{
		logger.debug("persisting Plusvalia instance");
		
		if (transientInstance.getRefcatastral().length() > 18)
		{
			transientInstance.setRefcatastral(transientInstance.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			getHibernateTemplate().persist(transientInstance);
			
			logger.debug("persist successful");
		}
		catch (RuntimeException re)
		{
			logger.error("persist failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public void attachDirty(PlusvaliaTempDTO instance)
	{
		logger.debug("attaching dirty Plusvalia instance");
		
		if (instance.getRefcatastral().length() > 18)
		{
			instance.setRefcatastral(instance.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			getHibernateTemplate().saveOrUpdate(instance);
			
			logger.debug("attach successful");
		}
		catch (RuntimeException re)
		{
			
			logger.error("attach failed", re);
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public void attachClean(PlusvaliaTempDTO instance)
	{
		logger.debug("attaching clean Plusvalia instance");
		
		if (instance.getRefcatastral().length() > 18)
		{
			instance.setRefcatastral(instance.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			getHibernateTemplate().lock(instance, LockMode.NONE);
			
			logger.debug("attach successful");
		}
		catch (RuntimeException re)
		{
			logger.error("attach failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public PlusvaliaTempDTO merge(PlusvaliaTempDTO instance)
	{
		logger.debug("merging Plusvalia instance");
		
		if (instance.getRefcatastral().length() > 18)
		{
			instance.setRefcatastral(instance.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			PlusvaliaTempDTO result = (PlusvaliaTempDTO) getHibernateTemplate().merge(instance);
			
			logger.debug("merge successful");
			
			return result;
		}
		catch (RuntimeException re)
		{
			logger.error("merge failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public PlusvaliaTempDTO findById(String refCatastral)
	{
		logger.debug("getting Plusvalia instance with refCatastral: " + refCatastral);
		
		if (refCatastral.length() > 18)
		{
			refCatastral = refCatastral.substring(0, 18);
		}
		
		try
		{
			PlusvaliaTempDTO instance = (PlusvaliaTempDTO)getHibernateTemplate().get("es.ieci.plusvalias.domain.PlusvaliaTempDTO", refCatastral.trim());

			if (instance == null)
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instance found");
			}

			return instance;

		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public List findByExample(PlusvaliaTempDTO instance)
	{
		logger.debug("finding Plusvalia instance by example");
		
		if (instance.getRefcatastral().length() > 18)
		{
			instance.setRefcatastral(instance.getRefcatastral().substring(0, 18));
		}
		
		try
		{
			Criteria criteria = getSession().createCriteria(PlusvaliaTempDTO.class);
			criteria.add(Example.create(instance));
			List list = criteria.list();

			if (list == null)
			{
				logger.debug("find successful, no instance found");
			}
			else
			{
				logger.debug("find successful, result size: " + list.size());
			}
			
			return list;
		}
		catch (RuntimeException re)
		{
			logger.error("find by example failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public PlusvaliaTempDTO pagar(String refCatastral, String nifTrans, String nifAdq, int claseDerecho)
	{
		if (refCatastral.length() > 18)
		{
			refCatastral = refCatastral.substring(0, 18);
		}
		
		logger.debug("getting Plusvalia instance with refCatastral: " + refCatastral);
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaTempDTO.class);
			criteria.add(Restrictions.eq("refcatastral", refCatastral.trim()));
			criteria.add(Restrictions.eq("nifTrans", nifTrans));
			criteria.add(Restrictions.eq("nifAdq", nifAdq));
			criteria.add(Restrictions.eq("claseDerecho", claseDerecho));
			
			List list = getHibernateTemplate().findByCriteria(criteria);
			
			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				final PlusvaliaTempDTO plusvalia = ((PlusvaliaTempDTO)list.get(0));
				plusvalia.setPagada("S");
				
				getHibernateTemplate().execute(new HibernateCallback()
				{
					public Object doInHibernate(Session session) throws HibernateException, SQLException
					{
						Transaction tran = session.beginTransaction();
						try
						{
							tran.begin();
							
							String command = "update AYTO_PLUSVALIA_TEMP set PAGADA='S', SITUACION='4 - COBRADA' "
								+ "where " + "REFCATASTRAL= '" + plusvalia.getRefcatastral()
								+ "' AND NIFTRANSMITIENTE= '" + plusvalia.getNifTrans()
								+ "' AND NIFADQUIRIENTE= '" + plusvalia.getNifAdq()
								+ "' AND CLASEDERECHO= " + plusvalia.getClaseDerecho();

							SQLQuery sql = session.createSQLQuery(command);

							logger.debug("Executing: \n\t" + command);

							sql.executeUpdate();

							tran.commit();
						}
						catch (HibernateException e)
						{
							tran.rollback();
						}
						return null;
					}
				});

				logger.debug("get successful, instance found");
			}
			
			return (PlusvaliaTempDTO) list.get(0);
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public List getAll()
	{
		logger.debug("getting all Plusvalias");
		
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaTempDTO.class);
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);

			if (list == null || list.isEmpty())
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instance found: " + list.size());
			}
			
			return list;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}

	public PlusvaliaTempDTO find(String refCatastral, String nifTrans, String nifAdq, int claseDerecho)
	{
		logger.debug("finding plusvalia");

		String referencia = refCatastral;

		if (referencia.length() > 18)
		{
			referencia = referencia.substring(0, 18);
		}

		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaTempDTO.class);
			criteria.add(Restrictions.eq("refcatastral", referencia.trim()));
			criteria.add(Restrictions.eq("nifTrans", nifTrans));
			criteria.add(Restrictions.eq("nifAdq", nifAdq));
			criteria.add(Restrictions.eq("claseDerecho", claseDerecho));
			
			List list = getHibernateTemplate().findByCriteria(criteria);

			if (list == null || list.isEmpty())
			{
				logger.debug("find successful, no instance found");
				return null;
			}
			else
			{
				logger.debug("find successful, last Id: " + list.get(0).toString());
				
				return (PlusvaliaTempDTO)list.get(0);
			}
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			
			throw new PlusvaliaException(ErrorCode.DATABASE_ERROR);
		}
	}
}
