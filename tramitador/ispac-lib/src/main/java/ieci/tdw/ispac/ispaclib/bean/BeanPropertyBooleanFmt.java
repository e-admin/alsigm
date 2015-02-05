package ieci.tdw.ispac.ispaclib.bean;

public class BeanPropertyBooleanFmt extends BeanPropertyFmt {

	public BeanPropertyBooleanFmt ()
	{
		super();
	}

	public Object format(Object value)
	{
		if ((value != null) &&
			(value.toString().equals("1"))) {
		    
			return "true";
		}
		
		return "false";
	}
	
}
