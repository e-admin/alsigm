/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import es.ieci.tecdoc.fwktd.core.model.LabelValue;

/**
 * 
 * @author IECISA
 * 
 */
public class LookupDao {

	/**
	 * Construtor del DAO.
	 * 
	 * @param dataSource
	 *            dataSource que utilizará el DAO para comunicarse con la base
	 *            de datos
	 * @param tableName
	 *            nombre de la tabla que contiene los datos (
	 *            <i>clave=valor</i>)
	 * @param keyColumn
	 *            nombre de la columna de la cual se obtendrán las claves
	 * @param valueColumn
	 *            nombre de la columna de la que se extraerán los valores
	 */
	public LookupDao(DataSource dataSource, String tableName, String keyColumn,
			String valueColumn) {

		this.dataSource = dataSource;
		this.tableName = tableName;
		this.valueColumn = valueColumn;
		this.keyColumn = keyColumn;

		// Validaciones
		if (dataSource == null) {
			throw new IllegalStateException(
					"Intentando ejecutar busqueda de propiedades, en tabla: "
							+ tableName + "," + "con datasource null");
		}

		this.jdbcTemplate = new JdbcTemplate();
		this.jdbcTemplate.setDataSource(dataSource);

		logger
				.debug(
						"PropertiesDAO creado para la tabla: {}, columna de clave: {} y columna de propiedades: {}",
						new String[] { tableName, keyColumn, valueColumn });
	}

	/**
	 * Obtiene una lista de todos los pares <i>value=label </i> que contiene la
	 * tabla. <br/>
	 * El resultado se devolverá como una lista de beans de tipo
	 * <code>PropertyBean</code>.
	 * 
	 * @return lista de <code>PropertyBean</code> que contiene los pares
	 *         <i>value=label </i> de la tabla
	 */
	public List<LabelValue> getAllProperties() {
		return getProperties(null);
	}

	/**
	 * Obtiene una lista de todos los pares <i>clave=valor</i> que se ajusten a
	 * una determinada condición. <br/>
	 * El resultado se devolverá como una lista de beans de tipo
	 * <code>PropertyBean</code>.
	 * 
	 * 
	 * @param whereCondition
	 *            la condición where a adjuntar a la consulta de busqueda
	 * 
	 * @return lista de <code>PropertyBean</code> que contiene los pares
	 *         <i>clave=valor</i>
	 */
	public List<LabelValue> getProperties(String whereCondition) {

		String where = StringUtils.EMPTY;
		if (whereCondition != null && !StringUtils.EMPTY.equals(whereCondition)) {
			where = "WHERE " + whereCondition;
		}

		String query = new MessageFormat(selectAllQuery).format(new Object[] {
				keyColumn, valueColumn, tableName, where, keyColumn });

		logger.debug(query);

		PropertiesRowMapper mapper = new PropertiesRowMapper();
		return jdbcTemplate.query(query, mapper);

	}

	/**
	 * Busca en la tabla el "valor" que tiene como clave un <code>key</code>
	 * determinado.
	 * 
	 * @param key
	 *            clave asociada al "value" que se quiere encontrar
	 * @return "value" identificado por el <code>key</code> que se especifica
	 *         como parámetro
	 */
	public LabelValue findProperty(Object key) {

		if (key == null) {
			logger
					.warn("Se está intentando buscar una property con una clave nula.");
			return null;
		}

		String query = new MessageFormat(selectWhereQuery).format(new Object[] {
				valueColumn, tableName, keyColumn });

		List<Map<String, Object>> list = jdbcTemplate.queryForList(query,
				new Object[] { key });

		LabelValue result = new LabelValue();
		if (list == null || list.size() == 0) {
			logger.warn("No se ha encontrado property con id: " + key);
			return result;
		}

		Map<String, Object> r = list.get(0);
		result.setLabel(key.toString());
		result.setValue((String) r.get(valueColumn));
		return result;
	}

	/**
	 * Introduce en la tabla un par <i>clave=valor</i>
	 * 
	 * @param key
	 *            "clave" a introducir en la tabla
	 * @param value
	 *            "value" a introducir en la tabla
	 */
	public void insertProperty(String key, String value) {

		String query = new MessageFormat(insertQuery).format(new Object[] {
				tableName, keyColumn, valueColumn });

		int res = jdbcTemplate.update(query, new Object[] { key, value });

		if (logger.isDebugEnabled()) {
			if (res != 1) {
				logger.warn("Al intentar insertar el par (" + key + "," + value
						+ ") en al tabla " + tableName + " se han modificado "
						+ res + " filas.");
			}
		}

	}

