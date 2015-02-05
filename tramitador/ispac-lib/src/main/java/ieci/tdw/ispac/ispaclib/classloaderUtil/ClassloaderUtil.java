/*
 * Created on 19-abr-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.classloaderUtil;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;

public class ClassloaderUtil
{

	private ClassLoader m_cl = null;

	/**
	 * Constructor por defecto. Usa el classloader del sistema para realizar las
	 * operacion
	 */
	public ClassloaderUtil()
	{
		//m_cl = new ClassLoader()
		//{
		//};
		m_cl = getClass().getClassLoader();
	}

	/**
	 * Constructor que recibe un classloader con el que realizará las operaciones
	 */
	public ClassloaderUtil(ClassLoader cl)
	{
		m_cl = cl;
	}

	/**
	 * Comprueba si una clase es accesible o no por el classloader
	 * @param name nombre de la clase
	 * @return true si la clase es accesible para el classloader; false en caso
	 *         contrario
	 */
	public boolean findClass(String name)
	{
		boolean rc = true;
		try
		{
			m_cl.loadClass(name);
		}
		catch (ClassNotFoundException e)
		{
			rc = false;
		}
		return rc;
	}

	/**
	 * Devuelve una instancia de la clase cuyo nombre es pasado como parametro
	 * @param name nombre de la clase
	 * @return objeto de la clase; null si no se pudo obtener instancia
	 * @exception ISPACException si el classloader no encuentra la clase
	 */
	public Object getInstance(String name) throws ISPACException
	{

		Object result;
		try
		{
			Class c = m_cl.loadClass(name);
			result = c.newInstance();
		}
		catch (Exception e)
		{
			throw new ISPACException ("ClassloaderUtil:getInstance(): Clase:"+ name +" no encontrada", e);
		}
		return result;
	}

	/**
	 * Informa de si una clase implementa o no una determinada interfaz
	 * @param nameClass nombre de la clase
	 * @param nameInterface nombre de la interfaz
	 * @return true si la clase implementa interfaz; false en caso contrario
	 * @exception ISPACException si el classloader no encuentra la clase o no se puede
	 * obtener una instancia de la clase
	 */
	public boolean classImplementsInterface(String nameClass, String nameInterface) throws ISPACException
	{
		Class c = null;
		boolean rc = false;

		try
		{
			c = m_cl.loadClass (nameClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new ISPACException ("ClassloaderUtil:classImplementsInterface(): Clase:"+ nameClass +" no encontrada", e);

		}

		Class[] interfaces = c.getInterfaces();
		for (int i = 0; i < interfaces.length; i++)
			if (interfaces[i].getName().equals(nameInterface))
			{
				rc = true;
				break;
			}

		return rc;
	}

  /**
   * Encuentra un recurso a partir de nombre
   */
  public InputStream getResourceAsStream (String name)
  {
    return m_cl.getResourceAsStream(name);
  }

}
