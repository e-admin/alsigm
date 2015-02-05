package common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utilidad para clonar objetos.
 */
public class ObjectCloner
{

	/**
	 * Realiza una copia en profundidad de un objeto.
	 * @param obj Objeto original.
	 * @return Copia del objeto.
	 */
	public static Object deepCopy(Object obj)
	{
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Object newObject = null;
		
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			
			newObject = ois.readObject();
		}
		catch (Exception e)
		{
			// No debería dar error
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			}
			catch (IOException e)
			{}

			try
			{
				if (ois != null)
					ois.close();
			}
			catch (IOException e)
			{}
		}
		
		return newObject;
	}
}