	/**
	 * Actualiza, en la base de datos, el <code>valor</code> correspondiente al
	 * <code>key</code> especificado.
	 * 
	 * @param key
	 *            clave cuyo valor se va a actualizar
	 * @param value
	 *            nuevo valor a asignar
	 */
	public void updateProperty(String key, String value) {

		String query = new MessageFormat(updateQuery).format(new Object[] {
				tableName, valueColumn, keyColumn });

		int res = jdbcTemplate.update(query, new Object[] { value, key });

		if (logger.isDebugEnabled()) {
			if (res != 1) {
				logger.warn("Al intentar actualizar el par (" + key + ","
						+ value + ") en al tabla " + tableName
						+ " se han modificado " + res + " filas.");
			}
		}

	}

	/**
	 * Elimina todos los pares <i>clave=valor</i> de la tabla.
	 */
	public void deleteAllProperties() {
		String query = new MessageFormat(deletellAQuery)
				.format(new Object[] { tableName });

		jdbcTemplate.execute(query);
	}

	/**
	 * Elimina el par <i>clave=valor</i> identificado por la <code>clave</code>
	 * que se indica como parámetro.
	 * 
	 * @param key
	 *            el identificador del par <i>clave=valor</i> a eliminar.
	 */
	public void deleteProperty(String key) {

		String query = new MessageFormat(deleteWhereQuery).format(new Object[] {
				tableName, keyColumn });

		int res = jdbcTemplate.update(query, new Object[] { key });

		if (logger.isDebugEnabled()) {
			if (res > 1) {
				logger
						.warn(
								"Al intentar eliminar la property: {} en la tabla {} se han borrado {} filas.",
								new Object[] { key, tableName, res });
			}
		}
	}

	/**
	 * Obtiene una lista de pares {value, label} y los vuelca sobre una
	 * collection de tipo Properties.
	 * 
	 * @return coleccion que contiene todos los pares {label, value}
	 * @see java.util.Properties
	 */
	public Properties getProperties() {

		Properties props = new Properties();
		List<LabelValue> l = getAllProperties();
		Iterator<LabelValue> it = l.iterator();
		while (it.hasNext()) {
			LabelValue pb = it.next();
			props.setProperty(pb.getLabel(), pb.getValue().toString());
		}
		return props;
	}

	// Members
	/**
	 * Esqueleto para la consulta SQL que permitirá obtener un listado de todos
	 * los pares <i>clave=valor</i> de la tabla. La consulta se ordena por un
	 * campo que, habitualmente se forzará a que sea el índice de la consulta.
	 */
	protected final static String selectAllQuery = "SELECT {0},{1} FROM {2} {3} ORDER BY {4}";

	/**
	 * Esqueleto para la consulta SQL que permitirá obtener el "valor" de una
	 * "clave" concreta.
	 */
	protected final static String selectWhereQuery = "SELECT {0} FROM {1} WHERE {2}=?";

	/**
	 * Esqueleto para la consulta SQL que permitirá introducir un par
	 * <i>clave=valor</i> en la tabla.
	 */
	protected final static String insertQuery = "INSERT INTO {0} ({1},{2}) VALUES (?,?)";

	/**
	 * Esqueleto para la consulta que permitirá actualizar el "valor" de una
	 * "clave" dada.
	 */
	protected final static String updateQuery = "UPDATE {0} SET {1}=? WHERE {2}=?";

	/**
	 * Esqueleto para la consulta que permitirá vaciar la tabla de pares
	 * <i>clave=valor</i>.
	 */
	protected final static String deletellAQuery = "DELETE FROM {0}";

	/**
	 * Esqueleto que permitirá eliminar un par <i>clave=valor</i>, identificado
	 * por su campo "clave".
	 */
	protected final static String deleteWhereQuery = "DELETE FROM {0} WHERE {1}=?";

	/**
	 * Logger de la clase.
	 */
	protected static Logger logger = LoggerFactory.getLogger(LookupDao.class);

	/**
	 * DataSource que permitirá acceder a la base de datos para ejecutar las
	 * consultas.
	 */
	protected DataSource dataSource;

	/**
	 * Plantilla JDBC de Spring que ejecutará las sentencias SQL
	 * 
	 */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * Contiene el nombre de la tabla donde se almacenan los pares
	 * <i>clave=valor</i>.
	 */
	protected String tableName;

	/**
	 * Nombre del campo que contiene las claves
	 */
	protected String keyColumn;

	/**
	 * Nombre del campo que contiene los valores
	 */
	protected String valueColumn;
}
