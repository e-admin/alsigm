/*
 * ImplDataSourceSpring.java
 *
 * Created on 2 de julio de 2007, 10:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.core.db.impl;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ieci.tecdoc.sgm.core.db.DataSource;

/**
 *
 * @author X73994NA
 */
public class DataSourceSpringImpl extends 
               DriverManagerDataSource
                implements DataSource{
    
    /** Creates a new instance of ImplDataSourceSpring */
    public DataSourceSpringImpl() {
        
    }
    
     public java.sql.Connection getConnection ()
                                        throws java.sql.SQLException{
         return super.getConnection();
     }
    
}
