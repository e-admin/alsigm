package es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource;

import javax.sql.DataSource;

/**
 * @author Iecisa
 * @version $Revision$
 *
 * Inteface de obtención del datasource para el MultityEntityDataSoruce
 *
 */
public interface MultiEntityDatasourceHelper {

	DataSource getDatasource();

	String getJndiName();
}
