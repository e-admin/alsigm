package common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.vos.IKeyId;

/**
 * Utilidades de manejo de arrays
 *
 */
public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {
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
         if (length == 1 && array[0] != null)
            result = array[0].toString();
         else
         {
	         StringBuffer buffer = new StringBuffer();
	         buffer.append(array[0]);
	         for (int i=1; i<length; i++)
	            buffer.append(token).append(array[i]);
	         result = buffer.toString();
         }
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

   /**
    * Indica si el array tiene elementos. Tambien comprueba si solo tiene una posición
    * @param array Array de objetos.
    * @return true si el objeto no está vacío.
    */
   public static boolean isNotEmptyOrBlank(String[] array){

	   if( isNotEmpty(array) &&
		  (array.length !=0 && StringUtils.isNotEmpty(array[0]))
		  ){

		  return true;
   		}
		  return false;
   }

   /**
    * Indica si el array tiene elementos. Tambien comprueba si solo tiene una posición
    * @param array Array de objetos.
    * @return true si el objeto no está vacío.
    */
   public static boolean isEmptyOrBlank(String[] array){

	   if( isEmpty(array) ||
		  (array.length == 1 && StringUtils.isEmpty(array[0]))
		  ){

		  return true;
   		}
		  return false;
   }

   /**
    * Indica si el array tiene elementos
    * @param array Array de objetos.
    * @return true si el objeto no está vacío.
    */
   public static boolean isNotEmpty(Object [] array)
   {
       return ( (array != null) && (array.length > 0) );
   }

   public static String[] toString(Object[] object)
   {

	   String[] result=new String[object.length];
	   for(int i=0;i<object.length;i++)
	   {
		   result[i]=(String)object[i];
	   }
	   return result;
   }

   public static int[] IntegerToInt(Object[] array)
   {

	   int[] result=new int[array.length];
	   for(int i=0;i<array.length;i++)
	   {
		   result[i]=((Integer)array[i]).intValue();
	   }
	   return result;
   }

   public static String IntToString(int[] array)
   {

	   String result="";
	   for(int i=0;i<array.length;i++)
	   {
		   result+=String.valueOf(array[i]);
	   }
	   return result;
   }

   /**
    * Obtiene el array de Ids de los elementos de la lista que implementan el interfaz IKeyId
    * @param lista Lista de Elementos que implementan el interfaz IKeyId
    * @return Array de String
    */
   public static String[] getArrayIds(List lista){
	   String[] ids = new String[0];
		if(ListUtils.isNotEmpty(lista)){
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				   Object vo = iterator.next();
				   if (vo instanceof IKeyId) {
					   IKeyId keyId  = (IKeyId) vo;
					   ids = (String[]) ArrayUtils.add(ids, keyId.getId());
				   }
				   if(vo instanceof String){
					   String id = (String) vo;
					   ids = (String[]) ArrayUtils.add(ids, id);
				   }
			}
			   return ids;
		}
		else{
		   return null;
	}
   }

}
