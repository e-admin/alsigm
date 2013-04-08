package ieci.tecdoc.sgm.pe.struts;

import java.io.ByteArrayOutputStream;

public class Util {

	public static String formatearImporte(String pcImporte, int peTamano){
		if((pcImporte == null) || ("".equals(pcImporte))){
			return null;
		}
		if(peTamano <= 0){
			return null;
		}
		String cAux = null;
		if (pcImporte.indexOf(".") != -1)
			cAux  = removeChar(pcImporte, '.');
		else cAux = pcImporte + "00";
		if(cAux.length() > peTamano){
			return null;
		}else{
			int eCeros = peTamano - cAux.length();
			StringBuffer sbAux = new StringBuffer();
			for(int eContador = 0; eContador < eCeros; eContador++){
				sbAux.append("0");
			}
			sbAux.append(cAux);
			return sbAux.toString();
		}
		
	}

	public static String removeChar(String str, char c)
	{

	      byte[] data = str.getBytes();
	      
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      for (int i = 0; i < data.length; i++)
	      {
	         if (data[i] != c)
	            out.write(data[i]);
	      }
	      
	      return(out.toString());
	}

}
