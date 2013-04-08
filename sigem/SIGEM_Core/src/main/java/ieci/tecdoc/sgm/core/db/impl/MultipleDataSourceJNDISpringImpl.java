package ieci.tecdoc.sgm.core.db.impl;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;

import java.util.HashMap;

import org.springframework.jndi.JndiObjectFactoryBean;

public class MultipleDataSourceJNDISpringImpl {

	   private HashMap dataSourceMap = new HashMap();
	   private String prefijoJNDI;
	   
	   
	   private java.sql.Connection getConnection(String dataSourceName_, Config poConfig, String clave) throws Exception {
		   DataSourceJNDISpringImpl oDataImpl = (DataSourceJNDISpringImpl)dataSourceMap.get(clave);
		   if(  oDataImpl == null){
			   // creamos un nuevo origen de datos.
			   synchronized (DataSourceManagerMultientidad.class) {
				   oDataImpl = (DataSourceJNDISpringImpl)dataSourceMap.get(clave);
				   if( oDataImpl == null){
					   org.springframework.jndi.JndiObjectFactoryBean odatasource = new JndiObjectFactoryBean();
					   odatasource.setJndiName(dataSourceName_);
					   oDataImpl = new DataSourceJNDISpringImpl();
					   oDataImpl.setJndiDataSource((javax.sql.DataSource)odatasource.getObject());
					   dataSourceMap.put(clave, oDataImpl);	
				   }
			   }
		   }
		   return oDataImpl.getConnection();
	   }

	   public java.sql.Connection getConnection(Config poConfig, String clave) throws Exception {
		   if( (clave == null) || ("".equals(clave))){
			   throw new Exception("Error: entidad no definida a la hora de establecer conexión a la base de datos.");
		   }
		   StringBuffer sbNombre = new StringBuffer(prefijoJNDI);
		   sbNombre.append(clave);
	       return getConnection(sbNombre.toString(), poConfig, clave);
	   }

	   public String getPrefijoJNDI() {
			return prefijoJNDI;
		   }

		   public void setPrefijoJNDI(String prefijoJNDI) {
			   this.prefijoJNDI = prefijoJNDI;
		   }
}
