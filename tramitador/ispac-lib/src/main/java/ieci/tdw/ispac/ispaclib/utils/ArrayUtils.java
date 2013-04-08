package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {

	public static int [] convertToInt(String [] strArray) 
			throws ISPACException {
		
		int [] intArray = null;
		
		if (strArray != null) {
			intArray = new int[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				intArray[i] = TypeConverter.parseInt(strArray[i]);
			}
		}
		
		return intArray;
	}
	   public static final String[] EMPTY_STRING_ARRAY = new String[0];
	   public static final List EMPTY_LIST = new ArrayList(0);

	   private static Class getCommonType(Class classA, Class classB) {
	      if (classA == classB)
	         return classA;
	      else if (classA.isAssignableFrom(classB))
	         return classA;
	      else if (classB.isAssignableFrom(classA))
	         return classB;
	      else
	         return null;
	   }
	   
	   /**
	    * Crea un array mediante la concatenación de otros dos
	    */
	   public static Object[] concat(Object[] arrayA, Object[] arrayB) {
	      if (arrayB == null) return arrayA;
	      if (arrayA == null) return arrayB;
	      Class commonType = getCommonType(
	            arrayA.getClass().getComponentType(), 
	            arrayB.getClass().getComponentType());
	      if (commonType == null) return null;
	      int lenArrayA = arrayA!=null?arrayA.length:0;
	      int lenArrayB = arrayB!=null?arrayB.length:0;
	      int size = lenArrayA + lenArrayB;
	      Object result = Array.newInstance(commonType, size);
	      System.arraycopy(arrayA, 0, result, 0, lenArrayA);
	      System.arraycopy(arrayB, 0, result, lenArrayA, lenArrayB);
	      return (Object[])result;
	   }
	   
	   /**
	    * Construye una cadena resultado de la concatenacion de los elementos de un array separados 
	    * por una determinada cadena de caracteres 
	    */
	   public static String join(Object[] array, String token) {
	      String result = "";
	      if ( (array != null) && (array.length > 0) ) {
	         int length = array.length;
	         if (length == 1) 
	            result = array[0].toString();
	         StringBuffer buffer = new StringBuffer();
	         buffer.append(array[0]);
	         for (int i=1; i<length; i++)
	            buffer.append(token).append(array[i]);
	         result = buffer.toString();
	      }
	      return result;
	   }

	   /**
	    * Construye una cadena resultado de la concatenacion de los elementos de un array 
	    * de enteros separados por una determinada cadena de caracteres 
	    */
	   public static String join(int[] array, String token) {
	      String result = "";
	      if ( (array != null) && (array.length > 0) ) {
	         int length = array.length;
	         if (length == 1) 
	            result = String.valueOf(array[0]);
	         StringBuffer buffer = new StringBuffer();
	         buffer.append(array[0]);
	         for (int i=1; i<length; i++)
	            buffer.append(token).append(array[i]);
	         result = buffer.toString();
	      }
	      return result;
	   }

	   
	   /**
	    * Indica si el array es nulo o está vacío.
	    * @param array Array de objetos.
	    * @return true si el objeto está vacío.
	    */
	   public static boolean isEmpty(Object [] array)
	   {
	       return ( (array == null) || (array.length == 0) );
	   }
	   
	   public static String[] getArray(String regex, String delim){
		   if (!StringUtils.isBlank(regex)){
			   String[] array = regex.split(delim);
			   return array;
		   }
		   return new String[]{};
	   }
	   
	   public static String[] toStringArray(Object[] array){
		   if (array == null)
			   return null;
		   
		   String[] _return = new String[array.length];
		   for (int i = 0; i < array.length; i++) {
			   if (array[i] != null)
				   _return[i] = array[i].toString();
			   else
				   _return[i] = (String)array[i];
		   }
		   return _return;
	   }
}
