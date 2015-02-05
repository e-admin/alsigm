package ieci.tdw.ispac.ispacmgr.common.constants;

import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispacmgr.common.Formatters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Componente a traves del que es posible acceder a todas las constantes empleadas en el sistema. 
 * En el arranque de la aplicación se pone accesible en el contexto de aplicación para tener acceso
 * a él desde las páginas jsp mediante las que se implementan las diferentes vistas mediante las que
 * el usuario interactua con la aplicación
 */
public class AppConstants {
   
    public final 
    //contendra las constantes asociadas a cada clase
    HashMap contantsMap = new HashMap();
    
    /**
     * Extrae mediante introspección los campos de una clase declarados como estáticos almacenando
     * en un mapa los pares de valores 'Nombre de campo' - 'Valor'
     * @param source Definición de clase
     * @return Mapa con las constantes definidas en la clase
     */
//   protected Map getConstantsMap(Class source) {
//        HashMap constMap = null;
//        //buscar en el map de constantes asociado a la clase
//        if (contantsMap != null){
//
//            //obtener map de constantes asociado a la clase
//            constMap = (HashMap)contantsMap.get(source.getName());
//            if (constMap == null)
//                constMap = (HashMap) AppUtilConstants.getConstantsMap(source);
// 	      
//            contantsMap.put(source.getName(),constMap);
//        }
//        return constMap;
//    }
    
    
    /**
     * Extrae mediante introspección los campos de una clase y sus predecesoras
     * declarados como estáticos almacenando
     * en un mapa los pares de valores 'Nombre de campo' - 'Valor'
     * @param source Definición de clase
     * @return Mapa con las constantes definidas en la clase
     */
  protected Map getConstantsMap(Class source) {
	  if (source == Object.class)
		  return null;
	  HashMap constMap = null;
	  //buscar en el map de constantes asociado a la clase
	  if (contantsMap != null){
	
	      //obtener map de constantes asociado a la clase
	      constMap = (HashMap)contantsMap.get(source.getName());
	      if (constMap == null)
	          constMap = (HashMap) AppUtilConstants.getConstantsMap(source);
	     
	      contantsMap.put(source.getName(),constMap);
	  }
	  Map returnMap = getConstantsMap(source.getSuperclass());
	  if (returnMap != null)
		  constMap.putAll(returnMap);
	  return  constMap;
}    
    
//   /**
//    * Extrae mediante introspección los campos declarados como estáticos en un conjunto de clases
//    * almacenando en un mapa los pares de valores 'Nombre de campo' - 'Valor'
//    * @param sources Conjunto de clases
//    * @return Mapa con las constantes definidas en la clase
//    */
//    private Map getConstantsMap(Class[] sources) {
//       int nClasses = sources.length;
//       Map constMap = new HashMap();
//       for (int i=0; i<nClasses; i++)
//          constMap.putAll(getConstantsMap(sources[i]));
//       return constMap;
//    }

    /**
     * Obtiene las constantes generales empleadas en el módulo de transferencias
     * @return Mapa con las constantes definidas en la clase {@link TransferenciasConstants}
     */
//   public Map getTransferencias() {
//       return getConstantsMap(TransferenciasConstants.class);
//   }

   /**
    * Obtiene las constantes comunes a los diferentes módulos del sistema
    * @return Mapa con las constantes definidas en la clase {@link Constants}
    */
//   public Map getCommon(){
//       return getConstantsMap(Constants.class);
//   }
   

   public long getUniqueId(){
       return Double.doubleToRawLongBits(Math.random() * new Date().getTime());
   }
   
   public Map getFormatters(){
	   return Formatters.getInstance().getMap();
	   
   }

   public Map getActions(){
	   return getConstantsMap(ActionsConstants.class);
   }
   
   public Map getSignStates(){
	   return getConstantsMap(SignStatesConstants.class);
   }
   
   public Map getDocumentLockStates(){
	   return getConstantsMap(DocumentLockStates.class);
   }
   
   public Map getNotifyStatesConstants(){
	   return getConstantsMap(NotifyStatesConstants.class);
   }
 
   public Map getEntities(){
	   return getConstantsMap(SpacEntities.class);
   }
   
   public Map getConfig(){
	   try {
		   return ISPACConfiguration.getInstance();
	   } catch (ISPACException e) {
		   return new HashMap();
	   }
   }
}
