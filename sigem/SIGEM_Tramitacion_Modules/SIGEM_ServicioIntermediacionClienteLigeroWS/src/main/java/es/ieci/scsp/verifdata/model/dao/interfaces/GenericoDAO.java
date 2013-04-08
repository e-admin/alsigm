package es.ieci.scsp.verifdata.model.dao.interfaces;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

public abstract class GenericoDAO {
	
	private static final Logger log = Logger.getLogger(GenericoDAO.class);
	
	/**
	 * Devuelve el entity manager
	 * @return
	 */
	public abstract EntityManager getEntityManager();
	/**
	 * Devuelve el entity manager factory
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		return null;
	}
	
	/**
	 * 
	 * @param objeto
	 * @return
	 */
	public boolean save(Object objeto) {
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		boolean resultado=false;
		try {
			log.debug("GenericoDAO.save");
			transaccion.begin();
			em.persist(objeto);
			transaccion.commit();
			resultado = true;
			log.debug("save successful");
		} catch (RuntimeException re) {
			if (transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			log.error("save failed", re);
			throw re;
		} finally {		
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return resultado;
	}	

	/**
	 * 
	 * @param objeto
	 * @return
	 */
	public boolean delete(Class clase, Object id) {
			
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		boolean salir = false;
		try {
			 transaccion.begin();	
			 Object instance = em.find(clase, id);
			 em.remove(instance);
		     transaccion.commit();
		     salir = true;
		}catch (Exception e) {	
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			log.error("delete failed", e);
		} finally {	
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return salir;
	}	

	/**
	 * 
	 * @param objeto
	 * @param id
	 * @return
	 */
	public boolean update(Object objeto, Object id){
		
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		Class clase = objeto.getClass();
		boolean salir = false;
		try{
		    transaccion.begin();
		    Object objectx = em.find(objeto.getClass(), id);
//		    objectx = objeto;
		    Field fieldlist[] = clase.getDeclaredFields();
		    Field campo = null;
		    for (int i = 0; i < fieldlist.length; i++) {
		    	campo = fieldlist[i];
		    	campo.setAccessible(true);
			    campo.set(objectx, campo.get(objeto));
		    }
		    transaccion.commit();
		    salir = true;
		}catch(Exception e){
			log.error("update failed", e);
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
		} finally {
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return salir;
	}
	
	/**
	 * 
	 * @param clase
	 * @return
	 */
	public List getAll(String clase){	 
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		List instance = null;
		try{			
			instance = em.createQuery("from "+clase).getResultList();
			if(transaccion!=null && transaccion.isActive())
				transaccion.commit();
		}
		catch(Exception e){
			log.error("update failed", e);
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
		} finally {	
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param clase
	 * @param id
	 * @return
	 */
	public Object findById(Class clase, Object id) {
		log.debug("getting instance with id: " + id);
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		Object instance = null;
		try {
			transaccion.begin();			
			instance = em.find(clase, id);
			transaccion.commit();
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("findById failed", re);
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			throw re;
		} finally {		
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param consulta
	 * @return
	 */
	public List ejecutaHQLConsulta(String consulta){
		log.debug("GenericoDAO.ejecutaConsutla: Ejecutamos la consulta: "+consulta);
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		List resultado = new ArrayList();
		try{
			transaccion.begin();			
			resultado = em.createQuery(consulta).getResultList();
			transaccion.commit();
			log.debug("GenericoDAO.ejecutaConsutla: Consulta ejecutada correctamente");		
		}
		catch(Exception e){
			log.error("GenerioDAO.ejecutaConsulta: error al ejecutar la consulta: "+consulta+". Error: "+e.getMessage());
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
		} finally {			
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return resultado;
	}

	/**
	 * 
	 * @param consulta
	 * @return
	 */
	public List ejecutaHQLConsultaNativa(String consulta){
		log.debug("GenericoDAO.ejecutaConsutla: Ejecutamos la consulta nativa: "+consulta);
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		List resultado = new ArrayList();
		try{
			transaccion.begin();
			resultado = em.createNativeQuery(consulta).getResultList();
			transaccion.commit();
			log.debug("GenericoDAO.ejecutaConsultaNativa: Consulta ejecutada correctamente");		
		}
		catch(Exception e){
			log.error("GenerioDAO.ejecutaConsultaNativa: error al ejecutar la consulta: "+consulta+". Error: "+e.getMessage());
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
		} finally {		
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return resultado;
	}
	
	/**
	 * Método para ejecutar Inserts y Updates nativos
	 * @param consulta
	 * @return
	 */
	public int ejecutaInsertUpdateNativo(String consulta){
		log.debug("GenericoDAO.ejecutaInsertUpdateNativo: Ejecutamos la Inserción/Actualización nativa: "+consulta);
		int resultado = Integer.MIN_VALUE;
		EntityManager em = getEntityManager();
		EntityTransaction transaccion = em.getTransaction();
		try{
			transaccion.begin();
			resultado = em.createNativeQuery(consulta).executeUpdate();
			transaccion.commit();
			log.debug("GenericoDAO.ejecutaInsertUpdateNativo: Inserción/Actualización ejecutada correctamente");
			log.debug("GenericoDAO.ejecutaInsertUpdateNativo: " + resultado + " filas actualizadas o insertadas");
		}
		catch(Exception e){
			log.error("GenerioDAO.ejecutaInsertUpdateNativo: error al realizar la Inserción/Actualización: "+consulta+". Error: "+e.getMessage());
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
		} finally {		
			if(transaccion!=null && transaccion.isActive())
				transaccion.rollback();
			em.close();
		}
		return resultado;
	}
}
