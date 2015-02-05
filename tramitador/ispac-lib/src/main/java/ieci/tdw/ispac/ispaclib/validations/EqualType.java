package ieci.tdw.ispac.ispaclib.validations;

import ieci.tdw.ispac.ispaclib.db.InternalDataType;

import java.util.HashMap;
import java.util.Map;

public class EqualType {
	
	
   public static Map tipoEquivalente= new HashMap();

   static{
	
	tipoEquivalente.put(new Integer(InternalDataType.NIF.getId()), new Integer(InternalDataType.SHORT_TEXT.getId()));
	tipoEquivalente.put(new Integer(InternalDataType.CIF.getId()), new Integer(InternalDataType.SHORT_TEXT.getId()));
	tipoEquivalente.put(new Integer(InternalDataType.NIE.getId()), new Integer(InternalDataType.SHORT_TEXT.getId()));
	tipoEquivalente.put(new Integer(InternalDataType.EMAIL.getId()), new Integer(InternalDataType.SHORT_TEXT.getId()));
	tipoEquivalente.put(new Integer(InternalDataType.CCC.getId()), new Integer(InternalDataType.SHORT_TEXT.getId()));
	
}

public static Integer getDataTypeEquivalente(int dataType){
	
  if( tipoEquivalente.containsKey(new Integer(dataType)))
   {
	   return ((Integer)tipoEquivalente.get(new Integer(dataType)));
   }
  
 
   return new Integer(dataType);
	
}


public static boolean isNotTypePrimitive(int dataType)
{
	return (tipoEquivalente.containsKey(new Integer(dataType)));
}

  
  
	
	

}
