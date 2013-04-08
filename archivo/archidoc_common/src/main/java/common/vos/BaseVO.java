package common.vos;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import es.archigest.framework.core.vo.ArchigestValueObject;


/**
 * Clase base para los VOs.
 */
public class BaseVO implements ArchigestValueObject
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     */
    public BaseVO()
    {
    }


	/**
	 * Obtiene una representación XML del objeto.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);
		final String classname = getJavaBeanName();

		// Tag de inicio
		xml.append(tabs + "<");
		xml.append(classname);
		xml.append(">");
		xml.append(Constants.NEWLINE);

		// Atributos
		Field [] atributos = this.getClass().getDeclaredFields();
		for (int i = 0; (atributos != null) && (i < atributos.length); i++)
		{
			// Tag de inicio del atributo
			xml.append(tabs + "  <");
			xml.append(atributos[i].getName());
			xml.append(">");

			// Valor del atributo
			xml.append(getAttributeValue(atributos[i].getName()));

			// Tag de cierre del atributo
			xml.append("</");
			xml.append(atributos[i].getName());
			xml.append(">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</");
		xml.append(classname);
		xml.append(">");

		return xml.toString();
	}


	/**
	 * Devuelve el nombre del JavaBean.
	 * @return Nombre del JavaBean.
	 */
	private String getJavaBeanName()
	{
		String className = this.getClass().getName();
		int ix = className.lastIndexOf(".");
		if (ix > 0)
			className = className.substring(ix + 1);
		return className;
	}


	/**
	 * Devuelve el objeto atributo a partir del nombre del atributo.
	 * @param atributo Nombre del atributo.
	 * @return Objeto atributo.
	 */
	private Object getAttributeValue(String atributo)
	{
		Object valor = null;

		try
		{
			if ( (atributo != null) && (atributo.length() > 0) )
			{
				StringBuffer methodName = new StringBuffer();
				if (atributo.length() == 1)
					methodName.append("get").append(atributo.toUpperCase());
				else
					methodName.append("get").append(atributo.substring(0,1).toUpperCase()).append(atributo.substring(1));

				Method metodo = this.getClass().getMethod(methodName.toString(), null);
				valor = metodo.invoke(this, null);
			}
		}
		catch(Exception e)
		{
			valor = null;
		}

		return (valor != null ? valor : "");
	}


	/**
	 * Obtiene una representación del objeto.
	 * @return Representación del objeto.
	 */
    public String toString()
    {
        return toXML(0);
    }

	/**
	 * Obtiene una representación XML del objeto.
	 * @return Representación del objeto.
	 */
    public String toXML()
    {
        return toXML(0);
    }
}
