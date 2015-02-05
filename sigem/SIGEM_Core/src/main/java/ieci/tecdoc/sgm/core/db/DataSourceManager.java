/*
 * DataSourceManager.java
 *
 * Created on 29 de junio de 2007, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.core.db;


import ieci.tecdoc.sgm.core.config.impl.spring.Config;

/**
 *
 * @author X73994NA
 */
public class DataSourceManager {
    
   private static DataSourceManager m_instance;
   private static DataSource m_dataSource;
    
   public static final String DEFAULT_DATASOURCE_NAME = "DEFAULT_DATASOURCE";
   public static final String ADMINISTRACION_DATASOURCE_NAME = "SIGEM_ADMIN_DATASOURCE";
   
   private DataSourceManager(){
   }
   
   static{
      synchronized (DataSourceManager.class){
         if (m_instance == null){
             m_instance = new DataSourceManager();
         }
      }
   }
   
   public static DataSourceManager getInstance(){
      return m_instance;
   }
   
   public java.sql.Connection getConnection(String dataSourceName_, Config poConfig) throws Exception {
	   if(m_dataSource == null){
		   m_dataSource = (DataSource)poConfig.getBean(dataSourceName_);   
	   }
       return m_dataSource.getConnection();
   }

   public java.sql.Connection getConnection(Config poConfig) throws Exception {
       return getConnection(DEFAULT_DATASOURCE_NAME, poConfig);
   }

}
