/*
 * DataSourceManager.java
 *
 * Created on 29 de junio de 2007, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package es.ieci.tecdoc.isicres.admin.database;

import java.sql.Connection;

import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 *
 * @author X73994NA
 */
public class DataSourceManager {

	public static Connection getConnection(String entidad) throws Exception {
		return BBDDUtils.getConnection(entidad);
	}

}
