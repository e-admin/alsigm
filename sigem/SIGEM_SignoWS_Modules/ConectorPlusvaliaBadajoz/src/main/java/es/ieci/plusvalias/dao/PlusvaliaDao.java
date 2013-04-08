package es.ieci.plusvalias.dao;

// Generated 30-jun-2010 10:06:15 by Hibernate Tools 3.2.4.CR1

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.domain.PlusvaliaDTO;

/**
 * Home object for domain model class PlusvaliaDTO.
 * @see es.ieci.plusvalias.domain.PlusvaliaDTO
 * @author Hibernate Tools
 */
public class PlusvaliaDao extends HibernateDaoSupport{
    protected final Log logger = LogFactory.getLog(getClass());

	public void delete(PlusvaliaDTO plusvalia){
		logger.debug("deleting Plusvalia");
		try{
			getHibernateTemplate().delete(plusvalia);
			logger.debug("delete successful");
		}catch(RuntimeException re){
			logger.error("delete failed", re);
			throw re;
		}
	}

	public Long save(PlusvaliaDTO plusvalia){
		logger.debug("saving Plusvalia");
		try{
			Long id = (Long)getHibernateTemplate().save(plusvalia);
			logger.debug("save successful");
			return id;
		}catch(RuntimeException re){
			logger.error("save failed", re);
			throw re;
		}
	}

	public void persist(PlusvaliaDTO transientInstance){
		logger.debug("persisting Plusvalia instance");
		try{
			getHibernateTemplate().persist(transientInstance);
			logger.debug("persist successful");
		}catch (RuntimeException re){
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PlusvaliaDTO instance){
		logger.debug("attaching dirty Plusvalia instance");
		try{
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		}catch(RuntimeException re){
			logger.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PlusvaliaDTO instance){
		logger.debug("attaching clean Plusvalia instance");
		try{
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		}catch(RuntimeException re){
			logger.error("attach failed", re);
			throw re;
		}
	}

	public PlusvaliaDTO merge(PlusvaliaDTO detachedInstance){
		logger.debug("merging Plusvalia instance");
		try{
			PlusvaliaDTO result = (PlusvaliaDTO) getHibernateTemplate().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		}catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public PlusvaliaDTO findById(Long numero){
		logger.debug("getting Plusvalia instance with id: " + numero);
		try{
			PlusvaliaDTO instance = (PlusvaliaDTO) getHibernateTemplate().get("es.ieci.plusvalias.domain.PlusvaliaDTO", numero);
			if(instance == null){
				logger.debug("get successful, no instance found");
			}else{
				logger.debug("get successful, instance found");
			}
			return instance;
		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PlusvaliaDTO instance){
		logger.debug("finding Plusvalia instance by example");
		try{
			Criteria criteria = getSession().createCriteria(PlusvaliaDTO.class);
		    criteria.add(Example.create(instance));
		    List list = criteria.list();    
		    
		    if(list == null){
		    	logger.debug("find successful, no instance found");
			}else{
				logger.debug("find successful, result size: " + list.size());
			}
			return list;
		}catch(RuntimeException re){
			logger.error("find by example failed", re);
			throw re;
		}
	}

	public int getLastId(Integer year){
		logger.debug("finding last Id");
		try{		
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaDTO.class);
			ProjectionList projectionList = Projections.projectionList().add(Projections.max("numliquidacion"));
			criteria.setProjection(projectionList);
			criteria.add(Restrictions.eq("anyoejercicio", year)); 
			List list = getHibernateTemplate().findByCriteria(criteria);			
			
			
			if(list == null || list.isEmpty()){
		    	logger.debug("find successful, no instance found");
			}else{
				logger.debug("find successful, last Id: " + list.get(0).toString());
			}
			return Integer.parseInt(list.get(0).toString());
		}catch(NullPointerException ex){
			logger.debug("no data, last Id forced to 0");
			return 0;
		}catch(RuntimeException re){
			logger.error("find failed", re);
			throw re;
		}
	}
	
	public Long nextId(Integer year){
		Long secuencial = null;
		try{		
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaDTO.class);
			ProjectionList projectionList = Projections.projectionList().add(Projections.rowCount());
			criteria.setProjection(projectionList);
			criteria.add(Restrictions.eq("anyoejercicio", year)); 
			List list = getHibernateTemplate().findByCriteria(criteria);			
			
			if(list == null || list.isEmpty()){
		    	logger.debug("find successful, no instance found");
			}else{
				logger.debug("find successful, count: " + list.get(0).toString());
			}
			secuencial = new Long(list.get(0).toString());
		}catch(NullPointerException ex){
			logger.debug("no data, last Id forced to 0");
			secuencial = new Long(0);
		}catch(RuntimeException re){
			logger.error("find failed", re);
			throw re;
		}
		
		long secu = secuencial.longValue();
		secu ++;

		NumberFormat formatter = new DecimalFormat("0000000000");
		String formatNumber = formatter.format(new Long(secu)); 
		String regNumber = year + formatNumber;
		
		if (logger.isDebugEnabled()){
			logger.debug("Numero de registro: " + regNumber);	
		}
		return new Long(regNumber);
	}

	public List getAll(){
		logger.debug("getting all Plusvalias");
		try{		
			DetachedCriteria criteria = DetachedCriteria.forClass(PlusvaliaDTO.class);
			criteria.addOrder(Order.asc("numero"));
			List list = getHibernateTemplate().findByCriteria(criteria);
			
			if(list == null || list.isEmpty()){
				logger.debug("get successful, no instance found");
			}else{
				logger.debug("get successful, instance found: " + list.size());
			}
			return list;
		}catch(RuntimeException re){
			logger.error("get failed", re);
			throw re;
		}
	}
}
