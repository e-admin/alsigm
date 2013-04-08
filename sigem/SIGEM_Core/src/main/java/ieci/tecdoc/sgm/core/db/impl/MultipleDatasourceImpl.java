package ieci.tecdoc.sgm.core.db.impl;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class MultipleDatasourceImpl {
 
	   private Hashtable m_hashtable = new Hashtable();

	   private String raizNombreJNDI;
	   
	   private synchronized void setDataSource(String ctxName) throws Exception
	   {      
	      Context ctx = null;
	      DataSource m_dataSource = null;
	      if (m_hashtable.containsKey(ctxName)){
	    	  return;
	      }
	      try
	      {
	         ctx = new InitialContext();
	         m_dataSource = (DataSource) ctx.lookup(ctxName);
//	         m_dataSource = new SingleConnectionDataSource("org.postgresql.Driver", 
//	        		 "jdbc:postgresql://localhost/registro00001", "postgres", "postgres", true);
	         m_hashtable.put(ctxName, m_dataSource);
	      }
	      catch (Exception e)
	      {
	         throw e;
	      }
	   }

	   public DataSource getDataSource(String entidad) throws Exception
	   {
		   if( (entidad == null) || ("".equals(entidad))){
			   throw new Exception("Error entidad no especificada.");
		   }
		   StringBuffer sbNombre = new StringBuffer(getRaizNombreJNDI());
		   sbNombre.append(entidad);		   
		   if (!m_hashtable.containsKey(sbNombre.toString())) setDataSource(sbNombre.toString());
	      
		   return (DataSource) m_hashtable.get(sbNombre.toString());
	   }

		public String getRaizNombreJNDI() {
			return raizNombreJNDI;
		}
	
		public void setRaizNombreJNDI(String raizNombreJNDI) {
			this.raizNombreJNDI = raizNombreJNDI;
		}

}
