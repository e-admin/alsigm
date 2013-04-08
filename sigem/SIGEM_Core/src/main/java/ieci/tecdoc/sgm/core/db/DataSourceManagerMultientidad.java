package ieci.tecdoc.sgm.core.db;

import java.sql.Connection;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.db.impl.MultipleDatasourceImpl;

public class DataSourceManagerMultientidad {

	   private static DataSourceManagerMultientidad m_instance;
	    
	   public static final String DEFAULT_DATASOURCE_NAME = "SIGEM_MULTIENTIDAD_DATASOURCE";
	   public static final String BACKOFFICE_DATASOURCE_NAME = "SIGEM_BACKOFFICE_DATASOURCE";
	   
	   private DataSourceManagerMultientidad(){}
	   
	   static{
	      synchronized (DataSourceManager.class){
	         if (m_instance == null){
	             m_instance = new DataSourceManagerMultientidad();
	         }
	      }
	   }
	   
	   public static DataSourceManagerMultientidad getInstance(){
	      return m_instance;
	   }

	   /**
	    * @deprecated utilizar el método getConnection(String datasourceName, Config poConfig, String entidad).
	    */
	   public Connection getConnection(Config poConfig, String entidad) throws Exception {
		   return getConnection(DEFAULT_DATASOURCE_NAME, poConfig, entidad);
	   }
	   
	   public Connection getConnection(String datasourceName, Config poConfig, String entidad) throws Exception {
		   
	       Connection conn = null;
		   
		   if( (entidad == null) || ("".equals(entidad))){
			   throw new Exception("Error: entidad no definida a la hora de establecer conexión a la base de datos.");
		   }
		   
		   MultipleDatasourceImpl m_dataSource = (MultipleDatasourceImpl)poConfig.getBean(datasourceName);
		   if (m_dataSource != null) {
			   return m_dataSource.getDataSource("_" + entidad).getConnection();
		   }
	       
	       return conn;
	   }

}
