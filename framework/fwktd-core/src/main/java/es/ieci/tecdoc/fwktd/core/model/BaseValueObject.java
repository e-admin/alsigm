package es.ieci.tecdoc.fwktd.core.model;

/*
 * 
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * Clase base para la implementación de objetos de datos (
 * <code>ValueObject</code>),
 * 
 * @author IECISA
 */
public abstract class BaseValueObject implements ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508373998996142816L;

	protected transient final Logger logger = LoggerFactory.getLogger(this
			.getClass());

	/**
	 * Factory Method para crear ValueObjects partiendo de una representación en
	 * XML. Se utilizará el formato de XML definido por la herramienta <a
	 * href="http://xstream.codehaus.org">XStream</a>.
	 * 
	 * 
	 * 
	 * @param xml
	 *            el XML que contiene la representación del objeto
	 * @return el objeto creado a partir del XML.
	 */
	public static Object fromXML(String xml) {
		XStream stream = new XStream();
		return stream.fromXML(xml);
	}

	/**
	 * El codigo XML devuelto, se ajusta al formato definido por la librería <a
	 * href="http://xstream.codehaus.org">XStream</a>.
	 * 
	 */
	public String toXML() {
		XStream stream = new XStream();
		return stream.toXML(this);
	}

	@Override
	public String toString() {
		return toXML();
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);

			oout.writeObject(this);

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(out.toByteArray()));

			return in.readObject();
		} catch (Exception e) {
			throw new CloneNotSupportedException("cannot clone class ["
					+ this.getClass().getName() + "] via serialization: "
					+ e.toString());
		}
	}

}