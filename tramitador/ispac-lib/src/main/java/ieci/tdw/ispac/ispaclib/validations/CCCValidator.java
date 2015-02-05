package ieci.tdw.ispac.ispaclib.validations;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

/**
 * Clase que valida el Código Cuenta Cliente 
 * 
 * @author 66423658
 *
 */
public class CCCValidator implements IValidator {

	public CCCValidator() {
		super();
	
	}

	public boolean validate(Object objeto) {
	
		if(objeto==null || objeto.toString().length()!=20 || !StringUtils.isNumeric(objeto.toString())){
			return false;
		}
		String ccc=objeto.toString();
		if(ccc.equals("00000000000000000000")){
			return true;
		}
		  if (!(obtenerDigito("00" + ccc.substring(0, 4) + ccc.substring(4, 8)) ==Integer.parseInt(ccc.substring(8,9))) || 
            !(obtenerDigito(ccc.substring(10,20)) ==Integer.parseInt(ccc.substring(9, 10))))
             return false;
	    else
          return true;
      
	
	}
	
	
	protected int  obtenerDigito(String valor){
		 int []valores= {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
		  int control = 0;
		  for (int i=0; i<=9; i++){
		    control += Integer.parseInt(valor.substring(i, i+1)) * valores[i];
		  }
		  control = 11 - (control % 11);
		  if (control == 11) control = 0;
		  else if (control == 10) control = 1;
		return control;
		 
		}
}
