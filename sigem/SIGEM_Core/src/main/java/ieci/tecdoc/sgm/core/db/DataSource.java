/*
 * ImplDataSource.java
 *
 * Created on 2 de julio de 2007, 10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.core.db;

/**
 *
 * @author X73994NA
 */
public interface DataSource {
    
    public java.sql.Connection getConnection () throws java.sql.SQLException;
}
