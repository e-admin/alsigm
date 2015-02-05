package es.ieci.scsp.verifdata.model.dao.interfaces;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import es.ieci.scsp.verifdata.config.ClienteLigeroConfigLoader;

public abstract class ClienteLigeroGenericoDAO extends GenericoDAO{
	
	public String _PERSISTENCE_UNIT = "clienteligero";
	
	protected static EntityManagerFactory emf;
	protected EntityManager em;
	private static final Logger log = Logger.getLogger(ClienteLigeroGenericoDAO.class); 

	/**
	 * Devuelve el entity manager
	 * @return
	 */
	public EntityManager getEntityManager(){
		try{
			Map<String, String> properties = new HashMap<String, String>();
			ClienteLigeroConfigLoader loader = ClienteLigeroConfigLoader.getInstance();
			properties.put("hibernate.connection.url",loader.getValue(loader.CLIENTE_LIGERO_URL));
			properties.put("hibernate.connection.username",loader.getValue(loader.CLIENTE_LIGERO_USERNAME));
			properties.put("hibernate.connection.password",loader.getValue(loader.CLIENTE_LIGERO_PASSWORD));
			properties.put("hibernate.connection.driver_class",loader.getValue(loader.CLIENTE_LIGERO_DRIVER_CLASS));
			properties.put("hibernate.dialect",loader.getValue(loader.CLIENTE_LIGERO_HIBERNATE_DIALECT));
			properties.put("hibernate.connection.nombre" , loader.getValue(loader.CLIENTE_LIGERO_NOMBRE));
			properties.put("hibernate.connection.provider_class",loader.getValue(loader.CLIENTE_LIGERO_PROVIDER_CLASS));
			properties.put("hibernate.c3p0.max_size",loader.getValue(loader.CLIENTE_LIGERO_MAX_SIZE));
			properties.put("hibernate.c3p0.min_size",loader.getValue(loader.CLIENTE_LIGERO_MIN_SIZE));
			properties.put("hibernate.c3p0.acquire_increment",loader.getValue(loader.CLIENTE_LIGERO_ACQUIRE_INCREMENT));
			properties.put("hibernate.c3p0.idle_test_period",loader.getValue(loader.CLIENTE_LIGERO_IDLE_TEST_PERIOD));
			properties.put("hibernate.c3p0.max_statements",loader.getValue(loader.CLIENTE_LIGERO_MAX_STATEMENTS));
			properties.put("hibernate.c3p0.timeout",loader.getValue(loader.CLIENTE_LIGERO_TIMEOUT));			
			
			if(emf == null || !emf.getProperties().get("hibernate.connection.nombre").equals(_PERSISTENCE_UNIT)){
				//emf = Persistence.createEntityManagerFactory(_PERSISTENCE_UNIT);
				emf = Persistence.createEntityManagerFactory(_PERSISTENCE_UNIT, properties);
			}
			em = emf.createEntityManager();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		return em;
	}
	
	public static EntityManagerFactory getEntityManagerFactory(){
		return emf;
	}
}
