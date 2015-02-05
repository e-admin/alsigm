package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;

import java.util.Iterator;
import java.util.List;

public class OrderUtil {

	public static void ordenar (boolean subir, String campo_orden, IItemCollection itemcol, String [] seleccion, List listPars,List checkChange ) throws ISPACException{
		
		 	
	        for (Iterator iter = itemcol.iterator(); iter.hasNext();) {
				IItem element = (IItem) iter.next();
				listPars.add(element);
				checkChange.add(new Boolean(false));
			}
	        
	        int j=0;
	        if(subir)
	        {
	        	for(j=0; j<seleccion.length;j++)
	        	{
	        		String idValor=seleccion[j];
	        		int pos=0; 
	        		int l=0; 
	        		IItem item=null;
	        		boolean encontrado=false;
	        		for(l=0; l<listPars.size()&&!encontrado; l++){
	        			 item = (IItem) listPars.get(l);
	        			if(item.getString("ID").equals(idValor))
	        			{
	        				pos=l; 
	        				encontrado=true;
	        			}
	        			
	        		}
	        		
	        		if(pos>=1){//La primera no puede subir mas 
	        		
	        			checkChange.set(pos,new Boolean(true));
	        			checkChange.set(pos-1,new Boolean(true));
	        			IItem itemAnt=(IItem)listPars.get(pos-1);
	        			String orden=itemAnt.getString(campo_orden);
	        			itemAnt.set(campo_orden, item.getString(campo_orden));
	        			item.set(campo_orden, orden);
	        			listPars.set(pos, itemAnt);
	        			listPars.set(pos-1,  item);
	        			
	        			
	        		}
	        	}
	        }
	        
	        else{
	        	
	        	
	        	
	        	for(j=seleccion.length-1; j>=0;j--)
	        	{
	        		String idValor=seleccion[j];
	        		int pos=0; 
	        		int l=0; 
	        		IItem item=null;
	        		boolean encontrado=false;
	        		for(l=0; l<listPars.size()&&!encontrado; l++){
	        			 item = (IItem) listPars.get(l);
	        			if(item.getString("ID").equals(idValor))
	        			{
	        				pos=l; 
	        				encontrado=true;
	        			}
	        			
	        		}
	        		
	        		if(pos!=checkChange.size()-1){
	        		
	        			checkChange.set(pos,new Boolean(true));
	        			checkChange.set(pos+1,new Boolean(true));
	        			IItem itemSgt=(IItem)listPars.get(pos+1);
	        			String orden=itemSgt.getString(campo_orden);
	        			itemSgt.set(campo_orden, item.getString(campo_orden));
	        			item.set(campo_orden, orden);
	        			listPars.set(pos, itemSgt);
	        			listPars.set(pos+1,  item);
	        			
	        			
	        		}
	        	}	
	        }
	        
	       
  	        	
	        	
	        
	
	}
}
