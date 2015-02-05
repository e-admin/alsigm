package ieci.tdw.ispac.ispaclib.validations;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements IValidator {

	
	
	public EmailValidator() {
		super();
	}

	public boolean validate(Object objeto) {
	  
		if(objeto==null || StringUtils.isBlank(objeto.toString())){
			return false;
		}
		String email=objeto.toString();
		
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher m = p.matcher(email);

	    boolean matchFound = m.matches();

	    if (matchFound){
	        return true;
	      }

		return false;
		
	}
	

}
