package ieci.tdw.ispac.designer.client.helper;

import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;


public class Html {
	
	
	public static String addPx(String px , int cuantos)
	{
		
		String res;
		
		int pxi=Integer.parseInt(px.substring(0, px.length()-2));
		pxi+=cuantos;
		res=pxi+"px";
		return res;
	}
	
	 public static String hr(String color)
	    {
	        StringBuffer buffer = new StringBuffer("<hr");

	        if (color != null)
	        {
	            buffer.append(" style=\"color: " + color + ";\"");
	        }

	        buffer.append("/>");
	        return buffer.toString();
	    }
	 
	 public static String imageWithText(String url, String text)
	    {
		 
	
	        return "<div style='white-space: nowrap;'><img border='0' align='top' src='"
	                + url + "'/>&nbsp;" + text + "</div>";
	     
	    }


	 public static void changeLiteralNodo(String literal, Element tbody)
	 {
			

			Element tr=DOM.getChild(tbody, 0);
			Element td=DOM.getChild(tr, 0);
			Element div=DOM.getChild(td, 0);
		
		   DOM.setInnerHTML(div, literal);
	 }
	 public static String label( String text)
	    {
	        return "<div style='white-space: nowrap;'><label>&nbsp;" + text + "</label></div>";
	     
	    }
	 
	 public static boolean contains(String original, String filter) {
		 if(original==null)return false;
		 if(filter==null)return false;
		      if (filter.length() == 0) {
		            return true;
		         }
		       if (filter.length() > original.length()) {
		            return false;
		        }
		       for (int i = 0; i < original.length() - filter.length() + 1; i++) {
		            if (original.charAt(i) == filter.charAt(0)) {
	               boolean matches = true;
		                for (int j = 0; j < filter.length(); j++) {
		                    if (original.charAt(i + j) != filter.charAt(j)) {
	                       matches = false;
	                       break;
		                    }
		               }
		                if (matches) {
		                   return true;
		                }
	           }
		        }
	        return false;
		     }
	 
	 public static void hiddenAllChildren(Element e)
	 {
		 int numChild = DOM.getChildCount(e);
			for (int i = numChild - 1; i >= 0; i--) {
				Element hijo= DOM.getChild(e, i);
				if(DOM.getChildCount(hijo)>0)
					hiddenAllChildren(hijo);
				DOM.setStyleAttribute(hijo, "display", "none");
			}
	 }
	 public static void removeAllChildren(Element e) {
		
		 if(e!=null){
		 int numChild = DOM.getChildCount(e);
				for (int i = numChild - 1; i >= 0; i--) {
				Element hijo= DOM.getChild(e, i);
		
				if(DOM.getChildCount(hijo)>0)
					removeAllChildren(hijo);
				DOM.removeChild(e, hijo);
			}
		 }
		 
		
	 }
	
