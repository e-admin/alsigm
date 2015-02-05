package ieci.tdw.ispac.designer.client.helper;

import ieci.tdw.ispac.designer.client.objetos.Evento;

import java.util.List;

public class Util {

	public  static boolean esCompatible(Integer tipoS, int tipoCampo)
	{
	
		boolean compatible=false;
		if(tipoS!=null){
		int tipoSel=tipoS.intValue();
		if(varDefs.SHORTDECIMAL==tipoSel || varDefs.SHORTINTEGER==tipoSel ||varDefs.SHORTTEXT==tipoSel || varDefs.DATETIME==tipoSel || varDefs.LONGBIN==tipoSel)
			return tipoSel==tipoCampo;
		else if(varDefs.LONGDECIMAL==tipoSel)
			return (tipoSel==tipoCampo  || tipoCampo==varDefs.SHORTDECIMAL);
		else if (varDefs.LONGINTEGER==tipoSel)
			return (tipoSel==tipoCampo  || tipoCampo==varDefs.SHORTINTEGER);
		else if(varDefs.LONGTEXT==tipoSel)
			return (tipoSel==tipoCampo  || tipoCampo==varDefs.SHORTTEXT);
		
		}
		else 
			return true;
		return compatible;
		
	}
	

	public static boolean isDate(String valor){
		
		String dia, mes , anio;
		String [] fecha=valor.split("/");
		if(fecha.length!=3)return false;
		dia=fecha[0];
		if(dia.length()>2 || dia.length()<1)return false;
		mes=fecha[1];
		if(mes.length()>2 || mes.length()<1)return false;
		anio=fecha[2];
		if(anio.length()!=4)return false;
		
		try {
			int mesVal, diaVal;
			 mesVal=diaVal=0;
			 mesVal=Integer.parseInt(mes);
			 diaVal=Integer.parseInt(dia);
			 if(mesVal>12 || mesVal <1)return false;
			 if(diaVal>31 || diaVal<1)return false;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		}
		 
		return true;
	}
	public static boolean valorCompatible(int tipoSel , String valor)
	{
			try {
				if(varDefs.SHORTDECIMAL==tipoSel)
				{
                   char [] array= valor.toCharArray();
                
                   String valor2="";
                 
                   for(int i=0; i<array.length; i++)
                   {
                	   if(array[i]==',')
                		   valor2 +="&";
                	   else if(array[i]=='.')
                		   valor2+="-";
                	   else
                		   valor2+=array[i];
 
                   }
                    
                   String []parteEntera=valor2.split("-");
				
					String []parteDecimal=valor2.split("&");
					if(parteEntera==null || parteEntera.length==0)
					{
						if(parteDecimal==null || parteDecimal.length==0)
							Integer.parseInt(valor);
						    valor.replaceAll(".", "");
						
						
						
					}
					else{
						
						//
							String [] auxi2= parteEntera[parteEntera.length-1].split("&");
							parteEntera[parteEntera.length-1]=auxi2[0];
						//Valido que la parte entera entre cada punto haya 3 digito salvo en el de mas a la izq
						for(int i=0; i< parteEntera.length; i++)
						{
							
							if(parteEntera[i].length()>3)
								return false;
							if(parteEntera[i].length()<3 && i!=0)
								return false;
						}
			
						valor2=valor2.replaceAll("-", "");
						valor2=valor2.replaceAll("&", ".");
				
					}
            
                  
				   Double.parseDouble(valor2);
					
					
				}
				else if(varDefs.SHORTINTEGER==tipoSel){
					Integer.parseInt(valor);
				}
				else if(varDefs.DATETIME==tipoSel){
					return isDate(valor);
				}
				else if(varDefs.LONGBIN==tipoSel){
					Byte.parseByte(valor);
				}
				else if(varDefs.LONGBIN==tipoSel){
					Byte.parseByte(valor);
				}
				else if(varDefs.LONGDECIMAL==tipoSel){
					Float.parseFloat(valor);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				return false;
			}
			return true;
			
				
	}
	
	public static String [] ordenarCondiciones(List allCondiciones, String [] selectedCondiciones){
		
		String [] res = new String [selectedCondiciones.length];
		int ordenados=0;
		for(int i=0; i<allCondiciones.size(); i++)
		{
			String idCondicion=((Evento)allCondiciones.get(i)).getId();
			boolean encontrado=false;
			for(int j=0; j<selectedCondiciones.length && !encontrado; j++)
			{
				if(selectedCondiciones[j].compareTo(idCondicion)==0)
				{
				   encontrado=true;
				   res[ordenados]=idCondicion;
				   ordenados=ordenados + 1;
				}
			}
			
		}
		return res;
		
	}
}
