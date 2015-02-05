package ieci.tdw.ispac.designer.client.helper;

import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;
import ieci.tdw.ispac.designer.client.objetos.Condition;
import ieci.tdw.ispac.designer.client.objetos.ConditionSimple;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.Operando;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.Window;


public class Validates {
	
	/**
	 * 
	 * @param origen Nodo origen del flujo
	 * @param destino Nodo destino del flujo
	 * @return cierto si ya tenemos un flujo con el mismo origen y destino
	 */
	public static boolean existFlow(int origen, int destino)
	{
		 Iterator iterator = DiagramBuilderExample.flujos.values().iterator();
         boolean encontrado=false;	     
		   while( iterator. hasNext() && !encontrado ){
 
	    	   DrawFlow aux=(DrawFlow) iterator.next();
	    	   if(aux.getIdOrigen()==origen && aux.getIdDestino()==destino)
	    		   encontrado=true;
	    	
	    	 
	    	}
		   
		   return encontrado;
		   
	}
	
	
	/**
	 * 
	 * @param idFase Fase al que se le quiere añadir una transición entrante
	 * @return true si ya existe una transición entrante y false en caso contrario
	 */
	public static boolean existIncomingFlowinNode(int idFase)
	{
		
		Iterator iterator = DiagramBuilderExample.flujos.values().iterator();
        boolean encontrado=false;	     
		   while( iterator. hasNext() && !encontrado ){

	    	   DrawFlow aux=(DrawFlow) iterator.next();
	    	   if(aux.getIdDestino()==idFase)
	    		   encontrado=true;
	    	
	    	 
	    	}
		   
		   return encontrado;
		
	}
	
	
	public  static void deleteAllNodeConnect(int idNode)
	{
		if(idNode!=0){
		Iterator iterator = DiagramBuilderExample.flujos.values().iterator();
        	     
		   while( iterator.hasNext()){

	    	   DrawFlow aux=(DrawFlow) iterator.next();
	    	   if(aux.getIdDestino()==idNode || aux.getIdOrigen()==idNode)
	    		   {
	    		   DiagramBuilderExample.flujos.remove(new String(aux.getIdS()));
	    		   iterator=DiagramBuilderExample.flujos.values().iterator();
	    		   }
	    	
	    	 
	    	}
		   
		} 
	}
	
	/**
	 * 
	 * @param c Objeto de tipo condición con los datos introducidos por el usuario
	 * @param tipoCampoFila Listado donde cada elemento represnta el tipo del campo seleccionado en la fila 
	 * @param valorEntidad array donde cada fila tiene dos posiciones la primera para el operadon 2 y la segunda para el operando3, si tiene true
	 * quiere decir que el usuario ha seleccionado coger los valores de los listbox(entidad / campos) y sino los ha introducido manualmente
	 * @return
	 */

