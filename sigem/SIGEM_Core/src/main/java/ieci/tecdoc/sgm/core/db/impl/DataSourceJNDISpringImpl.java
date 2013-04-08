/*
 * ImplDataSourceSpring.java
 *
 * Created on 2 de julio de 2007, 10:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.core.db.impl;

import ieci.tecdoc.sgm.core.db.DataSource;

/**
 *
 * @author X73994NA
 */
public class DataSourceJNDISpringImpl implements DataSource{
    
	private javax.sql.DataSource jndiDataSource;

	
	public void setJndiDataSource(javax.sql.DataSource jndiDataSource) {
		this.jndiDataSource = jndiDataSource;
	}

	/** Creates a new instance of ImplDataSourceSpring */
    public DataSourceJNDISpringImpl() {
        
    }
    
    public java.sql.Connection getConnection ()
                                        throws java.sql.SQLException{
    	return jndiDataSource.getConnection();
    }
    
}