	 /**
	  * Elimna la imagen que hubiera al lado del nombre de la fase cuyo identificador recibimos como parámetro.
	  * La imagen se ha de eliminar cuando el usuario ha eliminado todos los tramites que tuviera asociados la fase
	  * @param idFase
	  */
	 public static void deletePlus(int idFase){
		 Element div = DOM.getElementById(""+idFase);
		 boolean borrado=false;
		 if(div!=null){
		 if(DOM.getChildCount(div)>0){
			 Element table= DOM.getChild(div, 0);
			 if(DOM.getChildCount(table)>0){
				 Element tbody=DOM.getChild(table, 0);
				 if(DOM.getChildCount(tbody)>0){
					 Element trFase= DOM.getChild(tbody, 0);
					 if(DOM.getChildCount(trFase)>0){
						 Element tdImg =DOM.getChild(trFase, 0);
						 if(tdImg!=null){
							 borrado=true;
							 DOM.removeChild(trFase, tdImg);
						 }
					 }
				 }
		 }
			
		 }
		 
		 }
		     
		 if(!borrado){
			 Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

						"deletePlus",null));
		 }  
			
	 }
	 
	
	
	public static String estela (int x0, int x1, int y0, int y1)
	{

		x0+=15;
		y0+=15;
		y1+=+15;
	

		String res="";
		Element imagen=DOM.createElement("img");
	
		// Si alguna de las dos componentes son iguales, dibujamos una línea recta.
		if (x0 == x1)
		{
		res =res + "<div  style='position:absolute;top:" + y0 + "px;left:" + x0 + "px;height:" + Math.abs(y1-y0) + "px;width:1px;font-size:1px;background:black;z-index:3000;'></div>";
		}
		else
		{
		if (y0 == y1)
		{
		res =res +"<div style='position:absolute;top:" + y0 + "px;left:" + x0 + "px;width:" + Math.abs(x1-x0) + "px;height:1px;font-size:1px;background:black;z-index:3000'></div>";
		}
		else
		{
			
			int inicio, fin;
			if(x0>x1){
				inicio=x1;
				fin=x0;
			}
			else{
				inicio=x0;
				fin=x1;
				}
			
			int cont=0;
			int yanterior=0;
			int tamanio=0;
			int xanterior=0;
			boolean casoa=false;
			if( y0<y1)
				casoa=true;
			
			if(x0<x1){
			for(int x=x0; x<x1; x++)
			{
				int cociente=Math.abs(y1-y0);
				int dividendo=fin-inicio;
				
				float aux= Float.parseFloat(Integer.toString(cociente))/Float.parseFloat(Integer.toString(dividendo)) ;
				int yi=0;
				if(casoa)
					 yi= (int) (y0 + aux*cont);
					else
						yi= (int) (y0-aux*cont);
				tamanio+=1;
				if(yanterior!=0 && yanterior!=yi)
				{
					int dif=Math.abs(yi-yanterior);
					if(yanterior!=Math.min(y0,y1) && dif>1)
					res =res +"<div  style='position:absolute;top:" + yanterior + "px;left:" + xanterior + "px;width:1px;height:"+tamanio+"px;font-size:1px;background:black;z-index:3000'></div>";
					res =res +"<div  style='position:absolute;top:" + yi + "px;left:" + x + "px;width:1px;height:1px;font-size:"+dif+"px;background:black;z-index:3000'></div>";
					tamanio=0;
				}
				xanterior=x;
				yanterior=yi;	
				cont+=1;
		
			}
			
			
			}
			else{
				for(int x=x0; x>=x1; x--)
				{
					int cociente=Math.abs(y1-y0);
					int dividendo=fin-inicio;
					
					float aux= Float.parseFloat(Integer.toString(cociente))/Float.parseFloat(Integer.toString(dividendo)) ;
					int yi=0;
					if(casoa)
					 yi= (int) (y0 + aux*cont);
					else
						yi= (int) (y0-aux*cont);
					tamanio+=1;
					if(yanterior!=0 && yanterior!=yi)
					{
						int dif=Math.abs(yi-yanterior);
						if(yanterior!=Math.min(y0,y1) && dif>1)
						res =res +"<div  style='position:absolute;top:" + yanterior + "px;left:" + xanterior + "px;width:1px;height:"+tamanio+"px;font-size:1px;background:black;z-index:3000'></div>";
						res =res +"<div  style='position:absolute;top:" + yi + "px;left:" + x + "px;width:1px;height:1px;font-size:"+dif+"px;background:black;z-index:3000'></div>";
						tamanio=0;
					}
					xanterior=x;
					yanterior=yi;	
					cont+=1;
			
				}	
				
			}
			
			
			
			
			if(0<(y0-y1) && (y0-y1)<10)
				 DOM.setElementProperty(imagen, "src", "img/flechaVerticalUp.JPG");
				else if(0<(y1-y0)&& (y1-y0)<10)
					 DOM.setElementProperty(imagen, "src", "img/flechaVerticalDown.JPG");
				else if(casoa)
					 DOM.setElementProperty(imagen, "src", "img/flechaHorizontalLeft.JPG");
				else
					DOM.setElementProperty(imagen, "src", "img/flechaHorizontalRight.JPG");
		}
		}
	
		
		return res;
	}
	 
	
	 
}