	public static boolean validarCondicion(Condition c , ArrayList tipoCampoFila )
	{
		boolean validado=true;
		
		
		
		if(c.getNombre()==null ||c.getNombre().compareTo("")==0)
		{
			Window.alert(DiagramBuilderExample.mensajeError.campoObligatorio(300, DiagramBuilderExample.mensajes.nombreCondicion()));
			return false;
		}
		else if(c.getCondicionesSimples()==null || c.getCondicionesSimples().size()==0){
			Window.alert(DiagramBuilderExample.mensajeError.campoObligatorio(300, DiagramBuilderExample.mensajes.condition()));
			return false;
				
		}
	
	
		else {
			int parentesisAbiertos=0; 
			int parentesisCerrados=0;
			//Hay que comprobar que se ha rellenado correctamente la condicion sql
			for(int i=0; i<c.getCondicionesSimples().size(); i++)
			{
				ConditionSimple cs=(ConditionSimple) c.getCondicionesSimples().get(i);
				if(i==0 && cs.getAndOr()!=null && (cs.getAndOr().trim()).length()>0){
					Window.alert(DiagramBuilderExample.mensajeError.AndOrNoVacio(300, DiagramBuilderExample.mensajes.andOr(), (i+1)));
					return false;
				}
				if(i!=0){
				if(!(cs.getAndOr().compareTo(DiagramBuilderExample.mensajes.And())==0 || cs.getAndOr().compareTo(DiagramBuilderExample.mensajes.Or())==0))
				{
					Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.operadorLogico(), (i+1)));
					return false;
				}
				}
				if(cs.getParentesisClose()==1)
					parentesisCerrados+=1;
				if(cs.getParentesisOpen()==1)
					parentesisAbiertos+=1;
				//Validacion de campos
		
				
				//Operador
				String operador= cs.getOperador();
				
				if(operador==null ||operador.compareTo("")==0)
				{
					Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.operando(), (i+1)));
					return false;
				}
				
				Operando op1= cs.getOp1();
				Operando op2=cs.getOp2();
				Operando op3 = cs.getOp3();
				int tipoCampo=((Integer)tipoCampoFila.get(i)).intValue();
				if(op1.getNombreEntidad()==null || op1.getNombreEntidad().compareTo("")==0 )
				{
					Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op1(), (i+1)));
					return false;
				}
				else if(op1.getNombreCampo()==null ||op1.getNombreCampo().compareTo("-1")==0)
				{
					Window.alert(DiagramBuilderExample.mensajeError.opCampo(300, DiagramBuilderExample.mensajes.op1(), (i+1)));
					return false;
				}
			
				if(operador!=null && operador.compareTo((DiagramBuilderExample.mensajes.notNull()))!=0 && operador.compareTo((DiagramBuilderExample.mensajes.nulo()))!=0 )
				{
					boolean noEntidad=false;
					if(op2.getNombreEntidad()==null || op2.getNombreEntidad().compareTo("-1")==0)
						noEntidad=true;
					if(noEntidad )	
					{
						
						
						if(op2.getValor()==null ){
						Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op2(), (i+1)));
						return false;
						}
						else if(op2.getValor().compareTo("")==0 ){
							Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op2(), (i+1)));
						return false;
						}
						else if(!Util.valorCompatible(tipoCampo, op2.getValor())){
							
							Window.alert(DiagramBuilderExample.mensajeError.valorIncorrecto(300, DiagramBuilderExample.mensajes.op2(), (i+1)));
							return false;
							
						}
					}
					
					//Compruebo que si ha seleccioando la entidad que haya seleccioando el campo
					if(!noEntidad )
					{
						if(op2.getNombreCampo()==null || op2.getNombreCampo().compareTo("-1")==0)
						{
							Window.alert(DiagramBuilderExample.mensajeError.opCampo(300,  DiagramBuilderExample.mensajes.op2(), (i+1)));
								
							return false;
						}
					}
					 noEntidad=false;
					if(op3.getNombreEntidad()==null || op3.getNombreEntidad().compareTo("")==0)
						noEntidad=true;
					if(noEntidad &&  operador.compareTo(DiagramBuilderExample.mensajes.between())==0)
					{
				
						if(op3.getValor()==null || op3.getValor().compareTo("")==0  ){
						Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op3(), (i+1)));
						return false;
						}
						else if(op3.getValor().compareTo("")==0){
							Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op3(), (i+1)));
						return false;
						}
						else if(!Util.valorCompatible(tipoCampo, op3.getValor())){
							
							Window.alert(DiagramBuilderExample.mensajeError.valorIncorrecto(300, DiagramBuilderExample.mensajes.op3(), (i+1)));
							return false;
							
						}
						}
						else if( operador.compareTo(DiagramBuilderExample.mensajes.between())==0 && op3.getValor()==null && op3.getValor().compareTo("")==0 ){
							Window.alert(DiagramBuilderExample.mensajeError.campoLinea(300, DiagramBuilderExample.mensajes.op3(), (i+1)));
							return false;
						}
						
						//Compruebo que si ha seleccioando la entidad que haya seleccioando el campo
						if(!noEntidad && operador.compareTo(DiagramBuilderExample.mensajes.between())==0 )
						{
							if(op3.getNombreCampo()==null || op3.getNombreCampo().compareTo("-1")==0)
							{
								Window.alert(DiagramBuilderExample.mensajeError.opCampo(300,  DiagramBuilderExample.mensajes.op3(), (i+1)));
								return false;
							}
						}
					}
				
				
				
			}
        
			if(parentesisAbiertos> parentesisCerrados){
				Window.alert(DiagramBuilderExample.mensajeError.parentesisDesequilibradosAbiertos(300));
				return false;
			}
			else if(parentesisCerrados> parentesisAbiertos){
				Window.alert(DiagramBuilderExample.mensajeError.parentesisDesequilibradosCerrados(300));
				return false;
			}
			
		}
		
		return validado;
	}


	
}
