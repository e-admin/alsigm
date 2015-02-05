/*
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * Especialización de <code>TypeHandlerCallback</code> para persistir y
 * recuperar de base de datos objetos de tipo <code>ValuedEnum</code>.
 * 
 * @author IECISA
 * @see TypeHandlerCallback
 * @see ValuedEnum
 */
public class ValuedEnumTypeHandlerCallback<E extends ValuedEnum> implements
		TypeHandlerCallback {

	/**
	 * Constructor de la clase. Recibe la clase del enumerado y si se quiere
	 * persistir/recuperar como ordinal o cadena de texto.
	 * 
	 * @param aClass
	 *            clase del enumerado a persistir/recuperar
	 * @param aStoreAsOrdinal
	 *            <code>true</code> en caso de querer persistir/recuperar el
	 *            enumerado a partir de su valor ordinal (<code>value</code>) y
	 *            <code>false</code> si se quiere persistir/recuperar a partir
	 *            de su <code>name</code>
	 */
	public ValuedEnumTypeHandlerCallback(Class<E> aClass,
			boolean aStoreAsOrdinal) {
		this.clazz = aClass;
		this.storeAsOrdinal = aStoreAsOrdinal;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getResult(ResultGetter getter) throws SQLException {
		try {
			if (isStoreAsOrdinal()) {
				int ordinal = getter.getInt();
				if (getter.wasNull()) {
					return null;
				}

				return EnumUtils.getEnum(getEnumClass(), ordinal);
			} else {
				String name = getter.getString();
				if (getter.wasNull()) {
					return null;
				}
				return EnumUtils.getEnum(getEnumClass(), name);
			}
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
				setter.setNull(isStoreAsOrdinal() ? Types.INTEGER
						: Types.VARCHAR);
			} else {
				if (isStoreAsOrdinal()) {
					ValuedEnum ve = (ValuedEnum) parameter;
					setter.setInt(ve.getValue());
				} else {
					ValuedEnum e = (ValuedEnum) parameter;
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
	 * entero y <code>false</code> si se recupera/persiste la cadena de
	 * caracteres asociada al enumerado.
	 * 
	 * @return
	 */
	protected boolean isStoreAsOrdinal() {
		return this.storeAsOrdinal;
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
			.getLogger(ValuedEnumTypeHandlerCallback.class);

	/**
	 * Flag que marca si se desea almacenar el enumerado como numero.
	 */
	protected boolean storeAsOrdinal = true;

	/**
	 * Clase del enumerado a persistir / recuperar.
	 */
	protected Class<E> clazz;
}
