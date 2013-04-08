package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Especialización de <code>TypeHandlerCallback</code> para persistir y
 * recuperar de base de datos objetos de tipo <code>StringValuedEnum</code>.
 *
 * @author IECISA
 * @see TypeHandlerCallback
 * @see StringValuedEnum
 */
public class StringValuedEnumTypeHandlerCallback<E extends StringValuedEnum> implements
		TypeHandlerCallback {

	/**
	 * Constructor de la clase. Recibe la clase del enumerado y si se quiere
	 * persistir/recuperar como ordinal o cadena de texto.
	 *
	 * @param aClass
	 *            clase del enumerado a persistir/recuperar
	 * @param aStoreValue
	 *            <code>true</code> en caso de querer persistir/recuperar el
	 *            enumerado a partir de su valor (<code>value</code>) y
	 *            <code>false</code> si se quiere persistir/recuperar a partir
	 *            de su <code>name</code>
	 */
	public StringValuedEnumTypeHandlerCallback(Class<E> aClass,
			boolean aStoreValue) {
		this.clazz = aClass;
		this.storeValue = aStoreValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getResult(ResultGetter getter) throws SQLException {
		try {
			String value = getter.getString();
			if (getter.wasNull()) {
				return null;
			}
			return (StringValuedEnum) StringValuedEnum.getEnum(getEnumClass(), value);
		} catch (Exception e) {
			logger.error(
					"No se ha podido recuperar el enumerado {} debido a {}",
					getter.getObject(), e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		try {
			if (null == parameter) {
				setter.setNull(Types.VARCHAR);
			} else {
				if (isStoreValue()) {
					StringValuedEnum ve = (StringValuedEnum) parameter;
					setter.setString(ve.getValue());
				} else {
					StringValuedEnum e = (StringValuedEnum) parameter;
					setter.setString(e.getName());
				}
			}
		} catch (Exception e) {
			logger.error(
					"No se ha podido persistir el enumerado {} debido a {}",
					parameter, e.getMessage());
			throw new SQLException(e.getMessage());
		}
	}

	public Object valueOf(String s) {
		return s;
	}

	/**
	 * Devuelve <code>true</code> si el enumerado se recupera/persiste como
	 * valor y <code>false</code> si se recupera/persiste la cadena de
	 * caracteres asociada al enumerado.
	 *
	 * @return
	 */
	protected boolean isStoreValue() {
		return this.storeValue;
	}

	/**
	 * Devuelve la clase del enumerado gestionado por este callback.
	 *
	 * @return la clase del enumerado gestionado por este callback
	 */
	protected Class<E> getEnumClass() {
		return this.clazz;
	}

	// Members

	protected Logger logger = LoggerFactory
			.getLogger(StringValuedEnumTypeHandlerCallback.class);

	/**
	 * Flag que marca si se desea almacenar el valor del enumerado.
	 */
	protected boolean storeValue = true;

	/**
	 * Clase del enumerado a persistir / recuperar.
	 */
	protected Class<E> clazz;
}
