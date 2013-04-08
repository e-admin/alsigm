package es.ieci.tecdoc.fwktd.time.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.time.TimeService;
import es.ieci.tecdoc.fwktd.time.exception.TimeException;

/**
 * @author Iecisa
 * @version $Revision$
 * Implementación del servicio de tiempos para obtener la hora actual de una
 */
public class JdbcTimeServiceImpl implements TimeService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcTimeServiceImpl.class);

	/**
	 * Fuentes de datos a utilizar
	 */
	private DataSource dataSource;

	/**
	 * Sentencia a ejecutar en la fuentes de datos según el tipo de base de datos
	 */
	protected String query;

	/**
	 * @param dataSource Fuentes de datos
	 */
	public JdbcTimeServiceImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.time.TimeService#getCurrentDate()
	 */
	public Date getCurrentDate() throws TimeException {
		try{
			String query = getQuery();
			Assert.isTrue(query!=null);
			if (logger.isDebugEnabled()){
				logger.debug("Obteniedo una conexion del dataSource: "+dataSource.toString());
			}
			Connection cnt = dataSource.getConnection();
			if (logger.isDebugEnabled()){
				logger.debug("Obteniedo fecha actual de la base de datos con la consulta: '" + query + "'");
			}
			ResultSet rs = cnt.createStatement().executeQuery(query);
			if (rs.next()){
				Timestamp timeStamp = rs.getTimestamp(1);
				Date date = new Date(timeStamp.getTime());
				return date;
			}
		}catch(Exception e){
			throw new TimeException(e);
		}
		return null;
	}

	public String getQuery(){
		return query;
	}
	public void setQuery(String query){
		this.query = query;
	}


}
